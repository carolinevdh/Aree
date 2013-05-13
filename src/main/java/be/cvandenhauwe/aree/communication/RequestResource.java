/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.configuration.AreeArguments;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.AreeReferee;
import be.cvandenhauwe.aree.configuration.ConfigurationMgr;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

    @Inject
    private AreeConfiguration injConfiguration;
    
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
    @Path("get")
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    
    @POST
    @Path("post")
    @Consumes("application/json")
    public Response postJson(String content) {
        Object output = new Object();
        
        System.out.println("Server received request: " + content);
        JSONObject json = JSONObject.fromObject(content);        
        AreeConfiguration config = ConfigurationMgr.getConfigurationMgr().getConfiguration(json.getInt("key"));
        try {
            if(!json.containsKey("args")) System.out.println("Server: no runtime arguments specified" );
            config.refresh(injConfiguration);
            output = AreeReferee.process(
                    config, AreeArguments.getArgumentsFromJSON(json.getJSONObject("args")), json.get("data"));
            System.out.println("Server: success, generating output: " + output);
        } catch (Exception ex) {
            System.out.println("Server: Exception, " + ex.getMessage());
            return Response.status(500).entity("{\"succes\": false, \"message\": \"Error: "+ ex.getMessage() +"\"").build();
        }
        return Response.status(201).entity(output.toString()).build();
    }
}
