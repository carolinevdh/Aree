/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.ConfigurationMgr;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import org.dom4j.Element;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@Path("newconfiguration")
@RequestScoped
public class NewConfigurationResource {

    @Context
    private UriInfo context;
    
    @Inject
    private AreeConfiguration injConfig;
    
    String response = "{msg: 'Welcome to Aree'}";

    /**
     * Creates a new instance of NewConfigurationResource
     */
    public NewConfigurationResource() { }

    @GET
    @Path("get")
    @Produces("application/json")
    public String getJson() {
        System.out.println("Server: some client said hi!");
        return response;
    }
    
    @POST
    @Path("post")
    @Consumes("application/xml")
    public Response postXml(String content) {
        try {
            System.out.println("Server: received new configuration from client: ---");
            System.out.println(content);
            System.out.println("--------------end---------------");
           
            AreeConfiguration config = parseXMLToConfiguration(content, injConfig);
            ConfigurationMgr.getConfigurationMgr().addNewConfiguration(config.getKey(), config);
            
            //response = "{success: true, id: " + config.getKey() + "}";
            return Response.status(201).entity("{\"success\": true, \"id\": " + config.getKey() + "}").build();
        } catch (InvalidDescriptorException ex) {
            //response = "{success: false, message: Your descriptor is invalid. " + ex.getMessage() + "}";
            return Response.status(500).entity("{\"success\": false, \"message\": \"Your descriptor is invalid. " + ex.getMessage() + "\"}").build();
        }
    }
    
    public AreeConfiguration parseXMLToConfiguration(String xml, AreeConfiguration c) throws InvalidDescriptorException{
        
        Element root = XMLParser.xmlToRootElement(xml);
        Element config = root.element("configuration");
        if(!config.attributeValue("action").equals("new")) 
            throw new InvalidDescriptorException("Error: Asking new configuration service for " + config.attributeValue("action") + " configuration.");
                
        int key = ConfigurationMgr.getConfigurationMgr().getUniqueKey();
        c.setup(key, config.element("input"), config.element("reasoner"), config.element("output"));
        
        return c;
    }
}
