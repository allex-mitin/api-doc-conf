function settings() {
    return {
        "services": [
            {
                "path": "service1",
                "name": "Сервис 1",
                "openapi": {
                    "url": "/test/openapi.json"
                },
                "asyncapi": {
                    "url": "/test/asyncapi.json"
                }
            },
            {
                "path": "service2",
                "name": "Сервис 2",
                "openapi": {
                    "url": "/test/openapi.json"
                },
                "asyncapi": {
                    "url": "/test/asyncapi.yml"
                }
            }
        ]
    }
}
