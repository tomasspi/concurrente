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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        
        //Iterator sec  = rdp.getSecuenciaAuxiliar().iterator();
        
        int encontrado;
        int cantFilas = rdp.getTInvariantes().size();
        
        //while(!secuenciaAuxiliar.isEmpty()){
        ArrayList<Integer> restantes = new ArrayList<>();
        for(int i = 0; i<cantFilas; i++){
            ArrayList<Integer> coincidencias = new ArrayList<>();
            
            int cantColumnas = rdp.getTInvariantes().get(i).size();

            for(int j = 0; j<cantColumnas; j++){

                int buscar = rdp.getTInvariantes().get(i).get(j);

                for(int l = 0; l < rdp.getSecuenciaAuxiliar().size(); l++)
                {
                    encontrado = rdp.getSecuenciaAuxiliar().get(l);
                    if(encontrado == buscar){
                        coincidencias.add(encontrado);
                        break;
                    }
                }
            }
            /* elimina la secuencia encontrada de los disparos */
            if(coincidencias.size() < cantColumnas){
            System.out.println("Camino parcial encontrado: ");
            System.out.println(coincidencias.toString()+"\n");
            for(int k = 0;k<coincidencias.size();k++){
                rdp.getTInvariantes().get(i).remove(coincidencias.get(k));
            }
            }
        }
        
        for(int i = 0; i<rdp.getTInvariantes().size(); i++){
            for(int j = 0; j<rdp.getTInvariantes().get(i).size(); j++){
                restantes.add(rdp.getTInvariantes().get(i).get(j));
            }
        }
        
//        Executioner executor = new Executioner(restantes);
//        executor.start();
//        try {
//            executor.join();
//        } catch (InterruptedException ex) {
//            ex.getMessage();
//        }
    }
}
