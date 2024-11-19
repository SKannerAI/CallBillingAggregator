package com.call.billing.aggregator.application.rest;
import com.call.billing.aggregator.exception.ServiceRuntimeException;

import java.util.concurrent.ExecutionException;

/**
 * A core service interface of the service layer, to cater to the service requests. This interface is intended to be
 * implementing a specific fine grained business functionality which shouldn't be too coarse nor too fine.
 * 
 * @author Sandeep Kanparthy
 * 
 * @param <I> input
 * @param <O> output
 */
public interface CoreService<I, O> {
    
    /**
     * Executes the service taking the Input, and returns an Output.
     * 
     * @param input
     * 
     * @return output
     * 
     * @throws ServiceRuntimeException
     */
    O execute(I input) throws InterruptedException, ExecutionException, Exception;
    /**
     * Empty Object container to deal with Empty Service Inputs.
     * 
     * @author Sandeep Kanparthy
     * 
     */
    public static class EmptyInput {
    }

    /**
     * Empty Object container to deal with Empty Service Outputs.
     * 
     * @author Sandeep Kanparthy
     * 
     */
    public static class EmptyOutput {
    }

}
