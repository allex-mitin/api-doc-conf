import React, { FC } from 'react';
import { ThemeProvider } from 'styled-components';
import { createBrowserRouter, createRoutesFromElements, redirect, Route } from 'react-router-dom';
import { Layout } from "./Layout";
import { RouterProvider } from "react-router";
import { WelcomePage } from "../pages/WelcomePage";
import { ErrorPage } from "../pages/ErrorPage";
import { Service } from "./Service";
import { getService, hasAsyncApi, hasOpenApi } from "../helpers";
import { Documentation } from "./Documentation";
import { FontsVTBGroup, LIGHT_THEME } from '@admiral-ds/react-ui';



export const App: FC = () => {
    const routes = createRoutesFromElements([
        <Route path="/" element={ <Layout/> }>
            <Route index element={ <WelcomePage/> }/>
            <Route path="/home" element={ <WelcomePage/> }/>
            <Route path="/service/:serviceName" element={ <Service/> } loader={ serviceLoader }>
                <Route index element={ <Documentation/> } loader={ defaultDocumentation }/>
                <Route path=":documentation" element={ <Documentation/> } loader={ serviceLoader }/>
            </Route>

            <Route path="*" element={ <ErrorPage/> }/>
        </Route>
    ])
    const router = createBrowserRouter(routes)

    return (
        <ThemeProvider theme={ LIGHT_THEME }>
            <FontsVTBGroup/>
            <RouterProvider router={ router }/>
        </ThemeProvider>
    )
};

export const serviceLoader = async ({ params }: { params: any }) => {
    return getService(params.serviceName);
}

export const defaultDocumentation = async ({ params }: { params: any }) => {
    const service = getService(params.serviceName)
    if (hasOpenApi(service)) {
        return redirect("./openapi")
    }
    if (hasAsyncApi(service)) {
        return redirect("./asyncapi")
    }
    return service
}
