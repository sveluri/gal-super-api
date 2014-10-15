package org.familysearch.gal.shared.common;

import javax.xml.namespace.QName;

import org.apache.abdera.model.Feed;

/**
 * XML QNames to use as ATOM Feed extensions.
 */
public class GALQNames {

    public static final String NAMESPACE = "http://familysearch.org/gal";

    public static final QName REL = new QName(NAMESPACE, "rel");
    public static final QName UUID = new QName(NAMESPACE, "uuid");
    public static final QName METHOD = new QName(NAMESPACE, "method");
    public static final QName DEPRECATED = new QName(NAMESPACE, "deprecated");

    /**
     * Declare the namespace in the ATOM feed document.
     * 
     * @param feed ATOM feed
     */
    public static void declareNamespace(Feed feed) {
        feed.declareNS(NAMESPACE, "gal");
    }
}
