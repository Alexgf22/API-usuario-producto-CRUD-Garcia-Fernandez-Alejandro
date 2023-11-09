package com.example.demo.error;

// throw new ProductNotFoundException("Producto no encontrado con id " + id);
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}