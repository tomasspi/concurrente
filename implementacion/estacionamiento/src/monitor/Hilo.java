package monitor;

import java.util.ArrayList;

/**
 *
 * @author Mr. Green
 */

public class Hilo extends Thread 
{
    GestorDeMonitor m = GestorDeMonitor.getMonitor();
    ArrayList<Integer> transiciones;
    
    public Hilo(GestorDeMonitor m,ArrayList<Integer> lista) 
    {
        this.m = m;
        for(int i = 0; i < lista.size(); i++)
        {
            transiciones.add(lista.get(i));
        }
    }
    
    @Override
    public void run() {
        while(true)
        {
            for(int i = 0; i < transiciones.size(); i++)
            {
                m.dispararTransicion(transiciones.get(i));
            }
        }
    }    
}
