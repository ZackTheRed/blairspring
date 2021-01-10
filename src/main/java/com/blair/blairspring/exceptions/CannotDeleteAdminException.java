package com.blair.blairspring.exceptions;

public class CannotDeleteAdminException extends RuntimeException {

    public CannotDeleteAdminException() {
        super("Cannot delete \"admin\".");
    }

}
