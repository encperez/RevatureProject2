import React from 'react';
import {Component} from 'react';

export const GlobalContext = React.createContext()

class GlobalProvider extends Component {
    state = {
        url: "www.test.com",
        setUrl: this.setUrl,        
        word: 'bird',
        setWord: this.setWord,
        user: '',
        setUser: this.setUser
    }

    setUrl = (u) => {
        this.setState({url: u})
    }

    setWord = (w) => {
        this.setState({word: w})
    }

    setUser = (u) => {
        console.log("I'm here");
        this.setState({user: u})
    }

    render() {
        const {children} = this.props
        const {url, word, user} = this.state
        const {setUrl, setWord, setUser} = this

        return(
            <GlobalContext.Provider value={{url, setUrl, word, setWord, user, setUser}}>
            {children}
            </GlobalContext.Provider>
        )
    }
}

export default GlobalContext

export { GlobalProvider }