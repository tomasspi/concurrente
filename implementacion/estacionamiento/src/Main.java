
import java.util.ArrayList;
import monitor.GestorDeMonitor;
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
      GestorDeMonitor monitor = GestorDeMonitor.getMonitor();
      System.out.println("Disparando transicion...");
      
      ArrayList<Integer> entrada1 = new ArrayList<>();
      entrada1.add(0);
      entrada1.add(3);
      entrada1.add(4);
      entrada1.add(5);
      
      ArrayList<Integer> entrada2 = new ArrayList<>();
      ArrayList<Integer> entrada3 = new ArrayList<>();
      ArrayList<Integer> planta_baja = new ArrayList<>();
      ArrayList<Integer> planta_alta = new ArrayList<>();
      ArrayList<Integer> caja = new ArrayList<>();
      ArrayList<Integer> salida1 = new ArrayList<>();
      ArrayList<Integer> salida2 = new ArrayList<>();
      

      
      Hilo entrada = new Hilo(monitor,"entrada1",entrada1);
      entrada.run();
    }
}
