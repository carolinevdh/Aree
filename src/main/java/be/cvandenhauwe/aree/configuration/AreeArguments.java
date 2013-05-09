/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.util.HashMap;
import java.util.Iterator;
import net.sf.json.JSONObject;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeArguments extends HashMap<String, Object>{

    public static AreeArguments getArgumentsFromJSON(JSONObject json) {
        AreeArguments args = new AreeArguments();
        if(!json.isNullObject() && !json.isEmpty()) args.addFromJSON(json);        
        return args;
    }
    
//    public static AreeArguments getArgumentsFromElement(Element element) {
//        AreeArguments args = new AreeArguments();
//        args.addFromElement(element);        
//        return args;
//    }
    
    public void addFromJSON(JSONObject json){
        Iterator it = json.keys();
        while(it.hasNext()){
            String key = it.next().toString();
            put(key, json.get(key));
        }
    }
    
    public void addFromElement(Element element){
        Iterator it = element.elementIterator();
        while(it.hasNext()){
            Element next = (Element) it.next();
            put(next.getName(), next.getText());
        }
    }
}
