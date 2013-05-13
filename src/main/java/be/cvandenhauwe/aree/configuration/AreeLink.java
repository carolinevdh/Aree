/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.versioning.VersioningStrategy;

/**
 *
 * @author caroline
 */
public class AreeLink {
    private final String className;
    private final VersioningStrategy versioning;
    private final String version;
    private final AreeArguments setupArguments;

    public AreeLink(String className, VersioningStrategy versioning, String version, AreeArguments setupArguments) {
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
}
