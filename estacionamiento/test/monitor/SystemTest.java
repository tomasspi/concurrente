package monitor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import archivos.Archivos;

class SystemTest 
{
	@Test
	void test() 
	{
	      Monitor monitor = Monitor.getMonitor();
	      
	      Archivos cargarHilos = new Archivos();
	      cargarHilos.leerHilos();
	      int cantidadHilos = cargarHilos.getHilos().size();
	      Hilo hilos[] = new Hilo[cantidadHilos];
	      //Hilo hilos[];
	      
	      for(int i = 0; i < cantidadHilos; i++)
	      {
	          hilos[i] = new Hilo(i,cargarHilos.getHilos().get(i));
	          hilos[i].print();
	          
	      }
	      
	      try{
	          //long cronometro = System.currentTimeMillis();
	          for(int i = 0; i< cantidadHilos;i++){
	            hilos[i].start();
	            //hilos[i].join();
	          }
	          //System.out.println("Tiempo: "+(System.currentTimeMillis() - cronometro));
	          System.out.println("Disparos: "+monitor.getCantDisparos());
	      }
	      catch(Exception ex){
	        ex.getMessage();
	      }
	      
	      try{
	          Thread.currentThread().sleep(5000);
	          System.out.println("1º####################################\n");monitor.getQueueMonitor();
	          
	          
	      }
	      catch(Exception ex){
	        }
	        RedDePetri.getRdP().printSecuenciaDisparos();
	        RedDePetri.getRdP().print4testings();
	      //hilos[0].start();
	      //hilos[3].start();
	      //hilos[4].start();
	      //hilos[5].start();
	        assertTrue(RedDePetri.getRdP().getPInvariantes());
	}

}
