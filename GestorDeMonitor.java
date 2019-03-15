/**
 * Clase Gestor de monitor.
 */

public class GestorDeMonitor
{
    Mutex mutex;    
    RdP rdp;
    Colas cola;
    Politicas politicas;
    
    boolean k;
    boolean[] vs;
    boolean[] vc;
    boolean[]m;
    int electo;
    
    public GestorDeMonitor() 
    {        
        //Se crea el semaforo.
        mutex=new Mutex();    
        //Se crea la red de petri.
        rdp=new RdP();    
        //Se crean una cola.
        cola=new Colas();    
        //Se crea un objeto politicas.
        politicas=new Politicas();
    }
   
    public void dispararTransicion() 
    {
        k = mutex.ACQUIRE(); 
        while (k==true) {
            System.out.println("La entrada al monitor fue " + k);
            k = rdp.disparar();
            
            if(k==true){
                vs = rdp.sensibilizadas();
                vc = cola.quienesEstan();
                m = and(vs, vc);
                if(algunVerdadero(m)) {
                    electo = politicas.cual();
                    mutex.RELEASE();
                }
                else{
                    k=false;
                }
                
            }
            else {
                mutex.RELEASE();
            }
        }     
    }
    
    private boolean[] and(boolean[] x, boolean[] y) 
    {
	boolean[] res = new boolean[x.length];
	
	for(int i=0;i<x.length;i++) {
	    res[i] = x[i] && y[i];
	}
	
	return res;
     }
     
    private boolean algunVerdadero(boolean[] z) 
    {
	for(int i=0;i<z.length;i++) {
	    if(z[i]==true) {
	        return true;
	       }					
	   }
	   
	return false;
    }
}
