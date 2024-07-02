package br.com.fiap.msusuario.exceptions;

import br.com.fiap.msusuario.infrastructure.validations.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<StandardError> handleWrongCredentials(BadCredentialsException e, HttpServletRequest request) {
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(NOT_FOUND.value())
                .error("Username or password are incorrect")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    ResponseEntity<StandardError> handleUserAlreadyExists(UserAlreadyExistsException e, HttpServletRequest request) {
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(CONFLICT.value())
                .error("Login already exists")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(CONFLICT.value()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationError> validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ValidationError.builder()
                        .field(((FieldError) error).getField())
                        .errorMessage(error.getDefaultMessage())
                        .build()
                )
                .toList();
        return ResponseEntity.badRequest().body(validationErrors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<StandardError> handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request) {
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(NOT_FOUND.value())
                .error("User not found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(NOT_FOUND.value()).body(error);
    }
}
