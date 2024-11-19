package com.call.billing.aggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sandeeep Kanparthy
 * 
 */
// 400
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input data")
public class InvalidDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
     * 
     */
	public InvalidDataException() {
	}

	/**
	 * @param message
	 */
	public InvalidDataException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidDataException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}

}