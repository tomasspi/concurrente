package monitor;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Gestor de monitor.
 */

public class Monitor
{
    private static Monitor monitor = null;
    private Semaphore mutex = new Semaphore(1);
    private RedDePetri rdp;
    private Colas cola;
    private final Politicas politicas;
    
    private boolean k;
    private int[] vs;
    private int[] vc;
    private int[] m;
    private int electo;
    
    
    //Singleton para el monitor.
    private Monitor() 
    {        
        //Se crea la red de petri.
        rdp= RedDePetri.getRdP();
        //Se crean una cola.
        //cola=new Colas();    
        //Se crea un objeto politicas.
        politicas=new Politicas(rdp,1);
    }
    
    public static Monitor getMonitor() {
        if(monitor == null) monitor = new Monitor();
        return monitor;
    }
   
    public void dispararTransicion(int t) 
    {
        try 
        { 
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (k==true) 
        {
            System.out.println("La entrada al monitor fue " + k);
            k = rdp.isSensibilizada(t);
            
            if(k==true)
            {
               // if(rdp.estaSensibilizada()){
                    //vs = rdp.getSensibilizadas().clone();
            }
                vc = cola.quienesEstan();
                m = and(vs, vc);
                if(algunVerdadero(m)) {
                    electo = politicas.decidir();
                    // el electo hay que despertarlo para que corra.
                    mutex.release();
                }
                else{
                    k=false;
                }
                
            }
            mutex.release();
    }    
    
    private int[] and(int[] x, int[] y) 
    {
        int[] res = new int[x.length];
    
        for(int i=0;i<x.length;i++) {
            res[i] = x[i] & y[i];
        }   
    
        return res;
    }
     
    private boolean algunVerdadero(int[] z)
    {
        for(int i=0;i<z.length;i++) {
            if(z[i]==1) {
                return true;
            }                  
        }
     return false;
    }
}