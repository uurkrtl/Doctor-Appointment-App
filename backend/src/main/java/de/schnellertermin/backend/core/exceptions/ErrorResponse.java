package de.schnellertermin.backend.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String apiPath;
    private HttpStatus status;
    private String title;
    private String message;
    private LocalDateTime timeStamp;
}
