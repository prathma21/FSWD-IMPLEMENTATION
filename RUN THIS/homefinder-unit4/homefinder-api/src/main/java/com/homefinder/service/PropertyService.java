package com.homefinder.service;

import com.homefinder.exception.PropertyNotFoundException;
import com.homefinder.model.Property;
import com.homefinder.repository.PropertyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * BUSINESS LOGIC LAYER: the rules of the application live here.
 * (not in the controller, not in the repository)
 */
@Service
public class PropertyService {

    private final PropertyRepository repository;

    // Dependency Injection: Spring creates the repository and passes it in.
    // We never write "new PropertyRepository()".
    public PropertyService(PropertyRepository repository) {
        this.repository = repository;
    }

    // Optional filters, same as the search box / dropdown / max price in React
    public List<Property> findAll(String status, BigDecimal maxPrice, String search) {
        String text = (search == null) ? "" : search.trim().toLowerCase();

        return repository.findAll(Sort.by("id")).stream()
                .filter(p -> status == null || status.isBlank() || p.getStatus().equalsIgnoreCase(status))
                .filter(p -> maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0)
                .filter(p -> text.isEmpty()
                        || p.getTitle().toLowerCase().contains(text)
                        || p.getLocation().toLowerCase().contains(text))
                .toList();
    }

    public Property findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new PropertyNotFoundException(id));
    }

    public Property create(Property property) {
        validate(property);
        property.setId(null);              // make sure it is an INSERT, not an update
        return repository.save(property);
    }

    public Property update(Integer id, Property changes) {
        validate(changes);
        Property existing = findById(id);  // throws 404 if missing

        existing.setTitle(changes.getTitle());
        existing.setPrice(changes.getPrice());
        existing.setLocation(changes.getLocation());
        existing.setBedrooms(changes.getBedrooms());
        existing.setBathrooms(changes.getBathrooms());
        existing.setArea(changes.getArea());
        existing.setType(changes.getType());
        existing.setStatus(changes.getStatus());
        existing.setImageUrl(changes.getImageUrl());
        existing.setDescription(changes.getDescription());
        existing.setAgentId(changes.getAgentId());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new PropertyNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // ---- business rules ----
    private void validate(Property p) {
        if (p.getTitle() == null || p.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (p.getPrice() == null || p.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (!"For Sale".equals(p.getStatus()) && !"For Rent".equals(p.getStatus())) {
            throw new IllegalArgumentException("Status must be 'For Sale' or 'For Rent'");
        }
    }
}
