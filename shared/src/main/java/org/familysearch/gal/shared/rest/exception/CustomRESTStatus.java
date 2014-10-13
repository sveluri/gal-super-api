package org.familysearch.gal.shared.rest.exception;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

/**
 * Defines custom {@link javax.ws.rs.core.Response.StatusType} to define custom
 * error codes that detail exception cases but map
 * to standard HTTP status codes. The {@link javax.ws.rs.core.Response.StatusType#getReasonPhrase()}
 * contains a string of the custom code and new phrase, thus the
 * staus line of a HTTP response would be:
 * <code>
 * http/1.1 {standard code} [{custom code}] {custom phrase}
 * </code>
 *
 */
public enum CustomRESTStatus implements StatusType {

    // http/1.1 400 Bad Request
    INVALID_VERSION(Status.BAD_REQUEST, 1, "Invalid Version"),
    MISSING_PARAMETER(Status.BAD_REQUEST, 2, "Missing Parameter"),
    INVALID_PARAMETER_VALUE(Status.BAD_REQUEST, 3, "Invalid Parameter Value"),
    BLOCK_INVITATION(Status.CONFLICT, 4009,"User doesn't want to recieve invitation"),
    DELETE_USER_PROJECT_VIOLATION(Status.CONFLICT, 4091,"User is owner of the projects"),
    DELETE_USER_GROUP_VIOLATION(Status.CONFLICT, 4092,"User is owner of the groups");
    
    private int statusCode;
    private int customCode;
    private Family family;
    private String reasonPhrase;

    private CustomRESTStatus(StatusType baseType, int customCode, String reasonPhrase) {
        this.statusCode = baseType.getStatusCode();
        this.customCode = customCode;
        this.family = baseType.getFamily();

        this.reasonPhrase = String.format("[%03d] %s", customCode, reasonPhrase);
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Family getFamily() {
        return family;
    }

    @Override
    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public int getCustomCode() {
        return customCode;
    }
}
