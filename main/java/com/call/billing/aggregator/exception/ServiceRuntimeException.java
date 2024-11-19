package com.call.billing.aggregator.exception;

/**
 * @author Sandeep Kanparthy
 * 
 */
public class ServiceRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public ServiceRuntimeException() {
    }

    /**
     * @param message
     */
    public ServiceRuntimeException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ServiceRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
