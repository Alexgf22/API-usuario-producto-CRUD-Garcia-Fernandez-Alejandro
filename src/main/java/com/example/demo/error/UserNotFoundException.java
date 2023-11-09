package com.example.demo.error;

// throw new UserNotFoundException("Usuario no encontrado con id " + id);
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}