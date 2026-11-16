# HomeFinder - Unit 3 (SQL + JDBC)

## What you need
- MySQL 8.0.31 or newer (INTERSECT / EXCEPT need 8.0.31+) + MySQL Workbench (optional)
- JDK 17 or newer
- Maven (or an IDE like IntelliJ / Eclipse that can open a Maven project)

## Part A - SQL (folder: sql/)
Run the files in order. In MySQL Workbench: File > Open SQL Script > click the lightning icon.
Or from a terminal:

    mysql -u root -p < sql/01_schema.sql
    mysql -u root -p < sql/02_sample_data.sql

Then open 03, 04, 05, 06 and run the queries ONE AT A TIME (select a query, then Ctrl+Enter).

| File | Syllabus topic |
|---|---|
| 01_schema.sql | Data basics, CREATE TABLE, primary and foreign keys |
| 02_sample_data.sql | INSERT |
| 03_basic_queries.sql | Basic selection: WHERE, ORDER BY, LIMIT, aggregates, GROUP BY |
| 04_joins.sql | INNER, LEFT, RIGHT, multi-table and self joins |
| 05_set_queries_and_subqueries.sql | UNION, INTERSECT, EXCEPT, subqueries |
| 06_modify_data_and_tables.sql | INSERT, UPDATE, DELETE, CREATE / ALTER / DROP TABLE |

Tables:  users (buyers + agents) -> properties (agent_id) -> inquiries, favorites
NOTE: script 06 changes data (for example it raises rents by 5%). To reset, run 01 and 02 again.

## Part B - JDBC (folder: jdbc-demo/)
1. Open `src/main/java/com/homefinder/DBConnection.java` and put YOUR MySQL password in PASSWORD.
2. Run `TestConnection` first. You should see "Connected successfully!".
3. Run `Main` (a console menu).

With Maven from a terminal:

    cd jdbc-demo
    mvn compile exec:java -Dexec.mainClass=com.homefinder.TestConnection
    mvn compile exec:java -Dexec.mainClass=com.homefinder.Main

| Class | Syllabus topic |
|---|---|
| DBConnection | Connection interface, DriverManager, connection URL |
| PropertyDAO.findAll / count | Statement interface, ResultSet interface |
| PropertyDAO.findById / search | Querying with PreparedStatement (placeholders prevent SQL injection) |
| PropertyDAO.insert / updatePrice / delete | Updating the database: executeUpdate() |
| PropertyDAO.printPropertiesWithAgents | JOIN query read through a ResultSet |
| Property | One row = one object (becomes the Domain model in Unit 4) |

## Common errors
- "Access denied for user" -> wrong password in DBConnection.java
- "Communications link failure" -> MySQL is not running
- "Unknown database 'homefinder'" -> run 01_schema.sql
- "No suitable driver" -> the Maven dependency did not download; run `mvn compile` with internet on
- "Cannot delete or update a parent row" -> the property has inquiries/favorites; delete one you added yourself

## How this connects to Unit 4 (Spring Boot)
- properties table columns = Property.java fields = properties.js fields in React.
- PropertyDAO = the persistence layer. Spring Boot will give you this layer with less code.
- Property.java = the domain model. A REST controller will return it as JSON to the React app.
- Same MySQL database `homefinder` and the same user/password will go in application.properties.
