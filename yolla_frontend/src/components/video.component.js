import React, { Component } from "react";
import GlobalContext from './global.context'

export default class Video extends Component {
    static contextType = GlobalContext



    constructor(props) {
        super(props);
        this.state = { url: ''};


        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.makeEmbedURL = this.makeEmbedURL.bind(this);
      }

     makeEmbedURL = (url) =>{
        const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/;
        const match = url.match(regExp);
    
        return (match && match[2].length === 11)
          ? match[2]
          : null;
    }


    handleChange(event) {
        this.setState({ [event.target.name]: event.target.value})
    }

    handleSubmit = (event) => {

        let trimmed = this.state.url.trim()

      alert(trimmed);
      const {setUrl, ytPlayer, setVideoId} = this.context
      setUrl(trimmed)
      let embedURL = this.makeEmbedURL(trimmed);
      alert(embedURL);
      //document.getElementById("player").src = "https://www.youtube.com/embed/" + embedURL + "?enablejsapi=1&t=0";
      setVideoId(embedURL)
      ytPlayer.loadVideoById(embedURL, 0)

      event.preventDefault();
    }

    
    render() {
        return (
            <React.Fragment>
                <form onSubmit={this.handleSubmit}>
                    {/* <h3>Please enter the URL of the video you want to watch!</h3> */}
                    <div className="form-inline">
                        <label>Youtube URL</label>
                        <input type="text" className="form-control" placeholder="Enter URL" name="url" onChange={this.handleChange} value = {this.state.url} />
                        <button type="submit" className="btn btn-primary ">Submit</button>
                    </div>
                </form>
                <iframe src="https://www.youtube.com/embed/F-p_7XaEC84?enablejsapi=1" id="player" height="480" width="100%"/>
            </React.Fragment>         
        );
        
    }
}