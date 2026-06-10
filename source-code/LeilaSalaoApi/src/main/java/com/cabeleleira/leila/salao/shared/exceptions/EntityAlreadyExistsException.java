package com.cabeleleira.leila.salao.shared.exceptions;

public final class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
