/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.loading;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.util.concurrent.TimeUnit;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

@Stateless
public class AreeWatchService {

    private WatchKey watchKey;
    private WatchService watchService;        

    private final boolean notDone = true;
    
    @Asynchronous
    public void initialScan(Path path){
        AreeJarManager jarmgr = AreeJarManager.getAreeJarManager();
        System.out.println("Server: AreeWatchService attempting initial scan at " + path);

        
        DirectoryStream<Path> ds;
        try {
            ds = Files.newDirectoryStream(path);
            System.out.println(ds);
            for(Path file : ds){
                String filename = file.getFileName().toString();                
                if(filename.endsWith(".jar")) jarmgr.addJar(filename);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AreeWatchService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Server: AreeJarManager now contains " + jarmgr.toString());        
    }
    
    @Asynchronous
    public void poll(Path path, int everySeconds) {
        try {
            watchService = FileSystems.getDefault().newWatchService();
            watchKey = path.register(watchService,ENTRY_CREATE,ENTRY_DELETE,OVERFLOW);
        } catch (IOException ex) {
            Logger.getLogger(AreeWatchService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server: WatchService failed, will not be able to hot swap .jars.");
            return;
        }        
        
        AreeJarManager jarmgr = AreeJarManager.getAreeJarManager();
        
        System.out.println("Server: AreeWatchService now polling " + path);
        while(notDone){
            try{
                 watchService.poll(everySeconds,TimeUnit.SECONDS);
                 for(WatchEvent<?> event : watchKey.pollEvents()){
                     WatchEvent.Kind kind = event.kind();
                     WatchEvent<Path> ev = cast(event);
                     String name = ev.context().toString();
                     if(!name.endsWith(".jar")) continue; //only interested in .jar files
                     System.out.println("Server: " + kind + " " + name);
                     
                     if(kind == OVERFLOW)
                         System.out.println("Server: AreeWatchService OVERFLOW occured!");
                     else if(kind == ENTRY_CREATE){
                         jarmgr.addJar(name);
                         System.out.println("Server: AreeJarManager now contains " + jarmgr.toString());
                     }else if(kind == ENTRY_DELETE){
                         jarmgr.removeJar(name);
                         System.out.println("Server: AreeJarManager now contains " + jarmgr.toString());
                     }
                         
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