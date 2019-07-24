package Main;

import Archivos.Archivos;
import Monitor.*;

/**
 * Realiza todas las operaciones. Creación de hilos
 * 
 * @author Navarro, Piñero  
 * @version 18 de marzo de 2019
 */
public class Main
{
  //private RedDePetri rdp; 
  public static void main (String args[]) throws InterruptedException
  {
      //Crea el gestor de monitor.
      System.out.println("Creando Gestor de Monitor");
      Monitor monitor = Monitor.getMonitor();      
      
      Archivos cargarHilos = new Archivos();
      cargarHilos.leerHilos();
      int cantidadHilos = cargarHilos.getHilos().size();
      Hilo hilos[] = new Hilo[cantidadHilos];
      
      
      for(int i = 0; i < cantidadHilos; i++) 
      {
    	  hilos[i] = new Hilo(i,cargarHilos.getHilos().get(i));
      }
      
      System.out.println("Disparos a realizar: " + hilos[0].getDisparos() + "\n");
      
      for(int i = 0; i< cantidadHilos;i++) hilos[i].start();
      
      Thread.sleep(5000);
      RedDePetri.getRdP().printSecuenciaDisparos();
      cargarHilos.printToFile();
  }
}