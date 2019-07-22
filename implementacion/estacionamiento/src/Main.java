import archivos.Archivos;
import monitor.*;

/**
 * Realiza todas las operaciones. Creación de hilos
 * 
 * @author Navarro, Piñero  
 * @version 18 de marzo de 2019
 */

public class Main
{
    
  public static void main (String args[]) throws InterruptedException
  {
      //Crea el gestor de monitor.
      System.out.println("Creando Gestor de Monitor...");
      Monitor monitor = Monitor.getMonitor();
      
      Archivos cargarHilos = new Archivos();
      cargarHilos.leerHilos();
      int cantidadHilos = cargarHilos.getHilos().size();
      Hilo hilos[] = new Hilo[cantidadHilos];
      
      for(int i = 0; i < cantidadHilos; i++)
      {
          hilos[i] = new Hilo(i,cargarHilos.getHilos().get(i));
          //hilos[i].print();
      }
      
      long cron = System.currentTimeMillis();
      for(int i = 0; i < cantidadHilos; i++) 
      {
          hilos[i].start();
          //hilos[i].join();
      }
      System.out.println("Tiempo: " + (System.currentTimeMillis()-cron));
      
      System.out.println("FIN MAIN.");
  }
}
