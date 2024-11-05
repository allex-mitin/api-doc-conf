# Demonstrate project of API Documentation

The project contains several independent modules that demonstrate different ways of working with API documentation.


## swagger-simple-service
Simple swagger file with swagger-ui-dist.
```
cd ./swagger-single-service
docker-compose up --build
```
After container start you can change swagger file on-the-fly (after change refresh page in browser).

## swagger-simple-service
Multiple swagger files with swagger-ui-dist.
```
cd ./swagger-multiple-service
docker-compose up --build
```
After container start you can change swagger file on-the-fly (after change refresh page in browser).

## api-doc-ui
UI project on TypeScript to display OpenApi + AsyncApi. To build project use installed nodeJS.
```
npm run build
```

## api-doc-service
Service of documentation, uses #api-doc-ui as dist

## backend-apifirst 
Spring boot service to display API First code generation
