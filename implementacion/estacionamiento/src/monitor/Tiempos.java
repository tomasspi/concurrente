
package monitor;

/**
 *
 * @author N.P.
 */
public class Tiempos 
{
    private int ID;
    private int alpha, beta;
    private long timeStamp;
    private boolean cronometrando = false;
    
    public Tiempos(int ID, int alpha, int beta)
    {
        this.ID = ID;
        this.alpha = alpha;
        this.beta = beta;
    }
    
    public boolean checkVentana()
    {
        long now = System.currentTimeMillis();

        long cronometro = now - timeStamp;
        /* si esta dentro de la ventana */
        if(cronometro >= alpha && cronometro <= beta){
            /* deja de cronometrar porque se va a disparar */
            cronometrando = false;
            return true;
        }
        /* si supero el beta */
        else if (cronometro > beta){
            /* deja de cronometrar porque debe reiniciarse el cronometro*/
            cronometrando = false;
            return false;
        }
        /* no llego al alpha */
        return false;
    }
    
    /**
     * Si no se inicio el cronometro de la transicion, lo inicia.
     */
    public void setTS()
    {
        /* inicia el cronometro */
        if(!cronometrando) {
            timeStamp = System.currentTimeMillis();
            cronometrando = true;
        }
    }
    
    public boolean estaAntes()
    {
        long cronometro = System.currentTimeMillis() - timeStamp;
        if(cronometro < alpha) return true;
        else return false;
    }
   
    public boolean isCronometrando(){
        return cronometrando;
    }
    
    public long cuantoDormir()
    {
        long now = System.currentTimeMillis() - timeStamp;
        //return alpha - cronometro;
        return alpha - now;
    }
}
