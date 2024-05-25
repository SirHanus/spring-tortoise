# TortoiseManager

## Description

This project is designed to showcase the development of a robust application using Spring Framework, IntelliJ IDEA, and PostgreSQL. It aims to implement a series of endpoints, perform calculations on data arrays, and adhere to best coding practices.

## Features

- **Spring Framework**: Leverage Spring for application development.
- **IntelliJ IDEA**: Developed using IntelliJ IDEA for efficient coding and debugging.
- **PostgreSQL**: Utilizes PostgreSQL for database management.
- **Endpoints**: Includes at least 10 different endpoints (GET, POST, PUT, DELETE included).
- **Calculations**: Performs at least 5 distinct calculations on arrays of data.
- **DRY Principle**: Ensures the code follows the DRY (Don't Repeat Yourself) principle.
- **Methodology**: Enforces that each method performs a single task and adheres to the 20 lines and 2 indentations limit.
- **Error Handling**: Implements comprehensive error handling to manage exceptions.
- **Database Migrations**: Uses migrations for setting up and updating database tables.
- **Documentation**: Provides web-based documentation for all endpoints and models.
- **Testing**:
  - Unit tests for all calculations.
  - Integration tests for all endpoints.
  - Performance tests for all endpoints.

## Installation

### Prerequisites

- JAVA 21+
- Ensure you have Docker installed on your machine. If not, download and install it from [Docker's official site](https://www.docker.com/get-started).

### Docker Configuration

Run the following command to start a PostgreSQL container:

```bash
docker run --name postgres -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123 -p 5433:5432 -d postgres:latest
```

Run the following command to start a Keycloak container:
```bash
docker run --name keycloak-container -p 8050:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:24.0.4 start-dev
```

To export the Keycloak realm, use the following commands:

```bash
docker exec -it keycloak-container  /opt/keycloak/bin/kc.sh export --realm TortoiseManager --dir /tmp/export --users same_file

docker cp keycloak-container:/tmp/export/TortoiseManager-realm.json D:\
```
To import the Keycloak realm, use the following commands:
```bash
docker cp TortoiseManager-realm.json keycloak-container:/tmp/TortoiseManager-realm.json

docker exec -it keycloak-container /opt/keycloak/bin/kc.sh import --file /tmp/TortoiseManager-realm.json
```
