import React from 'react';
import {Component} from 'react';

export const LinkContext = React.createContext('defaultVal')

class LinkProvider extends Component {
    state = {
        url: "www.test.com",        
        word: 'bird',
        user: ''
    }

    setUrl = (u) => {
        this.setState({url: u})
    }

    setWord = (w) => {
        this.setState({word: w})
    }

    setUser = (u) => {
        this.setState({user: u})
    }

    // what this
    render() {
        const {children} = this.props
        const {url, word, user} = this.state
        const {setUrl, setWord, setUser} = this

        return(
            <LinkContext.Provider value={{url, setUrl, word, setWord, user, setUser}}>
            {children}
            </LinkContext.Provider>
        )
    }
}

export default LinkContext

export { LinkProvider }