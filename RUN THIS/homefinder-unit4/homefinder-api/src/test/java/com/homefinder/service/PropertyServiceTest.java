package com.homefinder.service;

import com.homefinder.exception.PropertyNotFoundException;
import com.homefinder.model.Property;
import com.homefinder.repository.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UNIT TEST: tests ONE class (the service) in isolation.
 * The repository is a Mockito "mock" (a fake), so no database and no Spring are started.
 * Fast and simple. Pattern: Arrange (set up) -> Act (call) -> Assert (check).
 */
@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository repository;

    @InjectMocks
    private PropertyService service;

    private Property sample(Integer id, String status, String price) {
        Property p = new Property();
        p.setId(id);
        p.setTitle("Test Flat");
        p.setLocation("Pune");
        p.setStatus(status);
        p.setPrice(new BigDecimal(price));
        return p;
    }

    @Test
    void findById_returnsProperty_whenItExists() {
        when(repository.findById(1)).thenReturn(Optional.of(sample(1, "For Sale", "100")));

        Property result = service.findById(1);

        assertEquals("Test Flat", result.getTitle());
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(PropertyNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void create_rejectsPriceZero() {
        Property bad = sample(null, "For Sale", "0");

        assertThrows(IllegalArgumentException.class, () -> service.create(bad));
        verify(repository, never()).save(any());          // nothing must reach the database
    }

    @Test
    void create_rejectsInvalidStatus() {
        Property bad = sample(null, "Sold", "5000");

        assertThrows(IllegalArgumentException.class, () -> service.create(bad));
    }

    @Test
    void create_savesValidProperty() {
        when(repository.save(any(Property.class))).thenAnswer(call -> call.getArgument(0));

        Property saved = service.create(sample(null, "For Rent", "15000"));

        assertEquals("For Rent", saved.getStatus());
        verify(repository).save(any(Property.class));
    }

    @Test
    void findAll_filtersByStatusAndMaxPrice() {
        when(repository.findAll(any(Sort.class))).thenReturn(List.of(
                sample(1, "For Sale", "100"),
                sample(2, "For Rent", "50"),
                sample(3, "For Rent", "500")));

        List<Property> result = service.findAll("For Rent", new BigDecimal("100"), null);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getId());
    }

    @Test
    void delete_throwsNotFound_whenIdMissing() {
        when(repository.existsById(5)).thenReturn(false);

        assertThrows(PropertyNotFoundException.class, () -> service.delete(5));
        verify(repository, never()).deleteById(any());
    }
}
