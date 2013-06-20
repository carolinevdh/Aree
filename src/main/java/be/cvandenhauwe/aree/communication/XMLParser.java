/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.configuration.AreeArguments;
import be.cvandenhauwe.aree.configuration.AreeArgumentsImpl;
import be.cvandenhauwe.aree.configuration.AreeChain;
import be.cvandenhauwe.aree.configuration.AreeChainCollection;
import be.cvandenhauwe.aree.configuration.AreeComponentWrapper;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.ConfigurationManager;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class XMLParser {   
    
    public static Element xmlToRootElement(String xml) throws InvalidDescriptorException{
        SAXReader reader = new SAXReader();
        Document doc;
        try {
            doc = reader.read(new ByteArrayInputStream(xml.getBytes()));
        } catch (DocumentException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new InvalidDescriptorException("invalid XML");
        }
        return doc.getRootElement();
    }
    
    public static AreeConfiguration descriptorToConfiguration(String descriptor) throws InvalidDescriptorException{
        Element config = xmlToRootElement(descriptor).element("configuration");
        
        int key = ConfigurationManager.getConfigurationMgr().getUniqueKey();
        AreeChainCollection chains = elementToChainCollection(config);
        
        return new AreeConfiguration(key, chains);
    }
    
    private static AreeChainCollection elementToChainCollection(Element config) {
        AreeChainCollection chains = new AreeChainCollection();
        Iterator<Element> it = config.elementIterator("chain");
        while(it.hasNext()) chains.add(elementToChain(it.next()));        
        return chains;
    }
    
    public static AreeChain elementToChain(Element chainEl){
        AreeChain chain = new AreeChain();
        Iterator<Element> it = chainEl.elementIterator("component");
        while(it.hasNext()) chain.add(elementToComponentWrapper(it.next()));
        return chain;
    }
    
    public static AreeComponentWrapper elementToComponentWrapper(Element compEl){
        String identifier = compEl.attributeValue("identifier");
        boolean hasSetup = Boolean.parseBoolean(compEl.attributeValue("hassetup"));
        if(hasSetup){
            AreeArguments setupArguments = new AreeArgumentsImpl();
            setupArguments.replaceFromElement(compEl.element("arguments"));
            return new AreeComponentWrapper(identifier, setupArguments);
        }else return new AreeComponentWrapper(identifier);        
    }
}
