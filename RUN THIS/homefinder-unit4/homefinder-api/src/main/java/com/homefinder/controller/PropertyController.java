package com.homefinder.controller;

import com.homefinder.model.Property;
import com.homefinder.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * PRESENTATION LAYER: receives HTTP requests, calls the service, returns JSON.
 * No business rules and no SQL here.
 *
 * @CrossOrigin lets the React app (running on port 5173) call this API (port 8080).
 */
@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "http://localhost:5173")
public class PropertyController {

    private final PropertyService service;

    public PropertyController(PropertyService service) {
        this.service = service;
    }

    // GET /api/properties?status=For Rent&maxPrice=30000&search=pune
    @GetMapping
    public List<Property> getAll(@RequestParam(required = false) String status,
                                 @RequestParam(required = false) BigDecimal maxPrice,
                                 @RequestParam(required = false) String search) {
        return service.findAll(status, maxPrice, search);
    }

    // GET /api/properties/3
    @GetMapping("/{id}")
    public Property getOne(@PathVariable Integer id) {
        return service.findById(id);
    }

    // POST /api/properties   (JSON body -> Property object)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Property create(@RequestBody Property property) {
        return service.create(property);
    }

    // PUT /api/properties/3
    @PutMapping("/{id}")
    public Property update(@PathVariable Integer id, @RequestBody Property property) {
        return service.update(id, property);
    }

    // DELETE /api/properties/3
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
