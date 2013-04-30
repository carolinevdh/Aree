/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@Path("request")
@RequestScoped
public class RequestResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RequestResource
     */
    public RequestResource() {
    }

    /**
     * Retrieves representation of an instance of be.cvandenhauwe.aree.communication.RequestResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RequestResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public Response putJson(String content) {
        
        System.out.println("received PUT");
        JSONObject json = JSONObject.fromObject(content);
        System.out.println(json.toString());
        
        return Response.status(201).entity("Response after request").build();
    }
}
