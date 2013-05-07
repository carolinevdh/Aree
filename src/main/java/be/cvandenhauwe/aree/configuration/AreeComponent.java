/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public interface AreeComponent {
    public Object process(Object obj) throws Exception;
    public void setup(Element setupArguments) throws Exception;
}
