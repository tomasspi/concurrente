/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Archivos.Archivos;
import Monitor.Hilo;
import Monitor.Monitor;
import Monitor.RedDePetri;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author root
 */
public class MainTest {
    /* común a cualquier test */
    Archivos archivos = Archivos.getArchivos();
    
    /*variantes en cada test*/
    RedDePetri rdp;
    Monitor monitor;
    Hilo hilos[];
    
    public MainTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        rdp = RedDePetri.getRdP();
    }
    
    @After
    public void tearDown() {
        rdp.deleteRdP();
        monitor = null;
        hilos = null;
    }

    /**
     * Test of main method, of class Main.
     * @throws java.lang.Exception
     */
    @Test
    public void testMain() throws Exception {
                /*Crea el gestor de monitor*/
        monitor = Monitor.getMonitor();      
        

        int cantidadHilos = archivos.getHilos().size();
        hilos = new Hilo[cantidadHilos];

        for(int i = 0; i < cantidadHilos; i++) 
        {
            hilos[i] = new Hilo(i,archivos.getHilos().get(i));
        }

        /*Comienzan los hilos*/
        for(int i = 0; i< cantidadHilos;i++) hilos[i].start();

        /*El hilo padre espera la finalizacion de disparos*/
        for(Thread t: hilos) t.join();
        
        System.out.println("Deben compararse");
        System.out.println(rdp.getSecuenciaDisparos().toString());
        System.out.println(rdp.getTInvariantes().toString());
        
        Iterator it = rdp.getSecuenciaDisparos().iterator();
        while(it.hasNext()){
            
        }
        
    }
    
}
