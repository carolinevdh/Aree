/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.input;

import be.cvandenhauwe.aree.configuration.AreeArguments;
import javax.enterprise.inject.Default;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

@Default
public class PlainInput implements AreeInput{

    @Override
    public Object process(AreeArguments runtimeArguments, Object obj) throws Exception{
        return obj;
    }    

    @Override
    public void setup(AreeArguments setupArguments) {
        //
    }
}
