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
    
    public static Object process(AreeConfiguration configuration, Object data) throws ComponentNotFoundException {
        //input
        Object inputDone = configuration.getInput().process(data);
        
        //reasoner        
        Object reasonerDone = configuration.getReasoner().process(inputDone);
        
        //output
        return configuration.getOutput().process(reasonerDone);
    }
}