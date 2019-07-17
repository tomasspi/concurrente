package monitor;

import java.util.concurrent.Semaphore;

/**
 * Extraído del libro 'Programación Concurrente' - Palma Méndez.
 * 
 * @author N.P.
 * @version 17/07/2019
 */
public class Colas
{
    Semaphore mutex, condicion;
    int dormidos;
    
    public Colas(Semaphore mutex)
    {
        this.mutex = mutex;
        condicion = new Semaphore(0, true);
        dormidos = 0;
    }
    
    public void DELAY() //bloquea a un hilo y devuelve el mutex
    {    
        dormidos++;
        mutex.release();
        
        try 
        {
            condicion.acquire();
        } catch (InterruptedException e) 
        {
            System.out.println("Interrupcion en condición del hilo " + Thread.currentThread().getId() + e.getMessage());
        }
        
        try 
        {
            mutex.acquire();
        } catch (InterruptedException e) 
        {
            System.out.println("Interrupcion en mutex del hilo " + Thread.currentThread().getId() + e.getMessage());
        }
       }

       public void RESUME() //desbloquea un hilo, si no hay bloqueados no hace nada
       {
           if(dormidos > 0)
           {
               dormidos--;
               condicion.release();
           }
       }

       public boolean EMPTY()
       { 
           return dormidos == 0;
       }
}
