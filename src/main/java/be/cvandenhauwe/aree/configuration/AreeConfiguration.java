/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.input.AreeInput;
import be.cvandenhauwe.aree.output.AreeOutput;
import be.cvandenhauwe.aree.reasoner.AreeReasoner;
import be.cvandenhauwe.aree.versioning.VersioningStrategy;
import be.cvandenhauwe.aree.configuration.AreeType;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeConfiguration {
    private AreeInput currentAI;
    private AreeReasoner currentAR;
    private AreeOutput currentAO;
    
    private AreeBeanSpecification specAI, specAR, specAO;
    
    private int id;

    AreeConfiguration(int key, Element inputEl, Element reasonerEl, Element outputEl) throws InvalidDescriptorException {
        id = key;
        
          
        specAI = new AreeBeanSpecification(AreeType.INPUT, inputEl);
        specAR = new AreeBeanSpecification(AreeType.REASONER, reasonerEl);
        specAO = new AreeBeanSpecification(AreeType.OUTPUT, outputEl);                
    }    
}
