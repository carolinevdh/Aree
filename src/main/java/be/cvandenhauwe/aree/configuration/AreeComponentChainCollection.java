/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.communication.XMLParser;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import be.cvandenhauwe.aree.loading.ComponentInjection;
import java.util.ArrayList;
import org.dom4j.Element;

/**
 * 
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeComponentChainCollection extends ArrayList<AreeComponentChain> {
    private Class cl;

    AreeComponentChainCollection(Class areeType, Element element) throws InvalidDescriptorException {
        cl = areeType;
        addAll(XMLParser.elementToComponentChainCollection(cl, element));
    }
    
    public AreeComponentChain getBestChain() throws ComponentNotFoundException{
        for(AreeComponentChain cc : this) if(cc.isComplete()) return cc;
        throw new ComponentNotFoundException("No valid (chain of) " + cl.getSimpleName() + " found." );
    }
    
    public boolean isOptimal(){
        return get(0).isComplete();
    }
    
    public boolean isReady(){
        for(AreeComponentChain cc : this) if(cc.isComplete()) return true;
        return false;
    }
    
    @Override
    public String toString(){        
        return cl.getSimpleName() + ": " + super.toString();
    }

    public void refresh(ComponentInjection inj) {
        for(AreeComponentChain cc : this){            
            if(cc.isComplete()) break; //we need only 1 complete componentchain
            if(cc.refresh(inj)) break;            
        }
    }
}
