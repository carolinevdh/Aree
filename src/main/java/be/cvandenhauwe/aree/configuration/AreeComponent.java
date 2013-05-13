/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public interface AreeComponent {
    public Object process(AreeArguments runtimeArguments, Object obj) throws Exception;
    public void setup(AreeArguments setupArguments) throws Exception;
}
