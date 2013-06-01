/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.input;

import be.cvandenhauwe.aree.configuration.AreeArguments;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

@Stateless(name = "plaininput") 
@EJB(beanInterface = AreeInput.class, beanName = "plaininput", name = "plaininput") 
public class PlainInput extends AreeInput{

    @Override
    public Object process(AreeArguments runtimeArguments, Object obj) throws Exception{
        return obj;
    }
}
