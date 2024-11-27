package org.citronix.exceptions.custom;

public class ConstraintFailed extends RuntimeException {
    private String id;

    public ConstraintFailed(String message, Throwable cause) {
        super(message, cause);
    }
    public ConstraintFailed(String entityName) {
        super(entityName + " constraints failed");
    }
}
