package org.familysearch.gal.shared.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Extends ArrayList to handle conversion of CSV list of strings
 * to and from a list of individual values.
 * @author himmittal
 *
 */
public class CSVParameterList extends ArrayList<String> {

    private static final long serialVersionUID = -249132979023954152L;

    public CSVParameterList() {}

    /**
     * Create Array list of strings, splitting any CSV string values.
     * @param params
     */
    public CSVParameterList(String params) {
        String[] values = StringUtils.split(params, ",");
        for (String value : values) {
            super.add(value);
        }        
    }
    
    /**
     * Add the list of strings, splitting any existing CSV entries into individual values.
     * @param params
     */
    public CSVParameterList(List<String> params) {
        for (String param : params) {
            String[] values = StringUtils.split(param, ",");
            for (String value : values) {
                super.add(value);
            }
        }
    }

    /**
     * Convert the list of values back to a CSV list.
     * @return CSV list of values
     */
    public String toCSVString() {
        return this.isEmpty() ? "" : StringUtils.join(this, ",");
    }
}
