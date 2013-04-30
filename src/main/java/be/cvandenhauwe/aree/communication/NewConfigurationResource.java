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
    private AreeConfiguration configuration;

    /**
     * Creates a new instance of NewConfigurationResource
     */
    public NewConfigurationResource() {
    }

    /**
     * Retrieves representation of an instance of be.cvandenhauwe.aree.communication.NewConfigurationResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of NewConfigurationResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/json")
    public Response putXml(String content) {
        try {
            System.out.println("--Server received from client:--");
            System.out.println(content);
            System.out.println("--------------end---------------");
           
            ConfigurationMgr mgr = ConfigurationMgr.getConfigurationMgr();
            AreeConfiguration setupConfiguration = parseXMLToConfiguration(content, configuration);
            mgr.addNewConfiguration(setupConfiguration.getKey(), setupConfiguration);
            
            
            
            //AreeConfiguration config = cfmgr.getConfiguration(1);
            //System.out.println("got config: " + configinjected.toString());
            //System.out.println(count);
            //count++;
            //String out = (String) aref.process(config, "Ohai.");
            //System.out.println(out);
            //System.out.println("TEST: " + aref.process(cfmgr.getConfiguration(1), "Hello CDI.").toString());
            
            return Response.status(201).entity("{success: true, id: " + setupConfiguration.getKey() + "}").build();
        } catch (InvalidDescriptorException ex) {
            return Response.status(500).entity("{success: false, message: Your descriptor is invalid. " + ex.getMessage() + "}").build();
        //} catch (ComponentNotFoundException ex) {
        //    return Response.status(500).entity(ex.getMessage()).build();
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
