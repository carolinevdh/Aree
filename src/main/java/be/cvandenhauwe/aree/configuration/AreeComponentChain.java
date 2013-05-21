/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.util.ArrayList;

/**
 * 
 * @author caroline
 */
public class AreeComponentChain extends ArrayList<AreeComponent>{
        
    public ArrayList<AreeComponentInterface> getInstances(){
        ArrayList<AreeComponentInterface> instances = new ArrayList<AreeComponentInterface>();
        for(AreeComponent c : this){
            instances.add(c.getInstance());
        }
        return instances;
    }
    
    @Override
    public String toString(){
        String str = "";
        for(AreeComponent link : this){
            str += link.getName() + " ";
        }
        return str;
    }
}
