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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message(ex.getMessage())
                        .errorCode("FAILED_REQUEST")
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
        log.error("Generic exception handled :: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message("Something went wrong. Please try again later")
                        .errorCode("FAIL_BAD_REQUEST")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
