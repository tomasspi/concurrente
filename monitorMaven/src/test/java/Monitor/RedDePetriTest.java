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
        System.out.println("--- FIN TEST SINGLETON ---\n");
    }

    /**
     * Test of isSensibilizada method, of class RedDePetri.
     */
    @Test
    public void testIsSensibilizada() {
        System.out.println("--- TEST SENSIBILIZADAS COMUNES ---");
        int t = 21;
        assertFalse(rdp.isSensibilizada(t));
        System.out.println("--- FIN TEST SENSIBILIZADAS COMUNES ---\n");
    }

    /**
     * Test of actualizarSensibilizadas method, of class RedDePetri.
     */
    @Test
    public void testActualizarSensibilizadas() {
        System.out.println("--- TEST ACTUALIZAR SENSIBILIZADAS ---");
        
        rdp.disparar(0);
        
        assertTrue(rdp.isSensibilizada(3));
        System.out.println("--- FIN TEST ACTUALIZAR SENSIBILIZADAS ---\n");
    }

    /**
     * Test of actualizarExtendida method, of class RedDePetri.
     */
    @Test
    public void testActualizarExtendida() {
        System.out.println("--- TEST ACTUALIZAR EXTENDIDA ---");
        
        rdp.disparar(0);
        
        assertTrue(rdp.isSensibilizada(3));
        System.out.println("--- FIN TEST ACTUALIZAR EXTENDIDA ---\n");
        
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
        System.out.println("--- FIN TEST ACTUALIZAR EXTENDIDA ---\n");
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
        System.out.println("--- FIN TEST GET TRANSICIONES ---\n");
    }

    /**
     * Test of pInvariante method, of class RedDePetri.
     */
    @Test
    public void testPInvariante() {
        System.out.println("--- TEST DE INVARIANTES DE PLAZA ---");
    	
    	ArrayList<ArrayList<Integer>> pInv = Archivos.getArchivos().getPinvariantes();
    	

    	rdp.disparar(0);
    	
    	rdp.disparar(1);
    	rdp.disparar(1);
    	rdp.disparar(1);
    	
    	rdp.disparar(2);
    	rdp.disparar(2);
    	rdp.disparar(2);
    	
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
        
        System.out.println("--- FIN TEST DE INVARIANTES DE PLAZA ---\n");
    }
    
    /**
     * Testea las pInvariantes luego de la ejecucion total del sistema.
     * @throws InterruptedException 
     */
    @Test
    public void testPInvarianteSistema() throws InterruptedException{
        /*Crea el gestor de monitor*/
        Monitor monitor = Monitor.getMonitor();      
        Archivos archivos = Archivos.getArchivos();

        int cantidadHilos = archivos.getHilos().size();
        Hilo hilos[] = new Hilo[cantidadHilos];

        for(int i = 0; i < cantidadHilos; i++) 
        {
            hilos[i] = new Hilo(i,archivos.getHilos().get(i));
        }

        /*Comienzan los hilos*/
        for(int i = 0; i< cantidadHilos;i++) hilos[i].start();

        /*El hilo padre espera la finalizacion de disparos*/
        for(Thread t: hilos) t.join();

        assertEquals(true, rdp.getPInvariantes());
    }
    
    /**
     * Testea las tInvariantes, comparando los disparos generados y los tInvariantes
     * del PIPE traducidos por el case 'tInvariantes' 
     */
    @Test
    public void testTInvariantesSistema() throws InterruptedException{

    }
}
