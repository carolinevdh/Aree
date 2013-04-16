/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.input;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

public class MarkerInput implements AreeInput{

    @Override
    public Object produceInput(Object obj) {
        System.out.println(obj.toString() + " as a Marker received.");
        return "Marker["+obj+"]";
    }
    
}
