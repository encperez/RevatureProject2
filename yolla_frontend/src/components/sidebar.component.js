import React, { Component } from "react";
import axios from "axios";
import GlobalContext from "./global.context";
import {CDBSidebar, CDBSidebarContent, CDBSidebarFooter, CDBSidebarHeader, CDBSidebarMenu, CDBSidebarMenuItem,} from 'cdbreact';
import { NavLink } from 'react-router-dom';

export default class Sidebar extends Component {
    static contextType = GlobalContext

    constructor(props) {
        super(props);
        this.state = {  };
    }

    handleChange(event) {
        this.setState({ [event.target.name]: event.target.value})
    }

    render() {
        return (
        <React.Fragment>
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
        </React.Fragment>
        );
    }
}