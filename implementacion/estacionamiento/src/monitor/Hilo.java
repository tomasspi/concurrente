package monitor;

import java.util.ArrayList;

/**
 *
 * @author Mr. Green
 */

public class Hilo extends Thread 
{
    Monitor m = Monitor.getMonitor();
    int id;
    ArrayList<Integer> transiciones;
    
    public Hilo(int id,ArrayList<ArrayList<Integer>> hilos) 
    {
        this.id = id;
        transiciones = new ArrayList<>();
        for(int i = 0; i < hilos.get(0).size(); i++)
        {
            if(hilos.get(id).get(i) != null) transiciones.add(hilos.get(id).get(i));
        }
    }
    
    public void print()
    {
        System.out.print("Soy el hilo " + (id+1) + " con transiciones ");
        for(int i = 0; i < transiciones.size(); i++)
        {
            System.out.print( transiciones.get(i) + " ");
        }
        System.out.println("a cargo.");
    }
    
    @Override
    public void run() 
    {
        while(true)
        {
            for(int i = 0; i < transiciones.size(); i++)
            {
                m.dispararTransicion(i);
            }
        }
    }
}
