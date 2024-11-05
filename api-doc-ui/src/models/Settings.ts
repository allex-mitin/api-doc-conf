interface Settings{
    services: [Service]
}

interface Service {
    path: string,
    name: string,
    openapi: {
        url: string
    },
    asyncapi: {
        url: string
    }
}
