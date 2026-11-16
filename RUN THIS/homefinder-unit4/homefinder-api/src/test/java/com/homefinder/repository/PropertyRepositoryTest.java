package com.homefinder.repository;

import com.homefinder.model.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SLICE TEST: starts only JPA + an in-memory H2 database.
 * Checks that our Property class really maps to a table and can be saved and read.
 */
@DataJpaTest
class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository repository;

    private Property newProperty() {
        Property p = new Property();
        p.setTitle("Repo Test Flat");
        p.setPrice(new BigDecimal("1234567.00"));
        p.setLocation("Noida");
        p.setType("Apartment");
        p.setStatus("For Sale");
        p.setImageUrl("http://img/x.jpg");
        return p;
    }

    @Test
    void save_generatesId_andCanBeReadBack() {
        Property saved = repository.save(newProperty());

        assertNotNull(saved.getId());
        Property found = repository.findById(saved.getId()).orElseThrow();
        assertEquals("Repo Test Flat", found.getTitle());
        assertEquals("http://img/x.jpg", found.getImageUrl());
    }

    @Test
    void deleteById_removesRow() {
        Property saved = repository.save(newProperty());

        repository.deleteById(saved.getId());

        assertFalse(repository.existsById(saved.getId()));
    }
}
