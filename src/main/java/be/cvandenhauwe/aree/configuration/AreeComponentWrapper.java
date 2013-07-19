/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.loading.AreeContext;
import be.cvandenhauwe.aree.loading.AreeClassLoader;
import be.cvandenhauwe.aree.loading.AreeJarManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Instance;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeComponentWrapper {
    
    //package and classname
    private String identifier;
    
    //actual component object
    private AreeComponentInterface instance;
    
    //presence of and setup arguments
    private boolean hasSetup;
    private AreeArguments setupArguments;
    
    public AreeComponentWrapper(String identifier){
        this.identifier = identifier;
        this.hasSetup = false;
    }
    
    public AreeComponentWrapper(String identifier, AreeArguments setupArguments){
        this.identifier = identifier;
        this.hasSetup = true;
        this.setupArguments = setupArguments;
    }   
    

    public String getIdentifier() {
        return identifier;
    }
    
    public AreeComponentInterface getInstance() {
        return instance;
    }
    
    public void setInstance(AreeComponentInterface instance){
        this.instance = instance;
    }
    
    public boolean isInstantiated(){
        return instance != null;
    }
    
    /**
     * instantiates a new AreeComponent
     * @return success
     */
    public boolean newInstance(AreeContext inj, String pathToComponents) throws Exception{
//        boolean done;
//        Long start = System.currentTimeMillis();        
//        done = loadInstance(pathToComponents);
//        Long end = System.currentTimeMillis();
//        
//        if(!done) done = injectInstance(inj);
//        
//        if(done){
//            AreeArgumentsImpl setupargs = new AreeArgumentsImpl();
//            setupargs.put("time", end-start);
//            getInstance().setup(setupargs);
//            return true;
//        }
//        return false;
        
        if(injectInstance(inj) || loadInstance(pathToComponents)){
            if(hasSetup) getInstance().setup(setupArguments);
            return true;
        }
        return false;
    }

    private boolean injectInstance(AreeContext inj) {
        return doInjectInstance(inj.getComponents());
    }
    
    private boolean doInjectInstance(Instance<AreeComponentInterface> instances) {
        for(AreeComponentInterface injected : instances){
            if(injected.getClass().getCanonicalName().equals(identifier)){
                setInstance(injected);
                return true;
            }
        }        
        return false;
    }

    private boolean loadInstance(String pathToComponents) {
        AreeClassLoader loader = new AreeClassLoader(AreeComponentWrapper.class.getClassLoader());
        
        //attempt to load the class
        Class loadedClass;
        try {
            loadedClass = loader.loadClass(AreeJarManager.getAreeJarManager().getJarURLs(pathToComponents), identifier);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AreeComponentWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        //attempt to instantiate
        Object obj;
        try {
            obj = loadedClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AreeComponentWrapper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if(obj == null) return false;
        
        //save the instance and finish succesfully
        setInstance((AreeComponentInterface) obj);
        return true;
    }
}
