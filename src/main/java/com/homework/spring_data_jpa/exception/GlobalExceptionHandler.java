package com.homework.spring_data_jpa.exception;

import com.homework.spring_data_jpa.model.dto.DeleteAndErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<DeleteAndErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        DeleteAndErrorResponse apiResponse = new DeleteAndErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        ) {
        };
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DeleteAndErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        DeleteAndErrorResponse apiResponse = new DeleteAndErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
