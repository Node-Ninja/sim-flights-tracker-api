package dev.nodeninja.simflightstracker.exceptions;

import dev.nodeninja.simflightstracker.api.v2.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.CONFLICT)
                        .message(ex.getMessage())
                        .errorCode("FAIL_CONFLICT")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(value = FlightNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFlightNotFoundException(FlightNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage())
                        .errorCode("FAIL_NOT_FOUND")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.info("Generic exception handled :: ");
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.PRECONDITION_FAILED)
                        .message("Something went wrong. Please try again later")
                        .errorCode("FAIL_CONFLICT")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
