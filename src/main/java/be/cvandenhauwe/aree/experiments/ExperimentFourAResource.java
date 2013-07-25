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
//import net.sourceforge.sizeof.SizeOf;
import org.github.jamm.MemoryMeter;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@Path("/expfour")
@RequestScoped
public class ExperimentFourAResource {
  
    public ExperimentFourAResource() { }
    
    @POST
    @Path("/post")
    public Response postXml(String content) {
        
        try {
            AreeConfiguration config = XMLParser.descriptorToConfiguration(content);
            ConfigurationManager.getConfigurationMgr().addNewConfiguration(config.getKey(), config);      
            
            MemoryMeter mm = new MemoryMeter();
            String output = config.size() + ", " + mm.measure(config) + ", " + mm.measureDeep(config);
            System.out.println("BOOLEAN = " + mm.measure(true));
            return Response.status(201).entity(output).build();
        } catch (InvalidDescriptorException ex) {
            return Response.status(201).entity("bad descriptor").build();
        }
    }
}
