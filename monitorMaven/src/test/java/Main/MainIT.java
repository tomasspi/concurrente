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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author root
 */
public class MainIT {
    
    public MainIT() {
    }
    
    @org.junit.BeforeClass
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
     * Test of main method, of class Main.
     * @throws java.lang.Exception
     */
    @org.junit.Test
    public void testMain() throws Exception {
        Monitor monitor = Monitor.getMonitor();
	Archivos archivos = Archivos.getArchivos();
	int cantidadHilos = archivos.getHilos().size();
	Hilo hilos[] = new Hilo[cantidadHilos];
	//Hilo hilos[];
	      
	for(int i = 0; i < cantidadHilos; i++)
	{
            hilos[i] = new Hilo(i,archivos.getHilos().get(i));
            hilos[i].print();

        }

        try{
            //long cronometro = System.currentTimeMillis();
            for(int i = 0; i< cantidadHilos;i++){
              hilos[i].start();
              //hilos[i].join();
            }
            //System.out.println("Tiempo: "+(System.currentTimeMillis() - cronometro));
            System.out.println("Disparos: "+monitor.getCantDisparos());
        }
        catch(Exception ex){
          ex.getMessage();
        }

        try{
            Thread.currentThread().sleep(5000);
            System.out.println("1º####################################\n");monitor.getQueueMonitor();


        }
        catch(Exception ex){
          }
          RedDePetri.getRdP().printSecuenciaDisparos();
          RedDePetri.getRdP().print4testings();
        //hilos[0].start();
        //hilos[3].start();
        //hilos[4].start();
        //hilos[5].start();
          assertTrue(RedDePetri.getRdP().getPInvariantes());
      }
    
}
