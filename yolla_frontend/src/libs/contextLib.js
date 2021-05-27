import { Component, createContext } from "react";

export const {Provider, Consumer} = createContext();

class AppContextProvider extends Component {
  state = {
    isAuthenticated: false
  }; 
  changeAuthenticated = (newAuth) => {
    console.log("made it here");
    this.setState({isAuthenticated: newAuth});
  };
  render(){
    return (
      <Provider value={{state: this.state, changeAuth: this.changeAuthenticated}}>
          {this.props.children}
      </Provider>
    );
  }
}

export { AppContextProvider, Consumer as AppContextConsumer };