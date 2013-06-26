/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.experiments;

import be.cvandenhauwe.aree.communication.XMLParser;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.ConfigurationManager;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@Path("exp1")
@RequestScoped
public class Exp1Resource {

    @Context
    private UriInfo context;


    /**
     * PUT method for updating or creating an instance of Exp1Resource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    //@Consumes("application/xml")
    //@Produces("text/plain")
    public Response postText(String content) {
        try {
            Long startTime = System.currentTimeMillis();
            
            //start test
            AreeConfiguration config = XMLParser.descriptorToConfiguration(content);
            ConfigurationManager.getConfigurationMgr().addNewConfiguration(config.getKey(), config);
            int key = config.getKey();            
            //end test
            
            Long endTime = System.currentTimeMillis();
            //System.out.println(endTime-startTime);
            return Response.status(Response.Status.OK).entity(endTime-startTime).build();
        } catch (InvalidDescriptorException ex) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex).build();
        }
    }
}
