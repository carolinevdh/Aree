/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.util.HashMap;
import java.util.Iterator;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.dom4j.Element;


public class AreeArgumentsImpl extends HashMap<String, Object> implements AreeArguments {
    
    @Resource(name = "pathtofiles")
    private String pathToFiles;
    
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
            return pathToFiles + json.getString("value");
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
    
    @Override
    public boolean empty(){
        return isEmpty();
    }
}