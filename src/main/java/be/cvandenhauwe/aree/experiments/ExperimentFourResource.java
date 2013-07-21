/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.experiments;

import be.cvandenhauwe.aree.communication.*;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.ConfigurationManager;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import org.github.jamm.MemoryMeter;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@Path("/expfour")
@RequestScoped
public class ExperimentFourResource {
  
    public ExperimentFourResource() { }
    
    @POST
    @Path("/post")
    public Response postXml(String content) {
        
        try {
            AreeConfiguration config = XMLParser.descriptorToConfiguration(content);
            ConfigurationManager.getConfigurationMgr().addNewConfiguration(config.getKey(), config);
            MemoryMeter meter = new MemoryMeter();            
            long measurement = meter.measure(ConfigurationManager.getConfigurationMgr().getConfiguration(config.getKey()));
            
            
            String output = measurement + "";
            
            return Response.status(201).entity(output).build();
        } catch (InvalidDescriptorException ex) {
            return Response.status(201).entity("bad descriptor").build();
        }
    }
}
