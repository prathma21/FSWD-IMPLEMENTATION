package com.homefinder.controller;

import com.homefinder.exception.PropertyNotFoundException;
import com.homefinder.model.Property;
import com.homefinder.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * SLICE TEST: starts only the web layer (controller + JSON), not the database.
 * MockMvc sends fake HTTP requests. The service is replaced by a @MockBean.
 */
@WebMvcTest(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService service;

    private Property sample() {
        Property p = new Property();
        p.setId(1);
        p.setTitle("Test Flat");
        p.setPrice(new BigDecimal("5000000"));
        p.setStatus("For Sale");
        p.setImageUrl("http://img/1.jpg");
        return p;
    }

    @Test
    void getAll_returnsJsonList() throws Exception {
        when(service.findAll(null, null, null)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Flat"))
                .andExpect(jsonPath("$[0].imageUrl").value("http://img/1.jpg"));   // same name React uses
    }

    @Test
    void getOne_returns404_whenNotFound() throws Exception {
        when(service.findById(99)).thenThrow(new PropertyNotFoundException(99));

        mockMvc.perform(get("/api/properties/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Property with id 99 not found"));
    }

    @Test
    void create_returns201() throws Exception {
        when(service.create(any(Property.class))).thenReturn(sample());

        String json = "{\"title\":\"Test Flat\",\"price\":5000000,\"status\":\"For Sale\"}";

        mockMvc.perform(post("/api/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_returns400_whenRuleBroken() throws Exception {
        when(service.create(any(Property.class))).thenThrow(new IllegalArgumentException("Price must be greater than 0"));

        mockMvc.perform(post("/api/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"X\",\"price\":0,\"status\":\"For Sale\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Price must be greater than 0"));
    }
}
