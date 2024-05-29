package dev.nodeninja.simflightstracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private HttpStatus statusCode;
    private String errorCode;
    private LocalDateTime timestamp;
}
