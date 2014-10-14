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

public abstract class BaseMarshallingSupport<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {

    protected abstract Class<T> getTypeToken();
    protected abstract List<MediaType> getMediaTypes();
    protected abstract T readFrom(InputStream entityStream) throws IOException;
    protected abstract void writeTo(T entity, OutputStream entityStream) throws IOException;

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isTypeSupported(type, mediaType);
    }
    @Override
    public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                      MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException,
                                                                                           WebApplicationException {
        if(isTypeSupported(type, mediaType)) {
            return readFrom(entityStream);
        }

        return null;
    }
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isTypeSupported(type, mediaType);
    }
    @Override
    public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }
    @Override
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException,
                                                                                              WebApplicationException {

        if(isTypeSupported(type, mediaType)) {
            writeTo(t, entityStream);
        }
    }

    private boolean isTypeSupported(Class<?> type, MediaType mediaType) {
        if(getTypeToken().isAssignableFrom(type)) {
            for(MediaType supported : getMediaTypes()) {
                if(mediaType.isCompatible(supported)) {
                    return true;
                }
            }
        }

        return false;
    }

}
