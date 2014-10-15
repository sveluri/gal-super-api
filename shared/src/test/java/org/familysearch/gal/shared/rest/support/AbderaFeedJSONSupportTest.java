package org.familysearch.gal.shared.rest.support;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.ByteArrayOutputStream;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Feed;
import org.junit.Test;

public class AbderaFeedJSONSupportTest {

    @Test
    public void test() throws Exception {
        Feed feed = Abdera.getInstance().newFeed();
        AbderaFeedJSONSupport jsonSupport = new AbderaFeedJSONSupport();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        jsonSupport.writeTo(feed, outStream);

        String actual = new String(outStream.toByteArray());
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void testRead() throws Exception {
        AbderaFeedJSONSupport jsonSupport = new AbderaFeedJSONSupport();

        assertThat(jsonSupport.isReadable(null, null, null, null), is(false));
    }
}
