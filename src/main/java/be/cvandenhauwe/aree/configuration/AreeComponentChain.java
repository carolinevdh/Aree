/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.loading.ComponentInjection;
import java.util.ArrayList;

/**
 * 
 * @author caroline
 */
public class AreeComponentChain extends ArrayList<AreeComponent>{
            
    public boolean isComplete(){
        if(size() < 1) return false;
        for(AreeComponent c : this) if(!c.isInstantiated()) return false;
        return true;
    }
    
    public ArrayList<AreeComponentInterface> getInstances(){
        ArrayList<AreeComponentInterface> instances = new ArrayList<AreeComponentInterface>();
        for(AreeComponent c : this){
            instances.add(c.getInstance());
        }
        return instances;
    }    

    public boolean refresh(ComponentInjection inj) {
        for(AreeComponent c : this){
            if(c.isInstantiated()) continue;
            if(!c.newInstance(inj)) return false;
        }
        return true;
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
