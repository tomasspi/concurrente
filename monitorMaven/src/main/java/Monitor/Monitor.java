package Monitor;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Clase Gestor de monitor.
 */

public class Monitor
{
    private static Monitor monitor = null;
    private Semaphore mutex = new Semaphore(1, true);
    private RedDePetri rdp;
    private Colas cola[];
    private final Politicas politica; 
    private int disparos = 0;
    
    //Singleton para el monitor.
    private Monitor() 
    {        
        //Se crea la red de petri.
        rdp= RedDePetri.getRdP();
        //Se crea una condición por transición.
        cola = new Colas[rdp.getTransiciones()];
        
        for(int i = 0; i < cola.length; i++) cola[i] = new Colas(mutex);
        
        //Se elije la politica.
        politica = new Politicas(1);
        //politica = new Politica(2);
        
    }
    
    public static Monitor getMonitor() 
    {
        if(monitor == null) monitor = new Monitor();
        return monitor;
    }
   
    public /*synchronized*/ void  dispararTransicion(ArrayList<Integer> transiciones) 
    {
        /* intenta obtener el acceso al monitor */
        try { mutex.acquire(); } 
        catch (InterruptedException ex) { ex.getMessage(); }

        /* consulta a la politica cual de sus transiciones disparar */        
        int t = politica.cual(transiciones, rdp.getSensibilizadas());        
        
        /* verifica si la transicion que desea disparar esta sensibilizada */
        /* de no estarla, el hilo se duerme dentro del monitor */
        while(rdp.isSensibilizada(t) || rdp.isTemporal(t) || UNLOCKEABLES())
        {
            if(rdp.isSensibilizada(t) || rdp.isTemporal(t)) 
            {
                /* 
                 * Disparar la rdp devuelve 3 tipos diferentes de valores 
                 * -Si devuelve 0 se disparo satisfactoriamente
                 * -Si devuelve un numero mayor a 0, este representa el tiempo que el hilo debe dormir
                 * para alcanzar el alpha
                 * -Si es menor a cero, no se pudo disparar, en teoria, por haber superado el beta
                 */               
                long caso = rdp.disparar(t);
                
                if(caso == 0) disparos++;     /* se realizo el disparo satisfactoriamente */
                
                /* 
                 * no se llego al alpha, el hilo debe dormir 
                 */ 
                if(caso > 0)
                {                     
                    /* cede el monitor a un despertable.. si no hay ninguno, libera el monitor */
                    if(!UNLOCKEABLES()) mutex.release();
                    else UNLOCK();
                    try
                    {
                        Thread.currentThread().sleep(caso);
                        mutex.acquire();
                    }
                    catch(Exception ex)
                    {
                        System.out.println("ERR Bloque sleep()");
                        ex.getMessage();
                    }
                } 
                /* 
                 * no se realizo el disparo, se supero el beta 
                 */
                if(caso<0) break;
                /*(caso < 0)  no se realizo el disparo, seguramente se supero el beta */
            }
            else
            {
                /* se elige alguien de la cola del monitor para continuar */
                if(!UNLOCKEABLES()) mutex.release();
                else UNLOCK();
                cola[t].DELAY();
            }
        }
        
        mutex.release();
    }    
    
    private void UNLOCK()
    {
        ArrayList<Integer> disponibles = getDisparablesEncolados();
        if(!disponibles.isEmpty()) cola[politica.cual(disponibles, rdp.getSensibilizadas())].RESUME();
            
    }
    
    private boolean UNLOCKEABLES()
    {
        ArrayList<Integer> disponibles = getDisparablesEncolados();
        return !disponibles.isEmpty();
    }
    
    private ArrayList<Integer> getDisparablesEncolados() 
    {
        ArrayList<Integer> disponibles = new ArrayList<>();
        for(int i = 0; i < rdp.getTransiciones(); i++)
        {
            if(!(cola[i].EMPTY()) && rdp.isSensibilizada(i))
            disponibles.add(i);
        }
        return disponibles;
    }
    
    public void getQueueMonitor(){
        System.out.println("Cola para mutex: "+mutex.getQueueLength());
        for(int i=0;i<cola.length;i++){
            System.out.println("Cola "+i+": "+cola[i].getQueue());
        }
    }
    
    public int getCantDisparos(){
        return disparos;
    }
}