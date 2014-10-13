package org.familysearch.gal.shared.rest.exception;

import javax.ws.rs.core.Response.Status;

/**
 * ServiceRESTException to indicate the request requires
 * authorization, but none exists.
 */
public class UnauthenticatedException extends ServiceRESTException {

    private static final long serialVersionUID = 5210032301434637644L;

    {
        super.statusType(Status.UNAUTHORIZED);
    }

    public UnauthenticatedException() {
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(Throwable cause) {
        super(cause);
    }

}
