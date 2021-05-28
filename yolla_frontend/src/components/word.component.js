import React, { Component } from "react";
import axios from "axios";

import "./words.css";
import { useEffect, useState } from "react";
import { Form, Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import GlobalContext from "./global.context";

export default class Word extends Component {
    static contextType = GlobalContext

    constructor(props) {
        super(props);
        this.state = { word: '', meanings: [] };
        this.myWordRef = React.createRef();
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.dictionaryAPI = this.dictionaryAPI.bind(this);
        this.handleAddWord = this.handleAddWord.bind(this);
      }

    dictionaryAPI = async(word) => {
        try {
            const data =await axios.get("https://api.dictionaryapi.dev/api/v2/entries/en/" + word);
            console.log(data.data);
            const newMeanings = [...data.data];
            this.setState({meanings: newMeanings})
            this.state.meanings.map(mean => console.log(mean.meanings));
        } catch (error) {
            console.log(error)
        }
    }

    handleChange(event) {
        this.setState({ [event.target.name]: event.target.value})
        this.context.setWord(event.target.value)
    }
    
    handleAddWord = (event) => {
        const {url, setUrl, word, setWord, user, setUser} = this.context
        console.log(this.state.word);
        let response;
        axios.put('http://localhost:8080/word/'+sessionStorage.getItem("myID")+'/'+this.state.word).then(res=>{
            response = res.data;
            console.log(res.data);
        }).then(resp => {
            if(response != null) {
                alert("Word added");
            }
            else {
                alert("Word not added");
            }
        });
        event.preventDefault();
    }
    
    handleSubmit = (event) => {
        let newWord = document.getElementById("wordInput").value;
        this.setState({word: newWord});
        this.dictionaryAPI(newWord);
        event.preventDefault();
        
      }

      render() {
        return (
        <React.Fragment>
            <form className="form-inline" onSubmit={this.handleSubmit}>
                <input id = "wordInput" type="text" className="form-control" placeholder="Enter a word you want to look up!" name="word" onChange={this.handleChange} value = {this.state.word} />
                <button id = "wordSubmitButton" type="submit" className={"btn btn-primary"}>Submit</button>
            </form>
            <div 
                className="meanings"
                style={{
                    maxHeight: 300,
                    overflowY: "scroll",
                
                }}
            >
                {/* audio---------------------------- */}
                {this.state.meanings[0] && this.state.word && (
                    <audio
                    style={{ backgroundColor: "#fff", borderRadius: 10 }}
                    src={this.state.meanings[0].phonetics[0] && this.state.meanings[0].phonetics[0].audio}
                    controls
                    >
                    Your browser does not support the audio element.
                    </audio>
                )}
                {/* audio---------------------------- */}

                {this.state.word === "" ? (
                    <span className="subTitle">Start by typing a word in search</span>
                ) : (
                    
                    this.state.meanings.map((mean) =>
                    mean.meanings.map((item) =>
                        item.definitions.map((def) => (
                        <div className="singleMean"     >
                            <b>{item.partOfSpeech + ": "}</b>
                            <b>{def.definition}</b>
                            <hr style={{ backgroundColor: "black", width: "100%" }} />
                            {def.example && (
                            <span>
                                <b>Example :</b> {def.example}
                            </span>
                            )}
                            {def.synonyms && (
                            <span>
                                <b>Synonyms :</b> {def.synonyms.map((s) => `${s}, `)}
                            </span>
                            )}
                            <div>
                                <form onSubmit={this.handleAddWord}>
                                    <button type="submit" className="btn btn-primary btn-block">Add Word</button>
                                </form>
                            </div>
                        </div>
                        ))
                    )
                    )
                )}
                
                
            </div>

        </ React.Fragment>
        )
      }
}