package org.familysearch.gal.shared.common;

/**
 * Relations for GAL API
 */
public enum GalRelation implements Relation {

    SELF("self", "The same resource"),
    ALTERNATE("alternate", "An alternate URI to the same resource"),
    FIRST("first", "First page of a paged list"),
    LAST("last", "Last page of a paged list"),
    PREVIOUS("previous", "Previous page of a paged list"),
    NEXT("next", "Next page of a paged list"),
    PARENT("parent", "Link to the parent resource"),
    CHILDREN("children", "Link to a list of child resources");

    private String rel;
    private String description;
    private boolean deprecated = false;

    private GalRelation(String rel, String description) {
        this.rel = rel;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String rel() {
        return rel;
    }

    /**
     * Description for the relation defined
     * 
     * @return description
     */
    public String description() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDeprecated() {
        return deprecated;
    }
}
