## Reactive Web with SpringBoot

Reactive web implementation allows to make non blocking calls to backend systems. This prevents thread starvation
from happening in applications with high transaction volume. Reactive implementation also helps clients from getting overwhelmed when high volume of
data being returned. 

### Reactive Web in Action
![webflux-edge.gif](src%2Fmain%2Fresources%2Fstatic%2Fwebflux-edge.gif)

![webflux-curl.gif](src%2Fmain%2Fresources%2Fstatic%2Fwebflux-curl.gif)

### Key Considerations

#### Noteworthy Dependencies

For Reactive Web and Reactive JDBC
```
implementation 'org.springframework.boot:spring-boot-starter-webflux'
implementation 'org.springframework.boot:spring-boot-starter-data-r2dbc'
runtimeOnly 'org.postgresql:r2dbc-postgresql'
```

For Flyway migration tool
```
runtimeOnly 'org.flywaydb:flyway-core'
implementation 'org.springframework:spring-jdbc'
runtimeOnly 'org.postgresql:postgresql'
```

#### Application Properties

```agsl
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/webflux
    username: <user>
    password: <password>
  flyway:
    url: jdbc:postgresql://localhost:5432/webflux
    user: <user>
    password: <password>
```

#### Repository

Please note that we are not using JpaRepository anymore. So tables wont get
created automatically. We will have to have a DB migration tool like Flyway to create 
tables etc.

```
public interface BookReactiveRepository extends ReactiveCrudRepository<Book, Integer>
```
#### Controller
Please note that controller method is returning a Flux and produces 
media type of MediaType.TEXT_EVENT_STREAM_VALUE
```
@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<Book> getBooks(){
    log.info("Getting all books");
    return bookService.getBooks().delayElements(Duration.ofSeconds(1));
}
```

### Getting Started
Set up PostgreSQL
```
docker compose up -d
```
Test the application
```
./gradlew test
```
Run the application
```
./gradlew bootRun
```

Access the application

```agsl
curl http://localhost:8080/api/books
```
or,

http://localhost:8080/api/books



