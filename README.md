# Spring Angular Template
MIT-licensed template for scaffolding self-container Java RESTful web server. Tech stack usage are
  - [Liquibase](https://www.liquibase.org)
  - [Spring Framework](https://spring.io)
  - [Angular](https://angular.io)
  - [Material UI](https://material.angular.io)
  - [Covalent](https://teradata.github.io/covalent)
  - [Gradle](https://gradle.org)

## Features
  - Spring Boot RESTful APIs using `@RequestMapping`
  - Generic ORM using JPA, any relational database engine should work through proper `JDBC` URL configuration.
  - Liquibase data migration integrated when the application starts.
  - Web UI built on Angular and Material UI.
  - A build system based on Gradle that build everything to an executable fat `.war`.
  - Visual Studio Code support.

## Comming Soon
  - Swagger documentation
  - Docker support
  - CI/CD pipeline
  - Reactive programming using R2DBC or MongoDB

## Getting Started
### Required Software
  - [OpenJDK 11+](https://adoptopenjdk.net)
  - [NodeJS 12+](https://nodejs.org)
  - [Visual Studio Code](https://code.visualstudio.com) for development
  - [Gradle](https://gradle.org) for building from CLI

### Development Configurations
Create a file at `server/src/resouces/application-dev.yaml` and override any configuration from its sibling `application.yaml` as needed.

### Commands
  - Install UI dependencies
```shell
cd ui
npm i
```
  - Start API development server. Not recommended because hot code replacement will not work unless you're using IDE like IntelliJ. If you're using Visual Studio Code, it's recommended that you start the web server inside vscode.
```shell
./gradlew bootRun
```
  - Start UI development server
```shell
cd ui
npm run dev
```
  - Build fat war distribution
```shell
./gradlew build
```

### Setting Up IDE
Install the following Visual Studio Code plugins
  - [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
  - [Spring Boot Tools](https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-spring-boot)
  - [Lombok Annotations Support for VS Code](https://marketplace.visualstudio.com/items?itemName=GabrielBB.vscode-lombok)
  - [EditorConfig](https://marketplace.visualstudio.com/items?itemName=EditorConfig.EditorConfig)
  - [Checkstyle for Java](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle)
  - [Angular Language Service](https://marketplace.visualstudio.com/items?itemName=Angular.ng-template)

Debug the API server by pressing `F5` or go to `Debug > Start Debugging`. Hot code replacement is also supported when running under Visual Studio Code.

## Documentation
Please read [here](/docs).
