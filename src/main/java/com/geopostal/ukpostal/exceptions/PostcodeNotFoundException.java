package com.geopostal.ukpostal.exceptions;

public class PostcodeNotFoundException extends RuntimeException {

    public PostcodeNotFoundException(Long id) {
        super("Could not find postcode id = " + id);
    }
}
