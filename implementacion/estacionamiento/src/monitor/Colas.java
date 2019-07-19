package monitor;

import java.util.ArrayList;
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
    ArrayList<Long> enCola;
    
    public Colas(Semaphore mutex)
    {
        this.mutex = mutex;
        condicion = new Semaphore(0, true);
        dormidos = 0;
        enCola = new ArrayList<>();
    }
    
    /*devuelve el mutex, y trata de adquirir la condicion (en 0), entonces el 
    hilo se bloquea, por lo que recien en RESUME(), al hacerse el release de la 
    condicion, el hilo continuará su ejecución y adquirira nuevamente el mutex*/
    public void DELAY() //bloquea a un hilo y devuelve el mutex
    {    
        dormidos++;
        mutex.release();
        
        try { condicion.acquire(); } 
        catch (InterruptedException e) 
        {
            System.out.println("Interrupcion en condición del hilo " + Thread.currentThread().getId() + e.getMessage());
        }
        
        try { mutex.acquire(); } 
        catch (InterruptedException e) 
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
