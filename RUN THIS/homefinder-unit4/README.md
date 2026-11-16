# HomeFinder - Unit 4 (Spring Boot, Part 1)

A REST API on top of the MySQL database from Unit 3. The React app from Unit 2 will read from it.

## What you need
- JDK 17 or newer, Maven (or IntelliJ / Eclipse / STS, which include Maven support)
- MySQL running with the `homefinder` database from Unit 3 (run 01_schema.sql and 02_sample_data.sql)

## Run it
1. Open `homefinder-api/src/main/resources/application.properties` and set YOUR MySQL password.
2. From the folder `homefinder-api`:

       mvn spring-boot:run

   (or open the folder in your IDE and run `HomeFinderApplication`)
3. Open http://localhost:8080/api/hello  ->  "HomeFinder API is running"
4. Open http://localhost:8080/api/properties  ->  JSON list of properties
5. Try every endpoint with `requests.http` (VS Code REST Client / IntelliJ) or Postman.

## Run the tests (no MySQL needed, tests use an in-memory H2 database)

    mvn test

## Connect React (Unit 2)
1. Copy `react-changes/useProperties.js` over `react-app/src/hooks/useProperties.js`.
2. Start Spring Boot (port 8080), then the React app with `npm run dev` (port 5173).
3. The Listings page now shows data from MySQL. Add a property with `requests.http`, refresh React: it appears.
The controller has `@CrossOrigin` for http://localhost:5173, otherwise the browser blocks the call.

---------------------------------------------------------------------

## 1. Basic concepts

**Spring** is a Java framework built around two ideas:
- Inversion of Control: Spring creates and manages your objects (called *beans*).
- Dependency Injection: Spring passes the objects a class needs into its constructor.
  `PropertyService` asks for a `PropertyRepository` in its constructor; we never write `new`.

**Spring Boot** is Spring with the setup done for you:
- Starters: one dependency (`spring-boot-starter-web`) brings Tomcat, Spring MVC and Jackson.
- Auto-configuration: Boot looks at the jars on the classpath and configures them. See pom.xml:
  web starter -> embedded Tomcat on port 8080; JPA + MySQL driver + the `spring.datasource.*`
  lines -> a connection pool (the Unit 3 `Connection`) and Hibernate. We wrote no XML and no config classes.
- Embedded server: no separate Tomcat install; the app is just `main()`.

## 2. Architecture: three-tier, three-layer

Three tiers (where things run):   React (browser)  ->  Spring Boot (server)  ->  MySQL (database)

Three layers (inside Spring Boot):

    HTTP request from React
          |
    controller/   PRESENTATION  PropertyController   receives request, returns JSON, no rules, no SQL
          |
    service/      BUSINESS      PropertyService      the rules (price > 0, status values, filters)
          |
    repository/   PERSISTENCE   PropertyRepository   talks to MySQL (replaces PropertyDAO of Unit 3)
          |
    model/        DOMAIN        Property             the object that represents one table row

A layer only talks to the layer directly below it. That is why you can test each layer alone.

## 3. Modeling our domain
`model/Property.java` is a JPA `@Entity` mapped to the `properties` table. Its fields are the same
names as in properties.js, so `imageUrl` in Java becomes `imageUrl` in JSON and `image_url` in MySQL.

## 4. Business logic
`service/PropertyService.java`: validation rules, the search/status/max-price filter, update, delete.
Rule violations throw exceptions; `exception/GlobalExceptionHandler.java` turns them into
HTTP 400 / 404 / 409 with a JSON message like `{"error":"Price must be greater than 0"}`.

## 5. Presentation layer
`controller/PropertyController.java`

| Method | URL | Result |
|---|---|---|
| GET | /api/properties?status=&maxPrice=&search= | list (filters optional) |
| GET | /api/properties/{id} | one property or 404 |
| POST | /api/properties | create, returns 201 |
| PUT | /api/properties/{id} | update |
| DELETE | /api/properties/{id} | 204, or 409 if inquiries/favorites exist |

`HelloController` is the "skeleton web app": one endpoint to prove the setup works.

## 6. Testing basics and testing in Spring Boot
Test pyramid: many small fast tests, fewer big ones. Pattern in every test: Arrange, Act, Assert.

| Test | Type | What starts | Layer |
|---|---|---|---|
| PropertyServiceTest | Unit test (JUnit 5 + Mockito) | nothing; repository is a mock | Business |
| PropertyControllerTest | `@WebMvcTest` slice + MockMvc | web layer only; service is a `@MockBean` | Presentation |
| PropertyRepositoryTest | `@DataJpaTest` slice | JPA + H2 in memory | Persistence |
| HomeFinderApplicationTests | `@SpringBootTest` | the whole application | Everything |

`src/test/resources/application.properties` replaces the MySQL settings with H2 during tests.

## Common errors
- "Access denied for user" -> wrong password in application.properties
- "Communications link failure" -> MySQL is not running
- "Table 'homefinder.properties' doesn't exist" -> run the Unit 3 scripts
- "Port 8080 already in use" -> stop the other program or set `server.port=8081`
- React shows nothing / CORS error in the browser console -> Spring Boot not running, or React is not on port 5173
- Java version error -> `java -version` must be 17 or higher

## Project structure

    homefinder-api/
      pom.xml
      requests.http
      src/main/java/com/homefinder/
        HomeFinderApplication.java
        controller/  HelloController, PropertyController
        service/     PropertyService
        repository/  PropertyRepository
        model/       Property
        exception/   PropertyNotFoundException, GlobalExceptionHandler
      src/main/resources/application.properties
      src/test/...   (4 test classes + test application.properties)
    react-changes/useProperties.js
