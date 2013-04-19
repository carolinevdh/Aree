/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.util.HashMap;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import be.cvandenhauwe.aree.versioning.VersioningStrategy;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class ConfigurationMgr {
    private HashMap<Integer, AreeConfiguration> configurations = new HashMap<Integer, AreeConfiguration>();
    private int lastKey;

    public void parseNewConfiguration(Element el) throws InvalidDescriptorException {
        if(!isValidConfiguration(el)) throw new InvalidDescriptorException();
        
        int key = getUniqueKey();
        
        Element inputEl = el.element("input");
        Element reasonerEl = el.element("reasoner");
        Element outputEl = el.element("output");
        
        AreeConfiguration cfg = new AreeConfiguration(key, inputEl, reasonerEl, outputEl);      
        configurations.put(key, cfg);
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
}
