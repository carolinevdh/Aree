/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.loading;

import be.cvandenhauwe.aree.communication.AreeProperties;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
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
    
    private static final Path PATH = Paths.get(AreeProperties.getComponentsPath());
    
    @EJB
    private AreeWatchService watchService;

    @PostConstruct
    public void init() {
        System.out.println("Server: Initiator PostConstruct");
        watchService.poll(PATH,60);
    }
}
