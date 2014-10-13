package org.familysearch.gal.shared.builder;

import java.util.*;

import org.familysearch.gal.shared.model.Page;

/**
 * Generic Feed Specification used to create Atom Feed
 */
public abstract class FeedOptions<M> implements Iterable<M> {

    protected Map<String, Object> searchOptions = new HashMap<String, Object>();
    protected List<String> sortOptions = new ArrayList<String>();
    protected List<M> list = new ArrayList<M>();
    protected Page page = new PageBuilder().builder().build();
    
    public List<M> getList() {
        return list;
    }

    public void setList(List<M> list) {
        this.list = list;
    }
    
    public abstract Map<String, Object> getSearchOptions();

    public abstract void setSearchOptions(Map<String, Object> sortOptions);
    
    @SuppressWarnings("unchecked")
    public <T> T getSearchOption(String key, Class<T> typeToken) {
        return (T) getSearchOptions().get(key);
    }
    
    public List<String> getSortOptions() {
        return sortOptions;
    }

    public void setSortOptions(List<String> sortOptions) {
       this.sortOptions = sortOptions;
    }

    /**
     * Convenience method to return the size of the Model list
     * @return size of the managed list, or zero if the list is null
     */
    public int size() {
        return (list==null) ? 0 : list.size();
    }

    /**
     * Convenience method to return an iterator for the Model list
     * @return list iterator, or empty iterator if the list is null
     */
    @Override
    public Iterator<M> iterator() {
        if (list == null) {
            return Collections.<M>emptyList().iterator();
        } else {
            return list.iterator();
        }
    }

    /**
     * Sets new page.
     *
     * @param page New value of page.
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * Gets page.
     *
     * @return Value of page.
     */
    public Page getPage() {
        return page;
    }
}
