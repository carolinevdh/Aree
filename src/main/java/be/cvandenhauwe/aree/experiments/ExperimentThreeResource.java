/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.experiments;

import be.cvandenhauwe.aree.communication.*;
import be.cvandenhauwe.aree.configuration.AreeArgumentsImpl;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.AreePipeline;
import be.cvandenhauwe.aree.loading.AreeContext;
import be.cvandenhauwe.aree.configuration.ConfigurationManager;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@Path("expthree")
@RequestScoped
public class ExperimentThreeResource {
    
    private static final String KEY = "key";
    private static final String INPUT = "data";   
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String RETURN = "return";
    private static final String ARGS = "args";
    
    @Inject
    private AreeContext inj;
    
    @Inject
    private AreeArgumentsImpl runtimeArgs;
    
    @Resource(name = "pathtocomponents")
    private String pathToComponents;
    
    
    /**
     * Creates a new instance of RequestResource
     */
    public ExperimentThreeResource() {
    }
    
    @POST
    @Path("post")
    @Consumes("application/json")
    public Response postJson(String content) {
        System.out.println("Server: received request " + content);
        
        String output;
        JSONObject injson = JSONObject.fromObject(content);
        
        //fetch configuration, refresh and process 'data'
        AreeConfiguration config = ConfigurationManager.getConfigurationMgr().getConfiguration(injson.getInt(KEY));
              
        try {
            //long start = System.currentTimeMillis();
            config.refresh(inj, pathToComponents);
            //long end = System.currentTimeMillis();
            //output = (end-start) + "";
            output = AreePipeline.process(config, runtimeArgs, "").toString();
            System.out.println("Server: returning " + output + " to client.");
            
        //return findings to client
        } catch (ComponentNotFoundException ex) {
            Logger.getLogger(RequestResource.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server: exception sent to client = " + ex.getMessage());
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } catch (Exception ex) {
            Logger.getLogger(RequestResource.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server: exception sent to client = " + ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(output).build();
        
    }
}
