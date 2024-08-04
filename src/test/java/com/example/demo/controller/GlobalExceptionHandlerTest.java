package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    @Test
    void testHandleGlobalException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception exception = new Exception("Test exception");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<String> response = handler.handleGlobalException(exception, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred: Test exception", response.getBody());
    }
}