package org.familysearch.gal.shared.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.familysearch.gal.shared.common.Relation;
import org.junit.Before;
import org.junit.Test;

public class TemplateTest {

    private Template template;

    @Before
    public void setup() {
        template = new Template();
    }

    @Test
    public void testPattern() throws Exception {
        template.setPattern("http://localhost/endpoint{?query}");
        assertThat(template.getPattern(), is(equalTo("http://localhost/endpoint{?query}")));
    }

    @Test
    public void testRel() throws Exception {
        template.setRel("templates/test");
        assertThat(template.getRel(), is(equalTo("templates/test")));
    }

    @Test
    public void testType() throws Exception {
        template.setType("application/xml");
        assertThat(template.getType(), is(equalTo("application/xml")));
    }

    @Test
    public void testMethod() throws Exception {
        template.setMethod("get,post,put,delete");
        assertThat(template.getMethod(), is(equalTo("get,post,put,delete")));
    }

    @Test
    public void testDeprecated() throws Exception {
        Boolean deprecated = true;
        template.setDeprecated(deprecated);
        assertThat(template.isDeprecated(), is(true));
    }

    @Test
    public void testDeprecated_false() throws Exception {
        Boolean deprecated = false;
        template.setDeprecated(deprecated);
        assertThat(template.isDeprecated(), is(nullValue()));
    }

    @Test
    public void testDeprecated_null() throws Exception {
        Boolean deprecated = null;
        template.setDeprecated(deprecated);
        assertThat(template.isDeprecated(), is(nullValue()));
    }

    @Test
    public void testBuilder() throws Exception {
        Template template = new Template.Builder()
            .pattern("http://localhost/endpoint{?query}")
            .rel("templates/test")
            .type("application/xml")
            .method("get")
            .build();

        assertThat(template.getPattern(), is("http://localhost/endpoint{?query}"));
        assertThat(template.getRel(), is("templates/test"));
        assertThat(template.getType(), is("application/xml"));
        assertThat(template.getMethod(), is("get"));
    }

    @Test
    public void testBuilder_deprecated() throws Exception {
        Template template = new Template.Builder()
            .pattern("http://localhost/endpoint{?query}")
            .rel("templates/test")
            .type("application/xml")
            .method("get")
            .deprecated()
            .build();

        assertThat(template.getPattern(), is("http://localhost/endpoint{?query}"));
        assertThat(template.getRel(), is("templates/test"));
        assertThat(template.getType(), is("application/xml"));
        assertThat(template.getMethod(), is("get"));
        assertThat(template.isDeprecated(), is(true));
    }

    @Test
    public void testBuilder_rel() throws Exception {
        Relation relation = Relation.SELF;
        Template template = new Template.Builder()
            .pattern("http://localhost/endpoint{?query}")
            .rel(relation)
            .type("application/xml")
            .method("get")
            .build();

        assertThat(template.getPattern(), is("http://localhost/endpoint{?query}"));
        assertThat(template.getRel(), is("self"));
        assertThat(template.getType(), is("application/xml"));
        assertThat(template.getMethod(), is("get"));
        assertThat(template.isDeprecated(), is(nullValue()));
    }

    @Test
    public void testBuilder_rel_deprecated() throws Exception {
        @SuppressWarnings("deprecation")
        // intentional
        Relation relation = Relation.DEPRECATED_SAMPLE;
        Template template = new Template.Builder()
            .pattern("http://localhost/endpoint{?query}")
            .rel(relation)
            .type("application/xml")
            .method("get")
            .build();

        assertThat(template.getPattern(), is("http://localhost/endpoint{?query}"));
        assertThat(template.getRel(), is("sample"));
        assertThat(template.getType(), is("application/xml"));
        assertThat(template.getMethod(), is("get"));
        assertThat(template.isDeprecated(), is(true));
    }

    @Test
    public void testBuilder_rel_deprecated_notDeprecated() throws Exception {
        @SuppressWarnings("deprecation")
        // intentional
        Relation relation = Relation.DEPRECATED_SAMPLE;
        Template template = new Template.Builder()
            .pattern("http://localhost/endpoint{?query}")
            .rel(relation)
            .type("application/xml")
            .method("get")
            .notDeprecated()
            .build();

        assertThat(template.getPattern(), is("http://localhost/endpoint{?query}"));
        assertThat(template.getRel(), is("sample"));
        assertThat(template.getType(), is("application/xml"));
        assertThat(template.getMethod(), is("get"));
        assertThat(template.isDeprecated(), is(nullValue()));
    }
}
