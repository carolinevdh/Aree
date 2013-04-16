/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.input.AreeInput;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeReferee {
    
    private ArrayList<String> aiNames = new ArrayList<String>();
    
    @Inject
    private Instance<AreeInput> ais;
    private AreeInput ai;
    
//    @Produces AreeInput getInput(InjectionPoint ip){
//        System.out.println("in InjectInput: ");
//        return new MarkerInput();        
//    }
    
    @PostConstruct
    public void process(){        
        System.out.println("Parsed from input: " + aiNames);
        if(aiNames.size() <= 0) return; //nothing imported, can't do anything here
        chooseComponents(ais, aiNames, true);      
        System.out.println("Other end of input = " + ai.produceInput("test"));
    }
    
    private void chooseComponents(Instance<AreeInput> candidates, ArrayList<String> classnames, boolean verbose){
        if(verbose) System.out.println("Components found: ");       
        Iterator it = candidates.iterator();
        while(it.hasNext()){
            Object next = it.next();
            String cls = next.getClass().getCanonicalName();
            if(verbose) System.out.println(cls);
            if(classnames.contains(cls)){
                System.out.println("chosen: " + next.getClass().getCanonicalName());
                ai = (AreeInput) next;
                //return;
            }
        }
    }

    public void configByDesc(String content) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(content.getBytes("utf-8"))));
            
            Node root = doc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            int nNodes = nodes.getLength();
            for(int i = 0; i < nNodes; i++){
                Node node = nodes.item(i);
                if(node.getNodeName().equals("input"))
                    aiNames.add(node.getTextContent());
            }
            
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(AreeReferee.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
                Logger.getLogger(AreeReferee.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AreeReferee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}