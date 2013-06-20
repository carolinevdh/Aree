/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.loading.AreeContext;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeConfiguration {
    private final int KEY;
    private final AreeChainCollection chains;
    
    public AreeConfiguration(int key, AreeChainCollection chains){
        this.KEY = key;
        this.chains = chains;
    }
    
    public boolean isComplete(){
        return chains.isReady();
    }
    
    public void refresh(AreeContext inj, String pathToComponents) throws Exception{
        if(!chains.isOptimal()) chains.refresh(inj, pathToComponents);
    }
    
    public AreeChain getChain() throws ComponentNotFoundException{
        return chains.getBestChain();
    }
    
    public int getKey(){
        return KEY;
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("======================configuration #" + KEY + "==========================\n");
        str.append(chains.toString());
        str.append("===============================================================");
        return str.toString();
    }
}
