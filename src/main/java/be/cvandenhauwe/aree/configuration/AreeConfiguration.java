/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.input.AreeInput;
import be.cvandenhauwe.aree.output.AreeOutput;
import be.cvandenhauwe.aree.reasoner.AreeReasoner;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

@Default
public class AreeConfiguration {
    
    @Inject
    private Instance<AreeInput> ais;
    private AreeInput ai;
    
    @Inject
    private Instance<AreeReasoner> ars;
    private AreeReasoner ar;
    
    @Inject
    private Instance<AreeOutput> aos;
    private AreeOutput ao;
    
    private AreeBeanSpecification specAI, specAR, specAO;
    
    private int id;
    
    public AreeConfiguration(){
        
    }

    AreeConfiguration(int key, Element inputEl, Element reasonerEl, Element outputEl) throws InvalidDescriptorException {
        System.out.println("new AreeConfiguration");
        
        id = key;        
          
        specAI = new AreeBeanSpecification(AreeType.INPUT, inputEl);
        specAR = new AreeBeanSpecification(AreeType.REASONER, reasonerEl);
        specAO = new AreeBeanSpecification(AreeType.OUTPUT, outputEl);   
    }   
    
    @PostConstruct
    public void chooseComponents(){        
        System.out.println("++++++++++++++++++++++++++++++++++ Injection happened in AreeConfiguration. ++++++++++++++++++++++++++++++++++");
//        ai = chooseInput();
//        ar = chooseReasoner();
//        ao = chooseOutput();
    }

    public AreeInput chooseInput() throws ComponentNotFoundException {
        System.out.println("Components found: ");       
        Iterator it = ais.iterator();
        while(it.hasNext()){
            Object next = it.next();
            String cls = next.getClass().getCanonicalName();
            System.out.println(cls);
            if(cls.equals(specAI.getClassName())){
                System.out.println("chosen: " + next.getClass().getCanonicalName());
                return (AreeInput) next;
            }
        }
        
        throw new ComponentNotFoundException("Input " + specAI.getClassName() + " could not be found, no alternative was provided.");
    }

    public AreeReasoner chooseReasoner() throws ComponentNotFoundException {
        System.out.println("Components found: ");       
        Iterator it = ars.iterator();
        while(it.hasNext()){
            Object next = it.next();
            String cls = next.getClass().getCanonicalName();
            System.out.println(cls);
            if(cls.equals(specAR.getClassName())){
                System.out.println("chosen: " + next.getClass().getCanonicalName());
                return (AreeReasoner) next;
            }
        }
        
        throw new ComponentNotFoundException("Reasoner " + specAR.getClassName() + " could not be found, no alternative was provided.");
    }

    public AreeOutput chooseOutput() throws ComponentNotFoundException {
        System.out.println("Components found: ");       
        Iterator it = aos.iterator();
        while(it.hasNext()){
            Object next = it.next();
            String cls = next.getClass().getCanonicalName();
            System.out.println(cls);
            if(cls.equals(specAO.getClassName())){
                System.out.println("chosen: " + next.getClass().getCanonicalName());
                return (AreeOutput) next;
            }
        }
        
        throw new ComponentNotFoundException("Input " + specAI.getClassName() + " could not be found, no alternative was provided.");
    }

    AreeInput getInput() {
        return ai;
    }

    AreeReasoner getReasoner() {
        return ar;
    }

    AreeOutput getOutput() {
        return ao;
    }
    

    public void setup(int key, Element inputEl, Element reasonerEl, Element outputEl) throws InvalidDescriptorException {
        System.out.println("setup AreeConfiguration");
        
        id = key;        
          
        specAI = new AreeBeanSpecification(AreeType.INPUT, inputEl);
        specAR = new AreeBeanSpecification(AreeType.REASONER, reasonerEl);
        specAO = new AreeBeanSpecification(AreeType.OUTPUT, outputEl); 
    }
    
}
