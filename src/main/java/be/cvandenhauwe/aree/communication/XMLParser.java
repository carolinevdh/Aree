/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.configuration.AreeArguments;
import be.cvandenhauwe.aree.configuration.AreeArgumentsImpl;
import be.cvandenhauwe.aree.configuration.AreeComponentChain;
import be.cvandenhauwe.aree.configuration.AreeComponent;
import be.cvandenhauwe.aree.configuration.AreeComponentChainCollection;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.configuration.ConfigurationManager;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import be.cvandenhauwe.aree.input.AreeInput;
import be.cvandenhauwe.aree.output.AreeOutput;
import be.cvandenhauwe.aree.reasoner.AreeReasoner;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
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
    
//    public static String descriptorToCacheKey(String descriptor) throws InvalidDescriptorException{
//        String key = "";
//        Element root = XMLParser.xmlToRootElement(descriptor);
//        Element config = root.element("configuration");
//        Element input = config.element("input");
//        if(input.elements().size() > 0) 
//        
//        
//        return key;
//    }
    
    public static AreeConfiguration descriptorToConfiguration(String descriptor) throws InvalidDescriptorException{
        Element root = XMLParser.xmlToRootElement(descriptor);
        Element config = root.element("configuration");
        if(!config.attributeValue("action").equals("new")) 
            throw new InvalidDescriptorException("Error: Asking new configuration service for " + config.attributeValue("action") + " configuration.");
                
        int key = ConfigurationManager.getConfigurationMgr().getUniqueKey();
        
        AreeComponentChainCollection inputCCC = elementToComponentChainCollection(AreeInput.class, config.element("input"));
        AreeComponentChainCollection reasonerCCC = elementToComponentChainCollection(AreeReasoner.class, config.element("reasoner"));
        AreeComponentChainCollection outputCCC = elementToComponentChainCollection(AreeOutput.class, config.element("output"));
        
        return new AreeConfiguration(key, inputCCC, reasonerCCC, outputCCC);
    }
    
    public static Collection<String> ElementToChildrenTextList(Element el, String tag){
        ArrayList<String> list = new ArrayList<String>();
        Iterator it = el.elementIterator(tag);
        while(it.hasNext())
            list.add(((Element) it.next()).getText());
        return list;
    }
    
    public static AreeComponentChainCollection elementToComponentChainCollection(Class cl, Element el) throws InvalidDescriptorException{
        ArrayList<AreeComponentChain> chains = new ArrayList<AreeComponentChain>();
        Iterator it = el.elementIterator("chain");
        while(it.hasNext()){
            Element next = (Element) it.next();
            chains.add(elementToComponentChain(cl, next));
        }
        
        return new AreeComponentChainCollection(cl, chains);
    }
    
    public static AreeComponentChain elementToComponentChain(Class cl, Element el){
        AreeComponentChain cc = new AreeComponentChain();
        Iterator it = el.elementIterator("link");
        while(it.hasNext()){
            Element next = (Element) it.next();
            cc.add(elementToComponent(cl, next));
        }
        
        return cc;
    }
    
    public static AreeComponent elementToComponent(Class cl, Element el){
        String className = el.elementText("class");
        AreeArguments arguments = new AreeArgumentsImpl();
        Element setupEl = el.element("arguments").element("setup");
        if(setupEl != null) arguments.replaceFromElement(setupEl);
        
        return new AreeComponent(cl, className, arguments);
    }
    
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
}
