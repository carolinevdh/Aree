/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.loading;

import be.cvandenhauwe.aree.configuration.AreeComponentInterface;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeContext {
    
    @Inject
    private Instance<AreeComponentInterface> components;
    
    public Instance<AreeComponentInterface> getComponents(){
        return components;
    }
}