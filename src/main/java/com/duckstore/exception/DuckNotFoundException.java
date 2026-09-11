package com.duckstore.exception;

public class DuckNotFoundException extends RuntimeException {

    public DuckNotFoundException(Integer id) {
        super("Duck not found with id: " + id);
    }
}