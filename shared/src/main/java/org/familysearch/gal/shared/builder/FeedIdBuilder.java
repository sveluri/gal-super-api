package org.familysearch.gal.shared.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Builder for creating Feed Ids to set in feed.setId() and entry.setId().
 */
public class FeedIdBuilder {

    private final String baseId;
    private List<String> paths = new ArrayList<>();
    private String rel = null;

    public FeedIdBuilder() {
        this.baseId = "urn:rel";
    }

    public FeedIdBuilder(String baseId) {
        this.baseId = baseId;
    }

    /**
     * Append the identifier to the feed id if the identifier id is not null
     * 
     * @param name Identifier Name
     * @param uuid Identifier ID
     * @return builder
     */
    public FeedIdBuilder identifier(String name, UUID uuid) {
        if (uuid != null) {
            paths.add(name.toLowerCase() + "/" + uuid.toString());
        }
        return this;
    }

    /**
     * Append the relation to the feed id.
     * 
     * @param rel relation
     * @return the builder
     */
    public FeedIdBuilder rel(String rel) {
        this.rel = rel.toLowerCase();
        return this;
    }

    /**
     * Build the final feed id.
     * 
     * @return String
     */
    public String build() {
        StringBuilder feedId = new StringBuilder(baseId);

        if (!paths.isEmpty()) {
            feedId.append("+uuid:");
        }

        if (rel != null) {
            if (paths.isEmpty()) {
                feedId.append(":").append(rel);
            }
            else {
                feedId.append(rel);
            }

            for (String path : paths) {
                feedId.append("/").append(path);
            }
        }
        else {
            for (String path : paths) {
                feedId.append(path);
            }
        }

        return feedId.toString().endsWith("/") ? feedId.substring(0, feedId.lastIndexOf("/")).toString() : feedId
            .toString();
    }
}
