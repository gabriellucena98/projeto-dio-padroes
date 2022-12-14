package com.dio.project.padroes.java.handler;

import com.dio.project.padroes.java.error.ClienteAlreadyExistsException;
import com.dio.project.padroes.java.error.DefaultError;
import com.dio.project.padroes.java.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultError> NotFoundhandlerException(NotFoundException e) {
        DefaultError defaultError = new DefaultError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(defaultError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteAlreadyExistsException.class)
    public ResponseEntity<DefaultError> ClienteAlreadyExistshandlerException(ClienteAlreadyExistsException e) {
        DefaultError defaultError = new DefaultError(HttpStatus.CONFLICT.value(), e.getMessage());
        return new ResponseEntity<>(defaultError, HttpStatus.CONFLICT);
    }
}
