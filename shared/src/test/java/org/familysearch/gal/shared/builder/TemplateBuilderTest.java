package org.familysearch.gal.shared.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.familysearch.gal.shared.model.Template;
import org.junit.Before;
import org.junit.Test;

public class TemplateBuilderTest {

    private String baseURI;

    @Before
    public void setup() {
        baseURI = "http://localhost:8080";
    }

    @Test
    public void testBuild() {
        String method = "method";
        Template actual = new TemplateBuilder(baseURI)
            .path(Endpoint.class)
            .path(Endpoint.class, method)
            .queryParams(Endpoint.class, method)
            .rel("template/endpointMethod")
            .type("application/xml")
            .build();

        assertThat(actual.getPattern(), is("http://localhost:8080/root/subpath/{pathParam}{?query1,query2}"));
        assertThat(actual.getRel(), is("template/endpointMethod"));
        assertThat(actual.getType(), is("application/xml"));
    }

    @Test
    public void testBuild_form() {
        String method = "methodForm";
        Template actual = new TemplateBuilder(baseURI)
            .path(Endpoint.class)
            .path(Endpoint.class, method)
            .queryParams(Endpoint.class, method)
            .rel("template/endpointMethod")
            .type("application/xml")
            .build();

        assertThat(actual.getPattern(),
                   is("http://localhost:8080/root/subpath/{pathParam}/form{?form1,form2,query1,query2}"));
        assertThat(actual.getRel(), is("template/endpointMethod"));
        assertThat(actual.getType(), is("application/xml"));
    }

    @Test
    public void testBuild_pathString() {
        Template actual = new TemplateBuilder(baseURI)
            .path("path1")
            .path("/path2")
            .path("path3/")
            .path("/path4/")
            .build();

        assertThat(actual.getPattern(), is("http://localhost:8080/path1/path2/path3/path4"));
    }

    @Test
    public void testBuild_pathVars() {
        Template actual = new TemplateBuilder(baseURI)
            .pathVars("pathParam2", "pathParam1", "pathParam3")
            .build();

        assertThat(actual.getPattern(), is("http://localhost:8080/{pathParam2}/{pathParam1}/{pathParam3}"));
    }

    @Test
    public void testBuild_QueryValueAndQueryParam() {
        Template actual = new TemplateBuilder(baseURI)
            .queryParams("queryParam2", "queryParam1", "queryParam3", "queryParam4")
            .queryParamValue("queryParam2", "queryParam2Value")
            .queryParamValue("queryParam4", "queryParam4Value")
            .build();
        assertThat(actual.getPattern(),
                   is("http://localhost:8080?queryParam2=queryParam2Value&queryParam4=queryParam4Value{&queryParam1,queryParam3}"));
    }

    @Test
    public void testBuild_pathValueQueryValue() {
        Template actual = new TemplateBuilder(baseURI)
            .path("path1")
            .pathVars("path2")
            .queryParams("queryParam2", "queryParam1", "queryParam3", "queryParam4")
            .queryParamValue("queryParam2", "queryParam2Value")
            .queryParamValue("queryParam4", "queryParam4Value")
            .build();
        assertThat(actual.getPattern(),
                   is("http://localhost:8080/path1/{path2}?queryParam2=queryParam2Value&queryParam4=queryParam4Value{&queryParam1,queryParam3}"));
    }

    @Test
    public void testBuild_QueryValue() {
        Template actual = new TemplateBuilder(baseURI)
            .path("path1")
            .pathVars("path2")
            .queryParams("queryParam2", "queryParam4")
            .queryParamValue("queryParam2", "queryParam2Value")
            .queryParamValue("queryParam4", "queryParam4Value")
            .build();
        assertThat(actual.getPattern(),
                   is("http://localhost:8080/path1/{path2}?queryParam2=queryParam2Value&queryParam4=queryParam4Value"));
    }

    @Test
    public void testBuild_queryParams_Strings() {
        Template actual = new TemplateBuilder(baseURI)
            .queryParams("var2", "var1")
            .build();

        assertThat(actual.getPattern(), is("http://localhost:8080{?var1,var2}"));
    }

    @Test
    public void testBuild_queryParams_Strings_existingQueryParams() {
        String uri = baseURI + "?existing=true";
        Template actual = new TemplateBuilder(uri)
            .queryParams("var2", "var1")
            .build();

        assertThat(actual.getPattern(), is("http://localhost:8080?existing=true{&var1,var2}"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPath_NoAnnotation_Class() {
        new TemplateBuilder(baseURI).path(String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPath_NoMethod() {
        new TemplateBuilder(baseURI).path(Endpoint.class, "noSuchMethod");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPath_NoAnnotation_Method() {
        new TemplateBuilder(baseURI).path(Endpoint.class, "otherMethod");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQueryParams_NoMethod() {
        new TemplateBuilder(baseURI).queryParams(Endpoint.class, "noSuchMethod");
    }
}
