
package monitor;

/**
 *
 * @author N.P.
 */
public class Tiempos 
{
    private int ID;
    private int alpha, beta;
    private long timeStamp, now, cronometro;
    
    public Tiempos(int ID, int alpha, int beta)
    {
        this.ID = ID;
        this.alpha = alpha;
        this.beta = beta;
    }
    
    public boolean checkVentana()
    {
        now = System.currentTimeMillis();
        
        cronometro = now - timeStamp;
        
        return alpha <= cronometro && cronometro < beta;
    }
    
    public void setTS()
    {
        timeStamp = System.currentTimeMillis();
    }
    
    public boolean estaAntes()
    {
        if(checkVentana() == false)
        {
            if(cronometro < alpha) return true;
        }
        return false;
    }
    
    public int getID()
    {
        return ID;
    }
    
    public long cuantoDormir()
    {
        return alpha - cronometro;
    }
}
