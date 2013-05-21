/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.communication.AreeProperties;
import be.cvandenhauwe.aree.input.AreeInput;
import be.cvandenhauwe.aree.output.AreeOutput;
import be.cvandenhauwe.aree.reasoner.AreeReasoner;
import be.cvandenhauwe.aree.exceptions.ComponentNotFoundException;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import be.cvandenhauwe.aree.output.ResultSettoJSONOutput;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.dom4j.Element;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
//@Singleton //bij gebruik van EJB3
@Default
public class AreeConfiguration {
    
    @Inject
    private Instance<AreeInput> iInstances;
    private ArrayList<AreeInput> iCCC;
    
    @Inject
    private Instance<AreeReasoner> rInstances;
    private ArrayList<AreeReasoner> rCCC;
    
    @Inject
    private Instance<AreeOutput> oInstances;
    private ArrayList<AreeOutput> oCCC;
    
    private AreeComponentChainCollection iDesc, rDesc, oDesc;
    
    private int key;
    
    private boolean ready;
    
    public AreeConfiguration(){}
    
    public String refreshURLClassLoader() {
        try {
            AreeClassLoader areeloader = new AreeClassLoader(this.getClass().getClassLoader());
            URL[] urls = new URL[1];
            urls[0] = new URL("file:///Users/caroline/Thesis/Code/AreeSQLiteTools/target/AreeSQLiteTools-1.0-SNAPSHOT.jar");
            Class reasonerClass = areeloader.loadClass(urls, "com.johndoe.areesqlitetools.SQLiteReadOnlyReasoner");
            
            AreeReasoner reasoner = (AreeReasoner) reasonerClass.newInstance();
            
            AreeArguments setupArgs = new AreeArguments();
            setupArgs.put("dbpath",AreeProperties.getFilesPath() + "Chemistry.db");
            reasoner.setup(setupArgs);
            
            Object reasonerOut = reasoner.process(new AreeArguments(), "select identifier from molecules");
            
            
            
            AreeOutput output = (AreeOutput) new ResultSettoJSONOutput();
            
            return output.process(new AreeArguments(), reasonerOut).toString();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AreeConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AreeConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AreeConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AreeConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
    
    //Original CDI method, does not detect hot swapped jars
    public void refreshCDI(AreeConfiguration newConfig) throws ComponentNotFoundException, Exception{
        System.out.println("Server: refreshing config " + key + ": ready? " + ready + ", injected? " + newConfig);
        if(ready){
                if(!iDesc.currentIsPreferred())
                    iCCC = chooseComponentCDI(newConfig.iInstances, iDesc, "input");
                if(!rDesc.currentIsPreferred())
                    rCCC = chooseComponentCDI(newConfig.rInstances, rDesc, "reasoner");
                if(!oDesc.currentIsPreferred())
                    oCCC = chooseComponentCDI(newConfig.oInstances, oDesc, "output");
        }else{
            iCCC = chooseComponentCDI(newConfig.iInstances, iDesc, "input");
            rCCC = chooseComponentCDI(newConfig.rInstances, rDesc, "reasoner");
            oCCC = chooseComponentCDI(newConfig.oInstances, oDesc, "output");
        }
    }
    
    public <T extends AreeComponentInterface> ArrayList<T> chooseComponentCDI(Instance<T> instances, AreeComponentChainCollection ccc, String type) throws ComponentNotFoundException, Exception {
        System.out.println("Server: choosing " + type + "-type component chain out of " + ccc.size() + " prefered.");
        ArrayList<T> list = new ArrayList<T>();
        ccc.setCurrent(-1);
        
        
        //For all possible chains
        for(int i = 0; i < ccc.size(); i++){ //for all possible chains
            System.out.println("Server: looking for a match to " + ccc.get(i));
            Iterator it = instances.iterator();
            
            
            int nLinks = ccc.get(i).size();
            int linksFound = 0;
            
            //For all found beans
            while(it.hasNext()){
                Object next = it.next();
                String beanName = next.getClass().getCanonicalName();
                System.out.println("?" + beanName);
                
                //For every link in this chain
                for(int j = 0; j < nLinks; j++){
                    AreeComponent link = ccc.get(i).get(i);
                    
                    //check whether the bean matches the link
                    if(beanName.equalsIgnoreCase(link.getName())){
                        System.out.println("chosen: " + beanName);
                        list.add(setupComponent((T) next, link.getSetupArguments()));
                        linksFound++;
                    }
                    
                    //if all links in this chain have been found, the chain is complete and the search is done
                    if(linksFound == nLinks){
                        ccc.setCurrent(i);
                        return list;
                    }
                }
            }
            System.out.println("Server: out of instances.");
        }        
        
        throw new ComponentNotFoundException("No Input could be found.");
    }

    public ArrayList<AreeInput> getInputChain() {
        return iCCC;
    }

    public ArrayList<AreeReasoner> getReasonerChain() {
        return rCCC;
    }

    public ArrayList<AreeOutput> getOutputChain() {
        return oCCC;
    }
    
    public Integer getKey(){
        return key;
    }
    

    public void setup(int key, Element inputEl, Element reasonerEl, Element outputEl) throws InvalidDescriptorException {
             
        this.key = key;        
          
        iDesc = new AreeComponentChainCollection(AreeType.INPUT, inputEl);
        rDesc = new AreeComponentChainCollection(AreeType.REASONER, reasonerEl);
        oDesc = new AreeComponentChainCollection(AreeType.OUTPUT, outputEl); 
        
        System.out.println("Server: setup AreeConfiguration " + key + ": " + iDesc + rDesc + oDesc);
    }    
    
    private <T extends Object & AreeComponentInterface> T setupComponent(T next, AreeArguments setupArguments) throws Exception {
        if(!setupArguments.isEmpty())  next.setup(setupArguments);
        return (T) next;
    }
}