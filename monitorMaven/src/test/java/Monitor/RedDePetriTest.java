/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;

import Archivos.Archivos;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mr. Green
 */
public class RedDePetriTest {
    
    RedDePetri rdp = RedDePetri.getRdP();
    int marcado[] = rdp.getMarcado();
    
    public RedDePetriTest() {
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
        int t = 0;
        assertTrue(rdp.isSensibilizada(t));
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
    	
//    	rdp.disparar(0);
//    	rdp.disparar(0);
//    	rdp.disparar(0);
//    	
//    	rdp.disparar(1);
//    	rdp.disparar(1);
//    	rdp.disparar(1);
//    	
//    	rdp.disparar(2);
//    	rdp.disparar(2);
//    	rdp.disparar(2);
    	
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
}
