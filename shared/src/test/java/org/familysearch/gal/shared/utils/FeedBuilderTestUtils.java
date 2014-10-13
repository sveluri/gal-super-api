package org.familysearch.gal.shared.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

import java.util.List;
import java.util.Map;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.familysearch.gal.shared.common.Relation;
import org.json.JSONObject;

/**
 * Utils class for testing {@link org.apache.abdera.model.Feed} built through
 * {@link org.familysearch.gal.shared.builder.DefaultFeedBuilder}
 */
public class FeedBuilderTestUtils {

    public static void assertFeedHeader(Feed feed) {
        assertThat("Feed", feed, notNullValue());
        assertThat("Feed author", feed.getAuthor(), notNullValue());
        assertThat("Feed title", feed.getTitle(), notNullValue());
        assertThat("Feed Id", feed.getId(), notNullValue());
        assertThat("Feed Entries", feed.getEntries(), notNullValue());
    }

    public static void assertFeedLinks(Feed feed, List<Relation> expectedLinksByRelation) throws Exception {
        assertThat("Feed", feed, notNullValue());
        assertThat("links in feed", feed.getLinks(), notNullValue());
        for (Relation relation : expectedLinksByRelation) {
            assertThat("Link by rel : " + feed.getLink(relation.rel()), feed.getLink(relation.rel()), notNullValue());
        }

    }

    public static void assertEntryHeader(Entry entry) throws Exception {
        assertThat("Entry", entry, notNullValue());
        assertThat("Entry title", entry.getTitle(), notNullValue());
        assertThat("Entry Id", entry.getId(), notNullValue());
    }

    public static void assertEntryLinks(Entry entry, List<Relation> expectedLinksByRelation) {
        assertThat("Entry", entry, notNullValue());
        assertThat("Entry links", entry.getLinks(), notNullValue());
        for (Relation relation : expectedLinksByRelation) {
            assertThat("Link by rel : " + entry.getLink(relation.rel()), entry.getLink(relation.rel()), notNullValue());
        }

    }

    public static void assertEntrySummary(Entry entry, Map<String, String> summaryProperties) throws Exception {
        assertThat("Entry", entry, notNullValue());
        String summary = entry.getSummary();
        JSONObject summaryJSON = new JSONObject(summary);
        assertThat("Entry summary", summary, notNullValue());
        for (String key : summaryProperties.keySet()) {
            assertThat("Summary entry for : " + key, String.valueOf(summaryJSON.get(key)),
                       is(summaryProperties.get(key)));
        }

    }
}
