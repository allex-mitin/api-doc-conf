import type {FC} from 'react';
import SwaggerUI from 'swagger-ui-react';
import {LoadingSpec} from '../components/LoadingSpec';

export const SwaggerPage: FC<{ service: Service | undefined }> = ({service}) => {
    const url = service?.openapi?.url
    return !url ? <LoadingSpec withError={true}/> : <SwaggerUI url={url} docExpansion="list" deepLinking/>;
};
