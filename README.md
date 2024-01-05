# Ticketguru 🛫

A sample project aiming testing Java Virtual Threads and, at the same time, showing how to do that using a implementation of (almost) real use case. 😉🚀

Basically, we want to simulate a travel agency fetching for flight tickets in different providers. To accomplish this mission we trying three different approaches. 



#### 💾 Tech Stack (that you'll need) 

- [Java 21](https://www.oracle.com/br/java/technologies/downloads/)
- [Quarkus](https://quarkus.io/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [K6](https://k6.io/)



---



> This application has made specifically to complement topics discussed in these two articles about **Java Virtual Threads**. 
>
> #### [Virtual Threads. First impressions (part.1)](https://medium.com/@anderanjos.ti/virtual-threads-first-impressions-part-1-ffbe5b26cf19)
>
> #### [Virtual Threads. Let’s running fast… (part.2)](https://medium.com/@anderanjos.ti/virtual-threads-lets-running-fast-part-2-6b55846d112e)
>
> Eventually, important aspects as package structure, test coverage, properly exception handling and others were left aside, for sake of simplicity.



------



#### 🔧 Infra

Before running the application it is necessary provides the infrastructure required. Therefore, execute *Docker Compose* script placed in: `src/main/docker/docker-compose.yaml`

It will spin up 2 fake company providers, responsible for simulate our flight ticket providers.
And they might be accessed in:

```shell
curl --location 'localhost:8081/api/ticket'
curl --location 'localhost:8082/api/ticket'
```



#### 📝 Usage

After docker compose script already have running, to start Ticketguru in development mode use:
```shell
mvn quarkus:dev
```
**It must be executed with wrapper in case Maven not been previously installed locally in your machine.* 

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



### Running the performance test

[Since K6 loading test tool has been installed](https://k6.io/docs/get-started/installation/), you'll need to execute it against Ticketguru application.
To do this, we provide a quite straightforward script, located in .... It contains:

```  javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {

  stages: [
    { duration: '20s', target: 200 }, // Ramp up config
    { duration: '60s', target: 1000}, // Total users simulated in the test
    { duration: '20s', target: 0 },
  ]
};

export default function () {
  const res = http.get('http://localhost:8080/api/search-vt') // microservice url
  check(res, { 'status was 200': (r) => r.status == 200 })
  sleep(1)
}
```



Feel free to play with script and try different test setup! 🤖


---

### 🤓 Author

#####    $ ➜ ~/anderanjos

##### [![LinkedIn Badge](https://img.shields.io/badge/LinkedIn-Profile-informational?style=flat&logo=linkedin&logoColor=white&color=0D76A8)](https://www.linkedin.com/in/anderanjos/)

