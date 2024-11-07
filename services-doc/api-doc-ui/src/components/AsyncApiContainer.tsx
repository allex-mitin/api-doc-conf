import type {FC} from 'react';
import {useEffect, useState} from "react";
import {Resolver} from "@stoplight/json-ref-resolver";
import YAML from 'yaml'
// @ts-expect-error - TODO
import AsyncApi from '@asyncapi/react-component/browser';

const asyncApiConfig = {
    show: {errors: process.env.NODE_ENV !== 'production'},
    parserOptions: {}
};

interface AsyncApiContainerProps {
    url: string;
}

export const AsyncApiContainer: FC<AsyncApiContainerProps> = ({url}) => {
    const [document, setDocument] = useState<string | undefined>(undefined)
    useEffect(() => {
        (async () => {
            const parsed = await parseDocument(url)
            const document = YAML.stringify(parsed.result)
            return setDocument(document)

        })();

        return () => {
            <div>Loading...</div>
        }
    }, []);

    return (
        <div style={{
            width: '100%',
            margin: '10px'
        }}>
            <AsyncApi schema={document} config={asyncApiConfig}/>
        </div>
    )
};

export const parseDocument = async (url: string) => {
    const resolver = new Resolver({
        resolvers: {
            file: {
                resolve: async ref => {
                    const [file] = await readFile(ref.toString())
                    return file
                }
            }
        }
    })

    const [yaml, baseUri] = await readFile(url)
    return resolver.resolve(yaml, {
        baseUri: baseUri
    })
}

export const readFile = async (uri: string) => {
    const file = await fetch(uri)
        .then(async value => {
            const text = await value.text();
            return {
                "text": text,
                "url": value.url
            }
        })
    return [YAML.parse(file.text), file.url]
}
