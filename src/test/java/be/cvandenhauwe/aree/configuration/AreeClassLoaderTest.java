/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeClassLoaderTest {
    
    public AreeClassLoaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadClass method, of class AreeClassLoader.
     */
    //@Test
    public void testLoadClass() throws Exception {
        System.out.println("loadClass");
        URL[] urls = new URL[1];
        urls[0] = new URL("file:///Users/caroline/Thesis/Code/AreeSQLiteTools/target/AreeSQLiteTools-1.0-SNAPSHOT.jar");
        String className = "com.johndoe.areesqlitetools.SQLiteReadOnlyReasoner";
        AreeClassLoader instance = new AreeClassLoader(AreeClassLoaderTest.class.getClassLoader());
        Class result = instance.loadClass(urls, className);
        assertNotNull(result);
    }
    
}
