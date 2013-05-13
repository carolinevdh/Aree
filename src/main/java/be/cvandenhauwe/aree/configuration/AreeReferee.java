/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import be.cvandenhauwe.aree.input.AreeInput;
import be.cvandenhauwe.aree.output.AreeOutput;
import be.cvandenhauwe.aree.reasoner.AreeReasoner;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeReferee {
    
    public static Object process(AreeConfiguration configuration, AreeArguments runtimeArguments, Object data) throws ComponentNotFoundException, Exception {
        //inputs
        for(AreeInput input : configuration.getInputChain()) data = input.process(runtimeArguments, data);
                      
        //reasoners
        for(AreeReasoner reasoner : configuration.getReasonerChain()) data = reasoner.process(runtimeArguments, data);
        
        //output
        for(AreeOutput output : configuration.getOutputChain()) data = output.process(runtimeArguments, data);
        
        return data;
    }
}