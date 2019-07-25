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
      Archivos archivos = Archivos.getArchivos();

      int cantidadHilos = archivos.getHilos().size();
      Hilo hilos[] = new Hilo[cantidadHilos];
      RedDePetri.getRdP().print4testings();
      
      for(int i = 0; i < cantidadHilos; i++) 
      {
    	  hilos[i] = new Hilo(i,archivos.getHilos().get(i));
      }
      
      System.out.println("Disparos a realizar: " + hilos[0].getDisparos() + "\n");
      
      for(int i = 0; i< cantidadHilos;i++) hilos[i].start();
      
      for(Thread t: hilos) t.join();
      
      RedDePetri.getRdP().printSecuenciaDisparos();
      archivos.printToFile();
  }
}