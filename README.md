# POC Spring Boot - H2 (memory DB)

POC using Spring Boot and H2 (memory DB) to handle users, designed by layers: Controller - Service - DAO (Repository) - Entity, using REGEX to filter email and pass, using Jackson Objet Mapper

##  users-app

### POC is using layers: Controller - Service - DAO (Repository) - Entity

### Para construir 
mvn install


### Para acceder al Swagger

http://localhost:8080/swagger-ui/index.html

http://localhost:8080/v3/api-docs

## Para acceder a la consola de la DB
http://localhost:8080/h2-console/

### Para probar

#### Endpoint de creación
http://localhost:8080/create

#### Body para endpoint de creación de usuario



{
"name": "Oscar",
"email": "oscar@correo.com",
"password": "123456$abcDEF",
"phones": [
{
"number": "12345",
"citycode": "1",
"countrycode": "57"
},
{
"number": "2468",
"citycode": "2",
"countrycode": "60"
}
]
}

