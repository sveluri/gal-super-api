package org.familysearch.gal.shared.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Base class for representation classes providing a list of links and templates.
 */
public abstract class AbstractRepresentation {

    private List<Link> links = new ArrayList<Link>();
    private List<Template> templates = new ArrayList<Template>();

    public AbstractRepresentation() {
        super();
    }

    @XmlElement(name = "link")
    @JsonProperty(value="links")
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public Link link(String rel) {
        for(Link link : links) {
            if(rel.equals(link.getRel())) {
                return link;
            }
        }

        return null;
    }

    public Link link(String rel, String type) {
        for(Link link : links) {
            if(rel.equals(link.getRel()) && type.equals(link.getType())) {
                return link;
            }
        }

        return null;
    }

    @XmlElement(name = "template")
    @JsonProperty(value="templates")
    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public void addTemplate(Template template) {
        templates.add(template);
    }

    public Template template(String rel) {
        for(Template template : templates) {
            if(rel.equals(template.getRel())) {
                return template;
            }
        }

        return null;
    }

    public Template template(String rel, String type) {
        for(Template template : templates) {
            if(rel.equals(template.getRel()) && type.equals(template.getType())) {
                return template;
            }
        }

        return null;
    }

}
