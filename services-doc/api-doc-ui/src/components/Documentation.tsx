import  {FC} from "react";
import {useLoaderData, useParams} from "react-router-dom";
import {SwaggerPage} from "../pages/SwaggerPage";
import {AsyncApiPage} from "../pages/AsyncApiPage";
import styled from "styled-components";

const DocumentationWrapper = styled.div`
    display: flex;
    overflow: auto;
`

export const Documentation: FC = () => {
    const params = useParams();
    const service = useLoaderData() as Service

    const docType = params.documentation

    const document = (() => {
        switch (docType) {
            case "openapi":
                return (<SwaggerPage service={service} />)
            case "asyncapi":
                return (<AsyncApiPage service={service} />)
            default:
                return (<div><p>Documentation is not available</p></div>)
        }
    })()
    return(<DocumentationWrapper>{document}</DocumentationWrapper>)
}

