/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.util.HashMap;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class ConfigurationManager {
    private HashMap<Integer, AreeConfiguration> configurations;
    private int lastKey;
    
    private static ConfigurationManager singleton;

    private ConfigurationManager(){
        configurations = new HashMap<>();
        lastKey = -1;
    }
    
    public static ConfigurationManager getConfigurationMgr(){
        if(singleton == null) singleton = new ConfigurationManager();
        return singleton;
    }
    
    public int getUniqueKey(){
        lastKey++;
        return lastKey;
    }

    public AreeConfiguration getConfiguration(int i) {
        if(!configurations.containsKey(i)) System.out.println("Server: oops, requesting non-existent configuration " + i);
        return configurations.get(i);
    }

    public void addNewConfiguration(Integer key, AreeConfiguration config) { 
        if(configurations.containsKey(key)) System.err.println("Configuration Key already exists, configuration " + key + " gets overwritten.");    
        configurations.put(key, config);
    }
}