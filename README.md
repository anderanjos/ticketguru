# Ticketguru 🛫

A sample project aiming testing Java Virtual Threads and, at the same time, showing how to do that using a implementation of (almost) real use case. 😉🚀

Basically, we want to simulate a travel agency fetching for flight tickets in different providers. To accomplish this mission we trying three different approaches. 

---


#### 📝 Usage
To run in development mode
```shell
mvn quarkus:dev
```
*It must be executed with wrapper in case Maven not been previously installed locally in your machine. 
```shell
./mvnw quarkus:dev
```



---



This micro *(to not say tiny)* service has three endpoints that might be found in: 

💻 http://localhost:8080/q/swagger-ui/#/  **(application must be running)*



All of them returns the same payload, just differs *how* they perform their roles. Although names are self explained, a simple description follows below:

| Endpoint            | Description                                                  |
| ------------------- | ------------------------------------------------------------ |
| ***/search***       | Sequential approach doing regular calls based on platform threads. |
| ***/search-async*** | Async calls based on Completable Future and Thread Pool over platform threads. |
| ***/search-vt***    | Sequential calls using the brand new *<u>Virtual Threads</u>* |





#### 💾 Tech Stack 
- Java 21
- Quarkus
- Maven



---

### 🤓 Author

#####    $ ➜ ~/anderanjos

##### [![LinkedIn Badge](https://img.shields.io/badge/LinkedIn-Profile-informational?style=flat&logo=linkedin&logoColor=white&color=0D76A8)](https://www.linkedin.com/in/anderanjos/)

