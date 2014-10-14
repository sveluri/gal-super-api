package org.familysearch.gal.shared.rest.support.adapter;

import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Extends {@link XmlAdapter} to marshal Locale.
 */
public class JAXBLocaleAdapter extends XmlAdapter<String, Locale> {

    /*
     * (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public String marshal(Locale locale) throws Exception {
        return locale.toString();
    }

    /*
     * (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @Override
    public Locale unmarshal(String value) throws Exception {
        return toLocale(value);
    }

    public static Locale toLocale(String value) {
        String[] chunks = value.split("_");
        if (chunks.length == 3) {
            return new Locale(chunks[0], chunks[1], chunks[2]);
        }
        else if (chunks.length == 2) {
            return new Locale(chunks[0], chunks[1]);
        }
        else {
            return new Locale(value);
        }
    }
}
