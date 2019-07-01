package monitor;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Mutex
{
    /**
     * Semaforo para controlar la exclusion mutua.
     */
    private final Semaphore semaphore;
    
    /**
     * Constructor del semaforo, se inicializa en 1 para permitir 1 hilo a la vez, y con justicia para la espera.
     */
    public Mutex(){
        semaphore=new Semaphore(1, true);
    }
    
    synchronized public boolean ACQUIRE(){
        //try{
          return  semaphore.tryAcquire();
        //} catch (InterruptedException e){
        //    e.printStackTrace();
        //} 
    }
    
    synchronized public void RELEASE(){
        semaphore.release();
    }
}
