package org.familysearch.gal.shared.builder;

import javax.ws.rs.core.Request;

import org.apache.abdera.model.Feed;

/**
 * Feed Builder
 *
 */
public interface FeedBuilder<M, R> {

    /**
     * Responsible to create Feed from given {@link org.familysearch.gal.shared.builder.FeedOptions}
     * 
     * @param request request instance
     * @param feedOptions feed options instance
     * @return {@link org.apache.abdera.model.Feed}
     */
    Feed createFeed(Request request, FeedOptions<M> feedOptions);

    /**
     * Responsible to create Representation from Model
     * 
     * @param request request instance
     * @param model Service model instance
     * @return Representation
     */
    R createRepresentation(Request request, M model);
}
