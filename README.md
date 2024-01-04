# Ticketguru 

A sample project aiming testing Java Virtual Threads and, at the same time, showing how to do that using a implementation of (almost) real use case. 😉🚀

---


#### Usage
To run in development mode
```shell
mvn quarkus:dev
```
*It must be executed with wrapper in case Maven not been previously installed locally in your machine. 
```shell
./mvnw quarkus:dev
```



This micro *(to not say tiny)* service has three endpoints that might be found in: 

💻 http://localhost:8080/q/swagger-ui/#/ 



All of them returns the same, just differs *how* they perform their roles. Although names are self explained, they are:

- ***/search*** - Sequential approach doing regular calls based on platform threads.
- ***/search-async*** - Async calls based on Completable Future and Thread Pool over platform threads.
- ***/search-vt*** - Sequential calls using the brand new Virtual Threads



#### 💾 Tech Stack 
- Java 21
- Quarkus
- Maven