import java.util.concurrent.ThreadPoolExecutor;
import monitor.GestorDeMonitor;
/**
 * Realiza todas las operaciones. Creación de hilos
 * 
 * @author Navarro, Piñero  
 * @version 18 de marzo de 2019
 */
public class Main
{
  public static void main (String args[]){
      //Crea el gestor de monitor.
      System.out.println("Creando Gestor de Monitor");
      GestorDeMonitor monitor = GestorDeMonitor.getMonitor();
      System.out.println("Disparando transicion...");
      //monitor.dispararTransicion();
      
      //Creo los hilos
      ThreadPoolExecutor hilos;
      
    }
}
