package com.cabeleleira.leila.salao.shared.exceptions;

public final class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
