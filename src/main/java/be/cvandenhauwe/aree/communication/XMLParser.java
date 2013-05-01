/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;

import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
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
    
    public static Collection<String> ElementToChildrenTextList(Element el, String tag){
        ArrayList<String> list = new ArrayList<String>();
        Iterator it = el.elementIterator(tag);
        while(it.hasNext())
            list.add(((Element) it.next()).getText());
        return list;
    }
}
