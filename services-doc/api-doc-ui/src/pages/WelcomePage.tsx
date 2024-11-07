import { FC } from "react";
import styled from "styled-components";
import { T } from "@admiral-ds/react-ui";

import openapi from '../assets/openapi.webp';
import asyncapi from '../assets/asyncapi.webp';

const WelcomePageWrapper = styled.div`
    display: flex;
    width: calc(100vw - 300px);
    justify-content: center;
    align-items: center;

    & > div {
        display: flex;
        flex-grow: 1;
        justify-content: center;
        
        #openapiID {
            width: 300px;
        }

        #asyncapiID {
            width: 300px;
        }
    }
`

export const WelcomePage: FC = () => {
    return (
        <WelcomePageWrapper>
            <div>
                <a href={ 'https://www.openapis.org/' } target={'_blank'}>
                    <img src={openapi} id={'openapiID'}/>
                </a>
            </div>
            <div>
                <a href={ 'https://www.asyncapi.com/en' } target={'_blank'}>
                    <img src={ asyncapi } id={'asyncapiID'}/>
                </a></div>
        </WelcomePageWrapper>
    )
}
