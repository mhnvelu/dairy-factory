# dairy-factory
![CircleCI](https://circleci.com/gh/mhnvelu/dairy-factory.svg?style=svg) (https://app.circleci.com/pipelines/github/mhnvelu/dairy-factory)

Spring Boot Microservices with Spring Cloud
----------
## Block Diagram
![Block-Diagram](images/Block-Diagram.png)


Below microservices have been implemented.

- [dairy-factory](https://github.com/mhnvelu/dairy-factory)
- [dairy-factory-order-service](https://github.com/mhnvelu/dairy-factory-order-service)
- [dairy-factory-inventory-service](https://github.com/mhnvelu/dairy-factory-inventory-service)
- [dairy-factory-inventory-failover-service](https://github.com/mhnvelu/dairy-factory-inventory-failover-service)
- [dairy-factory-gateway](https://github.com/mhnvelu/dairy-factory-gateway)
- [dairy-factory-eureka-server](https://github.com/mhnvelu/dairy-factory-eureka-server)
- [dairy-factory-config](https://github.com/mhnvelu/dairy-factory-config)
- [dairy-factory-config-server](https://github.com/mhnvelu/dairy-factory-config-server)

Please refer my [Notes](NOTES.md) to know about Sagas, Spring Cloud projects

A repository on Spring State Machine is available at [spring-state-machine-project](https://github.com/mhnvelu/spring-state-machine-project)

### Order Allocation Orchestration Saga
![Order Allocation Orchestration Saga](images/Order-Allocation-Orchestration-Saga.png)

- Saga Execution Coordinator is implemented using Spring State Machine.
- Events:
  - VALIDATE_ORDER, VALIDATION_PASSED, VALIDATION_FAILED, ALLOCATION_SUCCESS, 
  ALLOCATION_NO_INVENTORY, ALLOCATION_FAILED, BUTTER_ORDER_PICKED_UP, CANCEL_ORDER
- States:
  - NEW, VALIDATED, VALIDATION_EXCEPTION, ALLOCATED, ALLOCATION_ERROR, PENDING_INVENTORY, 
  PICKED_UP, DELIVERED, DELIVERY_EXCEPTION, CANCELLED

### Manually Running dairy factory microservices project
- Run the mysql container  [MySQL Docker](https://hub.docker.com/_/mysql)
  - docker run -d mysql
  - Connect to the mysql server and manually execute the script [mysql-init.sql](src/main/resources/scripts/mysql-init.sql). 
    This script creates the DB, DB User and Password.
- Run the JMS container
  - docker run -p 8161:8161 -p 8162:61616 vromero/activemq-artemis
- Run dairy-factory-eureka-server. It binds to port **8761**
- Run dairy-factory-config-server. It binds to port **8888**
- Run dairy-factory with profiles ``local-service-discovery,local``. It binds to port **8080**
- Run dairy-factory-order-service with profiles ``local-service-discovery,local``. It binds to port **8081**
- Run dairy-factory-inventory-service with profiles ``local-service-discovery,local``. It binds to port **8082**
- Run dairy-factory-inventory-failover-service with profiles ``local-service-discovery``. It binds to port **8083**
- Run dairy-factory-gateway with profiles ``local-service-discovery``. It binds to port **9090**
- Run Zipkin container
  - docker run -d -p 9411:9411 openzipkin/zipkin
  - The UI is available at http://localhost:9411/zipkin/
  