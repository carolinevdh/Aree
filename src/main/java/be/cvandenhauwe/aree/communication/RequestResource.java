/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.configuration.AreeArguments;
import be.cvandenhauwe.aree.configuration.AreeArgumentsImpl;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.AreeReferee;
import be.cvandenhauwe.aree.configuration.ConfigurationManager;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import be.cvandenhauwe.aree.loading.ComponentInjection;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("request")
@RequestScoped
public class RequestResource {
    
    private static final String KEY = "key";
    private static final String INPUT = "data";   
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String RETURN = "return";
    private static final String ARGS = "args";
    
    @Inject
    private ComponentInjection inj;
    
    /**
     * Creates a new instance of RequestResource
     */
    public RequestResource() {
    }
    
    @POST
    @Path("post")
    @Consumes("application/json")
    public Response postJson(String content) {
        System.out.println("Server received request: " + content);
        
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
        AreeArguments runtimeArgs = new AreeArgumentsImpl();
        if(injson.containsKey(ARGS)) runtimeArgs.replaceFromJSON(injson.getJSONObject(ARGS));
        
        //fetch configuration, refresh and process 'data'
        AreeConfiguration config = ConfigurationManager.getConfigurationMgr().getConfiguration(injson.getInt(KEY));
        //todo: check if config != null
        config.refresh(inj);
        try {
            output = AreeReferee.process(config, runtimeArgs, injson.get(INPUT));
            System.out.println("Server: returning " + output + " to client.");
        //return findings to client
        } catch (ComponentNotFoundException ex) {
            Logger.getLogger(RequestResource.class.getName()).log(Level.SEVERE, null, ex);
            outjson.accumulate(SUCCESS, false);
            outjson.accumulate(RETURN, ex.getMessage());
            System.out.println("Server: exception sent to client = " + ex.getMessage());
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(outjson.toString()).build();
        } catch (Exception ex) {
            Logger.getLogger(RequestResource.class.getName()).log(Level.SEVERE, null, ex);
            outjson.accumulate(SUCCESS, false);
            outjson.accumulate(RETURN, "Component Exception: " + ex.getMessage());
            System.out.println("Server: exception sent to client = " + ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(outjson.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(output.toString()).build();
        
    }
}
