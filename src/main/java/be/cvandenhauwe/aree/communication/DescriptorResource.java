/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.configuration.AreeReferee;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@RequestScoped
@Path("descriptor")
public class DescriptorResource {

    @Context
    private UriInfo context;
    
    @Inject
    private AreeReferee ar;

    /**
     * Creates a new instance of DescriptorResource
     */
    public DescriptorResource() {
    }

    /**
     * Retrieves representation of an instance of be.cvandenhauwe.aree.DescriptorResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/get")
    @Produces("application/xml")
    public String getXml() {
        return "<server>Grrreat Success!</server>";
    }

    /**
     * PUT method for updating or creating an instance of DescriptorResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("/put")
    @Consumes("application/xml")
    public Response putXml(String content) {
        System.out.println("--Server received from client:--");
        System.out.println(content);
        System.out.println("--------------end---------------");
       
        ar.doSomething();
        
        return Response.status(201).entity("Your descriptor was succesfully received.").build();
    }
}
