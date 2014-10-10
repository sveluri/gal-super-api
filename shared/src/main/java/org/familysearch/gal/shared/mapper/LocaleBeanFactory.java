package org.familysearch.gal.shared.mapper;

import java.util.Locale;

import org.dozer.BeanFactory;

/**
 * Factory Implementation for {@link java.util.Locale}
 *
 */
public class LocaleBeanFactory implements BeanFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object createBean(Object sourceObject, Class<?> sourceClass, String targetBeanId) {
        if (sourceObject == null) {
            return null;
        }

        Locale source = (Locale) sourceObject;
        return new Locale(source.getLanguage(), source.getCountry(), source.getVariant());
    }
}
