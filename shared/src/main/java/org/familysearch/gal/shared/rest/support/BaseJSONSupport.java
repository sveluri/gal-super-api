package org.familysearch.gal.shared.rest.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.ext.ContextResolver;

import org.codehaus.jackson.map.ObjectMapper;

public abstract class BaseJSONSupport<T> extends BaseMarshallingSupport<T> implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper objectMapper = new ObjectMapperFactory().getObjectMapper();

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }

    @Override
    protected T readFrom(InputStream entityStream) throws IOException {
        return (T)objectMapper.readValue(entityStream, getTypeToken());
    }

    @Override
    protected void writeTo(T entity, OutputStream entityStream) throws IOException {
        objectMapper.writeValue(entityStream, entity);
    }
}
