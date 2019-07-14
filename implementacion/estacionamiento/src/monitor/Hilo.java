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
    String nombre;
    
    public Hilo(GestorDeMonitor m, String nombre, ArrayList<Integer> lista) 
    {
        this.m = m;
        this.nombre = nombre;
        
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
   
    public String getNombre()
    {
        return nombre;
    }
}
