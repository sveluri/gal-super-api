package org.familysearch.gal.shared.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Request;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Feed;
import org.familysearch.gal.shared.common.Relation;
import org.familysearch.gal.shared.mapper.model.Product;
import org.familysearch.gal.shared.model.Page;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link org.familysearch.gal.shared.builder.FeedBuilder}
 */
public class FeedBuilderTest {

    private FeedBuilder<Product, ?> test;
    private Request request;
    private FeedOptions<Product> feedOptions;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        test = mock(FeedBuilder.class);
        
        request = mock(Request.class);
        
        List<Product> list = new ArrayList<>();
        
        Product Product = spy(new Product());
        for (int i=0; i<10; i++) {
            Product.setName("My Test Product " + i);
            Product.setLocale(Locale.ENGLISH);
            list.add(Product);
        }
        
        feedOptions = mock(FeedOptions.class);
        feedOptions.setList(list);
        feedOptions.setPage(mock(Page.class));   
        
        Feed feed =  Abdera.getInstance().newFeed();
        feed.addAuthor("FamilySearch");
        feed.setId(new FeedIdBuilder().rel(Relation.SELF.rel()).build());
        feed.setTitle("List of Products");
        feed.setUpdated(new Date());
        
        when(test.createFeed(request, feedOptions)).thenReturn(feed);
    }

    @Test
    public void testCreateFeed() {
        Feed feed = test.createFeed(request, feedOptions);

        assertThat(feed, is(notNullValue()));
        assertThat(feed.getEntries(), is(notNullValue()));
    }
}
