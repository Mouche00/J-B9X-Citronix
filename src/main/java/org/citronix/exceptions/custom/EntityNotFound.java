package org.citronix.exceptions.custom;

public class EntityNotFound extends RuntimeException {
    private String id;

    public EntityNotFound(String message, Throwable cause) {
        super(message, cause);
    }
    public EntityNotFound(String id) {
        super("Entity with ID: " + id + " not found");
    }
}
