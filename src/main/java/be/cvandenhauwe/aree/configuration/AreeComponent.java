/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.loading.AreeJarManager;
import be.cvandenhauwe.aree.loading.AreeClassLoader;
import be.cvandenhauwe.aree.input.AreeInput;
import be.cvandenhauwe.aree.loading.AreeContext;
import be.cvandenhauwe.aree.output.AreeOutput;
import be.cvandenhauwe.aree.reasoner.AreeReasoner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Instance;

/**
 * De beschrijving van één component of implementatie van een AreeInput, -Reasoner of -Output interface.
 * @author caroline
 */
public class AreeComponent {
    //expected class
    private final Class cl;
    
    //description variables
    private final String className;
    private final AreeArguments setupArguments;
    
    //actual component object
    private AreeComponentInterface instance;

    public AreeComponent(Class cl, String className, AreeArguments setupArguments) {
        this.cl = cl;
        this.className = className;
        this.setupArguments = setupArguments;
    }    
    
    public boolean isInstantiated(){
        return instance != null;
    }    

    public String getName() {
        return className;
    }
    
    public boolean usesSetup(){
        return !setupArguments.empty();
    }

    public AreeComponentInterface getInstance() {
        return instance;
    }
    
    public void setInstance(AreeComponentInterface instance){
        this.instance = instance;
    }
    
    public void removeInstance(){
        this.instance = null;
    }

    /**
     * instantiates a new AreeInput, -Reasoner or -Output
     * @return success
     */
    public boolean newInstance(AreeContext inj, String pathToComponents) throws Exception{
        if(injectInstance(inj) || loadInstance(pathToComponents)){
            if(usesSetup()) getInstance().setup(setupArguments);
            return true;
        }
        return false;
    }

    private boolean injectInstance(AreeContext inj) {
        boolean success = false;        
        String clName = cl.getSimpleName();
        
        if(clName.equals(AreeInput.class.getSimpleName())) success = doInjectInstance(inj.getInputs());
        else if(clName.equals(AreeReasoner.class.getSimpleName())) success = doInjectInstance(inj.getReasoners());
        else if(clName.equals(AreeOutput.class.getSimpleName())) success = doInjectInstance(inj.getOutputs());
        
        return success;
    }
    
    private <T extends AreeComponentInterface> boolean doInjectInstance(Instance<T> instances) {
        for(T injected : instances){
            if(injected.getClass().getCanonicalName().equals(className)){
                setInstance(injected);
                return true;
            }
        }        
        return false;
    }

    private boolean loadInstance(String pathToComponents) {
        AreeClassLoader loader = new AreeClassLoader(AreeComponent.class.getClassLoader());
        
        //attempt to load the class
        Class loadedClass;
        try {
            loadedClass = loader.loadClass(AreeJarManager.getAreeJarManager().getJarURLs(pathToComponents), className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AreeComponent.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        //attempt to instantiate
        Object obj = null;
        try {
            obj = loadedClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(AreeComponent.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AreeComponent.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if(obj == null) return false;
        
        //make sure instance implements correct interface
        if(cl.isInstance(obj)){
            setInstance((AreeComponentInterface) obj);
            return true;
        }else
            return false;
    }
}
