import type {FC} from 'react';
import {AsyncApiContainer} from '../components/AsyncApiContainer';
import {LoadingSpec} from '../components/LoadingSpec';

export const AsyncApiPage: FC<{ service: Service | undefined }> = ({service}) => {
    const url = service?.asyncapi?.url
    return !url ? <LoadingSpec withError={true}/> : <AsyncApiContainer url={url}/>;
};
