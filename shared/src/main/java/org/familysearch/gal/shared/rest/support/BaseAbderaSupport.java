package org.familysearch.gal.shared.rest.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.parser.Parser;
import org.apache.abdera.writer.Writer;

public abstract class BaseAbderaSupport<T extends Element> implements MessageBodyReader<T>, MessageBodyWriter<T> {

    private static final Abdera abdera = Abdera.getInstance();

    protected abstract Class<T> getTypeToken();
    protected abstract List<MediaType> getMediaTypes();
    protected abstract String getWriterName();

    @Override
    public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isSupported(type, mediaType);
    }

    @Override
    public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                      MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException,
                      WebApplicationException {

        if(isSupported(type, mediaType)) {
            return readFrom(entityStream);
        }

        return null;
    }

    protected T readFrom(InputStream entityStream) throws IOException {
        Parser parser = getParser();
        Document<T> document = parser.parse(entityStream);
        return document.getRoot();
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isSupported(type, mediaType);
    }

    @Override
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException,
                                                                                              WebApplicationException {

        if(isSupported(type, mediaType)) {
            writeTo(t, entityStream);
        }
    }

    protected void writeTo(T entity, OutputStream entityStream) throws IOException {
        Writer writer = abdera.getWriterFactory().getWriter(getWriterName());
        writer.writeTo(entity.getDocument(), entityStream);
    }

    protected Parser getParser() {
        return abdera.getParser();
    }

    private boolean isSupported(Class<?> type, MediaType mediaType) {
        if(getTypeToken().isAssignableFrom(type)) {
            for(MediaType mt : getMediaTypes()) {
                if(mt.equals(mediaType)) {
                    return true;
                }
            }
        }

        return false;
    }
}
