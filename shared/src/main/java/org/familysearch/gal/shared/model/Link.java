package org.familysearch.gal.shared.model;

import org.familysearch.gal.shared.common.Relation;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Hyper-media control representing a link to a resource.
 */
@XmlRootElement
public class Link {

    public static class Builder {

        private Link link = new Link();

        public Builder href(String href) {
            link.setHref(href);
            return this;
        }

        public Builder rel(String rel) {
            link.setRel(rel);
            return this;
        }

        public Builder rel(Relation relation) {
            link.setRel(relation.rel());

            if(relation.isDeprecated()) {
                link.setDeprecated(true);
            }

            return this;
        }

        public Builder type(String type) {
            link.setType(type);
            return this;
        }

        public Builder method(String method) {
            link.setMethod(method);
            return this;
        }

        public Builder title(String title) {
            link.setTitle(title);
            return this;
        }

        public Builder deprecated() {
            link.setDeprecated(true);
            return this;
        }

        public Builder notDeprecated() {
            link.setDeprecated(null);
            return this;
        }

        public Link build() {
            return new Link(link);
        }
    }

    private String href;
    private String rel;
    private String type;
    private String method;
    private String title;
    private Boolean deprecated = null;

    public Link() {}

    public Link(Link that) {
        this.href = that.href;
        this.rel = that.rel;
        this.type = that.type;
        this.method = that.method;
        this.title = that.title;
        this.deprecated = that.deprecated;
    }

    /**
     * Return the URI to the resource.
     * @return URI to the resource
     */
    @XmlAttribute
    public String getHref() {
        return href;
    }

    /**
     * Set the URI to the resource.
     * @param href URI to the resource
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * Return the link relation.
     * @return link relation
     */
    @XmlAttribute
    public String getRel() {
        return rel;
    }

    /**
     * Set the link relation.
     * @param rel link relation
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * Return the media type for the resource.
     * @return media type
     */
    @XmlAttribute
    public String getType() {
        return type;
    }

    /**
     * Set the media type for the resource.
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
     * Gets title.
     *
     * @return Value of title.
     */
    @XmlAttribute
    public String getTitle() {
        return title;
    }

    /**
     * Sets new title.
     *
     * @param title New value of title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the deprecated flag. Null value indicates false.
     * @return
     */
    @XmlAttribute
    public Boolean isDeprecated() {
        return deprecated;
    }

    /**
     * Set the deprecated flag. Sets the value null if false.
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
        builder.append("Link [href=").append(href)
            .append(", rel=").append(rel)
            .append(", type=").append(type)
            .append(", method=").append(method)
            .append(", title=").append(title)
            .append("]");
        return builder.toString();
    }

}
