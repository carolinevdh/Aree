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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Instance<AreeInput> ais;
    private AreeInput ai;
    
    @Inject
    private Instance<AreeReasoner> ars;
    private AreeReasoner ar;
    
    @Inject
    private Instance<AreeOutput> aos;
    private AreeOutput ao;
    
    private AreeBeanSpecification specAI, specAR, specAO;
    
    private int id;
    
    private boolean ready;
    
    public AreeConfiguration(){    
    }
    
    public void refresh(AreeConfiguration newConfig) throws ComponentNotFoundException, Exception{
        System.out.println("Server: refreshing config " + id + ": ready? " + ready + ", injected? " + newConfig);
        if(ready){
                if(!specAI.isPreferred(ai.getClass().getCanonicalName()))
                    ai = chooseComponent(newConfig.ais, specAI, "input");
                if(!specAR.isPreferred(ar.getClass().getCanonicalName()))
                    ar = chooseComponent(newConfig.ars, specAR, "reasoner");
                if(!specAO.isPreferred(ao.getClass().getCanonicalName()))
                    ao = chooseComponent(newConfig.aos, specAO, "output");
        }else{
            ai = chooseComponent(newConfig.ais, specAI, "input");
            ar = chooseComponent(newConfig.ars, specAR, "reasoner");
            ao = chooseComponent(newConfig.aos, specAO, "output");
        }
    }
    
    public <T extends AreeComponent> T chooseComponent(Instance<T> instances, AreeBeanSpecification spec, String type) throws ComponentNotFoundException, Exception {
        System.out.println("Server: choosing " + type + "-type component out of " + spec.size() + " prefered.");
        for(int i = 0; i < spec.size(); i++){
            System.out.println("Server: looking for a match to " + spec.getClassName(i));
            Iterator it = instances.iterator();
            while(it.hasNext()){
                Object next = it.next();
                String cls = next.getClass().getCanonicalName();
                System.out.println(cls);
                if(cls.equalsIgnoreCase(spec.getClassName(i))){
                    System.out.println("chosen: " + next.getClass().getCanonicalName());
                    //return (T) next;
                    if(spec.hasSetupArguments())
                        return setupComponentWithArguments((T) next, spec.getSetupArguments());
                    else
                        return (T) next;
                }
            }
            System.out.println("Server: out of instances.");
        }        
        
        throw new ComponentNotFoundException("No Input could be found.");
    }

    public AreeInput getInput() {
        return ai;
    }

    public AreeReasoner getReasoner() {
        return ar;
    }

    public AreeOutput getOutput() {
        return ao;
    }
    
    public Integer getKey(){
        return id;
    }
    

    public void setup(int key, Element inputEl, Element reasonerEl, Element outputEl) throws InvalidDescriptorException {
             
        id = key;        
          
        specAI = new AreeBeanSpecification(AreeType.INPUT, inputEl);
        specAR = new AreeBeanSpecification(AreeType.REASONER, reasonerEl);
        specAO = new AreeBeanSpecification(AreeType.OUTPUT, outputEl); 
        
        System.out.println("Server: setup AreeConfiguration " + key + ": " + specAI + specAR + specAO);
    }    

    private <T extends AreeComponent> T setupComponentWithArguments(AreeComponent next, Element setupArguments) throws Exception {
        if(setupArguments.hasContent())  next.setup(setupArguments);
        return (T) next;
    }
}
