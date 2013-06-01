/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.communication.AreeProperties;
import java.util.HashMap;
import java.util.Iterator;
import net.sf.json.JSONObject;
import org.dom4j.Element;


public class AreeArgumentsImpl extends HashMap<String, Object> implements AreeArguments {
    
    @Override
    public void replaceFromJSON(JSONObject json){
        if(json.isNullObject() || json.isEmpty()) return;
        Iterator it = json.keys();
        while(it.hasNext()){
            String key = it.next().toString();
            put(key, transform(json.getJSONObject(key)));
        }
    }
    
    @Override
    public void replaceFromElement(Element element){
        Iterator it = element.elementIterator();
        while(it.hasNext()){
            Element next = (Element) it.next();
            put(next.getName(), next.getText());
        }
    }
    
    public Object transform(JSONObject json){
        if(!json.containsKey("type") && !json.containsKey("value")) return "";
        String type = json.getString("type");
        if(type.equalsIgnoreCase("getfilepath")){
            AreeProperties props = new AreeProperties();
            return props.getFilesPath() + json.getString("value");
        }
        else return json.get("value");
    }

    @Override
    public boolean contains(String key) {
        return containsKey(key);
    }
    
    @Override
    public Object getValue(String key){
        return get(key);
    }
    
}
