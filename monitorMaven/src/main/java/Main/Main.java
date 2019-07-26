package Main;

import Archivos.Archivos;
import Monitor.*;
import java.util.ArrayList;

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
      System.out.println("Creando Gestor de Monitor...");
      Archivos archivos = Archivos.getArchivos();
      
      RedDePetri rdp = RedDePetri.getRdP();
      int cantidadHilos = archivos.getHilos().size();
      Hilo hilos[] = new Hilo[cantidadHilos];
      rdp.print4testings();
      
      for(int i = 0; i < cantidadHilos; i++) 
      {
    	  hilos[i] = new Hilo(i,archivos.getHilos().get(i));
      }
      
      System.out.println("Disparos a realizar: " + hilos[0].getDisparos() + "\n");
      
      for(int i = 0; i< cantidadHilos;i++) hilos[i].start();
      
      for(Thread t: hilos) t.join();
      
      rdp.printSecuenciaDisparos();
      archivos.printToFile();

      verificarTinvariantes(rdp.getSecuenciaDisparos(),archivos.getTinvariantes());
  }
  
  public static void verificarTinvariantes(ArrayList<Integer> secuencia, ArrayList<ArrayList<Integer>> invariante)
  {
      for(int i = 0; i < secuencia.size(); i++)
      {
          for (Integer get : invariante.get(i)) {
          }
      }
  }
}