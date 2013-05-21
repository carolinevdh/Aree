/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.net.URLClassLoader;
import java.util.ArrayList;
import org.dom4j.Element;
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
public class AreeConfigurationTest {
    
    public AreeConfigurationTest() {
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
     * Test of refreshURLClassLoader method, of class AreeConfiguration.
     */
    //@Test
    public void testRefreshURLClassLoader() {
        System.out.println("refreshURLClassLoader");
        AreeConfiguration instance = new AreeConfiguration();
        String result = instance.refreshURLClassLoader();
        System.out.println(result);
        assertNotNull(result);
    }

    
    /**
     * Test of refreshCDI method, of class AreeConfiguration.
     */
    //@Test
    public void testRefresh() throws Exception {
        System.out.println("refresh");
        AreeConfiguration newConfig = null;
        AreeConfiguration instance = new AreeConfiguration();
        instance.refreshCDI(newConfig);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of chooseComponentCDI method, of class AreeConfiguration.
     */
    //@Test
    public void testChooseComponent() throws Exception {
        System.out.println("chooseComponent");
        AreeConfiguration instance = new AreeConfiguration();
        ArrayList expResult = null;
        //ArrayList result = instance.chooseComponentCDI(null);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInputChain method, of class AreeConfiguration.
     */
    //@Test
    public void testGetInputChain() {
        System.out.println("getInputChain");
        AreeConfiguration instance = new AreeConfiguration();
        ArrayList expResult = null;
        ArrayList result = instance.getInputChain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReasonerChain method, of class AreeConfiguration.
     */
    //@Test
    public void testGetReasonerChain() {
        System.out.println("getReasonerChain");
        AreeConfiguration instance = new AreeConfiguration();
        ArrayList expResult = null;
        ArrayList result = instance.getReasonerChain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutputChain method, of class AreeConfiguration.
     */
    //@Test
    public void testGetOutputChain() {
        System.out.println("getOutputChain");
        AreeConfiguration instance = new AreeConfiguration();
        ArrayList expResult = null;
        ArrayList result = instance.getOutputChain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getKey method, of class AreeConfiguration.
     */
    //@Test
    public void testGetKey() {
        System.out.println("getKey");
        AreeConfiguration instance = new AreeConfiguration();
        Integer expResult = null;
        Integer result = instance.getKey();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setup method, of class AreeConfiguration.
     */
    //@Test
    public void testSetup() throws Exception {
        System.out.println("setup");
        int key = 0;
        Element inputEl = null;
        Element reasonerEl = null;
        Element outputEl = null;
        AreeConfiguration instance = new AreeConfiguration();
        instance.setup(key, inputEl, reasonerEl, outputEl);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}