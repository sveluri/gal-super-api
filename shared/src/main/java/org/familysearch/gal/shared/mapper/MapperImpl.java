package org.familysearch.gal.shared.mapper;

import static org.dozer.loader.api.TypeMappingOptions.beanFactory;

import java.util.*;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

/**
 * Abstract implementation for the {@link Mapper}
 */
public class MapperImpl<F, T> implements Mapper<F, T> {

    private Class<T> toType;
    private Class<F> fromType;
    private org.dozer.Mapper mapper;

    static final private BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {

        @Override
        protected void configure() {
            mapping(Locale.class, Locale.class, beanFactory(LocaleBeanFactory.class.getName()));
            mapping(UUID.class, UUID.class, beanFactory(UUIDBeanFactory.class.getName()));
        }
    };

    public MapperImpl(Class<F> fromType, Class<T> toType) {
        this.toType = toType;
        this.fromType = fromType;
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.addMapping(mappingBuilder);
        mapper = dozerBeanMapper;
    }

    /**
     * {@inheritDoc}
     */
    public F to(T t) {
        if (t == null) {
            return null;
        }
        else {
            return mapper.map(t, fromType);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public F to(T t, F f) {
        mapper.map(t, f);
        return f;
    }

    /**
     * {@inheritDoc}
     */
    public T from(F f) {
        if (f == null) {
            return null;
        }
        else {
            return mapper.map(f, toType);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T from(F f, T t) {
        mapper.map(f, t);
        return t;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<F> to(Collection<T> tCollection) {
        List<F> fList = new ArrayList<F>();
        if (tCollection != null) {
            for (T t : tCollection) {
                fList.add(to(t));
            }
        }
        return fList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> from(Collection<F> fCollection) {
        List<T> tList = new ArrayList<T>();
        if (fCollection != null) {
            for (F f : fCollection) {
                tList.add(from(f));
            }
        }
        return tList;
    }
}
