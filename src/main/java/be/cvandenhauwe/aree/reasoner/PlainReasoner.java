/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.reasoner;

import be.cvandenhauwe.aree.configuration.AreeArguments;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class PlainReasoner implements AreeReasoner{

    @Override
    public Object process(AreeArguments runtimeArguments, Object obj) throws Exception {
        return obj;
    }

    @Override
    public void setup(AreeArguments setupArguments) throws Exception {
    }
}
