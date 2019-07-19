import archivos.Archivos;
import monitor.Monitor;
import monitor.Hilo;

/**
 * Realiza todas las operaciones. Creación de hilos
 * 
 * @author Navarro, Piñero  
 * @version 18 de marzo de 2019
 */

public class Main
{
    static final int DISPAROS = 2;
    
  public static void main (String args[])
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
          hilos[i] = new Hilo(i,cargarHilos.getHilos().get(i),DISPAROS);
          hilos[i].print();
          hilos[i].start();
      }

  }
}
