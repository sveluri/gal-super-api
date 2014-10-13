package org.familysearch.gal.shared.rest.exception;

/**
 * Indicates that a required parameter is missing from the request.
 */
public class MissingParameterException extends ServiceRESTException {

    private static final long serialVersionUID = -5665570859795805081L;

    {
        super.statusType(CustomRESTStatus.MISSING_PARAMETER);
    }

    public MissingParameterException() {
        super();
    }

    public MissingParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingParameterException(String message) {
        super(message);
    }

    public MissingParameterException(Throwable cause) {
        super(cause);
    }

}
