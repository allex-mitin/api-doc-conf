import React, { FC, forwardRef, ReactNode, useMemo, useState } from 'react';
import { useNavigate } from "react-router-dom";
import {
    MenuItem,
    RenderOptionProps, TabIcon,
    TabMenuVertical, TabText,
    VerticalTab, VerticalTabBadge,
    VerticalTabProps
} from '@admiral-ds/react-ui';
import { Services } from "../helpers";
import styled from "styled-components";


interface TabContentProps extends VerticalTabProps {
    text: string;
    badge?: number;
    disabled?: boolean;
    icon?: ReactNode;
}

interface CustomVerticalTabProps extends TabContentProps {
}

const CustomVerticalTab = forwardRef<HTMLButtonElement, CustomVerticalTabProps>(
    (
        {
            dimension = 'l',
            disabled,
            selected,
            onSelectTab,
            icon,
            badge,
            tabId,
            text,
            ...props
        }: CustomVerticalTabProps,
        ref,
    ) => {
        return (
            <VerticalTab
                { ...props }
                ref={ ref }
                tabId={ tabId }
                dimension={ dimension }
                disabled={ disabled }
                selected={ selected }
                onSelectTab={ onSelectTab }
            >
                { icon && (
                    <TabIcon $dimension={ dimension } $disabled={ disabled }>
                        { icon }
                    </TabIcon>
                ) }
                <TabText>{ text }</TabText>
                { badge && (
                    <VerticalTabBadge disabled={ disabled } selected={ selected }>
                        { badge }
                    </VerticalTabBadge>
                ) }
            </VerticalTab>
        );
    },
);

const services = Services()
const tabs = services.map((item) => {
    return {
        text: item.name,
        tabId: item.path
    }
})

// tabs.unshift({
//     text: 'Главная',
//     tabId: '/'
// })


const MenuItemWrapper = styled.div`
    display: flex;
    overflow: hidden;
    text-overflow: ellipsis;
    align-items: center;
`;

const NavigatorWrapper = styled.div`
    box-sizing: border-box;
    padding: 10px 10px 0 10px;
    display: flex;
    flex-direction: column;
    gap: 10px;
    height: calc(100vh - 100px);
    overflow: hidden;
    align-items: center;
`;

export const Navigator: FC = () => {
    const navigate = useNavigate();

    const tabsMap = useMemo(() => {
        return tabs.map((tab) => tab.tabId);
    }, [tabs]);

    const [selectedTab, setSelectedTab] = useState<string | undefined>('/');
    const handleSelectTab = (tabId: string) => {
        const currentTab = services.find((tab) => tab.path === tabId);
        setSelectedTab(tabId)
        navigate(`/service/${ currentTab.path }`)
    };

    const tabIsDisabled = (tabId: string) => {
        const currentTab = tabs.find((tab) => tab.tabId === tabId);
        return !!currentTab?.disabled;
    };

    const renderTab = (tabId: string, selected?: boolean, onSelectTab?: (tabId: string) => void) => {
        const currentTab = tabs.find((tab) => tab.tabId === tabId);

        return (
            <CustomVerticalTab
                tabId={ tabId }
                text={ currentTab?.text || '' }
                key={ tabId }
                selected={ selected }
                onSelectTab={ onSelectTab }
            />
        );
    };
    const renderDropMenuItem = (tabId: string) => {
        const currentTab = tabs.find((tab) => tab.tabId === tabId);

        return (options: RenderOptionProps) => {
            return (
                <MenuItem  { ...options } key={ tabId }>
                    <MenuItemWrapper>
                        { currentTab?.text }
                    </MenuItemWrapper>
                </MenuItem>
            );
        };
    };

    return (
        <NavigatorWrapper>
            <TabMenuVertical
                selectedTabId={ selectedTab }
                onSelectTab={ handleSelectTab }
                tabsId={ tabsMap }
                renderTab={ renderTab }
                renderDropMenuItem={ renderDropMenuItem }
                tabIsDisabled={ tabIsDisabled }
                showUnderline
            />
        </NavigatorWrapper>
    );
}
