package org.familysearch.gal.shared.rest.support;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

public class ObjectMapperFactory {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(Feature.INDENT_OUTPUT, true);
        objectMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static ObjectMapper instance() {
        return objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
