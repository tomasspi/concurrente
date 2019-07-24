package Monitor;

import java.util.concurrent.Semaphore;

/**
 * Modificado del libro 'Programación Concurrente' - Palma Méndez.
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
        condicion = new Semaphore(0,true);
        dormidos = 0;
    }
    
    /*devuelve el mutex, y trata de adquirir la condicion (en 0), entonces el 
    hilo se bloquea, por lo que recien en RESUME(), al hacerse el release de la 
    condicion, el hilo continuará su ejecución y adquirira nuevamente el mutex*/
    public void DELAY() //bloquea a un hilo y devuelve el mutex
    {    
        //System.out.println("A DORMIR: " + Thread.currentThread().getName());
        dormidos++;
        mutex.release();
        
        try { condicion.acquire(); } 
        catch (InterruptedException e) 
        {
            System.out.println("Interrupcion en condición del hilo " + Thread.currentThread().getId() + e.getMessage());
        }

        //System.out.println("DESPERTÉ: " + Thread.currentThread().getName());
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

    public int getQueue() 
    {
        return condicion.getQueueLength();
    }
}
