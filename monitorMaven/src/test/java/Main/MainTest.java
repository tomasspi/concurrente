/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Archivos.Archivos;
import Monitor.Executioner;
import Monitor.Hilo;
import Monitor.Monitor;
import Monitor.RedDePetri;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void testMain() {
        
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

        System.out.println("Disparando...\n");
        
        /*El hilo padre espera la finalizacion de disparos*/
        for(Thread t: hilos) try {
            t.join();
        } catch (InterruptedException ex) {
            ex.getMessage();
        }        
        
        
        System.out.println("\nDeben compararse");
        System.out.println(rdp.getSecuenciaDisparos().toString());
        System.out.println(rdp.getTInvariantes().toString());
        System.out.println("Tamaño antes: "+rdp.getSecuenciaDisparos().size());
        
        /**
        * Se cargan todas las transiciones de la red al ArrayList 'todasTransiciones'
        */
        ArrayList<Integer> todasTransiciones = new ArrayList<>();
        for(int i = 0;i<cantidadHilos;i++){
            todasTransiciones.addAll(hilos[i].getTransiciones());
        }
        System.out.println(todasTransiciones.toString());        
        
        System.out.println("Cantidad de disparos en aux: "+rdp.getSecuenciaAuxiliar().size());
        
        rdp.tInvariantes();
        
        Executioner executor = new Executioner();
        executor.start();
        try {
            executor.join();
        } catch (InterruptedException ex) {
            ex.getMessage();
        }
    }
}
