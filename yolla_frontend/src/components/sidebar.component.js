import React, { Component } from "react";
import axios from "axios";
import GlobalContext from "./global.context";
import {CDBSidebar, CDBSidebarContent, CDBSidebarFooter, CDBSidebarHeader, CDBSidebarMenu, CDBSidebarMenuItem,} from 'cdbreact';
import { NavLink } from 'react-router-dom';
import {Button,  Collapse } from "react-bootstrap";

export default class Sidebar extends Component {
    static contextType = GlobalContext
    constructor(props) {
        super(props);
        this.state = { bookmarks: [], words: [], wordsOpen: false, bookmarkOpen: false, page: "/" };
        this.handleWordClick = this.handleWordClick.bind(this);
        this.handleInterval = this.handleInterval.bind(this);
    }
    componentDidMount() {
        this.interval = setInterval(() => this.handleInterval(), 1000)
    }

    handleInterval(){
        let wordResponse;
        let bookmarkResponse;
        this.setState({page: window.location.pathname});
        if(window.location.pathname == "/home"){
            axios.get('http://localhost:8080/user/' + sessionStorage.getItem("myID")).then(res=>{
                wordResponse = Object.keys(res.data.words);
                this.setState({words: wordResponse});
            });

            axios.get('http://localhost:8080/bookmark/' + sessionStorage.getItem("myUser")).then(res=>{
                bookmarkResponse = res.data;
                this.setState({bookmarks: bookmarkResponse});
            })
        }
    }
    componentWillUnmount() {
        clearInterval(this.interval);
      }

    handleWordClick(word){
        document.getElementById("wordInput").value = word;
        setTimeout(() => {
            document.getElementById("wordSubmitButton").click();
          }, 500);
          return

    }
    render() {
        const{wordsOpen} = this.state;
        const{bookmarksOpen} = this.state;
        return (
        
        <React.Fragment>
            {window.location.pathname == "/home" &&
            <div style={{ display: 'flex', height: '100vh', overflow: 'scroll initial' }}>
            <CDBSidebar textColor="#fff" backgroundColor="#333">
            <CDBSidebarHeader prefix={<i className="fa fa-bars fa-large"></i>}>
            <a
                href="/"
                className="text-decoration-none"
                style={{ color: 'inherit' }}
            >
                Sidebar
            </a>
            </CDBSidebarHeader>
            <Button variant="link" 					
                onClick={() => this.setState({ wordsOpen: !wordsOpen })}
				aria-controls="collapse-words"
			    aria-expanded={wordsOpen}
            >
                {sessionStorage.getItem("myUser")}'s words
            </Button>
            <hr style={{ backgroundColor: "#fff", width: "80%", marginLeft: '10%' }}/>
            <Collapse in={this.state.wordsOpen}>
                <div id ="collapse-words">
                    {this.state.words.map((word) =>
                    <div value = {word}
                    onClick = {() => 
                        this.handleWordClick(word)
                    }
                    style={{ textAlign: 'center' }}>
                        <p>{word}</p>
                    </div>
                    )}
                </div>
            </Collapse>

            <Button variant="link" 					
                onClick={() => this.setState({ bookmarksOpen: !bookmarksOpen })}
				aria-controls="collapse-words"
			    aria-expanded={bookmarksOpen}
            >
                {sessionStorage.getItem("myUser")}'s bookmarks
            </Button>
            <hr style={{ backgroundColor: "#fff", width: "80%", marginLeft: '10%' }}/>
            <Collapse in={this.state.bookmarksOpen}>
                <div id ="collapse-words">
                    {this.state.bookmarks.map((bookmark) =>
                    <div value = {bookmark.title}
                    onClick = {() => console.log("heya")
                        //this.handleWordClick(word)
                    }
                    style={{ textAlign: 'center' }}>
                        {bookmark.title === "" &&
                            <p>{bookmark.id}</p>
                        }
                        {bookmark.title !== "" &&
                            <p>{bookmark.title}</p>
                        }
                    </div>
                    )}
                </div>
            </Collapse>

                <CDBSidebarFooter style={{ textAlign: 'center' }}>
                <div
                    className="sidebar-btn-wrapper"
                    style={{
                    padding: '20px 5px',
                    }}
                >
                    Sidebar Footer
                </div>
                </CDBSidebarFooter>
            </CDBSidebar>
            </div>
            }
        </React.Fragment>
                );
    
    }
}