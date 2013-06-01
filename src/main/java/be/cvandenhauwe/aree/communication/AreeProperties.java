/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.communication;



public class AreeProperties {

    private static final String SERVER_HOME = "/usr/local/apache-tomee-plus-1.5.2/";

    public String getFilesPath() {
        return SERVER_HOME + "Aree-files/";
    }

    public String getComponentsPath() {
        return SERVER_HOME + "Aree-components/";
    }
    
}
