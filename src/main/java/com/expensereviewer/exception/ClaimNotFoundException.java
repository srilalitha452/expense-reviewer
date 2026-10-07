package com.expensereviewer.exception;

public class ClaimNotFoundException extends RuntimeException {

    public ClaimNotFoundException(Long id) {
        super("Claim not found with id: " + id);
    }
}