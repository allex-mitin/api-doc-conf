import React, { FC } from 'react';
import { NavLink, useParams } from 'react-router-dom';
import styled from "styled-components";
import { ContentSwitcher, ContentSwitcherItem } from "@admiral-ds/react-ui";
import { getService, Services } from "../helpers";


const MenuWrapper = styled.div`
    margin-left: auto;
    padding-right: 30px;
`

const openApiItem = (path) => {
    return {
        title: (
            <>
                <NavLink to={`service/${path}/openapi`}
                         className={ ({ isActive }) => [isActive ? 'active' : '', "menu-item"].join(" ") }>REST API
                    spec</NavLink>
            </>
        )
    }
};

const asyncApiItem = (path) => {
    return {
        title: (
            <>
                <NavLink to={`service/${path}/asyncapi`}
                         className={ ({ isActive }) => [isActive ? 'active' : '', "menu-item"].join(" ") }>ASYNC API
                    spec</NavLink>
            </>
        ),
    }
}

export const Menu: FC = () => {
    const list = [];
    const path = useParams()

    const service = getService(path.serviceName)

    if (service) {
        if (service.openapi) {
            list.push(openApiItem(service.path))
        }

        if (service.asyncapi) {
            list.push(asyncApiItem(service.path))
        }
    }

    const [active, setActive] = React.useState(0);

    if (list.length === 0) {
        return null;
    }

    return (
        <MenuWrapper>
            <ContentSwitcher dimension={ 'm' }>
                { list.map((item, index) => (
                    <ContentSwitcherItem
                        key={ index }
                        active={ index === active }
                        onClick={ () => setActive(index) }
                    >
                        { item.title }
                    </ContentSwitcherItem>
                )) }
            </ContentSwitcher>
        </MenuWrapper>
    )
};
