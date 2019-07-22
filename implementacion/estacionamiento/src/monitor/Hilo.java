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
    ArrayList<Integer> transiciones, conflictos;
    //boolean disparar = true;
    
    public Hilo(int id,ArrayList<Integer> transiciones) 
    {
        this.id = id;
        this.transiciones = transiciones;
        setConflicto();
    }
    
    public void print()
    {
        System.out.print("Hilo " + id + " con transiciones ");
        System.out.print( transiciones.toString() + "\n");
    }
    
    private void setConflicto()
    {
        conflictos = new ArrayList<>();
        if(id == 3) 
        {
            conflictos.add(transiciones.get(transiciones.size()-2));
            conflictos.add(transiciones.get(transiciones.size()-1));
            transiciones.remove(transiciones.size()-1);
            transiciones.remove(transiciones.size()-1);
            System.out.println(transiciones.toString());
            System.out.println(conflictos.toString());
        }
    }
    
    @Override
    public void run() 
    {
        int k = 1000;
        while(k>0)
        {
            m.dispararTransicion(transiciones);
            if (id == 3) m.dispararTransicion(conflictos);
            //disparos++;
            //System.out.println("DISPAROS HECHOS: " + disparos);
            k--;
        }
        System.out.println(Thread.currentThread().getName()+": TEMINÉ MIS EJECUCIONES.");
    }
    
    public ArrayList<Integer> getTransiciones()
    {
        return transiciones;
    }
}
