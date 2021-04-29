/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packt;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Richard
 */
@Path("packt")
public class SimpleRestfulService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SimpleRestfulService
     */
    public SimpleRestfulService() {
    }

    /**
     * Retrieves representation of an instance of packt.SimpleRestfulService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><body><h1>Simple Restful Example</body></h1></html>";
    }

    /**
     * PUT method for updating or creating an instance of SimpleRestfulService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
