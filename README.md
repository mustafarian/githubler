Githubler REST Demo
===================

An example application demonstrating creating a REST endpoint that consumes the github search api endpoint.

## Building and Running the Application 

1. Update the application.properties files.  
   In order to access github's api the caller needs to provide credentials. In this application these are stored in properties files: ./src/main/resources/application.properties, and ./src/test/resources/application.properties. 

   Generating a github token can be done [here](https://github.com/settings/tokens).

2. Build the project
   From the current directory run:
 
        mvn clean package

3. Run the application
    
        mvn spring-boot:run 

4. Access the application
      
        curl http://127.0.0.1:8080/search?lang=java

## Running in a docker container
 
1. Build the docker image using maven

        mvn package docker:build

2. Find the image id

        docker images --all|grep foobar/githubler

3. Launch the container

        docker run <image-id>

4. Connect to the service 
    Find the running container ip address

        docker ps
        docker inspect <container-name>|grep IPAddress
        curl http://<ip-address>:8080/search?lang=java
