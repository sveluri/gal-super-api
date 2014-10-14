package org.familysearch.gal.shared.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.familysearch.gal.shared.common.Relation;
import org.junit.Test;

/**
 * Test case for {@link org.familysearch.gal.shared.builder.FeedIdBuilder}
 */
public class FeedIdBuilderTest {

    UUID applicationId = UUID.randomUUID();
    UUID categoryId = UUID.randomUUID();
    UUID userId = null;

    @Test
    public void testBuild() {
        String feedId = new FeedIdBuilder().build();
        assertThat(feedId, is("urn:rel"));
    }

    @Test
    public void testBuild_rel() {
        String feedId = new FeedIdBuilder().rel(Relation.SELF.rel()).build();
        assertThat(feedId, is("urn:rel:" + Relation.SELF.rel()));
    }

    @Test
    public void testBuild_uuid() {
        String feedId = new FeedIdBuilder().identifier("application", applicationId)
            .build();
        assertThat(feedId, is("urn:rel+uuid:application/" + applicationId.toString()));
    }

    @Test
    public void testBuild_uuid_null() {
        String feedId = new FeedIdBuilder().identifier("user", userId)
            .identifier("application", applicationId)
            .build();
        assertThat(feedId, is("urn:rel+uuid:application/" + applicationId.toString()));
    }

    @Test
    public void testBuild_uuid_null_rel() {
        String feedId = new FeedIdBuilder().identifier("user", userId)
            .identifier("application", applicationId)
            .rel(Relation.SELF.rel())
            .build();
        assertThat(feedId,
                   is("urn:rel+uuid:" + Relation.SELF.rel() + "/" + "application/" + applicationId.toString()));
    }

    @Test
    public void testBuild_uuid_uuid_rel() {
        String feedId = new FeedIdBuilder().identifier("category", categoryId)
            .identifier("application", applicationId)
            .rel(Relation.SELF.rel())
            .build();
        assertThat(feedId, is("urn:rel+uuid:" + Relation.SELF.rel() + "/category/" + categoryId.toString()
                + "/application/" + applicationId.toString()));
    }
}
