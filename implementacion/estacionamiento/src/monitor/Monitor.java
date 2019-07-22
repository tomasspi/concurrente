package monitor;

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
        politica = new Politicas(0);
        //politica = new Politica(2);
        
    }
    
    public static Monitor getMonitor() 
    {
        if(monitor == null) monitor = new Monitor();
        return monitor;
    }
    
   
    public void dispararTransicion(ArrayList<Integer> transiciones) 
    {
        //System.out.println("Hilo "+Thread.currentThread().getId()+": Pidiendo entrar al mutex");
        /* intenta obtener el acceso al monitor */
        try { mutex.acquire(); } 
        catch (InterruptedException ex) { ex.getMessage(); }
        
        /* consulta a la politica cual de sus transiciones disparar */
        int t = politica.cual(transiciones);
        
        //System.out.println("Hilo " + Thread.currentThread().getId() + ": Accedió al mutex."+System.currentTimeMillis());
        
        /* verifica si la transicion que desea disparar esta sensibilizada */
        /* de no estarla, el hilo se duerme dentro del monitor */
        /*
        while(!rdp.isSensibilizada(t) || UNLOCK()){
            //rdp.disparar(t);
            //System.out.println("Hilo "+Thread.currentThread().getId()+": Entre al mutex pero no puedo disparar nada");
            if(UNLOCK()) {
                condiciones[politica.cual(AND(), rdp.getSensibilizadas())].RESUME();
                condiciones[t].DELAY();
            }
            else mutex.release();
            //System.out.println("Hilo "+Thread.currentThread().getId()+": Volvi de la cola del monitor (DELAY)");
        }*/
        
        while(rdp.isSensibilizada(t) || rdp.isTemporal(t) || UNLOCK()){
            if(rdp.isSensibilizada(t)) {
                if(rdp.isTemporal(t)) System.out.println("Soy temporal");
                //rdp.actualizarExtendida();
                rdp.disparar(t);
                disparos++;
            }
            else{
                cola[politica.cual(AND())].RESUME();
                cola[t].DELAY();
            }
        }
        
        /* verifica si es temporal */
        if(rdp.isTemporal(t))
        {
            System.out.println("SOY TEMPORIZADA");
            if(rdp.getTiempo(t).checkVentana()) rdp.disparar(t);
            else if(rdp.getTiempo(t).estaAntes()) 
            {
                UNLOCK();
                try { Thread.sleep(rdp.getTiempo(t).cuantoDormir()); }
                catch (InterruptedException ex) { ex.printStackTrace(); }
                rdp.disparar(t);
            } else System.out.println("El tiempo superó el beta.");
        } 
        
        /* dispara la transicion de mayor prioridad */
        else {
            //rdp.disparar(t);
        }
        
        /* despierta un hilo dormido en el monitor */
        //if(!AND().isEmpty()){
        //    //System.out.println("Hilo "+Thread.currentThread().getId()+": Viendo si hay bloqueados en cola de monitor");
        //    UNLOCK();
        //}
        ///* el hilo del monitor no tiene a nadie para despertar. Libera el monitor */
        //else {
        //    //System.out.println("Hilo "+Thread.currentThread().getId()+": Abro cerrojo de mutex");
            mutex.release();
        //}
        //System.out.println("Hilo "+Thread.currentThread().getId()+": Acaba de salir del mutex"); 
    }    
    
    private boolean UNLOCK()
    {
        ArrayList<Integer> disponibles = AND();
        if(!disponibles.isEmpty()){
            //System.out.println("get(0): "+disponibles.get(0));
            //condiciones[disponibles.get(0)].RESUME();
            cola[politica.cual(disponibles)].RESUME();
            return true;
        }
        return false;
    }
    
    private ArrayList<Integer> AND() 
    {
        ArrayList<Integer> disponibles = new ArrayList<>();
        for(int i = 0; i < rdp.getTransiciones(); i++)
        {
            if(!(cola[i].EMPTY()) && rdp.isSensibilizada(i))
            disponibles.add(i);
        }
        return disponibles;
    }

    public Politicas getPolitica()
    {
        return politica;
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