package com.eop.baseservice.common.errorhandler;

import com.eop.baseservice.common.response.WebResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<Void>> handleResponseStatusException(
        ResponseStatusException ex) {

        Map<String, List<String>> errors = new HashMap<>();
        errors.put("message",
            Collections.singletonList(ex.getReason()));

        return ResponseEntity
            .status(ex.getStatus())
            .body(WebResponse.failure(
                    ex.getStatus().value(),
                    errors
            ));
    }
}

