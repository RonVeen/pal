package org.ninjaware.pal.rest;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String id) {
        super(String.format("not found: %s", id));
    }
}
