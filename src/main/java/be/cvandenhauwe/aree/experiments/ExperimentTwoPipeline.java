/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.experiments;

import be.cvandenhauwe.aree.configuration.AreeArguments;
import be.cvandenhauwe.aree.configuration.AreeChain;
import be.cvandenhauwe.aree.configuration.AreeConfiguration;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class ExperimentTwoPipeline {
    public static Object process(AreeConfiguration configuration, AreeArguments runtimeArguments, Object data) throws ComponentNotFoundException, Exception {
        AreeChain chain = configuration.getChain();
        long end = System.nanoTime();
        //for(AreeComponentWrapper input : configuration.getChain()) data = input.getInstance().process(runtimeArguments, data);
        return end - Long.parseLong(data.toString());
    }
}
