/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.util.ArrayList;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeJarManager {
    private static AreeJarManager singleton;
    
    private ArrayList<String> availableJars;

    private AreeJarManager(){
        availableJars = new ArrayList<String>();
    }
    
    public static AreeJarManager getAreeComponentMgr(){
        if(singleton == null) singleton = new AreeJarManager();
        return singleton;
    }
}
