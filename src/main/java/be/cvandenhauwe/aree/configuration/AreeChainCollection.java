/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.loading.AreeContext;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeChainCollection extends ArrayList<AreeChain>{
    
    public AreeChain getBestChain() throws ComponentNotFoundException{
        for(AreeChain c : this) if(c.isComplete()) return c;
        throw new ComponentNotFoundException("No valid chain found." );
    }
    
    public boolean isOptimal(){
        return get(0).isComplete();
    }
    
    public boolean isReady(){
        for(AreeChain c : this) if(c.isComplete()) return true;
        return false;
    }

    public void refresh(AreeContext inj, String pathToComponents) throws Exception {
        for(AreeChain c : this){            
            if(c.isComplete()) break; //we need only 1 complete componentchain
            if(c.refresh(inj, pathToComponents)) break;            
        }
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        int size = size();
        for(int i = 0; i < size; i++){
            str.append("(");
            str.append(i);
            str.append("): ");
            str.append(get(i).toString());
            str.append("\n");
        }
        return str.toString();
    }
}
