import React, { Component } from "react";
import axios from "axios";
import GlobalContext from "./global.context";

export default class Bookmark extends Component {
    static contextType = GlobalContext

    constructor(props) {
        super(props);
        this.state = { title: '', starttime: 0, endtime: 0, url: '', username: '' };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({ [event.target.name]: event.target.value})
    }

    handleSubmit = (event) => {
        const {url, setUrl, word, setWord, user, setUser} = this.context
        const request = {title: this.state.title, starttime: this.state.starttime, endtime: this.state.endtime, url: this.context.url, username: sessionStorage.getItem("myUser")}
        let response;
        axios.post('http://localhost:8080/newBookmark', request).then(res=>{
            response = res.data;
            console.log(res.data);
        }).then(resp => {
            if(response === true) {
                alert("New bookmark created");
            }
            else {
                alert("Bookmark could not be created");
            }
        });
        event.preventDefault();
        
    }

    render() {
        return (
        <React.Fragment>
            <form onSubmit={this.handleSubmit}>
                <h3>Create a Bookmark</h3>
                <div className='form-group'>
                    <label>Title</label>
                    <input type="text" className="form-control" placeholder="Bookmark Title" name="title" onChange={this.handleChange} value={this.state.title} />
                </div>

                <div className='form-group'>
                    <label>Start Time</label>
                    <input type="number" min="0" className="form-control" placeholder="Bookmark Start Time" name="starttime" onChange={this.handleChange} value={this.state.starttime} />
                </div>

                <div className='form-group'>
                    <label>End Time</label>
                    <input type="number" className="form-control" placeholder="Bookmark End Time" name="endtime" onChange={this.handleChange} value={this.state.endtime} />
                </div>
                <button type="submit" className="btn btn-primary btn-block">Submit</button>
            </form>
        </React.Fragment>
        );
    }
}