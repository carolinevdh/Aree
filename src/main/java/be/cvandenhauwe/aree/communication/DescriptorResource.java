/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.AreeReferee;
import be.cvandenhauwe.aree.configuration.ConfigurationMgr;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.New;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
    private AreeReferee aref;
    
    @Inject
    private AreeConfiguration newConfig;
    
    private int count = 0;
    
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
        try {
            System.out.println("--Server received from client:--");
            System.out.println(content);
            System.out.println("--------------end---------------");
           
            
            parseXML(content);
            ConfigurationMgr mgr = ConfigurationMgr.getConfigurationMgr();
            
            
            
            //AreeConfiguration config = cfmgr.getConfiguration(1);
            //System.out.println("got config: " + configinjected.toString());
            //System.out.println(count);
            //count++;
            //String out = (String) aref.process(config, "Ohai.");
            //System.out.println(out);
            //System.out.println("TEST: " + aref.process(cfmgr.getConfiguration(1), "Hello CDI.").toString());
            
            return Response.status(201).entity("Your descriptor was succesfully received.").build();
        } catch (InvalidDescriptorException ex) {
            return Response.status(500).entity("Your descriptor is invalid: " + ex.getMessage()).build();
        //} catch (ComponentNotFoundException ex) {
        //    return Response.status(500).entity(ex.getMessage()).build();
        }
    }
    
    private void parseXML(String content) throws InvalidDescriptorException{  
        SAXReader reader = new SAXReader();
        Document doc;
        try {
            doc = reader.read(new ByteArrayInputStream(content.getBytes()));
        } catch (DocumentException ex) {
            Logger.getLogger(DescriptorResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new InvalidDescriptorException("invalid XML");
        }
        Element root = doc.getRootElement();
        Iterator cIt = root.elementIterator("configuration");

        while(cIt.hasNext()){
            Element next = (Element) cIt.next();
            if(next.attribute("action").getData().equals("new")){
                System.out.println("Descriptor contains new configuration.");
                //cfmgr.parseNewConfiguration(null, next);
            }
        }
    }
}
