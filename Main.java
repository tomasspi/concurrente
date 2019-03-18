


/**
 * Write a description of class Main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Main
{
  public static void main (String args[]){
      //Crea el gestor de monitor.
      System.out.println("Creando Gestor de Monitor");
      GestorDeMonitor gestordemonitor=new GestorDeMonitor();
      System.out.println("Disparando transicion...");
      gestordemonitor.dispararTransicion();
    }
}
