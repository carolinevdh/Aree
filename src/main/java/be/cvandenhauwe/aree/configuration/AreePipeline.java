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
public class AreePipeline {
    public static Object process(AreeConfiguration configuration, AreeArguments runtimeArguments, Object data) throws ComponentNotFoundException, Exception {
        
        for(AreeComponentWrapper input : configuration.getChain()) data = input.getInstance().process(runtimeArguments, data);
        return data;
    }
}
