import {FC} from "react";
import styled from "styled-components";
import { T } from "@admiral-ds/react-ui";

const WelcomePageWrapper = styled.div`
    
`

export const WelcomePage: FC = () => {
    return (
        <WelcomePageWrapper>
            <T font='Body/Body 2 Long'>
                Описание
            </T>
        </WelcomePageWrapper>
    )
}
