/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.communication.XMLParser;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import be.cvandenhauwe.aree.versioning.VersioningStrategy;
import java.util.ArrayList;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeBeanSpecification {
    private AreeType type;
    
    private ArrayList<String> classNames = new ArrayList<String>();
    
    private VersioningStrategy vStrategy;
    private String vStart;    //optional 
    
    private AreeArguments setupArgs = new AreeArguments(); //bean specific arguments, to be handled later

    AreeBeanSpecification(AreeType areeType, Element element) throws InvalidDescriptorException {
        type = areeType;
        classNames.addAll(XMLParser.ElementToChildrenTextList(element, "class"));
        vStart = element.elementText("version");
        vStrategy = VersioningStrategy.valueOf(element.element("version").attributeValue("strategy"));
                
        if(classNames.isEmpty() || classNames.get(0).length() == 0)
            throw new InvalidDescriptorException("No class name provided for " + type);
        
        
        if(element.element("arguments").hasContent()){
            setupArgs.addFromElement(element.element("arguments").element("setup"));
        }
    }

    public String getClassName(int index) {
        return classNames.get(index);
    }
    
    public boolean isPreferred(String className){
        return classNames.get(0).equals(className);
    }

    public int size() {
        return classNames.size();
    }
    
    public boolean hasSetupArguments(){
        return setupArgs != null;
    }
    
    public AreeArguments getSetupArguments(){
        return setupArgs;
    }
    
    @Override
    public String toString(){
        
        return type.name() + ": " + classNames.toString();
    }
}
