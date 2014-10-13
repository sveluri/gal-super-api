package org.familysearch.gal.shared.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents the instance specific definition of a paged list of
 * entities. Part of a set of wrapper classes intended
 * for a REST service.
 * <p>
 * NOTE: page numbering is one-based; meaning the first page is #1 (as opposed to zero-based).
 * </p>
 *
 */
@XmlType
@XmlRootElement
public class Page {

    public static final int MAX_PAGE_SIZE = 1000;

    protected Integer pageSize = Integer.valueOf(0);
    protected Integer pageNumber = Integer.valueOf(1);
    protected Integer totalRows = Integer.valueOf(0);
    protected String sortColumn;
    protected boolean ascending = true;

    /**
     * Factory method to create the next Page.
     * 
     * @param page current page definition
     * @return page definition for the next page
     */
    public static Page nextPage(Page page) {
        Page next = new Page(page);
        next.setPageNumber(next.getPageNumber() + 1);
        return next;
    }

    /**
     * Factory method to create the previous Page.
     * 
     * @param page current page definition
     * @return page definition for the previous page
     */
    public static Page previousPage(Page page) {
        Page next = new Page(page);
        next.setPageNumber(Math.max(0, next.getPageNumber() - 1));
        return next;
    }

    /**
     * Default constructor
     */
    public Page() {
    }

    /**
     * Default constructor with page size and page number.
     * 
     * @param pageSize page size -- number of items per page
     * @param pageNumber page number (one-based)
     */
    public Page(int pageSize, int pageNumber) {
        this(pageSize, pageNumber, null, true);
    }

    /**
     * Full constructor.
     * 
     * @param pageSize page size -- number of items per page
     * @param pageNumber page number (one-based)
     * @param sortColumn column name to sort on
     * @param ascending sort direction
     */
    public Page(int pageSize, int pageNumber, String sortColumn, boolean ascending) {
        this.pageSize = Math.min(pageSize, MAX_PAGE_SIZE);
        this.pageNumber = pageNumber;
        this.sortColumn = sortColumn;
        this.ascending = ascending;
    }

    /**
     * Copy constructor.
     * 
     * @param page Page to copy
     */
    public Page(Page page) {
        this.pageSize = Math.min(page.getPageSize(), MAX_PAGE_SIZE);
        this.pageNumber = page.getPageNumber();
        this.totalRows = page.getTotalRows();
        this.sortColumn = page.getSortColumn();
        this.ascending = page.isAscending();
    }

    /**
     * Return the page size.
     * 
     * @return the pageSize
     */
    @XmlAttribute
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Set the page size.
     * 
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = Math.min(pageSize, MAX_PAGE_SIZE);
    }

    /**
     * Return the page number. The page number is one-based
     * (as opposed to zero-based).
     * 
     * @return the pageNumber
     */
    @XmlAttribute
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * Set the page number. The page number is one-based
     * (as opposed to zero-based).
     * 
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Return the total number of rows in the result set.
     * 
     * @return the totalRows
     */
    @XmlAttribute
    public Integer getTotalRows() {
        return totalRows;
    }

    /**
     * Set the total number of rows in the result set.
     * 
     * @param totalRows the totalRows to set
     */
    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    /**
     * Return the column name to sort.
     * 
     * @return the sort column
     */
    @XmlAttribute
    public String getSortColumn() {
        return sortColumn;
    }

    /**
     * Set the column name to sort.
     * 
     * @param sortColumn
     */
    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    /**
     * True if sorting is in ascending order.
     * 
     * @return true if sort is ascending
     */
    @XmlAttribute
    public boolean isAscending() {
        return ascending;
    }

    /**
     * Set the sort order.
     * 
     * @param ascending true if sort order is ascending.
     */
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * Create a parameter map of key/value pairs to marshal page
     * data as query parameters.
     * 
     * @return parameter map.
     */
    public Map<String, String> toParameterMap() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("pageNumber", pageNumber.toString());
        map.put("pageSize", pageSize.toString());
        if (sortColumn != null && sortColumn.trim().length() > 0) {
            map.put("sortColumn", sortColumn.trim());
            map.put("ascending", Boolean.toString(ascending));
        }

        return map;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Page [number=").append(getPageNumber())
            .append(", size=").append(getPageSize()).append("]");
        return builder.toString();
    }

}
