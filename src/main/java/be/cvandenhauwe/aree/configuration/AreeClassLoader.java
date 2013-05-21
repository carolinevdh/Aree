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
        URL[] theseurls = new URL[1];
        try {            
            theseurls[0] = new URL("file://" + AreeProperties.getComponentsPath() + "AreeSQLiteTools-1.0-SNAPSHOT.jar");

            URLClassLoader child = new URLClassLoader (theseurls, this.getClass().getClassLoader());
            return Class.forName (className, true, child);
        } catch (MalformedURLException ex) {
            Logger.getLogger(AreeClassLoader.class.getName()).log(Level.SEVERE, null, ex);
            throw new ClassNotFoundException("Server: AreeClassLoader couldn't find class " + className + " in " + urls[0]);
        }
    }
}
