import React from 'react';
import {Component} from 'react';

export const GlobalContext = React.createContext()

class GlobalProvider extends Component {
    state = {
        url: "www.test.com",
        setUrl: this.setUrl,        
        word: 'bird',
        setWord: this.setWord,
        user: '',
        setUser: this.setUser,
        ytPlayer: null,
        setPlayer: this.SetPlayer,
        videoId: '',
        setVideoId: this.setVideoId,
        videoIdCb: null,
        setVideoIdCb: this.setVideoIdCb,
        onWordChange: [],
        subscribeWordChange: this.subscribeWordChange
    }

    //@todo unsubscribe
    subscribeWordChange = (cb) => {
        this.setState( { onWordChange: [...this.state.onWordChange, cb] } )
    }

    setVideoIdCb = (cb) => {
        this.setState({videoIdCb: cb})
    }

    setVideoId = (id) => {
        this.setState({videoId: id})
        if(this.state.videoIdCb !== null) {
            setTimeout(() => {
                this.state.videoIdCb()
            }, 1)
        }
    }

    setPlayer = (p) => {
        this.setState({ytPlayer: p})
    }

    setUrl = (u) => {
        this.setState({url: u})
    }

    setWord = (w) => {
        this.setState({word: w})
        setTimeout(() => {
            this.state.onWordChange.forEach( c => c() )
        }, 1)      
    }

    setUser = (u) => {
        //console.log("I'm here");
        this.setState({user: u})
    }

    render() {
        const {children} = this.props
        const {url, word, user, ytPlayer, videoId, videoIdCb, onWordChange} = this.state
        const {setUrl, setWord, setUser, setPlayer, setVideoId, setVideoIdCb, subscribeWordChange} = this

        return(
            <GlobalContext.Provider value={{url, setUrl, word, setWord, user, setUser, ytPlayer, setPlayer, videoId, setVideoId, videoIdCb, setVideoIdCb, onWordChange, subscribeWordChange}}>
            {children}
            </GlobalContext.Provider>
        )
    }
}

export default GlobalContext

export { GlobalProvider }