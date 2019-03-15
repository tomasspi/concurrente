/**
 * Clase Gestor de monitor.
 */

public class GestorDeMonitor
{
    Mutex mutex;    
    RedDePetri rdp;
    Colas cola;
    Politicas politicas;
    
    boolean k;
    int[] vs;
    int[] vc;
    int[] m;
    int electo;
    
    public GestorDeMonitor() 
    {        
        //Se crea el semaforo.
        mutex=new Mutex();    
        //Se crea la red de petri.
        rdp=rdp.getRdP();
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
            k = rdp.disparo();
            
            if(k==true){
                if(rdp.estaSensibilizada()){
                    vs = rdp.getSensibilizadas().clone();
                }
                vc = cola.quienesEstan();
                m = and(vs, vc);
                if(algunVerdadero(m)) {
                    electo = politicas.cual(m);
                    // el electo hay que despertarlo para que corra.
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
    
    private int[] and(int[] x, int[] y) {
        int[] res = new int[x.length];
    
        for(int i=0;i<x.length;i++) {
            res[i] = x[i] * y[i];
        }   
    
        return res;
    }
     
    private boolean algunVerdadero(int[] z){
        for(int i=0;i<z.length;i++) {
            if(z[i]==1) {
                return true;
            }                  
        }
     return false;
    }
}
