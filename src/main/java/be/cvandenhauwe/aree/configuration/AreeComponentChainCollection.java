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
public class AreeComponentChainCollection extends ArrayList<AreeComponentChain> {
    private AreeType type;
    private int pointer = 0;

    AreeComponentChainCollection(AreeType areeType, Element element) throws InvalidDescriptorException {
        type = areeType;
        this.addAll(XMLParser.elementToChainCollection(element));
    } 
    
    public AreeComponentChain getCurrent(){
        return get(pointer);
    }
    
    public void setCurrent(int i) {
        pointer = i;
    }    
    
    public boolean currentIsPreferred(){
        return pointer == 0;
    } 
    
    @Override
    public String toString(){        
        return type.name() + ": " + super.toString();
    }
}
