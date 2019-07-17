
import archivos.Archivos;
import java.util.ArrayList;
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
  public static void main (String args[])
  {
      //Crea el gestor de monitor.
      System.out.println("Creando Gestor de Monitor");
      Monitor monitor = Monitor.getMonitor();
      System.out.println("Disparando transicion...");
      
      Archivos cargarHilos = new Archivos();
      cargarHilos.leerHilos();
      int cantidadHilos = cargarHilos.getHilos().size();
      Hilo hilos[] = new Hilo[cantidadHilos];
      
      for(int i = 0; i < cantidadHilos; i++)
      {
          hilos[i] = new Hilo(i,cargarHilos.getHilos());
          hilos[i].print();
      }
      
    }
}
