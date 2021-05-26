import React, { Component } from "react";
import axios from "axios";
import { useAppContext } from "../libs/contextLib";

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = { username: '', password: '' };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }

      handleChange(event) {
          this.setState({ [event.target.name]: event.target.value})
      }

      handleSubmit = (event) => {

        const request = {username: this.state.username, password: this.state.password};
        // let response;
        let response;
      //  const { userHasAuthenticated } = useAppContext();
        axios.post('http://localhost:8080/login', request).then(res=>{
            response = res.data;
            console.log(res.data);
        }).then(resp =>{
            if(response === ""){
                alert("Incorrect username or password!");
            }
            else{
                alert("Valid Login");
               // this.props.userHasAuthenticated(true);
                this.props.history.push("/home");
            }
        });
        event.preventDefault();
      }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <h3>Sign In</h3>

                <div className="form-group">
                    <label>Username</label>
                    <input type="text" className="form-control" placeholder="Enter username" name="username" onChange={this.handleChange} value = {this.state.username} />
                </div>

                <div className="form-group">
                    <label>Password</label>
                    <input type="password" className="form-control" placeholder="Enter password" name = "password" onChange={this.handleChange} value = {this.state.password}/>
                </div>

                <div className="form-group">
                    <div className="custom-control custom-checkbox">
                        <input type="checkbox" className="custom-control-input" id="customCheck1" />
                        <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                    </div>
                </div>

                <button type="submit" className="btn btn-primary btn-block">Submit</button>
            </form>
        );
    }
}