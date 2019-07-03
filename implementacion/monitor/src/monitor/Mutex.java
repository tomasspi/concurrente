package monitor;

import java.util.concurrent.Semaphore;

public class Mutex
{
    //Semaforo binario para controlar la exclusion mutua.
    private final Semaphore semaforo;
    
    //Constructor del semaforo, se inicializa en 1 para permitir 1 hilo a la vez, y con justicia para la espera (FIFO)
    public Mutex(){
        semaforo = new Semaphore(1, true);
    }
    
    synchronized public boolean ACQUIRE(){
        //try{
          return  semaforo.tryAcquire();
        //} catch (InterruptedException e){
        //    e.printStackTrace();
        //} 
    }
    
    synchronized public void RELEASE(){
        semaforo.release();
    }
}
