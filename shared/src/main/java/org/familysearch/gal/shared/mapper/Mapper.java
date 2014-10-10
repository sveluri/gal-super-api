package org.familysearch.gal.shared.mapper;

import java.util.Collection;
import java.util.List;

/**
 * Mapper to map the properties from S to D
 * 
 */
public interface Mapper<S, D> {

    /**
     * Maps the D to S
     * 
     * @param d Database layer
     * @return service model
     */
    S to(D d);

    /**
     * Maps the D to S
     * 
     * @param d D layer
     * @param s existing s object
     * @return S type
     */
    S to(D d, S s);

    /**
     * Maps the S to D
     * 
     * @param s S model
     * @return Representation
     */
    D from(S s);

    /**
     * Maps the S to D
     * 
     * @param s S model
     * @param d D model
     * @return Representation
     */
    D from(S s, D d);

    /**
     * Maps the collection of D model to S model collection
     * 
     * @param dCollection collection of D model
     * @return collection of S model
     */
    List<S> to(Collection<D> dCollection);

    /**
     * Maps the collection of S model to D model
     * 
     * @param sCollection collection of S model
     * @return Collection of D model
     */
    List<D> from(Collection<S> sCollection);
}
