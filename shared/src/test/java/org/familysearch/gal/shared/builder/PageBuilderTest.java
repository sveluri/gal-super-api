package org.familysearch.gal.shared.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.familysearch.gal.shared.model.Page;
import org.junit.Test;

/**
 * Test for {@link org.familysearch.gal.shared.builder.PageBuilder}
 */
public class PageBuilderTest {

    @Test
    public void testBuild_default() {
        PageBuilder test = new PageBuilder();
        Page page = test.builder().build();

        assertThat(page.getPageSize(), is(20));
        assertThat(page.getPageNumber(), is(1));
        assertThat(page.getSortColumn(), is(nullValue()));
    }

    @Test
    public void testBuild() {
        PageBuilder test = new PageBuilder();
        Page page = test.builder(100, 5).sortField("name").asc(true).build();

        assertThat(page.getPageNumber(), is(5));
        assertThat(page.getPageSize(), is(100));
        assertThat(page.getSortColumn(), is(notNullValue()));
        assertThat(page.isAscending(), is(notNullValue()));
    }

    @Test
    public void testBuild_maxPageSize() {
        PageBuilder test = new PageBuilder();
        Page page = test.builder(5000, 1).sortField("name").asc(true).build();

        assertThat(page.getPageSize(), is(1000));
        assertThat(page.getPageNumber(), is(1));
        assertThat(page.getSortColumn(), is(notNullValue()));
        assertThat(page.isAscending(), is(notNullValue()));
    }
}
