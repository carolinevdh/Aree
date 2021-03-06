/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.loading;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeJarManager {
    private static AreeJarManager singleton;    
    private final ArrayList<String> availableJars;

    private AreeJarManager(){
        availableJars = new ArrayList<String>();
    }
    
    public static AreeJarManager getAreeJarManager(){
        if(singleton == null) singleton = new AreeJarManager();
        return singleton;
    }
    
    public void addJar(String jar){
        availableJars.add(jar);
    }
    
    public void removeJar(String jar){
        availableJars.remove(jar);
    }

    public URL[] getJarURLs(String path) {
        int size = availableJars.size();        
        URL[] urls = new URL[size];     
        
        try {
            for(int i = 0; i < size; i++) urls[i] = new URL("file://" + path + availableJars.get(i));
        } catch (MalformedURLException ex) {
            Logger.getLogger(AreeJarManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return urls;
    }
    
    @Override
    public String toString(){        
        int size = availableJars.size();
        if(size == 0) return "nothing";
        String str = availableJars.get(0);
        for(int i = 1; i < size; i++) str = str + ", " + availableJars.get(i);
             
        return str;
    }
}
