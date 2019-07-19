package monitor;

import java.util.ArrayList;

/** 
 *  A cada hilo se le asigana las transiciones a disparar.
 * 
 * @author Mr. Green
 */

public class Hilo extends Thread 
{
    Monitor m = Monitor.getMonitor();
    int id;
    ArrayList<Integer> transiciones;
    int disparos;
    
    public Hilo(int id,ArrayList<Integer> transiciones, int disparos) 
    {
        this.id = id;
        this.transiciones = transiciones;
        this.disparos = disparos;
    }
    
    public void print()
    {
        System.out.print("Hilo " + id + " con transiciones ");
        System.out.print( transiciones.toString() + "\n");
    }
    
    @Override
    public void run() 
    {
        while(disparos != 0)
        {
            for(int i = 0; i < transiciones.size(); i++)
            {
                if(id == 3 || id == 7) m.dispararTransicion(m.getPolitica().decidir(transiciones));
                m.dispararTransicion(transiciones.get(i));
                disparos--;
            }
        }
    }
}
