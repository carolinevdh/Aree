/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.communication.XMLParser;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import java.util.ArrayList;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeComponentDescription {
    private AreeType type;
    
    private ArrayList<AreeChain> chains = new ArrayList<AreeChain>();
    
    private int toCurrent = 0;

    AreeComponentDescription(AreeType areeType, Element element) throws InvalidDescriptorException {
        type = areeType;
        chains = XMLParser.elementToChainCollection(element);
    }
    
    void setCurrentChain(int i) {
        toCurrent = i;
    }
    
    public AreeChain getCurrentChain(){
        return chains.get(toCurrent);
    }
    
    public boolean isPreferredChain(){
        return toCurrent == 0;
    }   
    
    
    public AreeChain getPreferredChain(){
        return chains.get(0);
    }
    
    
    public AreeChain getChain(int i) {
        return chains.get(i);
    }
    
    
    
    public int size(){
        return chains.size();
    }
    
    @Override
    public String toString(){
        
        return type.name() + ": " + chains.toString();
    }

    

    
}
