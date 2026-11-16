package com.homefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point. @SpringBootApplication =
 *   @Configuration + @ComponentScan (finds our classes in com.homefinder.*) + @EnableAutoConfiguration
 * Auto-configuration sees "web", "JPA" and "MySQL driver" on the classpath and sets up
 * Tomcat, the DataSource (the Unit 3 Connection) and Hibernate for us.
 */
@SpringBootApplication
public class HomeFinderApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeFinderApplication.class, args);
    }
}
