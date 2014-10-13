package org.familysearch.gal.shared.rest.exception;

/**
 * Indicates that a parameter value is invalid.
 */
public class InvalidParameterValueException extends ServiceRESTException {

    private static final long serialVersionUID = -5744031132108124601L;

    {
        super.statusType(CustomRESTStatus.INVALID_PARAMETER_VALUE);
    }

    public InvalidParameterValueException() {
    }

    public InvalidParameterValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterValueException(String message) {
        super(message);
    }

    public InvalidParameterValueException(Throwable cause) {
        super(cause);
    }

}
