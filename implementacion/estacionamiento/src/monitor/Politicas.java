package monitor;

/**
 * Write a description of class Politicas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Politicas
{
    private RedDePetri rdp;
    private int politica;
    private int politicas[], transiciones[];
    
    public Politicas(RedDePetri rdp, int cual)
    {
        this.rdp = rdp;
        politica = cual;
    }

    public int decidir()
    {
        transiciones = rdp.getSensibilizadas();
        politicas = new int[rdp.getTransiciones()];
        
        switch (politica)
        {
            case 1: 
                    System.out.println("Politica 1");
                    politicas[3] = 1;
                    politicas[10] = 1;
                    return transiciones[1];
                
            case 2: 
                    System.out.println("Politica 2");
                    politicas[4] = 1;
                    politicas[15] = 1;
                    
                    return transiciones[2];
                    
            default:
                    System.out.println("Politica default");
                    if(Math.random() > 0.5)
                    {
                        ;//hacer algo
                    }
                    return transiciones[3];
        }
    }
}
