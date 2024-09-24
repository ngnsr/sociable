package com.rr.sociable;

import com.rr.sociable.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {


    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .toList();

        Map<String, Object> body = Map.of(
                "timestamp", new Date(),
                "status", status,
                "errors", errors
        );

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", new Date(),
                "status", HttpStatus.NOT_FOUND,
                "errors", List.of(ex.getMessage())
        );

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", new Date(),
                "status", HttpStatus.CONFLICT,
                "errors", List.of(ex.getMessage())
        );

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<Object> handleInvalidJWTTokenException(InvalidJwtTokenException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", new Date(),
                "status", HttpStatus.BAD_REQUEST,
                "errors", List.of(ex.getMessage())
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyInGroupException.class)
    public ResponseEntity<Object> handleUserAlreadyInGroupException(UserAlreadyInGroupException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", new Date(),
                "status", HttpStatus.FORBIDDEN,
                "errors", List.of(ex.getMessage())
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotInGroupException.class)
    public ResponseEntity<Object> handleUserNotInGroupException(UserNotInGroupException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", new Date(),
                "status", HttpStatus.FORBIDDEN,
                "errors", List.of(ex.getMessage())
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<Object> handleUserNotInGroupException(InvalidArgumentException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", new Date(),
                "status", HttpStatus.BAD_REQUEST,
                "errors", List.of(ex.getMessage())
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}