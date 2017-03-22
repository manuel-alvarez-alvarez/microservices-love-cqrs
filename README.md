# Microservices And CQRS/Event Sourcing: A true love story

Sample project for the talk Microservices And CQRS/Event Sourcing: A true love story

The master branch has been intentionally left blank so the branches included in the project are:

1. **Monolith**. It's the original store build as a single application.
  
![Monolith](https://raw.githubusercontent.com/manuel-alvarez-alvarez/microservices-love-cqrs/master/monolith.png )

2. **CQRS**. We have moved from the original Monolith to a CQRS application to run in a single node.
  
![CQRS](https://raw.githubusercontent.com/manuel-alvarez-alvarez/microservices-love-cqrs/master/cqrs.png )

3. **Microservices**. Finally we separate the different aggregates from the CQRS app and placed them in different services.
  
![Microservices](https://raw.githubusercontent.com/manuel-alvarez-alvarez/microservices-love-cqrs/master/microservices.png )

## Requirements

A working Docker installation is the first requirements, download your copy 
from [Docker](https://www.docker.com)

Once Docker is installed you should download Node.js in order to build the frontend,
you can download it from [Node.js](https://nodejs.org)


## Build

In order to build the demo first we must install all the frontend dependencies 
and the Angular 2 CLI:

```bash
npm install -g @angular/cli
cd web
npm install
```

Once the frontend dependencies are ready we can build the application with gradle (the build targets Windows you can modify
the Angular2 CLI call for other OS as well):

```bash
./gradlew.bat build
```

## Running

To start and stop the application you should use `docker-compose`

```bash
docker-compose up
```

and 

```bash
docker-compose down
```