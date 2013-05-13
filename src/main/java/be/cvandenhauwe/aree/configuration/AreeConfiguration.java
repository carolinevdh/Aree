/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.input.AreeInput;
import be.cvandenhauwe.aree.output.AreeOutput;
import be.cvandenhauwe.aree.reasoner.AreeReasoner;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

@Default
public class AreeConfiguration {
    
    @Inject
    private Instance<AreeInput> iInstances;
    private ArrayList<AreeInput> iChain;
    
    @Inject
    private Instance<AreeReasoner> rInstances;
    private ArrayList<AreeReasoner> rChain;
    
    @Inject
    private Instance<AreeOutput> oInstances;
    private ArrayList<AreeOutput> oChain;
    
    private AreeComponentDescription iDesc, rDesc, oDesc;
    
    private int key;
    
    private boolean ready;
    
    public AreeConfiguration(){    
    }
    
    public void refresh(AreeConfiguration newConfig) throws ComponentNotFoundException, Exception{
        System.out.println("Server: refreshing config " + key + ": ready? " + ready + ", injected? " + newConfig);
        if(ready){
                if(!iDesc.isPreferredChain())
                    iChain = chooseComponent(newConfig.iInstances, iDesc, "input");
                if(!rDesc.isPreferredChain())
                    rChain = chooseComponent(newConfig.rInstances, rDesc, "reasoner");
                if(!oDesc.isPreferredChain())
                    oChain = chooseComponent(newConfig.oInstances, oDesc, "output");
        }else{
            iChain = chooseComponent(newConfig.iInstances, iDesc, "input");
            rChain = chooseComponent(newConfig.rInstances, rDesc, "reasoner");
            oChain = chooseComponent(newConfig.oInstances, oDesc, "output");
        }
    }
    
    public <T extends AreeComponent> ArrayList<T> chooseComponent(Instance<T> instances, AreeComponentDescription spec, String type) throws ComponentNotFoundException, Exception {
        System.out.println("Server: choosing " + type + "-type component chain out of " + spec.size() + " prefered.");
        ArrayList<T> list = new ArrayList<T>();
        spec.setCurrentChain(-1);
        
        
        //For all possible chains
        for(int i = 0; i < spec.size(); i++){ //for all possible chains
            System.out.println("Server: looking for a match to " + spec.getChain(i));
            Iterator it = instances.iterator();
            
            
            int nLinks = spec.getChain(i).size();
            int linksFound = 0;
            
            //For all found beans
            while(it.hasNext()){
                Object next = it.next();
                String beanName = next.getClass().getCanonicalName();
                System.out.println("?" + beanName);
                
                //For every link in this chain
                for(int j = 0; j < nLinks; j++){
                    AreeLink link = spec.getChain(i).get(i);
                    
                    //check whether the bean matches the link
                    if(beanName.equalsIgnoreCase(link.getName())){
                        System.out.println("chosen: " + beanName);
                        list.add(setupComponent((T) next, link.getSetupArguments()));
                        linksFound++;
                    }
                    
                    //if all links in this chain have been found, the chain is complete and the search is done
                    if(linksFound == nLinks){
                        spec.setCurrentChain(i);
                        return list;
                    }
                }
            }
            System.out.println("Server: out of instances.");
        }        
        
        throw new ComponentNotFoundException("No Input could be found.");
    }

    public ArrayList<AreeInput> getInputChain() {
        return iChain;
    }

    public ArrayList<AreeReasoner> getReasonerChain() {
        return rChain;
    }

    public ArrayList<AreeOutput> getOutputChain() {
        return oChain;
    }
    
    public Integer getKey(){
        return key;
    }
    

    public void setup(int key, Element inputEl, Element reasonerEl, Element outputEl) throws InvalidDescriptorException {
             
        this.key = key;        
          
        iDesc = new AreeComponentDescription(AreeType.INPUT, inputEl);
        rDesc = new AreeComponentDescription(AreeType.REASONER, reasonerEl);
        oDesc = new AreeComponentDescription(AreeType.OUTPUT, outputEl); 
        
        System.out.println("Server: setup AreeConfiguration " + key + ": " + iDesc + rDesc + oDesc);
    }    
    
    private <T extends Object & AreeComponent> T setupComponent(T next, AreeArguments setupArguments) throws Exception {
        if(!setupArguments.isEmpty())  next.setup(setupArguments);
        return (T) next;
    }
}