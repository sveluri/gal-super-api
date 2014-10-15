package org.familysearch.gal.shared.rest.support;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.apache.abdera.model.Feed;
import org.springframework.stereotype.Component;

@Component
@Provider
@Produces({MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_XML})
public class AbderaFeedXMLSupport extends BaseAbderaSupport<Feed> {

    private static final List<MediaType> mediaTypes;

    static {
        mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_ATOM_XML_TYPE);
        mediaTypes.add(MediaType.APPLICATION_XML_TYPE);
    }

    @Override
    protected Class<Feed> getTypeToken() {
        return Feed.class;
    }

    @Override
    protected List<MediaType> getMediaTypes() {
        return mediaTypes;
    }

    @Override
    protected String getWriterName() {
        return "prettyxml";
    }

}
