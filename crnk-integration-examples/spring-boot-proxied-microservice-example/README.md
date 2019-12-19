# spring-boot-proxied-microservice-example

Builds on the Spring boot microservice examples and removes the need for the consuming service 
have any/complete knowledge of the structure of the remote resource.

One service serves projects, while the other tasks. A JSON:API relationship is introduced
between task and project which spans the two services.

The Task Service uses a Project 'proxy resource'. This has a map decorated with @JsonAnyGetter/@JsonAnySetter.
to store any property returned from the remote resource that an not known to the client.

Known properties are mapped. None matching properties are added to the attributes map. 

In order to run this example do:

## How to run with Gradle

	gradlew :crnk-integration-examples:spring-boot-proxied-microservice-example:run

## The service will be available at
 
 	http://localhost:8080/
