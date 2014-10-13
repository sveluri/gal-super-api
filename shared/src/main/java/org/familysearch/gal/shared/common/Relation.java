package org.familysearch.gal.shared.common;

/**
 * Represents a hyper-media relation.
 */
public interface Relation {

    /**
     * Return the string representation of the relation.
     * @return the relation
     */
    String rel();

    /**
     * True if the relation is deprecated.
     * @return true if deprecated
     */
    boolean isDeprecated();
}
