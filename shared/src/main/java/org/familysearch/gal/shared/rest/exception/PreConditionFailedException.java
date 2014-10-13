package org.familysearch.gal.shared.rest.exception;

import javax.ws.rs.core.Response;

/**
 * Represents a condition failure, HTTP response code of 412.
 */
public class PreConditionFailedException extends ServiceRESTException {

    private static final long serialVersionUID = 8962958892978559700L;

    {
        super.statusType(Response.Status.PRECONDITION_FAILED);
    }

    /**
     * Default constructor
     */
    public PreConditionFailedException() {
    }

    /**
     * Constructor with exception message and the cause of exception
     * 
     * @param message reason of the exception
     * @param cause {@link Throwable} cause of the exception
     */
    public PreConditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with exception message
     * 
     * @param message reason of the exception
     */
    public PreConditionFailedException(String message) {
        super(message);
    }

    /**
     * Constructor with {@link Throwable} clause
     * 
     * @param cause cause of the exception
     */
    public PreConditionFailedException(Throwable cause) {
        super(cause);
    }
}
