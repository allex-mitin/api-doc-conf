import { FC } from "react";
import { Menu } from "./Menu";
import { useLoaderData } from "react-router-dom";
import { Outlet } from "react-router";
import styled from "styled-components";


const ServiceWrapper = styled.div`
    display: flex;
    flex-direction: column;
    width: calc(100vw - 300px);
`

export const Service: FC = () => {
    // const service = useLoaderData() as Service

    return (
        <ServiceWrapper>
            <Menu/>
            <Outlet/>
        </ServiceWrapper>
    )
}
