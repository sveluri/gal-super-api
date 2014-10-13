package org.familysearch.gal.shared.rest.exception;

import javax.ws.rs.core.Response.Status;

/**
 * ServiceRESTException thrown to indicate the request requires authorization,
 * but the user does not have sufficient privileges.
 */
public class UnauthorizedException extends ServiceRESTException {

    private static final long serialVersionUID = 5210032301434637644L;

    {
        super.statusType(Status.FORBIDDEN);
    }

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

}
