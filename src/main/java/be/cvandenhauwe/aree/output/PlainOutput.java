/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.output;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class PlainOutput implements AreeOutput{

    @Override
    public Object produceOutput(Object obj) {
        return "plainoutput: " +obj;
    }    
}
