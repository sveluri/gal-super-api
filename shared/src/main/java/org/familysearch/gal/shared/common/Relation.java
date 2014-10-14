package org.familysearch.gal.shared.common;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Relations for GAL API
 */
public enum Relation {

    SELF("self", "The same resource"),
    ALTERNATE("alternate", "An alternate URI to the same resource"),
    FIRST("first", "First page of a paged list"),
    LAST("last", "Last page of a paged list"),
    PREVIOUS("previous", "Previous page of a paged list"),
    NEXT("next", "Next page of a paged list"),
    PARENT("parent", "Link to the parent resource"),
    CHILDREN("children", "Link to a list of child resources"),
    RELATIONS_APPLICATIONS("relations/applications", "List of applications"),
    RELATIONS_APPLICATION("relations/application", "Application resource"),
    
    @Deprecated
    DEPRECATED_SAMPLE("sample", "Deprecated sample for test cases");

    private static final Logger LOG = LoggerFactory.getLogger(Relation.class);

    static {
        try {
            for (Field field : Relation.class.getFields()) {
                if (field.isAnnotationPresent(Deprecated.class)) {
                    Relation rel = (Relation) field.get(null);
                    rel.deprecated = true;
                }
            }
        }
        catch (Exception e) {
            LOG.error("Exception setting deprecated field", e);
        }
    }

    private String rel;
    private String description;
    private boolean deprecated = false;

    private Relation(String rel, String description) {
        this.rel = rel;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
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
    public boolean isDeprecated() {
        return deprecated;
    }
}
