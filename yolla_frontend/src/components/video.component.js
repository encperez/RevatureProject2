import React, { Component } from "react";

export default class Video extends Component {



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

      alert(this.state.url);
      let embedURL = this.makeEmbedURL(this.state.url);
      alert(embedURL);
      document.getElementById("player").src = "//www.youtube.com/embed/" + embedURL;
      event.preventDefault();
    }

    
    render() {
        return (
            <React.Fragment>
                <form onSubmit={this.handleSubmit}>
                    <h3>Please enter the URL of the video you want to watch!</h3>

                    <div className="form-group">
                        <label>Youtube URL</label>
                        <input type="text" className="form-control" placeholder="Enter URL" name="url" onChange={this.handleChange} value = {this.state.url} />
                    </div>
                    <button type="submit" className="btn btn-primary btn-block">Submit</button>
                </form>
                <iframe src="https://www.youtube.com/embed/zenMEj0cAC4?enablejsapi=1" id="player"/>
            </React.Fragment>         
        );
        
    }
}