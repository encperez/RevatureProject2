import React, { Consumer, Component } from "react";
import axios from "axios";
import { useAppContext } from "../libs/contextLib";
import GlobalContext from './global.context'
import FocusTrap from 'focus-trap-react'

export default class Register extends Component {

    static contextType = GlobalContext;
    constructor(props) {
        super(props);
        console.log(this);
        //const  userHasAuthenticated  = this.props.isAuthenticated;
        //console.log(userHasAuthenticated);
        this.state = { username: '', password: '' };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }

      handleChange(event) {
          this.setState({ [event.target.name]: event.target.value})
      }

      handleSubmit = (event) => {

        const request = {username: this.state.username, password: this.state.password};
        let response;
        if(this.state.password === ""){
            alert("Please enter a valid password");
        }
        else if(this.state.username === ""){
            alert("Please enter a valid username");
        }
        else if(this.state.password.length < 7){
            alert("Password must at least be 7 characters");
        }
        else{
        axios.post('http://localhost:8080/register', request).then(res=>{
            response = res.data;
            console.log(res.data);
        }).then(resp =>{
            if(response === "false"){
                alert("Username already taken.");
            }
            else{
                this.context.setUser(this.state.username);
                console.log(sessionStorage.getItem("myUser"));
                axios.post('http://localhost:8080/login', request).then(res=>{
                    response = res.data;
                    console.log(res.data);
                }).then(resp =>{

                    sessionStorage.setItem('myUser', this.state.username);
                    sessionStorage.setItem('myID', response);
                    this.context.setUser(this.state.username);
                    console.log(sessionStorage.getItem("myUser"));
                    alert("Thanks for registering an account with us now redirecting you to the homepage!");
                    this.props.history.push("/home");

                });
            }
        });
        }
        event.preventDefault();
      }

    render() {
        return (
            <FocusTrap active={true}>
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
                <GlobalContext.Consumer>
                {({ user , setUser }) => (
                    <button type="submit" className="btn btn-primary btn-block" onClick={() => setUser(this.state.username)}>Submit</button>
                )}
                </GlobalContext.Consumer>
            </form>
            </FocusTrap>
        );
    }
}