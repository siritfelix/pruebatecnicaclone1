package com.superheroes.pruebatecnica.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String mensaje) {
        super(mensaje);
    }

}
