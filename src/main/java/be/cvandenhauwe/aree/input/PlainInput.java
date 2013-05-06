/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.input;

import javax.enterprise.inject.Default;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

@Default
public class PlainInput implements AreeInput{

    @Override
    public Object process(Object obj) throws Exception{
        return obj;
    }    
}
