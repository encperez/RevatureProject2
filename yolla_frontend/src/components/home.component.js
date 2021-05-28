import React, { Component } from "react";
import Word from "./word.component";
import Video from "./video.component";
import Caption from './caption.component';

export default class Home extends Component {
    constructor(props) {
        super(props);
        if(sessionStorage.getItem("myUser") == null){
            console.log("No Login")
            props.history.push("/");
        }
        this.state = { url: ''};
        console.log(sessionStorage.getItem("myUser")+" has made it.")

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
                <div>
                    <Video />
                </div>
                <Caption />           
                <div>
                    <Word />
                </div>
            </React.Fragment>
        );
        
    }
}