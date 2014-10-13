package org.familysearch.gal.shared.rest.exception;

import javax.ws.rs.core.Response.Status;

public class UnsupportedMediaTypeException extends ServiceRESTException {

    private static final long serialVersionUID = 8711558957037852200L;

    {
        super.statusType(Status.UNSUPPORTED_MEDIA_TYPE);
    }

    public UnsupportedMediaTypeException() {
    }

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }

    public UnsupportedMediaTypeException(Throwable cause) {
        super(cause);
    }

}
