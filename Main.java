import java.util.concurrent.ThreadPoolExecutor;
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
      GestorDeMonitor gestordemonitor=new GestorDeMonitor();
      System.out.println("Disparando transicion...");
      gestordemonitor.dispararTransicion();
      
      //Creo los hilos
      ThreadPoolExecutor hilos;
      
    }
}
