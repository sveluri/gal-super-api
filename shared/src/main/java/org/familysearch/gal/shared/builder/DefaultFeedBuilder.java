package org.familysearch.gal.shared.builder;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Text;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.familysearch.gal.shared.common.Relation;
import org.familysearch.gal.shared.model.Link;
import org.familysearch.gal.shared.model.Page;
import org.familysearch.gal.shared.model.Template;
import org.familysearch.gal.shared.rest.exception.ServiceRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.familysearch.gal.shared.common.FeedConstants.PAGE;
import static org.familysearch.gal.shared.common.FeedConstants.PAGE_SIZE;
import static org.familysearch.gal.shared.common.FeedConstants.TYPE;

/**
 * Generic Feed Builder to create ATOM feed for service response
 *
 */
public abstract class DefaultFeedBuilder<M, R> implements FeedBuilder<M, R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFeedBuilder.class);

    @Autowired
    protected ObjectMapper objectMapper;

    protected abstract Feed doCreateFeedHeader(Feed feed, FeedOptions<M> feedOptions);

    protected abstract Feed doCreateFeedLink(Feed feed, Request request, FeedOptions<M> feedOptions);

    protected abstract void doCreateEntryHeader(Entry entry, M model, Request request);

    protected abstract void doCreateEntryLinks(Entry entry, M model, Request request);

    protected abstract void doCreateEntrySummary(Entry entry, M model, Map<String, Object> searchOptions);

    public void createFeedEntry(Entry entry, M model, FeedOptions<M> feedOptions, Request request) {
        doCreateEntryHeader(entry, model, request);
        doCreateEntryLinks(entry, model, request);
        doCreateEntrySummary(entry, model, feedOptions.getSearchOptions());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Feed createFeed(Request request, FeedOptions<M> feedOptions) {
        Feed feed = Abdera.getInstance().newFeed();
        feed = doCreateFeedHeader(feed, feedOptions);
        feed = doCreateFeedLink(feed, request, feedOptions);

        for (M model : feedOptions.getList()) {
            createFeedEntry(feed.addEntry(), model, feedOptions, request);
        }
        return feed;
    }

    /**
     * Adds Pagination Links to feed
     * 
     * NOTE: The query params which are responsible to build link should be given in params
     *
     * page and pagesize are the query params for pagination
     * 
     * @param feed feed
     * @param partialTemplate template from which final link to be formed
     * @param params query params needed to form link
     * @param feedOptions feed options
     */
    protected void addPaginationLinks(Feed feed, Template partialTemplate, Map<String, String> params,
                                      FeedOptions<M> feedOptions) {

        Page page = feedOptions.getPage();
        int totalRows = page.getTotalRows();
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();

        TemplateLinkBuilder templateLinkBuilder = new TemplateLinkBuilder(partialTemplate);
        for (String key : params.keySet()) {
            templateLinkBuilder.param(key, params.get(key));
        }

        if (totalRows > pageSize) {
            if (pageNumber > 1) {
                Link firstPage = templateLinkBuilder
                    .param(PAGE, String.valueOf(1))
                    .param(PAGE_SIZE, String.valueOf(feedOptions.getPage().getPageSize()))
                    .build();
                String hrefToFirstPage = firstPage.getHref();
                feed.addLink(hrefToFirstPage, Relation.FIRST.rel()).setMimeType(MediaType.APPLICATION_ATOM_XML);

                Link previousPage = templateLinkBuilder
                    .param(PAGE, String.valueOf(feedOptions.getPage().getPageNumber() - 1))
                    .param(PAGE_SIZE, String.valueOf(feedOptions.getPage().getPageSize()))
                    .build();
                String hrefToPreviousPage = previousPage.getHref();
                feed.addLink(hrefToPreviousPage, Relation.PREVIOUS.rel()).setMimeType(MediaType.APPLICATION_ATOM_XML);
            }

            if (totalRows > pageNumber * pageSize) {
                Link linkToNextPage = templateLinkBuilder
                    .param(PAGE, String.valueOf(feedOptions.getPage().getPageNumber() + 1))
                    .param(PAGE_SIZE, String.valueOf(feedOptions.getPage().getPageSize()))
                    .build();
                String hrefToNextPage = linkToNextPage.getHref();
                feed.addLink(hrefToNextPage, Relation.NEXT.rel()).setMimeType(MediaType.APPLICATION_ATOM_XML);

                Link linkToLastPage = templateLinkBuilder
                    // We can also use Math.ceil(totalRows/(float)pageSize) to get highest rounded off number
                    .param(PAGE, String.valueOf((totalRows + (pageSize - 1)) / pageSize))
                    .param(PAGE_SIZE, String.valueOf(feedOptions.getPage().getPageSize()))
                    .build();
                String hrefToLastPage = linkToLastPage.getHref();
                feed.addLink(hrefToLastPage, Relation.LAST.rel())
                    .setMimeType(MediaType.APPLICATION_ATOM_XML);
            }
        }
    }

    /**
     * Add summary to entry
     * 
     * @param entry entry instance
     * @param summaryProperties summary values
     */
    protected void addSummary(Entry entry, Map<String, Object> summaryProperties) {
        Text summary;
        try {
            summary = entry.setSummary(objectMapper.writeValueAsString(summaryProperties));
        }
        catch (JsonGenerationException e) {
            LOGGER.error("Unable to create entry", e);
            throw new ServiceRESTException("Unable to create entry :", e);
        }
        catch (JsonMappingException e) {
            LOGGER.error("Unable to create entry ", e);
            throw new ServiceRESTException("Unable to create entry :", e);
        }
        catch (IOException e) {
            LOGGER.error("Unable to create entry ", e);
            throw new ServiceRESTException("Unable to create entry :", e);
        }
        summary.setAttributeValue(TYPE, MediaType.APPLICATION_JSON);
    }

    /**
     * Sets new objectMapper.
     *
     * @param objectMapper New value of objectMapper.
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
