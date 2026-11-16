package com.homefinder.repository;

import com.homefinder.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PERSISTENCE LAYER: talks to the database.
 * This is an interface only - Spring writes the implementation at startup.
 * It gives us findAll(), findById(), save(), deleteById(), existsById() ...
 * which replace all the JDBC code of PropertyDAO from Unit 3.
 */
public interface PropertyRepository extends JpaRepository<Property, Integer> {
}
