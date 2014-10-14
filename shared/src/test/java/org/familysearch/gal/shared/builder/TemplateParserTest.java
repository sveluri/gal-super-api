package org.familysearch.gal.shared.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URI;

import org.familysearch.gal.shared.model.Link;
import org.familysearch.gal.shared.model.Template;
import org.junit.Before;
import org.junit.Test;

public class TemplateParserTest {

    private String baseUri = "http://localhost:8080/";

    private Template template = new TemplateBuilder(baseUri)
        .path(Endpoint.class)
        .path(Endpoint.class, "method")
//        .queryParams(Endpoint.class, "method")    // NOTE: TemplateParser does not support query parameters.
        .build();

    private TemplateParser parser;

    private Link link = new TemplateLinkBuilder(template)
        .param("pathParam", "rainbow")
        .build();

    @Before
    public void setup() {
        parser = new TemplateParser(template);
    }

    @Test
    public void testParse_TemplateConstructor() throws Exception {
        TemplateParser.Result result = parser.parse(link);

        assertThat(result.get("pathParam"), is("rainbow"));
    }

    @Test
    public void testParse_StringConstructor() throws Exception {
        String pattern = template.getPattern();
        parser = new TemplateParser(pattern);

        TemplateParser.Result result = parser.parse(link);

        assertThat(result.get("pathParam"), is("rainbow"));
    }

    @Test
    public void testParse_URI() throws Exception {
        URI uri = new URI(link.getHref());

        TemplateParser.Result result = parser.parse(uri);

        assertThat(result.get("pathParam"), is("rainbow"));
    }

    @Test
    public void testName() throws Exception {
        String href = link.getHref();

        TemplateParser.Result result = parser.parse(href);
        assertThat(result.get("pathParam"), is("rainbow"));
    }
}
