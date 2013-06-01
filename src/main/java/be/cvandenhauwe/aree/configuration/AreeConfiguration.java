/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import be.cvandenhauwe.aree.loading.ComponentInjection;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeConfiguration {
    private final int KEY;
    private final AreeComponentChainCollection inputCCC, reasonerCCC, outputCCC;
    
    public AreeConfiguration(int key, 
            AreeComponentChainCollection inputCCC, 
            AreeComponentChainCollection reasonerCCC,
            AreeComponentChainCollection outputCCC) throws InvalidDescriptorException{
        
        this.KEY = key;
        this.inputCCC = inputCCC;
        this.reasonerCCC = reasonerCCC;
        this.outputCCC = outputCCC;
    }
    
    public boolean isComplete(){
        return inputCCC.isReady() && reasonerCCC.isReady() && outputCCC.isReady();
    }
    
    public void refresh(ComponentInjection inj) throws Exception{
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
    
//    public boolean isCacheable(){
//        return inputCCC.isCacheable() && reasonerCCC.isCacheable() && outputCCC.isCacheable();
//    }
//    
//    public String getCacheKey(){
//        return "<" + inputCCC.getCacheKey() + ">" + "<" + reasonerCCC.getCacheKey() + ">" + "<" + outputCCC.getCacheKey() + ">";
//    }
}