package com.call.billing.aggregator.application.rest.handlers;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * A convenient base class for @ControllerAdvice classes that wish to provide centralized exception handling across all @RequestMapping
 * methods through @ExceptionHandler methods.
 * 
 * @author Sandeep Kanparthy
 * 
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    final static Logger logger = LoggerFactory.getLogger("default.activity.logger");

    // API

    // 400

//    @ExceptionHandler({ InvalidDataException.class })
//    public ResponseEntity<Object> inputDataNotValid(final InvalidDataException ex, final WebRequest request) {
//        logger.error("Error while serving " + request.getDescription(false));
//        logger.error("400 Status Code", ex);
//        return handleExceptionInternal(ex,ExceptionUtils.getMessage(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }

    // 403

    // 404
    @ExceptionHandler({ HttpClientErrorException.class})
    public ResponseEntity<Object> notFound(final HttpClientErrorException ex, final WebRequest request) {
        logger.error("Error while serving " + request.getDescription(false));
        logger.error("404 Status Code", ex);
        return handleExceptionInternal(ex, ExceptionUtils.getMessage(ex), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    // 401


    // 409

    // 412

    // 500

//    @ExceptionHandler({ ServiceRuntimeException.class })
//    public ResponseEntity<Object> handleCustomException(final ServiceRuntimeException ex, final WebRequest request) {
//        logger.error("Error while serving " + request.getDescription(false));
//        logger.error("500 Status Code", ex);
//        return handleExceptionInternal(ex, ExceptionUtils.getRootCauseMessage(ex), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
//    }

    public RestResponseEntityExceptionHandler() {
        super();
    }
}
