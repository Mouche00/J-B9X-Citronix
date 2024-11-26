package org.citronix.exceptions.custom;

public class ListIsEmpty extends RuntimeException {
    public ListIsEmpty(String message, Throwable cause) {
        super(message, cause);
    }
    public ListIsEmpty(String entityName) {
        super(entityName + "s" + " liat is empty");
    }
    public ListIsEmpty() {
        super("List is empty");
    }
}
