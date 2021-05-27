import React, { Component } from "react";
import axios from "axios";
import Word from "./word.component";
import Video from "./video.component";
import Caption from './caption.component';
import Bookmark from './bookmark.component';
import { GlobalProvider } from "./global.context";

export default class Home extends Component {



    constructor(props) {
        super(props);
        this.state = { url: ''};
        const dictionaryAPI = async() =>{
            try{
                const data= await axios.get('https://api.dictionaryapi.dev/api/v2/entries/en/plane')
            }catch (error){
                console.log(error);
            }
        }


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
                <GlobalProvider>
                <div>
                    <Video />
                </div>
                <div>
                    <Bookmark />
                </div>
                <Caption />
                <div>
                    <Word />
                </div>
                </GlobalProvider>    
            </React.Fragment>
        );
        
    }
}