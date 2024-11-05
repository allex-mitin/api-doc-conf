import React, { FC } from 'react';
import { NavLink } from 'react-router-dom';
import styled from "styled-components";
import { ContentSwitcher, ContentSwitcherItem } from "@admiral-ds/react-ui";


const MenuWrapper = styled.div`
    align-self: flex-end;
    display: flex;
    padding: 20px;
`

const list = [
    {
        title: (
            <>
                <NavLink to="./openapi"
                         className={ ({ isActive }) => [isActive ? 'active' : '', "menu-item"].join(" ") }>REST API
                    spec</NavLink>
            </>
        ),
    },
    {
        title: (
            <>
                <NavLink to="./asyncapi"
                         className={ ({ isActive }) => [isActive ? 'active' : '', "menu-item"].join(" ") }>ASYNC API
                    spec</NavLink>
            </>
        ),
    }
];

export const Menu: FC = () => {

    const [active, setActive] = React.useState(0);

    return (
        <MenuWrapper>
            <ContentSwitcher dimension={'m'}>
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
