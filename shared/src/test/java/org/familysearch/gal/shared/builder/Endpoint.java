package org.familysearch.gal.shared.builder;

import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("/root")
public class Endpoint {

    @Path("/subpath/{pathParam}")
    public String method(@PathParam("pathParam") String pathParam,
                         @QueryParam("query1") String query1,
                         @QueryParam("query2") String query2) {

        return "success";
    }

    public void otherMethod() {}

    @Path("/subpath/{pathParam}/form")
    public String methodForm(@PathParam("pathParam") String pathParam,
                             @FormParam("form1") String form1,
                             @FormParam("form2") String form2,
                             @QueryParam("query1") String query1,
                             @QueryParam("query2") String query2) {

        return "success";
    }
}
