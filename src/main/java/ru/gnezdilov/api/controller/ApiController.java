package ru.gnezdilov.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gnezdilov.api.AbstractController;
import ru.gnezdilov.api.exception.UnauthorizedException;
import ru.gnezdilov.api.json.ErrorResponse;

@RestControllerAdvice
public class ApiController extends AbstractController {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(e.getMessage()));
    }
}
