package de.schnellertermin.backend.core.exceptions;

import de.schnellertermin.backend.core.exceptions.types.DuplicateRecordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private WebRequest webRequest;

    @BeforeEach
    public void setup() {
        globalExceptionHandler = new GlobalExceptionHandler();
        webRequest = mock(WebRequest.class);
    }

    @Test
    void testHandleDuplicateRecordException() {
        DuplicateRecordException ex = new DuplicateRecordException("Duplicate Record");
        when(webRequest.getDescription(false)).thenReturn("/api/categories");

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleDuplicateRecordException(ex, webRequest);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Duplicate Record", Objects.requireNonNull(responseEntity.getBody()).getMessage());
        assertEquals("/api/categories", responseEntity.getBody().getApiPath());
    }

    @Test
    void testHandleException() {
        Exception ex = new Exception("An error occurred");
        when(webRequest.getDescription(false)).thenReturn("/api/categories");

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleException(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An error occurred", Objects.requireNonNull(responseEntity.getBody()).getMessage());
        assertEquals("/api/categories", responseEntity.getBody().getApiPath());
    }
}