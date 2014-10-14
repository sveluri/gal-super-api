package org.familysearch.gal.shared.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.familysearch.gal.shared.model.Link;
import org.familysearch.gal.shared.model.Template;
import org.junit.Test;

import com.damnhandy.uri.template.impl.ExpressionParseException;

public class TemplateLinkBuilderTest {

    @Test(expected = ExpressionParseException.class)
    public void testBuild() throws Exception {
        String pattern = "http://localhost/something";
        TemplateLinkBuilder test = new TemplateLinkBuilder(pattern);
        Link actual = test.build();
        assertThat(actual.getHref(), is("http://localhost/something"));
    }

    @Test
    public void testBuild_pathParam() throws Exception {
        String pattern = "http://localhost/something/{foo}";
        TemplateLinkBuilder test = new TemplateLinkBuilder(pattern)
            .param("foo", "bar");
        Link actual = test.build();
        assertThat(actual.getHref(), is("http://localhost/something/bar"));
    }

    @Test
    public void testBuild_map() throws Exception {
        String pattern = "http://localhost/something/{foo}";

        TemplateLinkBuilder.Parameters params = new TemplateLinkBuilder.Parameters()
            .param("foo", "bar");

        TemplateLinkBuilder test = new TemplateLinkBuilder(pattern)
            .params(params);

        Link actual = test.build();
        assertThat(actual.getHref(), is("http://localhost/something/bar"));
    }

    @Test
    public void testBuild_queryParam() throws Exception {
        String pattern = "http://localhost/something{?blah}";
        TemplateLinkBuilder test = new TemplateLinkBuilder(pattern)
            .param("blah", "value");
        Link actual = test.build();
        assertThat(actual.getHref(), is("http://localhost/something?blah=value"));
    }

    @Test
    public void testToLinkBuilder() throws Exception {
        String pattern = "http://localhost/something{?blah}";
        TemplateLinkBuilder test = new TemplateLinkBuilder(pattern)
            .param("blah", "value");
        Link.Builder actual = test.toLinkBuilder();
        assertThat(actual, is(notNullValue()));

    }

    @Test
    public void testBuild_fromTemplate() throws Exception {
        Template template = new Template.Builder()
            .pattern("http://localhost/something/{foo}")
            .rel("relation")
            .type("text/plain")
            .method("get")
            .build();

        TemplateLinkBuilder test = new TemplateLinkBuilder(template)
            .param("foo", "bar");

        Link actual = test.build();

        assertThat(actual.getHref(), is("http://localhost/something/bar"));
        assertThat(actual.getRel(), is("relation"));
        assertThat(actual.getType(), is("text/plain"));
        assertThat(actual.getMethod(), is("get"));
    }
}
