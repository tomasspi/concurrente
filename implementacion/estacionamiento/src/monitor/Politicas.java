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
        politicas = new int[rdp.getTransiciones()];
    }

    public int decidir()
    {
        transiciones = rdp.getSensibilizadas();        
        
        switch (politica)
        {
            case 1: 
                    System.out.println("Politica 1: Priorizar PB, salida indistinta.");
                    politicas[9] = 1;
                    politicas[10] = 1;
                    return transiciones[1];
                
            case 2: 
                    System.out.println("Politica 2: Llenado indistinto, priorizar salida 2.");
                    politicas[20] = 1;
                    politicas[21] = 1;
                    
                    return transiciones[2];
                    
            default:
                    System.out.println("Politica default: aleatoriamente.");
                    if(Math.random() > 0.5)
                    {
                        return transiciones[2];
                    }
                    else return transiciones[3];
        }
    }
}
