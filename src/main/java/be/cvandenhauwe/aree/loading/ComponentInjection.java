/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.loading;

import be.cvandenhauwe.aree.input.AreeInput;
import be.cvandenhauwe.aree.output.AreeOutput;
import be.cvandenhauwe.aree.reasoner.AreeReasoner;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class ComponentInjection {
    @Inject
    private Instance<AreeInput> inputs;
    
    @Inject
    private Instance<AreeReasoner> reasoners;
    
    @Inject
    private Instance<AreeOutput> outputs;

    
    public Instance<AreeInput> getInputs() {
        return inputs;
    }

    public Instance<AreeReasoner> getReasoners() {
        return reasoners;
    }

    public Instance<AreeOutput> getOutputs() {
        return outputs;
    }    
}
