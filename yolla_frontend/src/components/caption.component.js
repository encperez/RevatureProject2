import React, { Component } from 'react';
import axios from 'axios';
import GlobalContext from './global.context'
import { isThisTypeNode } from 'typescript';
// import CaptionHighlight from './captionhighlight.component';
//xml2js module needed

const PHRASE_WORD_LEN = 5 // number of words shown in the select dropdown for context
const MIN_WORD_LEN = 4    // min length of word to make searchable
const MAX_WORD_LEN = 10   // max length ...
const UPDATE_CAPTION_MS = 250 //
//const videoId = 'zenMEj0cAC4'// cWDJoK8zw58 // zenMEj0cAC4

/*
https://youtu.be/F-p_7XaEC84     kurbernetes
https://youtu.be/bt90VCye6Vk     korean
https://youtu.be/RSIstPUiEjY     docker
https://youtu.be/69ngtfAW3Og     yourname        en-GB, 0   
https://youtu.be/zenMEj0cAC4     turbochargers   en, 0
https://youtu.be/tuJqH3AV0e8     microservices
https://youtu.be/HLWgVpmo1e0     podcast
*/

export default class Caption extends Component {
    static contextType = GlobalContext
    constructor(props) {
        super(props)
        //console.log(this);
        this.state = {
            line: '',
            timestamp: 0,
            searchTerm: '',
            captions: {},
            searchTable: {},
            player: {},
            updateIntervalId: null
        }
        this.getCaptions = this.getCaptions.bind(this)
        this.getCaptionLine = this.getCaptionLine.bind(this)
        this.getDropdown = this.getDropdown.bind(this)
        this.handleSelect = this.handleSelect.bind(this)
        this.onPlayerStateChange = this.onPlayerStateChange.bind(this)
        this.onPlayerReady = this.onPlayerReady.bind(this)
        this.onWordChangeEvt = this.onWordChangeEvt.bind(this)
    }

    componentDidMount = () => {
        if (typeof (window.YT) == 'undefined' || typeof (window.YT.Player) == 'undefined')
            new Promise((resolve) => {
                const tag = document.createElement('script')
                tag.src = 'https://www.youtube.com/iframe_api'
                const firstScriptTag = document.getElementsByTagName('script')[0]
                firstScriptTag.parentNode.insertBefore(tag, firstScriptTag)
                window.onYouTubeIframeAPIReady = () => {
                    resolve(window.YT)

                    // find the loaded player
                    // NOTE: the iframe player must allow js access with http://...?enablejsapi=1
                    let ytPlayer = new window.YT.Player('player',
                        {
                            playerVars: {
                                "enablejsapi":1,
                                "origin":document.domain,
                                "rel":0
                            },
                            events: {
                                'onStateChange': this.onPlayerStateChange,
                                'onReady': this.onPlayerReady,
                                'onApiChange': this.onApiChange
                            }
                        })
                    this.setState({ player: ytPlayer })
                    this.context.setPlayer(ytPlayer)
                    this.context.setVideoIdCb(this.onPlayerReady)
                    
                }
            })

        this.context.subscribeWordChange(this.onWordChangeEvt)
    }

    onWordChangeEvt = () => {
        this.getDropdown()
    }

    onPlayerReady = () => {
        //console.log('ready')

        // setTimeout(() => {
        //     console.log(this.context.videoId)
        //     // console.log(this.context.url)
        // }, 1)

          let vdata = this.state.player.getVideoData()
          let id = this.context.videoId || vdata.video_id;

        //console.log(id)

        // const { setUrl } = this.context
        // setUrl(id)
         this.getCaptions(id)


    }

    onAPiChange = () => {
        console.log('change')
    }

