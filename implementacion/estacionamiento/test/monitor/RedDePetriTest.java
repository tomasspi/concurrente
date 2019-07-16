/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Mr. Green
 */
public class RedDePetriTest {
    
    public RedDePetriTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getRdP method, of class RedDePetri.
     */
    @Test
    public void testGetRdP() {
        System.out.println("getRdP");
        RedDePetri expResult = null;
        RedDePetri result = RedDePetri.getRdP();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of disparar method, of class RedDePetri.
     */
    @Test
    public void testDisparar() {
        System.out.println("disparar");
        int t = 0;
        RedDePetri instance = null;
        instance.disparar(t);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isSensibilizada method, of class RedDePetri.
     */
    @Test
    public void testIsSensibilizada() {
        System.out.println("isSensibilizada");
        int t = 0;
        RedDePetri instance = null;
        boolean expResult = false;
        boolean result = instance.isSensibilizada(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actualizarSensibilizadas method, of class RedDePetri.
     */
    @Test
    public void testActualizarSensibilizadas() {
        System.out.println("actualizarSensibilizadas");
        RedDePetri instance = null;
        instance.actualizarSensibilizadas();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actualizarExtendida method, of class RedDePetri.
     */
    @Test
    public void testActualizarExtendida() {
        System.out.println("actualizarExtendida");
        RedDePetri instance = null;
        instance.actualizarExtendida();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSensibilizadas method, of class RedDePetri.
     */
    @Test
    public void testGetSensibilizadas() {
        System.out.println("getSensibilizadas");
        RedDePetri instance = null;
        int[] expResult = null;
        int[] result = instance.getSensibilizadas();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlazas method, of class RedDePetri.
     */
    @Test
    public void testGetPlazas() {
        System.out.println("getPlazas");
        RedDePetri instance = null;
        int expResult = 0;
        int result = instance.getPlazas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTransiciones method, of class RedDePetri.
     */
    @Test
    public void testGetTransiciones() {
        System.out.println("getTransiciones");
        RedDePetri instance = null;
        int expResult = 0;
        int result = instance.getTransiciones();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
