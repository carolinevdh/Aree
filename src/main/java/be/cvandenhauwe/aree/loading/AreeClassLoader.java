/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.loading;

import be.cvandenhauwe.aree.versioning.VersioningStrategy;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

public class AreeClassLoader extends ClassLoader{
    
    public AreeClassLoader(ClassLoader parent) {
        super(parent);
    }
    
    public Class loadClass(URL[] urls, String className, String version, VersioningStrategy versioning) throws ClassNotFoundException {
            URLClassLoader child = new URLClassLoader (urls, this.getClass().getClassLoader());
            return Class.forName (className, true, child);
    }
}
