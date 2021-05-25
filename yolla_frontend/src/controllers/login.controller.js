import React, {Component} from 'react';
import Login from "../components/login.component";

class LoginController extends Component{
    state = {
        username: "",
        password: ""
    }
    handleUsernameChange = username => {
        this.setState({username})
    }
    render(){
        return(
            <Login username={this.state.username} onUsernameChange={this.handleUsernameChange} />
        )
    }
}
 export default LoginController