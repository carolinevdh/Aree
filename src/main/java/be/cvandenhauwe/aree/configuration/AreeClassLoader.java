/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.communication.AreeProperties;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

public class AreeClassLoader extends ClassLoader{
    
    public AreeClassLoader(ClassLoader parent) {
        super(parent);
    }
    
    public Class loadClass(URL[] urls, String className) throws ClassNotFoundException {
                 
            //urls[0] = new URL("file:///Users/caroline/Thesis/Code/AreeSQLiteTools/target/AreeSQLiteTools-1.0-SNAPSHOT.jar");
            //System.out.println("Server: trying to read from " + AreeProperties.getComponentsPath());
            URLClassLoader child = new URLClassLoader (urls, this.getClass().getClassLoader());
            return Class.forName (className, true, child);
    }
}
