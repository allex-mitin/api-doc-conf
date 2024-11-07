# Demonstrate project of API Documentation

The project contains several independent modules that demonstrate different ways of working with API documentation with examples.



# swagger-doc
Group of projects to demonstrate working with swaddwr-ui dist. 


## swagger-simple-service
Simple swagger file with swagger-ui-dist.
```
cd ./swagger-doc/swagger-single-service
docker-compose up --build
```
After container start you can change swagger file on-the-fly (after change refresh page in browser).
Swagger page http://localhost:8091


## swagger-simple-service
Multiple swagger files with swagger-ui-dist.
```
cd ./swagger-doc/swagger-multiple-service
docker-compose up --build
```
After container start you can change swagger file on-the-fly (after change refresh page in browser).
Swagger page http://localhost:8092


# services-doc

## api-doc-ui
UI project on TypeScript to display OpenApi + AsyncApi. To build project use installed nodeJS.
```
npm run build
```
After build you can use dist from `build` folder.


## api-doc-service
Service of documentation Openapi and Asyncapi examples.
Uses api-doc-ui dist to render documentation.

Documentation contains multipla services documentation:

- **info-service** (composite api with pet-service and user-service)
- **pet-service** (pet service from API Swagger Petstore)
- **user-service**  (user service from API Swagger Petstore)
- **store-service**  (store service from API Swagger Petstore)

Run container with commands:
```
cd ./services-doc/api-doc-service
docker-compose up --build
```
Documentation page: http://localhost:8093

Build project and publish documentation to maven local with command:
```
gradle clean build publish
```

# backend-services

All services run with docker compose. Before run containers, you must build jar application.
Docker compose contains kafka and kafka-ui (http://localhost:8888)

```
gradle clean build
cd ./services-java
docker-compose up --build
```

## info-service
Spring boot service to example API First code generation
1. Generate controllers from openapi
2. Generate http-client to pet-store and user-store services
3. Generate asyncapi dto model to call kafka

Service has one method `GET /info`, that call pet-service, user-service, send message to kafka and return compose response (with Pet and User DTO)
Service start with address http://localhost:8181

## pet-service
Spring boot service with generated controller, that's return random response.
Service start with address http://localhost:8182

## user-service
Spring boot service with generated controller, that's return random response.
Service start with address http://localhost:8183

