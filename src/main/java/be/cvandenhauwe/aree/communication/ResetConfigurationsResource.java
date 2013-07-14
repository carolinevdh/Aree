/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.configuration.ConfigurationManager;
import javax.annotation.Resource;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;

/**
 * REST Web Service
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
@Path("resetconfigurations")
@RequestScoped
public class ResetConfigurationsResource {

    @Context
    private UriInfo context;
    
    @Resource(name = "resetkey")
    private String resetkey;

    /**
     * Creates a new instance of ResetConfigurationsResource
     */
    public ResetConfigurationsResource() {
    }

    /**
     * PUT method for updating or creating an instance of ResetConfigurationsResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void put(String key) {
        if(!key.equals(resetkey)) return;
        ConfigurationManager.getConfigurationMgr().reset();
        System.out.println("!!! Number of configurations is " + ConfigurationManager.getConfigurationMgr().size() + "!!!");
    }
}
