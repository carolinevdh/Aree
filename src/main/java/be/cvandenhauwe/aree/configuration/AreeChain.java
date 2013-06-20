/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.loading.AreeContext;
import java.util.ArrayList;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeChain extends ArrayList<AreeComponentWrapper>{    
    
    public boolean isComplete(){
        if(size() < 1) return false;
        for(AreeComponentWrapper c : this) if(!c.isInstantiated()) return false;
        return true;
    }
    
    public ArrayList<AreeComponentInterface> getInstances(){
        ArrayList<AreeComponentInterface> instances = new ArrayList<>();
        for(AreeComponentWrapper c : this){
            instances.add(c.getInstance());
        }
        return instances;
    }
    
    public boolean refresh(AreeContext inj, String pathToComponents) throws Exception {
        for(AreeComponentWrapper c : this){
            if(c.isInstantiated()) continue;
            if(!c.newInstance(inj,pathToComponents)) return false;
        }
        return true;
    }    
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(AreeComponentWrapper cw : this){
            str.append(cw.getIdentifier());
            str.append(' ');
        }
        return str.toString();
    }
}
