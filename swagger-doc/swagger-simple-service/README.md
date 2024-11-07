# Разбивка swagger файла

```shell
.
└── api-doc/
    ├── openapi.yml
    ├── components/
    │   ├── model/
    │   │   ├── Pet.yml
    │   │   └── ...
    │   ├── request/
    │   │   ├── PostPetRequest.yml
    │   │   └── ...
    │   ├── response/
    │   │   ├── PostPetResponse.yml
    │   │   └── ...
    │   └── schemas/
    │       ├── request.yml
    │       └── response.yml
    └── operations/
        ├── save-pet.yml
        └── ...
```

/openapi.yml
```yaml
openapi: 3.0.3
...
paths:
  /pet:
    $ref: './operations/pet-save.yml'
```

/operations/pet-save.yml
```yaml
put:
  ...
  requestBody:
    content:
      application/json:
        schema:
          $ref: '../components/schemas/request.yml#/components/schemas/PetRequest'
  responses:
    '200':
      content:
        application/json:
          schema:
            $ref: '../components/schemas/request.yml#/components/schemas/PetResponse'
```

/components/schemas/request.yml
```yaml
components:
  schemas:
    PetRequest:
      $ref: '../request/PetRequest.yml#/PetRequest'
```

# API нескольких микросервисов
```yml
.
├── swagger-ui-dist/
│   └── *.html, *.css, *.js
├── api-doc/
│   ├── pet-service/
│   │   └── swagger.yml
│   ├── store-service/
│   │   └── swagger.yml
│   └── user-service/
│       └── swagger.yml
└── Dockerfile
```

```javascript
window.onload = function() {
  window.ui = SwaggerUIBundle({
    urls: [
        {
            url: "/api-doc/pet-service/swagger.yml",
            name: "pet-service"
        },
        {
            url: "/api-doc/store-service/swagger.yml",
            name: "store-service"
        },
        {
            url: "/api-doc/user-service/swagger.yml",
            name: "user-service"
        }
    ]
  });
};

```

# Общие модели в микросервисах

```shell
.
└── api-doc/
    ├── common/
    │   └── components/
    │       └── schemas/
    │           └── Error.yml
    └── services/
        ├── pet-service/
        │   ├── openapi.yml
        │   ├── operations/
        │   │   └── ...
        │   └── components/
        │       ├── schemas/
        │       │   ├── common.yml
        │       │   ├── request.yml
        │       │   └── response.yml
        │       └── ... 
        ├── store-service/
        │   ├── openapi.yml
        │   └── ...
        └── user-service/
            ├── openapi.yml
            └── ...
```

```yml
components:
  schemas:
    Error:
      $ref: '../../../../common/components/schemas/Error.yml#/components/schemas/Error'
```

```yml
  responses:
    default:
      description: Unexpected error
      content:
        'application/json':
          schema:
            $ref: '../components/schemas/common.yml#/components/schemas/Error'
```


# AsyncAPI

```shell
.
├── api-ui-dist/
│   └── *.html, *.css, *.js
└── api-doc/
    ├── common/
    └── services/
        ├── pet-service/
        │   ├── asyncapi.yml
        │   ├── openapi.yml
        │   ├── channels/
        │   │   └── ...
        │   ├── operations/
        │   └── components/
        │       ├── models/
        │       ├── request/
        │       ├── response/
        │       ├── message/
        │       ├── schemas/
        │       │   ├── common.yml
        │       │   ├── request.yml
        │       │   ├── response.yml
        │       │   └── message.yml
        │       └── ... 
        ├── store-service/
        │   ├── asyncapi.yml
        │   ├── openapi.yml
        │   └── ...
        └── user-service/
            ├── asyncapi.yml
            ├── openapi.yml
            └── ...
```

# Локальная разработка UI

```shell
.
├── api-doc/
│   ├── common/
│   └── services/
│       ├── pet-service/
│       │   ├── asyncapi.yml
│       │   ├── openapi.yml
│       │   ├── operations/
│       │   ├── components/
│       │   └── channels/
│       ├── store-service/
│       │   ├── asyncapi.yml
│       │   ├── openapi.yml
│       │   └── ...
│       └── user-service/
│           ├── asyncapi.yml
│           ├── openapi.yml
│           └── ... 
├── Dockerfile
└── build.gradle
```