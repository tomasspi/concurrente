package monitor;

import java.util.ArrayList;

/**
 *
 * @author Mr. Green
 */

public class Hilo extends Thread 
{
    Monitor m = Monitor.getMonitor();
    String nombre;
    
    public Hilo(Monitor m, String nombre) 
    {
        this.m = m;
        this.nombre = nombre;
    }
    
    @Override
    public void run() 
    {
        while(true)
        {
             m.dispararTransicion(0);
        }
    }
   
    public String getNombre()
    {
        return nombre;
    }
}
