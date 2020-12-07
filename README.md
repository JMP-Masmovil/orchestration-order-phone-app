# orchestration-order-phone-app

Orchestration Order Microservice

Microservicio la orquestacion de creacion de pedidos.
Orquesta las llamadas realizando las siguientes acciones:
	- Creacion de cliente en customer-phone-app
	- Validacion de terminales consultando en catalog-phone-app
	- Creacion del pedido en order-phone-app

#Swagger
http://localhost:8080/swagger-ui.html

#Docker
Create container from shell 
	"mvn package spring-boot:repackage"
	"docker build -t orchestration-order-phone-app/v1 ."