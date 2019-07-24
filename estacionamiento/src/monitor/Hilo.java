package monitor;

import java.util.ArrayList;

/** 
 *  A cada hilo se le asigana las transiciones a disparar.
 * 
 * @author Mr. Green
 */

public class Hilo extends Thread 
{
    static final int DISPAROS = 200;
    Monitor m = Monitor.getMonitor();
    int id;
    ArrayList<Integer> transiciones;
    
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
