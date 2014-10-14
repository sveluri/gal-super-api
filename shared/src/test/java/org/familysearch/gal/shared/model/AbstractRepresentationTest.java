package org.familysearch.gal.shared.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for {@link org.familysearch.gal.shared.model.AbstractRepresentation}
 */
public class AbstractRepresentationTest {

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractRepresentationTest.class);

    private AbstractRepresentationTestClass abstractRepresentation;
    private List<Link> links = new ArrayList<Link>();
    private List<Template> templates = new ArrayList<Template>();

    protected Link newTestLink() {
        Link link = new Link();
        link.setHref("http://localhost/user/1234");
        link.setType("application/xml");
        link.setRel("relations/test/user");
        return link;
    }

    protected Template newTestTemplate() {
        Template template = new Template();
        template.setPattern("http://localhost/user/{userid}{?query}");
        template.setRel("templates/test/user");
        template.setType("application/xml");
        template.setMethod("get,post,put,delete");
        return template;
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        abstractRepresentation = new AbstractRepresentationTestClass();
        abstractRepresentation.setLinks(links);
        abstractRepresentation.setTemplates(templates);
    }

    @Test
    public void testGetLinks() {
        assertThat(abstractRepresentation.getLinks(), is(equalTo(links)));
    }

    @Test
    public void testGetTemplates() {
        assertThat(abstractRepresentation.getTemplates(), is(equalTo(templates)));
    }

    @Test
    public void testLinks() throws Exception {
        Link link = newTestLink();
        abstractRepresentation.addLink(link);
        List<Link> links = abstractRepresentation.getLinks();
        Link actual = links.get(0);

        assertThat(actual.getRel(), is(equalTo("relations/test/user")));
    }

    @Test
    public void testLink_rel() throws Exception {
        Link link = newTestLink();
        abstractRepresentation.addLink(link);

        String rel = "relations/test/user";

        Link actual = abstractRepresentation.link(rel);
        assertThat(actual.getRel(), is(equalTo(rel)));
    }

    @Test
    public void testLink_rel_notFound() throws Exception {

        Link link = newTestLink();
        abstractRepresentation.addLink(link);

        String rel = "relations/someother";

        Link actual = abstractRepresentation.link(rel);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testLink_rel_type() throws Exception {

        Link link = newTestLink();
        abstractRepresentation.addLink(link);

        link = newTestLink();
        link.setType("application/json");

        abstractRepresentation.addLink(link);

        String rel = "relations/test/user";
        String type = "application/json";

        Link actual = abstractRepresentation.link(rel, type);
        assertThat(actual.getRel(), is(equalTo(rel)));
        assertThat(actual.getType(), is(equalTo(type)));
    }

    @Test
    public void testLink_rel_type_notFoundRel() throws Exception {

        Link link = newTestLink();
        abstractRepresentation.addLink(link);

        String rel = "relations/someother";
        String type = "application/xml";

        Link actual = abstractRepresentation.link(rel, type);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testLink_rel_type_notFoundType() throws Exception {

        Link link = newTestLink();
        abstractRepresentation.addLink(link);

        String rel = "relations/test/user";
        String type = "application/json";

        Link actual = abstractRepresentation.link(rel, type);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testTemplates() throws Exception {

        Template template = newTestTemplate();

        abstractRepresentation.addTemplate(template);

        List<Template> templates = abstractRepresentation.getTemplates();
        Template actual = templates.get(0);

        assertThat(actual.getRel(), is(equalTo("templates/test/user")));
    }

    @Test
    public void testTemplate_rel() throws Exception {
        Template template = newTestTemplate();
        abstractRepresentation.addTemplate(template);

        String rel = "templates/test/user";

        Template actual = abstractRepresentation.template(rel);
        assertThat(actual.getRel(), is(equalTo(rel)));
    }

    @Test
    public void testTemplate_rel_notFound() throws Exception {

        Template template = newTestTemplate();
        abstractRepresentation.addTemplate(template);

        String rel = "templates/someother";

        Template actual = abstractRepresentation.template(rel);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testTemplate_rel_type() throws Exception {
        Template template = newTestTemplate();
        abstractRepresentation.addTemplate(template);

        template = newTestTemplate();
        template.setType("application/json");

        abstractRepresentation.addTemplate(template);

        String rel = "templates/test/user";
        String type = "application/json";

        Template actual = abstractRepresentation.template(rel, type);
        assertThat(actual.getRel(), is(equalTo(rel)));
        assertThat(actual.getType(), is(equalTo(type)));
    }

    @Test
    public void testTemplate_rel_type_notFoundRel() throws Exception {
        Template template = newTestTemplate();
        abstractRepresentation.addTemplate(template);

        String rel = "templates/someother";
        String type = "application/xml";

        Template actual = abstractRepresentation.template(rel, type);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testTemplate_rel_type_notFoundType() throws Exception {

        Template template = newTestTemplate();
        abstractRepresentation.addTemplate(template);

        String rel = "templates/test/user";
        String type = "application/json";

        Template actual = abstractRepresentation.template(rel, type);
        assertThat(actual, is(nullValue()));
    }
}
