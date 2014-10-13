package org.familysearch.gal.shared.rest.exception;

/**
 * Thrown to indicate an entity is out-of-date with respect to
 * the current state of the entity. (For example, if an attempt to
 * update an entity in the database and Hibernate throws a
 * StaleObjectException, this would wrap that exception.)
 */
public class InvalidVersionException extends ServiceRESTException {

    private static final long serialVersionUID = -5230429677140318922L;

    {
        super.statusType(CustomRESTStatus.INVALID_VERSION);
    }

    public InvalidVersionException() {
        super();
    }

    public InvalidVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVersionException(String message) {
        super(message);
    }

    public InvalidVersionException(Throwable cause) {
        super(cause);
    }
}
