/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.util.HashMap;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import javax.enterprise.inject.New;
import javax.ws.rs.Produces;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class ConfigurationManager {
    private HashMap<Integer, AreeConfiguration> configurations = new HashMap<Integer, AreeConfiguration>();
    private int lastKey;
    
    private static ConfigurationManager singleton;

    private ConfigurationManager(){
        configurations = new HashMap<Integer, AreeConfiguration>();
        lastKey = -1;
    }
    
    public static ConfigurationManager getConfigurationMgr(){
        if(singleton == null) singleton = new ConfigurationManager();
        return singleton;
    }
    
    @Produces
    public void parseNewConfiguration(@New AreeConfiguration config, Element el) throws InvalidDescriptorException {
        if(!isValidConfiguration(el)) throw new InvalidDescriptorException();
        
        int key = getUniqueKey();
        
        Element inputEl = el.element("input");
        Element reasonerEl = el.element("reasoner");
        Element outputEl = el.element("output");
         
        config.setup(key, inputEl, reasonerEl, outputEl);
        configurations.put(key, config);
    }
    
    public int getUniqueKey(){
        lastKey++;
        return lastKey;
    }
    
    public boolean isValidConfiguration(Element el){
        if(el.elements("input").size() < 1) return false;
        if(el.elements("reasoner").size() < 1) return false;
        if(el.elements("output").size() < 1) return false;
        
        return true;
    }

    public AreeConfiguration getConfiguration(int i) {
        if(!configurations.containsKey(i)) System.out.println("Server: oops, requesting non-existent configuration " + i);
        return configurations.get(i);
    }

    public void addNewConfiguration(Integer key, AreeConfiguration config) {
        if(configurations.containsKey(key)) System.err.println("Configuration Key already exists, configuration " + configurations.get(key) + " gets overwritten.");    
        configurations.put(key, config);
    }
}