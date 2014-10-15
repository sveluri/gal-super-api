package org.familysearch.gal.shared.rest.support;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Feed;
import org.junit.Test;

public class AbderaFeedXMLSupportTest {

    @Test
    public void testWrite() throws Exception {
        Feed feed = Abdera.getInstance().newFeed();
        AbderaFeedXMLSupport xmlSupport = new AbderaFeedXMLSupport();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        xmlSupport.writeTo(feed, outStream);

        String actual = new String(outStream.toByteArray());
        assertThat(actual, containsString("feed"));
    }

    @Test
    public void testRead() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/testFeed.xml");

        AbderaFeedXMLSupport xmlSupport = new AbderaFeedXMLSupport();
        Feed feed = xmlSupport.readFrom(inputStream);

        assertThat(feed, is(notNullValue()));
        assertThat(feed.getAuthor().getName(), is("FamilySearch"));
    }
}