    // @todo could manually parse the xml first time through so json conversion is eliminated
    getCaptions = async (videoId) => {
        try {
            let data = await axios.get(`http://video.google.com/timedtext?type=track&id=0&lang=en&v=${videoId}`);
            if(data.data === "")
                data = await axios.get(`http://video.google.com/timedtext?type=track&id=0&lang=en-GB&v=${videoId}`);
            if(data.data === "") {
                this.setState({ captions: {}, searchTable: {} })
                throw 'unable to load'
            }

            //convert xml to json, then parse into lookup table
            let xml2js = require('xml2js')
            let parser = new xml2js.Parser({emptyTag: ' '})
            const parseString = parser.parseString;
            let captions = {}
            let lookup = {}

            parseString(data.data, function (err, result) {
                result.transcript.text.map(entry => {
                    // time, line
                    let time = Math.floor(Number(entry.$.start))
                    //let line = entry._ // remove html code
                    let line = entry._.replaceAll('\n', ' ').replaceAll('&#39;', "'").replaceAll('&quot;', '"').replaceAll('&apos;', "'") // remove html code

                    captions[time] = line

                    // build time lookup with lowercase words
                    let regexWord = `^[-a-zA-Z0-9]{${MIN_WORD_LEN},${MAX_WORD_LEN}}$`
                    const words = line.split(" ")
                    words.forEach((word, index) => {
                        //lowercase, remove punctuation, then match regex
                        let w = word.toLowerCase().match(regexWord)
                        //let w = word.toLowerCase().replace(/[\p{P}$+<=>^`|~]/gu, '').match(regexWord)
                        if (w === null)
                            return

                        // include a following phrase for word context  
                        const lastIndex = words.length
                        let startIndex = index;
                        let endIndex = Math.min(startIndex + PHRASE_WORD_LEN, lastIndex)

                        if (endIndex - startIndex < PHRASE_WORD_LEN) {
                            startIndex = Math.max(0, endIndex - PHRASE_WORD_LEN)
                        }

                        let phrase = words.slice(startIndex, endIndex).join(" ")

                        // create word entry
                        if (!lookup.hasOwnProperty(w))
                            lookup[w] = []

                        // create timestamp
                        let stampPhrase = lookup[w]
                        let foundIndex = -1

                        //use every instead of forEach so we can short circuit
                        stampPhrase.every((item, index) => {
                            if (item.time === time) {
                                foundIndex = index
                                return false
                            }
                            return true
                        })

                        if (foundIndex >= 0)
                            stampPhrase[foundIndex].phrase += ', ' + phrase
                        else
                            stampPhrase.push({ time, phrase });
                    })

                    return null;
                })
            })
            this.setState({ captions: captions, searchTable: lookup })
              //console.log(this.state.searchTable)
            //  console.log(this.state.captions)
            this.getCaptionLine(this.state.timestamp)
        } catch (err) {
            console.log(err)
        }
    }

    // ensure captions is loaded before lookup
    getCaptionLine = (time) => {
        let caption = ''
        for (const [key, value] of Object.entries(this.state.captions)) {
            if (time >= key) {
                caption = value
            } else {
                break
            }
        }
        this.setState({ line: caption })
    }

    handleSelect = (event) => {
        const {value} = event.target
        if (!isNaN(value)) {
            this.state.player.seekTo(value)
        }
    }

    updateCaptions = () => {
        let time = this.state.player.getCurrentTime()
        const captionLine = this.getCaptionLine(time)

        //@todo more efficient tracking 
        if (captionLine !== this.state.line)
            this.setState({ line: captionLine })
    }

    onPlayerStateChange = (event) => {
        if (event.data === window.YT.PlayerState.PLAYING) {
            let intervalId = setInterval(() => { this.updateCaptions() }, UPDATE_CAPTION_MS)
            this.setState({ updateIntervalId: intervalId })
        } else {
            clearInterval(this.state.updateIntervalId)
        }
    }

    getDropdown = () => {
        // console.log('getdropdown--------------------')
        try {
            const {word} = this.context
            const wordLower = word.toLowerCase()
            const entry = this.state.searchTable[wordLower]
            //const entry = this.state.searchTable[this.state.searchTerm]
            const options = []
            const strapOptions = []
            if (entry) {
                let timeOutput = new Date(null)
                entry.forEach((e, i) => {
                    const {phrase, time} = e
                    timeOutput.setSeconds(time)
                    let timeStr = timeOutput.toISOString().substr(11, 8)
                    options.push(<option key={i} value={time}>{timeStr} - {phrase}</option>)
                })
            }

            return (
                <React.Fragment>
                    <div className="input-group mb-3">
                        <div className="input-group-prepend">
                            <label className="input-group-text" htmlFor="inputGroupSelect01">Seek to...</label>
                        </div>
                        <select className="custom-select" id="inputGroupSelect01" onChange={e => this.handleSelect(e)} disabled={options.length === 0 ? 'disabled' : ''}>
                            <option defaultValue>Choose...</option>
                            {options}
                        </select>
                    </div>
                </React.Fragment>
            )
        } catch (err) {
            console.log(err)
        }
    }

    render() {
        return (
            <div className='Caption'>
                {/* render being called alot */}
                { Object.entries(this.state.captions).length !== 0 ? (
                    <form><fieldset><legend>Captions</legend>
                        <div className="form-group">
                            <input type='text' className='form-control' name='caption' placeholder='caption' value={this.state.line} readOnly />
                            {/* <CaptionHighlight phrase={this.state.line}/> */}
                            {/* <input type='text' className='form-control' name='searchCaption' placeholder='search' onChange={e => this.setState({searchTerm: e.target.value})}/> */}
                            {this.getDropdown()}
                        </div>
                    </fieldset></form>
                ) : null}
            </div>
        )
    }
}