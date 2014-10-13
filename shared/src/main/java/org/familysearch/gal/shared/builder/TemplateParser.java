package org.familysearch.gal.shared.builder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.familysearch.gal.shared.model.Link;
import org.familysearch.gal.shared.model.Template;

import com.sun.jersey.api.uri.UriTemplate;

/**
 * Given an uri and a template, parse the uri to extract the template parameters.
 * <p>
 * NOTE: currently, this only supports path parameters in the template; the template
 * must not contain query parameters.
 * </p>
 * <p>
 * NOTE: this currently uses Jersey's UriTemplate to parse the method parameters.
 * A future version of Damnhandy UriTemplate should provide better support
 * (query parameters).
 * </p>
 *
 */
public class TemplateParser {

    private UriTemplate uriTemplate;

    public static class Result {

        private final Map<String, String> map;

        public Result(Map<String, String> map) {
            this.map = map;
        }

        public String get(String var) {
            return map.get(var);
        }

    }

    public TemplateParser(Template template) {
        this(template.getPattern());
    }

    public TemplateParser(String pattern) {
        uriTemplate = new UriTemplate(pattern);
    }

    public Result parse(Link link) {
        return parse(link.getHref());
    }

    public Result parse(String href) {
        Map<String, String> map = new HashMap<String, String>();
        uriTemplate.match(href, map);

        return new Result(map);
    }

    public Result parse(URI uri) {
        return parse(uri.toString());
    }

}
