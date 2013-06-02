/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.ConfigurationManager;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@Path("/newconfiguration")
@RequestScoped
public class NewConfigurationResource {

    private static final String KEY = "key";
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
  
    /**
     * Creates a new instance of NewConfigurationResource
     */
    public NewConfigurationResource() { }

    @GET
    @Path("/get")
    @Produces("application/json")
    public String getJson() {
        JSONObject json = new JSONObject();
        json.accumulate(SUCCESS, "true");
        return json.toString();
    }
    
    @POST
    @Path("/post")
    public Response postXml(String content) {
        JSONObject outjson = new JSONObject();
        
        try {
            System.out.println("Server: received new configuration from client: ---");
            System.out.println(content);
            System.out.println("--------------end---------------");
           
            AreeConfiguration config = XMLParser.descriptorToConfiguration(content);
            ConfigurationManager.getConfigurationMgr().addNewConfiguration(config.getKey(), config);
            
            outjson.accumulate(SUCCESS, true);
            outjson.accumulate(KEY, config.getKey());
            System.out.println("Server: key sent to client = " + config.getKey());
            return Response.status(201).entity(outjson.toString()).build();
        } catch (InvalidDescriptorException ex) {
            outjson.accumulate(SUCCESS, false);
            outjson.accumulate(ERROR, ex.getMessage());    
            System.out.println("Server: exception sent to client = " + ex.getMessage());
            return Response.status(201).entity(outjson.toString()).build();
        }
    }
}
