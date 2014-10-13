package org.familysearch.gal.shared.builder;

import java.util.HashMap;
import java.util.Map;

import org.familysearch.gal.shared.model.Link;
import org.familysearch.gal.shared.model.Template;

import com.damnhandy.uri.template.UriTemplate;

/**
 * Use a Template or URI Template pattern (according to RFC 6570)
 * and expand it using values into a Link object.
 *
 */
public class TemplateLinkBuilder {

    public static class Parameters extends HashMap<String, Object> {

        private static final long serialVersionUID = 2897362086799694157L;

        public Parameters param(String name, String value) {
            put(name, value);
            return this;
        }

    }

    private final Map<String, Object> params = new HashMap<String, Object>();

    private Template template;

    /**
     * Construct an instance based on the given Template instance. Values for
     * 'rel', 'type', and 'method' are taken from the template as well.
     * 
     * @param template Template instance
     */
    public TemplateLinkBuilder(Template template) {
        this.template = template;
    }

    /**
     * Construct an instance based on the given URI template pattern. Note
     * that 'rel', 'type', and 'method' will default to null values.
     * 
     * @param pattern URI template pattern
     */
    public TemplateLinkBuilder(String pattern) {
        this.template = new Template.Builder()
            .pattern(pattern)
            .build();
    }

    /**
     * Set the template parameter value for expansion.
     * 
     * @param name template parameter name
     * @param value parameter value.
     * @return builder
     */
    public TemplateLinkBuilder param(String name, String value) {
        params.put(name, value);
        return this;
    }

    /**
     * Add the parameter values for expansion from the given parameter name to value map.
     * 
     * @param paramMap parameter map
     * @return builder
     */
    public TemplateLinkBuilder params(Map<String, Object> paramMap) {
        params.putAll(paramMap);
        return this;
    }

    /**
     * Expand the URI template using the specified parameter values and return the result
     * as a Link.Builder instance.
     * 
     * @return Link.Builder instance
     */
    public Link.Builder toLinkBuilder() {
        UriTemplate uriTemplate = UriTemplate.fromTemplate(template.getPattern());

        if (!params.isEmpty()) {
            uriTemplate.set(params);
        }

        String uri = uriTemplate.expand();

        Link.Builder linkBuilder = new Link.Builder()
            .href(uri)
            .rel(template.getRel())
            .type(template.getType())
            .method(template.getMethod());
        return linkBuilder;
    }

    /**
     * Expand the URI template using the specified parameter values and return the result
     * as a Link instance.
     * 
     * @return link
     */
    public Link build() {
        Link.Builder linkBuilder = toLinkBuilder();
        return linkBuilder.build();
    }

}
