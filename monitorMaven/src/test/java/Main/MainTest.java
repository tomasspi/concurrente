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
import java.util.ArrayList;
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

        System.out.println("Disparando...\n");
        
        /*El hilo padre espera la finalizacion de disparos*/
        for(Thread t: hilos) t.join();        
        
        
        System.out.println("\nDeben compararse");
        System.out.println(rdp.getSecuenciaDisparos().toString());
        System.out.println(rdp.getTInvariantes().toString());
        System.out.println("Tamaño antes: "+rdp.getSecuenciaDisparos().size());
        
        
        
        
        ArrayList<Integer> secAuxiliar = rdp.getSecuenciaDisparos();
        System.out.println("Cantidad de disparos en aux: "+secAuxiliar.size());
        
        Iterator sec  = secAuxiliar.iterator();
        
        int encontrado;
        int cantFilas = rdp.getTInvariantes().size();
        int contador = 0;
        int antes = 1,despues = 0;
        while(antes != despues){
            antes = secAuxiliar.size();
            for(int i = 0; i<cantFilas; i++){
                ArrayList<Integer> coincidencias = new ArrayList<>();
                
                int cantColumnas = rdp.getTInvariantes().get(i).size();
                
                for(int j = 0; j<cantColumnas; j++){
                    
                    int buscar = rdp.getTInvariantes().get(i).get(j);
                    
                    while(sec.hasNext()){
                        
                        Integer invariante = (Integer)sec.next();
                        encontrado = invariante.intValue();
                        
                        if(encontrado == buscar){
                            coincidencias.add(encontrado);
                            break;
                        }
                    }
                }
                /* elimina la secuencia encontrada de los disparos */
                if(coincidencias.size() == cantColumnas){
                    System.out.println("Todo el camino encontrado. Se removerá la secuencia: ");
                    System.out.println(coincidencias.toString()+"\n");
                    for(int k = 0;k<coincidencias.size();k++){
                        secAuxiliar.remove(coincidencias.get(k));
                    }
                    contador++;
                }
                //else break;
                sec = secAuxiliar.iterator();
                despues = secAuxiliar.size();
            }
            System.out.println("Auxiliar actual: "+secAuxiliar.toString());
            System.out.println("Encontré " + contador + " t-invariantes.");
            System.out.println("Tamaño: " + secAuxiliar.size());
        }
        
    
        
        
        
        
        
        
    }
    
}
