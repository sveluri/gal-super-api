package org.familysearch.gal.shared.mapper;

import org.dozer.BeanFactory;

/**
 * Factory Implementation for {@link java.util.UUID}
 *
 */
public class UUIDBeanFactory implements BeanFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object createBean(Object sourceObject, Class<?> sourceClass, String targetBeanId) {
        return sourceObject;
    }
}
