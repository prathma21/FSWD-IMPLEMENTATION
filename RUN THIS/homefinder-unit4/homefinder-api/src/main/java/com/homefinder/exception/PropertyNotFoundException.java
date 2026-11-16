package com.homefinder.exception;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException(Integer id) {
        super("Property with id " + id + " not found");
    }
}
