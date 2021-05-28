import React, { useState } from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import Login from "./components/login.component";
import Home from "./components/home.component";
import { AppContext } from "./libs/contextLib";
import { GlobalProvider } from "./components/global.context";
import Sidebar from './components/sidebar.component'
import Bookmark from './components/bookmark.component';

function App() {
  return (
    <div className='App'>
      {/* localStorage.setItem */}
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light fixed-top">
          <div className="container">
            <Link className="navbar-brand" to={"/sign-in"}>Y.O.L.L.A.</Link>
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link className="nav-link" to={"/sign-in"}>Login</Link>
                </li>

              </ul>
            </div>
          </div>
        </nav>

        <GlobalProvider>
          <div className='content-container'>
          <Sidebar />
            <div className="auth-wrapper">
              <div className="auth-inner">
                <Switch>
                  <Route exact path='/' component={Login} />
                  <Route path="/sign-in" component={Login} />
                  <Route path="/home" component={Home} />
                </Switch>
              </div>
            </div>
            <div>
              <Bookmark />
            </div>
          </div>
        </GlobalProvider>
      </div>
    </div>
  );
}

export default App;