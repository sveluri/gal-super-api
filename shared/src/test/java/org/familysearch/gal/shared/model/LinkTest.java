package org.familysearch.gal.shared.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.familysearch.gal.shared.common.Relation;
import org.junit.Before;
import org.junit.Test;

public class LinkTest {

    private Link link;

    @Before
    public void setup() {
        link = new Link();
    }

    @Test
    public void testHref() throws Exception {
        link.setHref("http://localhost");
        assertThat(link.getHref(), is(equalTo("http://localhost")));
    }

    @Test
    public void testRel() throws Exception {
        link.setRel("relations/test");
        assertThat(link.getRel(), is(equalTo("relations/test")));
    }

    @Test
    public void testType() throws Exception {
        link.setType("application/xml");
        assertThat(link.getType(), is(equalTo("application/xml")));
    }

    @Test
    public void testMethod() throws Exception {
        link.setMethod("get,post,put,delete");
        assertThat(link.getMethod(), is(equalTo("get,post,put,delete")));
    }

    @Test
    public void testTitle() throws Exception {
        link.setTitle("link title");
        assertThat(link.getTitle(), is(equalTo("link title")));
    }

    @Test
    public void testDeprecated() throws Exception {
        link.setDeprecated(true);
        assertThat(link.isDeprecated(), is(true));
    }

    @Test
    public void testDeprecated_false() throws Exception {
        link.setDeprecated(false);
        assertThat(link.isDeprecated(), is(nullValue()));
    }

    @Test
    public void testDeprecated_null() throws Exception {
        link.setDeprecated(null);
        assertThat(link.isDeprecated(), is(nullValue()));
    }

    @Test
    public void testBuilder() throws Exception {
        Link link = new Link.Builder()
            .href("http://localhost:8080/endpoint")
            .rel("relations/rel")
            .type("application/xml")
            .method("get")
            .title("link title")
            .build();

        assertThat(link.getHref(), is("http://localhost:8080/endpoint"));
        assertThat(link.getRel(), is("relations/rel"));
        assertThat(link.getType(), is("application/xml"));
        assertThat(link.getMethod(), is("get"));
        assertThat(link.getTitle(), is("link title"));
    }

    @Test
    public void testBuilder_deprecated() throws Exception {
        Link link = new Link.Builder()
            .href("http://localhost:8080/endpoint")
            .rel("relations/rel")
            .type("application/xml")
            .method("get")
            .title("link title")
            .deprecated()
            .build();

        assertThat(link.getHref(), is("http://localhost:8080/endpoint"));
        assertThat(link.getRel(), is("relations/rel"));
        assertThat(link.getType(), is("application/xml"));
        assertThat(link.getMethod(), is("get"));
        assertThat(link.getTitle(), is("link title"));
        assertThat(link.isDeprecated(), is(true));
    }

    @Test
    public void testBuilder_rel() throws Exception {
        Relation relation = Relation.SELF;
        Link link = new Link.Builder()
            .href("http://localhost:8080/endpoint")
            .rel(relation)
            .type("application/xml")
            .method("get")
            .title("link title")
            .build();

        assertThat(link.getHref(), is("http://localhost:8080/endpoint"));
        assertThat(link.getRel(), is("self"));
        assertThat(link.getType(), is("application/xml"));
        assertThat(link.getMethod(), is("get"));
        assertThat(link.getTitle(), is("link title"));
    }

    @Test
    public void testBuilder_rel_deprecated() throws Exception {
        @SuppressWarnings("deprecation")
        // intentional
        Relation relation = Relation.DEPRECATED_SAMPLE;
        Link link = new Link.Builder()
            .href("http://localhost:8080/endpoint")
            .rel(relation)
            .type("application/xml")
            .method("get")
            .title("link title")
            .build();

        assertThat(link.getHref(), is("http://localhost:8080/endpoint"));
        assertThat(link.getRel(), is("sample"));
        assertThat(link.getType(), is("application/xml"));
        assertThat(link.getMethod(), is("get"));
        assertThat(link.getTitle(), is("link title"));
        assertThat(link.isDeprecated(), is(true));
    }

    @Test
    public void testBuilder_rel_deprecated_notDeprecated() throws Exception {
        @SuppressWarnings("deprecation")
        // intentional
        Relation relation = Relation.DEPRECATED_SAMPLE;
        Link link = new Link.Builder()
            .href("http://localhost:8080/endpoint")
            .rel(relation)
            .type("application/xml")
            .method("get")
            .title("link title")
            .notDeprecated()
            .build();

        assertThat(link.getHref(), is("http://localhost:8080/endpoint"));
        assertThat(link.getRel(), is("sample"));
        assertThat(link.getType(), is("application/xml"));
        assertThat(link.getMethod(), is("get"));
        assertThat(link.getTitle(), is("link title"));
        assertThat(link.isDeprecated(), is(nullValue()));
    }
}
