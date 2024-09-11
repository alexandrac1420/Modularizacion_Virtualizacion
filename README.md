# Microservices Project with Spring Boot and Docker

This project implements a microservices architecture using Spring Boot, Docker, and MongoDB. The system allows users to send messages through a load balancer that distributes requests among multiple instances of the logging service, which then stores the data in a MongoDB database. The results of the last 10 logs are displayed on a web interface.

![Demo GIF](https://github.com/alexandrac1420/Modularizacion_Virtualizacion/blob/master/Pictures/Dise%C3%B1o%20sin%20t%C3%ADtulo%20(1).gif)
![Demo GIF](https://github.com/alexandrac1420/Modularizacion_Virtualizacion/blob/master/Pictures/download.gif)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need to install the following tools and configure their dependencies:

1. **Java** (versions 7 or 8)
    ```sh
    java -version
    ```
    Should return something like:
    ```sh
    java version "1.8.0"
    Java(TM) SE Runtime Environment (build 1.8.0-b132)
    Java HotSpot(TM) 64-Bit Server VM (build 25.0-b70, mixed mode)
    ```

2. **Maven**
    - Download Maven from [here](http://maven.apache.org/download.html)
    - Follow the installation instructions [here](http://maven.apache.org/download.html#Installation)

    Verify the installation:
    ```sh
    mvn -version
    ```
    Should return something like:
    ```sh
    Apache Maven 3.2.5 (12a6b3acb947671f09b81f49094c53f426d8cea1; 2014-12-14T12:29:23-05:00)
    Maven home: /Users/dnielben/Applications/apache-maven-3.2.5
    Java version: 1.8.0, vendor: Oracle Corporation
    Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0.jdk/Contents/Home/jre
    Default locale: es_ES, platform encoding: UTF-8
    OS name: "mac os x", version: "10.10.1", arch: "x86_64", family: "mac"
    ```

3. **Git**
    - Install Git by following the instructions [here](http://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

    Verify the installation:
    ```sh
    git --version
    ```
    Should return something like:
    ```sh
    git version 2.2.1
    ```

4. **Docker**
    - Install Docker by following the instructions [here](https://docs.docker.com/get-docker/).
    - Verify the installation:
    ```sh
    docker --version
    ```

### Installing

1. Clone the repository and navigate into the project directory:
    ```sh
    git clonehttps://github.com/alexandrac1420/Modularizacion_Virtualizacion.git

    cd Modularizacion_Virtualizacion
    ```

2. Build the project:
    ```sh
    mvn package
    ```

    Should display output similar to:
    ```sh
      [INFO] --- jar:3.3.0:jar (default-jar) @ Spark ---
      [INFO] Building jar: C:\Users\alexa\OneDrive\Escritorio\Servidores_Aplicaciones\target\SpringEci-1.0-SNAPSHOT.jar
      [INFO] BUILD SUCCESS
    ```

3. Run the application:
    ```sh
    java -jar target/DockerAWS-1.0-SNAPSHOT.jar

    ```
    And now you can access `index.html` and other static files in http://localhost:8080/index.html

## Usage

The user interface allows users to send messages and interact with various services in the system. Below are the key services.

#### 1. Logging Service

- **Description:** Allows sending messages that will be stored in the MongoDB database.
- **How to use it:** Enter a message in the text field and press "Send".
- **Expected behavior:** The message will be stored in MongoDB, and you will be able to see the last 10 messages.

#### 2. Load Balancer Service

- **Description:** The load balancer distributes requests among the different instances of `LogService` using a round-robin algorithm.
- **Expected behavior:** Requests are evenly distributed among the instances of `LogService`.


    
## Architecture

![Architecture Diagram](https://github.com/alexandrac1420/Modularizacion_Virtualizacion/blob/master/Pictures/Arquitectura.png)

This project is designed using a microservices architecture, where each service is decoupled and independently managed. The architecture is containerized using Docker, allowing the application to be deployed and scaled across multiple environments, such as local development, cloud infrastructure, and CI/CD pipelines. Below is a detailed breakdown of the system's architecture and how its components interact.

### Overview

The system is composed of several key components:

1. **Client (Browser)**: The user interacts with the system through a web interface. The client sends HTTP requests to the backend services using a simple form where messages are submitted. The client can see the results directly on the interface, including the last 10 logs returned from the backend.
   
2. **Load Balancer (Round Robin)**: The `LoadBalancerController` acts as a central point to distribute incoming requests among multiple instances of `LogService`. The round-robin algorithm ensures that requests are evenly distributed across all available `LogService` instances, providing fault tolerance and load distribution. Each incoming request is forwarded to one of the `LogService` instances in a cyclic order.

3. **LogService**: Each instance of `LogService` is a Spring Boot-based microservice responsible for receiving and processing log messages. Once a message is received, it is stored in a MongoDB database. The `LogService` then responds with the last 10 logs stored in the database, which are displayed to the client. Each `LogService` instance is stateless, making them easy to scale horizontally.

4. **MongoDB (Database)**: MongoDB is used to store log messages. The database is containerized using Docker and runs in a separate container. It acts as a central repository for all logs generated by the `LogService` instances. MongoDB provides high availability, scalability, and the ability to handle unstructured data efficiently. The database stores each log entry with a message and a timestamp, and the service can query the most recent 10 entries efficiently.

5. **Dockerized Infrastructure**: Each service (`web`, `logservice1`, `logservice2`, `logservice3`, `db`) runs in its own Docker container. Docker Compose is used to orchestrate and manage these services, ensuring that the containers are networked together and that the services can communicate seamlessly. This containerized setup simplifies deployment and makes the entire system portable and easy to scale.

### Interaction Flow

1. **Client Request**: 
   - The client (browser) sends an HTTP POST request containing a message to the load balancer (`/submit` endpoint). This request originates from the frontend, which presents a simple form for message input.
   
2. **Load Balancing**:
   - The `LoadBalancerController` receives the client request and forwards it to one of the `LogService` instances using the round-robin algorithm. This ensures that each `LogService` instance handles an equal share of incoming requests, preventing overload on any single instance.

3. **Message Processing**:
   - The selected `LogService` instance processes the incoming message by storing it in the MongoDB database. It creates a `LogEntry` object containing the message and a timestamp, and then persists this object in the database.

4. **Database Interaction**:
   - MongoDB, running in its own Docker container, stores the logs in a collection. The logs are indexed by timestamp to allow for efficient retrieval of the most recent entries.

5. **Response Generation**:
   - Once the message is stored, the `LogService` instance queries MongoDB to retrieve the last 10 log entries. These are returned as a JSON array to the load balancer, which then forwards the response back to the client.

6. **Client Response**:
   - The client receives the response from the load balancer, which contains the last 10 logs in JSON format. These logs are then displayed on the web interface, providing immediate feedback to the user.


## Class Diagram

![Class Diagram](https://github.com/alexandrac1420/Modularizacion_Virtualizacion/blob/master/Pictures/diagramaClases.png)

### Overview

The main classes in the system can be grouped into three categories:

1. **Controllers**: These handle incoming HTTP requests and route them to the appropriate services.
2. **Models**: Data structures representing the information that is stored in the MongoDB database.
3. **Repositories**: These provide an abstraction over MongoDB operations, allowing the system to interact with the database in a structured way.

### Detailed Class Breakdown

#### 1. `RestServiceApplication`

- **Location**: `edu.escuelaing.arep`
- **Description**: This is the main entry point of the Spring Boot application. It sets up the default server port and initializes the entire web service.
- **Key Methods**:
  - `main(String[] args)`: Launches the application.
  - `getPort()`: Determines the port on which the service will run (defaults to 6000 unless an environment variable is set).

#### 2. `LoadBalancerController`

- **Location**: `edu.escuelaing.arep.controller`
- **Description**: The `LoadBalancerController` is responsible for distributing incoming POST requests across multiple instances of the `LogService`. It uses a simple round-robin algorithm to balance the load evenly.
- **Key Attributes**:
  - `instances`: An array storing the URLs of the `LogService` instances.
  - `currentInstance`: Tracks the current instance in the round-robin sequence.
- **Key Methods**:
  - `sendToLogService(String message)`: Accepts a message from the client and forwards it to one of the `LogService` instances, cycling through the available instances.
  
#### 3. `LogServiceController`

- **Location**: `edu.escuelaing.arep.controller`
- **Description**: The `LogServiceController` handles requests to log messages and retrieve the last 10 logs. It interacts with the MongoDB database through the `LogRepository`.
- **Key Attributes**:
  - `logRepository`: Injected Spring Data repository that manages access to the MongoDB database.
- **Key Methods**:
  - `logMessage(String message)`: Saves a log message to the database and returns the last 10 logs in JSON format.

#### 4. `LogEntry`

- **Location**: `edu.escuelaing.arep.model`
- **Description**: The `LogEntry` class represents a log message in the system. It is stored in MongoDB as a document and consists of two main fields: the message and a timestamp.
- **Key Attributes**:
  - `id`: Unique identifier for each log entry (generated by MongoDB).
  - `message`: The log message.
  - `timestamp`: The time the message was created.
- **Key Methods**:
  - Standard getter and setter methods for each attribute.
  - `toString()`: A method to output the log entry in a human-readable format.

#### 5. `LogRepository`

- **Location**: `edu.escuelaing.arep.repository`
- **Description**: The `LogRepository` interface extends the `MongoRepository` interface from Spring Data, providing methods to save and retrieve `LogEntry` objects from the MongoDB database.
- **Key Methods**:
  - `findTop10ByOrderByTimestampDesc()`: Custom query method that retrieves the last 10 log entries, ordered by timestamp in descending order.

### Key Class Relationships

- **RestServiceApplication**: 
  This is the root class that launches the Spring Boot application. It manages the lifecycle of all other components and binds the services to a specific port.

- **LoadBalancerController**: 
  Interacts with multiple `LogService` instances. It receives requests from the client and forwards them to a service instance using round-robin load balancing. This controller does not directly interact with MongoDB.

- **LogServiceController**: 
  Manages the core logging functionality. It receives requests from the load balancer, processes the logs, and interacts with MongoDB via `LogRepository`.

- **LogRepository**: 
  Provides the data access layer to MongoDB, allowing the service to save and retrieve logs.

- **LogEntry**: 
  Represents the log data stored in MongoDB. It is the entity that gets saved and retrieved through the `LogRepository`.


## Test Report -  Microservices with Spring Boot and Docker

### Author
- **Name**: Alexandra Cortes Tovar

### Date
- **Date**: 11/09/2024

This test report covers the verification of key components in the microservices architecture. It ensures that the application behaves as expected under various scenarios, including data persistence, load balancing, and environment configuration.

### 1. Test: `LogEntryTest.testLogEntryCreation`

- **Description:** Verifies that a `LogEntry` object is correctly created with a message and timestamp.
- **Objective:** Ensure the constructor initializes `LogEntry` fields as expected.
- **Test Scenario:** Create a `LogEntry` with a message and timestamp, and check that the values are correctly assigned.
- **Expected Behavior:** The message and timestamp fields should match the input.
- **Result:** Passed.
- **Verification:** The test confirmed that the `LogEntry` object was created with the correct message and timestamp.

### 2. Test: `LogEntryTest.testLogEntrySettersAndGetters`

- **Description:** Verifies the functionality of setter and getter methods in the `LogEntry` class.
- **Objective:** Ensure that the setters and getters work correctly.
- **Test Scenario:** Set a message and timestamp on a `LogEntry` and then retrieve the values using the getters.
- **Expected Behavior:** The retrieved values should match the set values.
- **Result:** Passed.
- **Verification:** The setter and getter methods returned the expected values.

### 3. Test: `LogRepositoryTest.testSaveAndRetrieveLog`

- **Description:** Tests saving a log entry and retrieving the last 10 logs from the `LogRepository`.
- **Objective:** Ensure the repository correctly saves and retrieves logs.
- **Test Scenario:** Simulate saving a log entry and retrieving it using the custom query method.
- **Expected Behavior:** The log entry should be saved and returned from the repository.
- **Result:** Passed.
- **Verification:** The log entry was correctly saved and retrieved, and the repository methods were called the expected number of times.

### 4. Test: `LogServiceControllerTest.testLogMessage`

- **Description:** Verifies that the `LogServiceController` saves a log message and retrieves the last 10 logs.
- **Objective:** Ensure the controller interacts correctly with the repository.
- **Test Scenario:** Simulate a log message being sent to the controller, and verify that it is saved and the last 10 logs are returned.
- **Expected Behavior:** The log message should be saved, and the correct number of logs should be returned.
- **Result:** Passed.
- **Verification:** The log message was saved, and the repository returned the correct logs.

### 5. Test: `RestServiceApplicationTest.testMain`

- **Description:** Simulates running the main method of the application.
- **Objective:** Ensure the application starts without exceptions.
- **Test Scenario:** Call the `main()` method of `RestServiceApplication`.
- **Expected Behavior:** The application should start without throwing any exceptions.
- **Result:** Passed.
- **Verification:** The test passed, indicating that the application starts successfully.

### 6. Test: `RestServiceApplicationTest.testGetPortFromEnv`

- **Description:** Verifies that the application retrieves the correct port from the environment variable.
- **Objective:** Ensure the application can read the port from the `PORT` environment variable.
- **Test Scenario:** Set the `PORT` environment variable and verify that the application retrieves it correctly.
- **Expected Behavior:** The retrieved port should match the value of the environment variable.
- **Result:** Passed.
- **Verification:** The test confirmed that the application correctly reads the port from the environment variable.

### 7. Test: `RestServiceApplicationTest.testGetDefaultPort`

- **Description:** Verifies that the application uses the default port if no environment variable is set.
- **Objective:** Ensure the application defaults to port 6000 when no `PORT` environment variable is provided.
- **Test Scenario:** Clear the `PORT` environment variable and check that the application defaults to port 6000.
- **Expected Behavior:** The port should default to 6000.
- **Result:** Passed.
- **Verification:** The test confirmed that the default port was used correctly.
   
![image](https://github.com/user-attachments/assets/f4e55516-ed59-4728-b72d-02a17dbf4e28)

### Building Docker Images

1. **Build the images for each service** and push them to Docker Hub:
    ```sh
    docker build -tag modularizacion .
    docker tag modularizacion alexandrac1420/modularizacion      
    docker push alexandrac1420/modularizacion:latest
    ```

2. The Docker image `alexandrac1420/modularizacion:latest` was pushed to the Docker Hub repository and referenced in the `docker-compose.yml` file.

3. **Run the containers** using Docker Compose:
    On the virtual machine, the `docker-compose.yml` file was uploaded, and the following command was executed to start the containers:
    ```sh
    docker-compose up -d
    ```

4. The application was then accessible through the following URL:
    [http://ec2-44-204-153-46.compute-1.amazonaws.com:8080/index.html](http://ec2-44-204-153-46.compute-1.amazonaws.com:8080/index.html)

### Docker Compose Configuration

The following `docker-compose.yml` file configures the web service, multiple instances of `LogService`, and MongoDB:

```yaml
version: '2'

services:
    web:
        image: alexandrac1420/modularizacion:latest
        container_name: web
        ports:
            - "8080:6000"
    
    logservice1:
        image: alexandrac1420/modularizacion:latest
        container_name: logservice1
        environment:
            - SPRING_DATA_MONGODB_URI=mongodb://db:27017/logs
        ports:
            - "34001:6000"
    
    logservice2:
        image: alexandrac1420/modularizacion:latest
        container_name: logservice2
        environment:
            - SPRING_DATA_MONGODB_URI=mongodb://db:27017/logs
        ports:
            - "34002:6000"
    
    logservice3:
        image: alexandrac1420/modularizacion:latest
        container_name: logservice3
        environment:
            - SPRING_DATA_MONGODB_URI=mongodb://db:27017/logs
        ports:
            - "34003:6000"
    
    db:
        image: mongo:3.6.1
        container_name: db
        volumes:
            - mongodb:/data/db
            - mongodb_config:/data/configdb
        ports:
            - "27017:27017"
        command: mongod

volumes:
    mongodb:
    mongodb_config:

```
### Verifying Execution

To verify that all containers are running correctly on the virtual machine, the following command was used:

```sh
docker ps
```

![Image](https://github.com/alexandrac1420/Modularizacion_Virtualizacion/blob/master/Pictures/image.png)

## Built With


* [Maven](https://maven.apache.org/) - Dependency Management
* [Git](http://git-scm.com/) - Version Control System
* [Docker](https://www.docker.com) - Containerization and deployment.
* [MongoDB](https://www.mongodb.com) - NoSQL database. 

## Versioning

I use [GitHub](https://github.com/) for versioning. For the versions available, see the [tags on this repository](https://github.com/alexandrac1420/Modularizacion_Virtualizacion.git).

## Authors

* **Alexandra Cortes Tovar** - [alexandrac1420](https://github.com/alexandrac1420)

## License

This project is licensed under the GNU
