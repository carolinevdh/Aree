/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import be.cvandenhauwe.aree.input.AreeInput;
import be.cvandenhauwe.aree.loading.ComponentInjection;
import be.cvandenhauwe.aree.output.AreeOutput;
import be.cvandenhauwe.aree.reasoner.AreeReasoner;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeConfiguration {
    private final int KEY;
    private final AreeComponentChainCollection inputCCC, reasonerCCC, outputCCC;
    
    public AreeConfiguration(int key, Element inputEl, Element reasonerEl, Element outputEl) throws InvalidDescriptorException{
        KEY = key;
        inputCCC = new AreeComponentChainCollection(AreeInput.class, inputEl);
        reasonerCCC = new AreeComponentChainCollection(AreeReasoner.class, reasonerEl);
        outputCCC = new AreeComponentChainCollection(AreeOutput.class, outputEl);    }
    
    public boolean isComplete(){
        return inputCCC.isReady() && reasonerCCC.isReady() && outputCCC.isReady();
    }
    
    public void refresh(ComponentInjection inj){
        System.out.println("Server: refreshing config " + KEY);
        
        if(!inputCCC.isOptimal()) inputCCC.refresh(inj);
        if(!reasonerCCC.isOptimal()) reasonerCCC.refresh(inj);
        if(!outputCCC.isOptimal()) outputCCC.refresh(inj);
    }
    
    public AreeComponentChain getInputChain() throws ComponentNotFoundException{
        return inputCCC.getBestChain();
    }
    
    public AreeComponentChain getReasonerChain() throws ComponentNotFoundException{
        return reasonerCCC.getBestChain();
    }
    
    public AreeComponentChain getOutputChain() throws ComponentNotFoundException{
        return outputCCC.getBestChain();
    }
    
    public int getKey(){
        return KEY;
    }
}