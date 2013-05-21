/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.communication.AreeProperties;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

//@Singleton
//@Startup
public class AreeWatchService {
    private static AreeWatchService singleton;

    private static final Path PATH = Paths.get(AreeProperties.getComponentsPath());
    private WatchKey watchKey;
    private WatchService watchService;        

    private boolean notDone = true;

    public AreeWatchService() {
        System.out.println("Server: new AreeWatchService");
    }
        
    //@PostConstruct
    public void init(){
            System.out.println("Server: AreeWatchService PostConstruct for " + PATH);
            try {
                watchService = FileSystems.getDefault().newWatchService();
                watchKey = PATH.register(watchService,ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY,OVERFLOW);
                poll();
            } catch (IOException ex) {
                Logger.getLogger(AreeWatchService.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static AreeWatchService getAreeWatchService(){
        if(singleton == null) singleton = new AreeWatchService();
        return singleton;
    }

    private void poll() {
        while(notDone){
            try{
                 watchService.poll(60,TimeUnit.SECONDS);
                 for(WatchEvent<?> event : watchKey.pollEvents()){
                     WatchEvent.Kind kind = event.kind();
                     WatchEvent<Path> ev = cast(event);
                     Path name = ev.context();

                     if(kind == OVERFLOW)
                         System.out.println("Server: AreeWatchService OVERFLOW occured!");

                     System.out.println("Server: " + kind + " = " + name);
                 }
                 if(!watchKey.reset()){
                    System.out.println("Server: AreeWatchService OVERFLOW occured!");
                 }
             }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
             }
        }
    }
    
    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }
}
