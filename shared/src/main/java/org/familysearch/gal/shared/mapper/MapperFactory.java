package org.familysearch.gal.shared.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains a list of {@link Mapper} instances.
 *
 */
public class MapperFactory {

    private static final Map<Class<?>, Mapper<?, ?>> mappers = new HashMap<Class<?>, Mapper<?, ?>>();

    /**
     * Request a Mapper instance for the given types.
     * 
     * @param fromType type to convert from
     * @param toType type to convert to
     * @return Mapper instance or null
     */
    @SuppressWarnings("unchecked")
    public static <F, T> Mapper<F, T> instance(Class<F> fromType, Class<T> toType) {

        // If custom mapper is available then use it, otherwise use default Dozer mapper
        Mapper<F, T> mapper = (Mapper<F, T>) mappers.get(toType);

        if (mapper == null) {
            mapper = new MapperImpl<F, T>(fromType, toType);
        }
        return mapper;
    }
}
