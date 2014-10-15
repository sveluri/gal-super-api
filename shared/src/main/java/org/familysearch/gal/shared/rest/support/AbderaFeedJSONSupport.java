package org.familysearch.gal.shared.rest.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.apache.abdera.model.Feed;
import org.apache.abdera.parser.Parser;
import org.springframework.stereotype.Component;

@Component
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class AbderaFeedJSONSupport extends BaseAbderaSupport<Feed> {

    private static final List<MediaType> mediaTypes;

    static {
        mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    protected String getWriterName() {
        return "json";
    }

    @Override
    protected Class<Feed> getTypeToken() {
        return Feed.class;
    }

    @Override
    protected List<MediaType> getMediaTypes() {
        return mediaTypes;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return false;   // there is currently no JSON parser for Abdera; create instance of NamedParser to do so
    }

    @Override
    protected Parser getParser() {
        throw new UnsupportedOperationException("No Abdera parser available for JSON");
    }
}
