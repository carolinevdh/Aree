/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.versioning.VersioningStrategy;

/**
 * De beschrijving van één component of implementatie van een AreeInput, -Reasoner of -Output interface.
 * @author caroline
 */
public class AreeComponent {
    //description variables
    private final String className;
    private final VersioningStrategy versioning;
    private final String version;
    private final AreeArguments setupArguments;
    
    //actual component object
    private AreeComponentInterface instance;

    public AreeComponent(String className, VersioningStrategy versioning, String version, AreeArguments setupArguments) {
        this.className = className;
        this.versioning = versioning;
        this.version = version;
        this.setupArguments = setupArguments;
    }

    public String getName() {
        return className;
    }

    public AreeArguments getSetupArguments() {
        return setupArguments;
    }

    public AreeComponentInterface getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
