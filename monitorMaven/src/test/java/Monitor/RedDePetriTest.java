/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;

import Archivos.Archivos;
import MaquinaDeEstado.MaqEstado;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Mr. Green
 */
public class RedDePetriTest {
    
    RedDePetri rdp = RedDePetri.getRdP();
    int marcado[] = rdp.getMarcado();
    
    public RedDePetriTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Test of getRdP method, of class RedDePetri.
     */
    @Test
    public void testGetRdP() {
        System.out.println("--- TEST SINGLETON ---");
        RedDePetri expResult = rdp;
        RedDePetri result = RedDePetri.getRdP();
        assertEquals(expResult, result);
    }

    /**
     * Test of isSensibilizada method, of class RedDePetri.
     */
    @Test
    public void testIsSensibilizada() {
        System.out.println("--- TEST SENSIBILIZADAS COMUNES ---");
        int t = 21;
        assertFalse(rdp.isSensibilizada(t));
    }

    /**
     * Test of actualizarSensibilizadas method, of class RedDePetri.
     */
    @Test
    public void testActualizarSensibilizadas() {
        System.out.println("--- TEST ACTUALIZAR SENSIBILIZADAS ---");
        
        rdp.disparar(0);
        
        assertTrue(rdp.isSensibilizada(3));
    }

    /**
     * Test of actualizarExtendida method, of class RedDePetri.
     */
    @Test
    public void testActualizarExtendida() {
        System.out.println("--- TEST ACTUALIZAR EXTENDIDA ---");
        
        rdp.disparar(0);
        
        assertTrue(rdp.isSensibilizada(3));
    }

    /**
     * Test of getPlazas method, of class RedDePetri.
     */
    @Test
    public void testGetPlazas() {
        System.out.println("--- TEST GET PLAZAS ---");
        
        int expResult = 32;
        int result = rdp.getPlazas();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getTransiciones method, of class RedDePetri.
     */
    @Test
    public void testGetTransiciones() {
        System.out.println("--- TEST GET TRANSICIONES ---");
        
        int expResult = 24;
        int result = rdp.getTransiciones();
        assertEquals(expResult, result);
    }

    /**
     * Test of pInvariante method, of class RedDePetri.
     */
    @Test
    public void testPInvariante() {
        System.out.println("--- TEST DE INVARIANTES DE PLAZA ---");
    	
    	ArrayList<ArrayList<Integer>> pInv = Archivos.getArchivos().getPinvariantes();
    	

    	rdp.disparar(0);
    	
        for(int i=0; i<pInv.size();i++)
        {
            int suma = 0;
            for(int j=0;j< pInv.get(i).size();j++)
            {
                if(j<pInv.get(i).size()-1)
                {
                    int indice = pInv.get(i).get(j);
                    suma += marcado[indice];
                }else if(j==pInv.get(i).size()-1)
                {
                    assertFalse(suma != pInv.get(i).get(j));
                }
            }
        }
    }

    /**
     * Test of disparar method, of class RedDePetri.
     */
    @Test
    public void testDispararSensibilizada() {
        System.out.println("--- TEST DE DISPARO SENSIBILIZADO ---");
        int t = 1;
        long expResult = 0;
        long result = rdp.disparar(t);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of disparar method, of class RedDePetri.
     */
    @Test
    public void testDispararNoSensibilizada() {
        System.out.println("--- TEST DE DISPARO NO SENSIBILIZADO ---");
        int t = 22;
        long expResult = -2;
        long result = rdp.disparar(t);
        assertEquals(expResult, result);
    }

    /**
     * Test of actualizarInhibidas method, of class RedDePetri.
     */
    @Test
    public void testActualizarInhibidas() {
        System.out.println("--- TEST DE ACTUALIZACIÓN DE INHIBIDAS ---");
        int expResult[] = rdp.getExtendido();
        rdp.disparar(1);
        int result[] = rdp.getExtendido();
        assertArrayEquals(expResult,result);
    }

    /**
     * Test of getSensibilizadas method, of class RedDePetri.
     */
    @Test
    public void testGetSensibilizadas() {
        System.out.println("--- TEST DE ACTUALIZACIÓN DE SENSIBILIZADAS ---");
        int expResult[] = rdp.getSensibilizadas();
        rdp.disparar(1);
        int result[] = rdp.getSensibilizadas();
        assertArrayEquals(expResult,result);
    }

    /**
     * Test of getExtendido method, of class RedDePetri.
     */
    @Test
    public void testGetExtendido() {
        System.out.println("--- TEST DE ACTUALIZACIÓN DE EXTENDIDO ---");
        int expResult[] = rdp.getExtendido();
        rdp.disparar(2);
        int result[] = rdp.getExtendido();
        assertArrayEquals(expResult,result);
    }

    /**
     * Test of isTemporal method, of class RedDePetri.
     */
    @Test
    public void testIsTemporal() {
        System.out.println("--- TEST DE IS TEMPORAL ---");
        int t = 13;
        boolean expResult = true;
        boolean result = rdp.isTemporal(t);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMarcado method, of class RedDePetri.
     */
    @Test
    public void testGetMarcado() {
        System.out.println("--- TEST DE OBTENCIÓN DEL MARCADO ---");
        int[] expResult = marcado;
        int[] result = rdp.getMarcado();
        assertArrayEquals(expResult, result);
    }
    
    
    
    
}
