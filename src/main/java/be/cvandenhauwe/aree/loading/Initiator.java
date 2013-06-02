/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.loading;

import java.nio.file.Path;
import java.nio.file.Paths;
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
    }
}
