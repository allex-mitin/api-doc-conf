import React, { FC } from 'react';
import { Header } from './Header';
import { Navigator } from "./Navigator";
import { Outlet } from "react-router";
import styled from "styled-components";

const LayoutWrapper = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100vh;
`;

const LayoutBodyWrapper = styled.div`
    display: flex;
    height: calc(100vh - 100px);
`;

export const Layout: FC = () => {
    return (
        <LayoutWrapper>
            <Header/>
            <LayoutBodyWrapper>
                <Navigator/>
                <Outlet/>
            </LayoutBodyWrapper>
        </LayoutWrapper>
    )
};
