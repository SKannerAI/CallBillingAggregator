package com.call.billing.aggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sandeep Kanparthy
 * 
 */
// 404
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public ResourceNotFoundException() {
    }

    /**
     * @param message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}