/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.output;

import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class PlainOutput implements AreeOutput{

    @Override
    public Object process(Object obj) throws Exception{
        return "plainoutput: " +obj;
    }    

    @Override
    public void setup(Element setupArguments) {
        //
    }
}
