/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import be.cvandenhauwe.aree.versioning.VersioningStrategy;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeBeanSpecification {
    private AreeType type;
    
    private String className;
    
    private VersioningStrategy vStrategy;
    private String vStart;    //optional 
    
    private Element arguments; //bean specific arguments, to be handled later

    AreeBeanSpecification(AreeType areeType, Element element) throws InvalidDescriptorException {
        type = areeType;
        className = element.elementText("class");
        vStart = element.elementText("version");
        vStrategy = VersioningStrategy.valueOf(element.element("version").attributeValue("strategy"));
                
        if(className.length() == 0) throw new InvalidDescriptorException("No class name provided for " + type);
    }

    public String getClassName() {
        return className;
    }
}
