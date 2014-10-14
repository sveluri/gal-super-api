package org.familysearch.gal.shared.model;

import org.familysearch.gal.shared.common.Relation;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Hyper-media control representing an RFC 6570 URI template.
 */
@XmlRootElement
public class Template {

    public static class Builder {
        private Template template = new Template();

        public Builder pattern(String pattern) {
            template.setPattern(pattern);
            return this;
        }

        public Builder rel(String rel) {
            template.setRel(rel);
            return this;
        }

        public Builder rel(Relation relation) {
            template.setRel(relation.rel());

            if(relation.isDeprecated()) {
                template.setDeprecated(true);
            }

            return this;
        }

        public Builder type(String type) {
            template.setType(type);
            return this;
        }

        public Builder method(String method) {
            template.setMethod(method);
            return this;
        }

        public Builder deprecated() {
            template.setDeprecated(true);
            return this;
        }

        public Builder notDeprecated() {
            template.setDeprecated(null);
            return this;
        }

        public Template build() {
            return new Template(template);
        }
    }

    private String pattern;
    private String rel;
    private String type;
    private String method;
    private Boolean deprecated = null;

    public Template() {}

    public Template(Template that) {
        this.pattern = that.pattern;
        this.rel = that.rel;
        this.type = that.type;
        this.method = that.method;
        this.deprecated = that.deprecated;
    }

    /**
     * Return the URI template pattern.
     * @return URI template pattern
     */
    @XmlAttribute
    public String getPattern() {
        return pattern;
    }

    /**
     * Set the URI template pattern.
     * @param pattern URI template pattern
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Return the link relation for this template.
     * @return link relation
     */
    @XmlAttribute
    public String getRel() {
        return rel;
    }

    /**
     * Set the link relation for this template.
     * @param rel link relation
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * Return the media type for this resource.
     * @return media type
     */
    @XmlAttribute
    public String getType() {
        return type;
    }

    /**
     * Set the media type for this resource.
     * @param type media type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Return a comma-separated list of HTTP methods supported by the resource end-point.
     * @return CSV list of HTTP methods
     */
    @XmlAttribute
    public String getMethod() {
        return method;
    }

    /**
     * Set a comma-separated list of HTTP methods supported by the resource end-point.
     * @param method CSV list of HTTP methods
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * True if the template is deprecated; null otherwise.
     * @return true if deprecated
     */
    @XmlAttribute
    public Boolean isDeprecated() {
        return deprecated;
    }

    /**
     * Set deprecated flag; sets the value to null if false.
     * @param deprecated
     */
    public void setDeprecated(Boolean deprecated) {
        if(deprecated != null && !deprecated) {
            deprecated = null;
        }

        this.deprecated = deprecated;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Template [pattern=").append(pattern)
            .append(", rel=").append(rel)
            .append(", type=").append(type)
            .append(", method=").append(method)
            .append("]");
        return builder.toString();
    }

}
