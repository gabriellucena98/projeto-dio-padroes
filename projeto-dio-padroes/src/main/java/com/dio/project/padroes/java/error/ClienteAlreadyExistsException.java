package com.dio.project.padroes.java.error;

public class ClienteAlreadyExistsException extends RuntimeException{
    public ClienteAlreadyExistsException(String message) {
        super(message);
    }
}
