package monitor;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Clase Gestor de monitor.
 */

public class Monitor
{
    private static Monitor monitor = null;
    private Semaphore mutex = new Semaphore(1,true);
    private RedDePetri rdp;
    private Colas condiciones[];
    private final Politicas politica;    
    
    //Singleton para el monitor.
    private Monitor() 
    {        
        //Se crea la red de petri.
        rdp= RedDePetri.getRdP();
        //Se crea una condición por transición.
        condiciones = new Colas[rdp.getTransiciones()];
        
        for(int i = 0; i < condiciones.length; i++) condiciones[i] = new Colas(mutex);
        
        //Se elije la politica.
        politica = new Politicas(1);
        //politica = new Politica(2);
        
    }
    
    public static Monitor getMonitor() 
    {
        if(monitor == null) monitor = new Monitor();
        return monitor;
    }
   
    public void dispararTransicion(int t) 
    {
        try { mutex.acquire(); } 
        catch (InterruptedException ex) { ex.getMessage(); }
        
        System.out.println("El hilo " + Thread.currentThread().getName() + " accedió al mutex.");
        
        if(!rdp.isSensibilizada(t)) condiciones[t].DELAY();
                    
        if(rdp.isTemporal(t))
        {
            if(rdp.getTiempo(t).checkVentana()) rdp.disparar(t);
            else if(rdp.getTiempo(t).estaAntes()) 
            {
                UNLOCK();
                try { Thread.sleep(rdp.getTiempo(t).cuantoDormir()); }
                catch (InterruptedException ex) { ex.printStackTrace(); }
                rdp.disparar(t);
            } else System.out.println("El tiempo superó el beta.");
            } else rdp.disparar(t);
            
            UNLOCK();
            
            mutex.release();
    }    
    
    private void UNLOCK()
    {
        ArrayList<Integer> disponibles = AND();
        if(!disponibles.isEmpty()) condiciones[disponibles.get(0)].RESUME();
    }
    
    private ArrayList<Integer> AND() 
    {
        ArrayList<Integer> disponibles = new ArrayList<>();
        for(int i = 0; i < rdp.getTransiciones(); i++)
        {
            if(!(condiciones[i].EMPTY()) && rdp.isSensibilizada(i))
            disponibles.add(i);
        }
        return disponibles;
    }

    public Politicas getPolitica()
    {
        return politica;
    }
}