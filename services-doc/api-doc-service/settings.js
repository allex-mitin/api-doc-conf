function settings() {
    return {
        "services": [
            {
                "path": "backend-apifirst",
                "name": "Backend API First Service",
                "openapi": {
                    "url": "/api-doc/services/info-service/openapi.yml"
                },
                "asyncapi": {
                    "url": "/api-doc/services/info-service/asyncapi.yml"
                }
            },
            {
                "path": "pet-service",
                "name": "Pet service",
                "openapi": {
                    "url": "/api-doc/services/pet-service/openapi.yml"
                }
            },
            {
                "path": "store-service",
                "name": "Store service",
                "openapi": {
                    "url": "/api-doc/services/store-service/openapi.yml"
                }
            },
            {
                "path": "user-service",
                "name": "User service",
                "openapi": {
                    "url": "/api-doc/services/user-service/openapi.yml"
                }
            }
        ]
    }
}
