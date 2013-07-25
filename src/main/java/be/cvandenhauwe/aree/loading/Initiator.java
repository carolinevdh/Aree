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
        
        generateConfigurations();
    }
    
    public void generateConfigurations(){
        for(int i = 5; i <= 100; i+= 5){   
            for(int j = 0; j < 35; j++){
                int key = ConfigurationManager.getConfigurationMgr().getUniqueKey();
            
                AreeChain chain = new AreeChain();
                for(int k = 1; k <= i; k++){
                    chain.add(new AreeComponentWrapper("be.cvandenhauwe.aree.experimentcomponents.ExperimentThreeComponent" + k));
                }
                AreeChainCollection chains = new AreeChainCollection();
                chains.add(chain);
                AreeConfiguration config = new AreeConfiguration(key, chains);    
                ConfigurationManager.getConfigurationMgr().addNewConfiguration(key, config);
            }            
        }
    }
}
