package org.familysearch.gal.shared.rest.exception;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.StatusType;

import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * Base class for service exceptions that map to HTTP response codes.
 */
public class ServiceRESTException extends RuntimeException {

    private static final long serialVersionUID = 837475890518050314L;

    protected StatusType statusType = Status.INTERNAL_SERVER_ERROR;
    protected Integer customCode = null;
    protected String moreInfoURI = null;
    protected String detail = null;
    protected Map<String, List<String>> headers = new HashMap<String, List<String>>();

    public ServiceRESTException() {
        super();
    }

    public ServiceRESTException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceRESTException(String message) {
        super(message);
    }

    public ServiceRESTException(Throwable cause) {
        super(cause);
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public Integer getCustomCode() {
        return customCode ;
    }

    public String getMoreInfoURI() {
        return moreInfoURI;
    }

    public String getDetail() {
        return detail;
    }

    /*
     * Chain setter methods
     */

    public ServiceRESTException statusType(StatusType statusType) {
        this.statusType = statusType;

        if(statusType instanceof CustomRESTStatus) {
            CustomRESTStatus restStatus = (CustomRESTStatus) statusType;
            this.customCode = restStatus.getCustomCode();
        }

        return this;
    }

    public ServiceRESTException moreInfoURI(URI uri) {
        this.moreInfoURI = uri.toString();
        return this;
    }

    public ServiceRESTException moreInfoURI(String uri) {
        this.moreInfoURI = uri;
        return this;
    }

    public ServiceRESTException detail(String detail) {
        this.detail = detail;
        return this;
    }

    public ServiceRESTException header(String name, String value) {
        putHeader(name, value);
        return this;
    }

    public ServiceRESTException location(String uri) {
        return header("Location", uri);
    }

    public ServiceRESTException warning(int warnCode, String warnText) {
        return header("Warning", warnCode + " " + warnText);
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public List<String> getHeader(String name) {
        return headers.get(name);
    }

    protected void putHeader(String name, String value) {
        List<String> list = headers.get(name);
        if(list == null) {
            list = new ArrayList<String>();
            headers.put(name, list);
        }

        list.add(value);
    }

}
