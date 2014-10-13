package org.familysearch.gal.shared.rest.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Exception indicates that requested Resource/Entity is not found
 */
public class NotFoundException extends ServiceRESTException{

	private static final long serialVersionUID = 4576046938766327939L;

    {
        super.statusType(Status.NOT_FOUND);
    }

    public NotFoundException() {
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

}
