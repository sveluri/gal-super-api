package org.familysearch.gal.shared.rest.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.ParamException;
import com.sun.jersey.api.client.ClientResponse;

/**
 * Exception mapper for Services
 */
@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionMapper.class);

    static class BodyBuilder {
        private Response.StatusType statusType;
        private String message;
        private Integer customCode;
        private String detail;
        private String moreInfoURI;
        private Map<String, List<String>> headers = new HashMap<String, List<String>>();

        public BodyBuilder(Exception e) {
            this.statusType = Response.Status.INTERNAL_SERVER_ERROR;
            this.message = e.getMessage();

            if (e instanceof ServiceRESTException) {
                ServiceRESTException restException = (ServiceRESTException) e;
                this.statusType = restException.getStatusType();
                this.customCode = restException.getCustomCode();
                this.detail = restException.getDetail();
                this.moreInfoURI = restException.getMoreInfoURI();
                this.headers = restException.getHeaders();
            }
            else if (e instanceof ParamException.PathParamException || e instanceof ParamException.QueryParamException) {
                BadRequestException badRequestException = new BadRequestException(e);
                this.statusType = badRequestException.statusType;
                this.customCode = badRequestException.getCustomCode();
                this.detail = badRequestException.getDetail();
                this.moreInfoURI = badRequestException.getMoreInfoURI();
                this.message = e.getCause().getMessage();
            }
            else if (e instanceof ConflictException) {
                ConstraintException constraintException = new ConstraintException(e);
                this.statusType = constraintException.statusType;
                this.customCode = constraintException.getCustomCode();
                this.detail = constraintException.getDetail();
                this.moreInfoURI = constraintException.getMoreInfoURI();
            }
            else if (e instanceof com.sun.jersey.api.NotFoundException) {
                NotFoundException notFoundException = new NotFoundException(e);
                this.statusType = notFoundException.statusType;
                this.customCode = notFoundException.getCustomCode();
                this.detail = notFoundException.getDetail();
                this.moreInfoURI = notFoundException.getMoreInfoURI();
            }
            else if (e instanceof UnrecognizedPropertyException) {
                UnrecognizedPropertyException unrecognizedPropertyException = (UnrecognizedPropertyException) e;
                this.message = String.format("Unrecognized Property : %s",
                                             unrecognizedPropertyException.getUnrecognizedPropertyName());
                BadRequestException badRequestException = new BadRequestException(e);
                this.statusType = badRequestException.statusType;
                this.customCode = badRequestException.getCustomCode();
                this.detail = badRequestException.getDetail();
                this.moreInfoURI = badRequestException.getMoreInfoURI();
            }
            else if (e instanceof JsonParseException) {
                this.message = String.valueOf("Error parsing JSON text");
                BadRequestException badRequestException = new BadRequestException(e);
                this.statusType = badRequestException.statusType;
                this.customCode = badRequestException.getCustomCode();
                this.detail = badRequestException.getDetail();
                this.moreInfoURI = badRequestException.getMoreInfoURI();
            }
            else if (e instanceof WebApplicationException) {
                WebApplicationException applicationException = (WebApplicationException) e;
                int statusCode = applicationException.getResponse().getStatus();
                String reasonPhrase = ClientResponse.Status.fromStatusCode(statusCode).getReasonPhrase();
                this.customCode = statusCode;
                this.statusType = null;
                this.message = reasonPhrase;
            }
        }

        public Response.StatusType getStatusType() {
            return statusType;
        }

        String toBody() {
            StringBuilder buff = new StringBuilder();

            appendLabel(buff, "statusCode", statusType.getStatusCode());
            appendLabel(buff, "reason", statusType.getReasonPhrase());
            appendLabel(buff, "message", message);
            appendLabel(buff, "customCode", customCode);
            appendLabel(buff, "detail", detail);
            appendLabel(buff, "moreInfoURI", moreInfoURI);

            return buff.toString();
        }

        private StringBuilder appendLabel(StringBuilder buff, String label, String value) {
            if (value != null) {
                if (buff.length() > 0) {
                    buff.append("\n");
                }

                buff.append(label).append(": ").append(value);
            }

            return buff;
        }

        private StringBuilder appendLabel(StringBuilder buff, String label, Integer value) {
            if (value != null) {
                return appendLabel(buff, label, value.toString());
            }

            return buff;
        }
    }

    @Override
    public Response toResponse(Exception exception) {
        BodyBuilder builder = new BodyBuilder(exception);

        Response.StatusType statusType = builder.getStatusType();
        if (statusType == null) {
            StringBuilder buff = new StringBuilder();
            builder.appendLabel(buff, "statusCode", builder.customCode);
            builder.appendLabel(buff, "reason", builder.message);
            return Response.status(builder.customCode)
                .entity(buff.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_TYPE)
                .header(HttpHeaders.CONTENT_ENCODING, "identity")
                .build();
        }
        String body = builder.toBody();

        if (statusType.getFamily().equals(Response.Status.Family.SERVER_ERROR)) {
            log.error("Caught SERVER_ERROR exception", exception);
        }
        else if (statusType.getFamily().equals(Response.Status.Family.CLIENT_ERROR)) {
            log.info("Caught CLIENT_ERROR exception", exception);
        }
        else {
            log.debug("Caught OTHER exception", exception);
        }

        Response.ResponseBuilder responseBuilder = Response.status(statusType)
            .entity(body)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_TYPE)
            .header(HttpHeaders.CONTENT_ENCODING, "identity");

        addHeaders(responseBuilder, builder.headers);

        return responseBuilder.build();
    }

    private void addHeaders(Response.ResponseBuilder responseBuilder, Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String name = entry.getKey();
            List<String> values = entry.getValue();

            for (String value : values) {
                responseBuilder.header(name, value);
            }
        }
    }
}
