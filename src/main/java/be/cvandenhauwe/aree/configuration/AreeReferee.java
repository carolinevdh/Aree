/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeReferee {
    
    public static Object process(AreeConfiguration configuration, AreeArguments runtimeArguments, Object data) throws ComponentNotFoundException, Exception {
        //inputchain
        for(AreeComponent input : configuration.getInputChain()) data = input.getInstance().process(runtimeArguments, data);
        
        //reasonerchain
        for(AreeComponent reasoner : configuration.getReasonerChain()) data = reasoner.getInstance().process(runtimeArguments, data);

        //outputchain
        for(AreeComponent output : configuration.getOutputChain()) data = output.getInstance().process(runtimeArguments, data);

        return data;
    }
}