package Monitor;

import java.util.ArrayList;

/** 
 *  A cada hilo se le asigna las transiciones a disparar.
 * 
 * @author NP
 */

public class Hilo extends Thread 
{
    static final int DISPAROS = 400;
    Monitor m = Monitor.getMonitor();
    int id;
    ArrayList<Integer> transiciones;

    public ArrayList<Integer> getTransiciones() {
        return transiciones;
    }
    
    /**
     * Constructor de hilos para concurrencia y manejo de red
     * @param id
     * @param transiciones 
     */
    public Hilo(int id,ArrayList<Integer> transiciones) 
    {
        this.id = id;
        this.transiciones = transiciones;
    }
    
    public void print()
    {
        System.out.print("Hilo " + id + " con transiciones ");
        System.out.print( transiciones.toString() + "\n");
    }
    
    @Override
    public void run() 
    {
        while(m.getCantDisparos() < DISPAROS)
        {
            m.dispararTransicion(transiciones);
        }

        System.out.println(Thread.currentThread().getName()+": FINALIZACION DE DISPAROS.");
    }
    
    @Override
    public long getId(){
        return id;
    }
    
    public int getDisparos()
    {
    	return DISPAROS;
    }
}
