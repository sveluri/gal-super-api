package org.familysearch.gal.shared.builder;

import org.familysearch.gal.shared.model.Page;

/**
 * Builder for {@link org.familysearch.gal.shared.model.Page}
 */
public class PageBuilder {

    private static final int defaultPageSize = 20;
    private static final int defaultPageNum = 1;
    Page page;

    public PageBuilder builder() {
        page = new Page(defaultPageSize, defaultPageNum);
        return this;
    }

    public PageBuilder builder(int pageSize, int pageNum) {
        page = new Page(pageSize, pageNum);
        return this;
    }

    public PageBuilder pageNumber(Integer pageNumber) {
        if (page != null) {
            this.page.setPageNumber(pageNumber);
        }
        return this;
    }

    public PageBuilder pageSize(Integer pageSize) {
        if (pageSize != null)
            page.setPageSize(pageSize);
        return this;
    }

    public PageBuilder sortField(String sortField) {
        page.setSortColumn(sortField);
        return this;
    }

    public PageBuilder asc(Boolean asc) {
        if (asc != null) {
            page.setAscending(asc);
        }
        return this;
    }

    public Page build() {
        return page;
    }
}
