package org.familysearch.gal.shared.mapper.model;

import java.util.Locale;
import java.util.UUID;

/**
 * Product Model class
 */

public class ProductModel {
    private UUID uuid;
    private Locale locale;
    private String name;
    private long quantity;

    /**
     * Gets quantity.
     *
     * @return Value of quantity.
     */
    public long getQuantity() {
        return quantity;
    }

    /**
     * Sets new name.
     *
     * @param name New value of name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets uuid.
     *
     * @return Value of uuid.
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets new uuid.
     *
     * @param uuid New value of uuid.
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Sets new locale.
     *
     * @param locale New value of locale.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Sets new quantity.
     *
     * @param quantity New value of quantity.
     */
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets name.
     *
     * @return Value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets locale.
     *
     * @return Value of locale.
     */
    public Locale getLocale() {
        return locale;
    }
}