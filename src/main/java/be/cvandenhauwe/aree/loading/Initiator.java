/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.loading;

import be.cvandenhauwe.aree.configuration.AreeChain;
import be.cvandenhauwe.aree.configuration.AreeChainCollection;
import be.cvandenhauwe.aree.configuration.AreeComponentWrapper;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.ConfigurationManager;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

@Singleton
@Startup
public class Initiator {
        
    @Resource(name = "pathtocomponents")
    private String pathToComponents;
    
    @EJB
    private AreeWatchService watchService;

    @PostConstruct
    public void init() {
        Path path = Paths.get(pathToComponents);
        watchService.initialScan(path);
        watchService.poll(path,60);
        
        //generateConfigurations();
    }
    
    public void generateConfigurations(){
        Random rg = new Random();
        
        //build configurations for usage
        int counter = 0;
        while(counter <= 1){
            int key = ConfigurationManager.getConfigurationMgr().getUniqueKey();
            int comp = rg.nextInt(100) + 1;
            
            AreeChain chain = new AreeChain();
            chain.add(new AreeComponentWrapper("be.cvandenhauwe.aree.components.ResultSettoJSONOutput"));
            AreeChainCollection chains = new AreeChainCollection();
            chains.add(chain);
            AreeConfiguration config = new AreeConfiguration(key, chains);    
            ConfigurationManager.getConfigurationMgr().addNewConfiguration(key, config);
            counter++;
        }
    }
}
