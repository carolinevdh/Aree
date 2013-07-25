/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.experiments;

import be.cvandenhauwe.aree.communication.*;
import be.cvandenhauwe.aree.configuration.AreeArgumentsImpl;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.loading.AreeContext;
import be.cvandenhauwe.aree.configuration.AreePipeline;
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
import org.github.jamm.MemoryMeter;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@Path("expfourb")
@RequestScoped
public class ExperimentFourBResource {
    
    private static final String KEY = "key";
    private static final String INPUT = "data";   
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
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
    public ExperimentFourBResource() {
    }
    
    @POST
    @Path("post")
    @Consumes("application/json")
    public Response postJson(String content) {
        System.out.println("Server: received request " + content);
        
        Object output = new Object();
        JSONObject outjson = new JSONObject();
        JSONObject injson = JSONObject.fromObject(content);
        
        //a 'key' is vital
        if(!injson.containsKey(KEY)){
            outjson.accumulate(SUCCESS, false);
            outjson.accumulate(ERROR, "'key' missing in request.");
            return Response.status(Response.Status.BAD_REQUEST).entity(outjson.toString()).build();
        }
        
        //'data' is vital
        if(!injson.containsKey(INPUT)){
            outjson.accumulate(SUCCESS, false);
            outjson.accumulate(ERROR, "'" + INPUT + "' missing in request.");
            return Response.status(Response.Status.BAD_REQUEST).entity(outjson.toString()).build();
        }
        
        //check for and load arguments
        if(injson.containsKey(ARGS)) runtimeArgs.replaceFromJSON(injson.getJSONObject(ARGS));
        
        //fetch configuration, refresh and process 'data'
        AreeConfiguration config = ConfigurationManager.getConfigurationMgr().getConfiguration(injson.getInt(KEY));
        if(config == null){
            outjson.accumulate(SUCCESS, false);
            outjson.accumulate(ERROR, "config " + injson.getInt(KEY) + " could not be found, please (re)send a descriptor.");
            System.out.println("Server: fault sent to client = no config for key" + injson.getInt(KEY));
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(outjson.toString()).build();
        }
        
        try {
            MemoryMeter mm = new MemoryMeter();
            output = mm.measureDeep(config);
            config.refresh(inj, pathToComponents);
            output += ", " + mm.measureDeep(config);
            
        //return findings to client
        } catch (Exception ex) {
            Logger.getLogger(RequestResource.class.getName()).log(Level.SEVERE, null, ex);
            outjson.accumulate(SUCCESS, false);
            outjson.accumulate(ERROR, "Component Exception: " + ex.getMessage());
            System.out.println("Server: exception sent to client = " + ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(outjson.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(output.toString()).build();
        
    }
}
