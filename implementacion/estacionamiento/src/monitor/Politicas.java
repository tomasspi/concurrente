package monitor;

import java.util.ArrayList;

/**
 * Write a description of class Politicas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Politicas
{
    private final int politica;
    
    public Politicas(int cual)
    {
        politica = cual;
    }

    public int decidir(ArrayList<Integer> transiciones)
    {
        
        switch (politica)
        {
            case 1: 
                    System.out.println("Politica 1: Priorizar PB, salida indistinta.");
                    
                    if(transiciones.get(0) == 9) //si es el hilo 3
                    {
                        if(Math.random() > 0.7) return 9;
                        return 10;
                    } else  //es el hilo 7
                    {
                        if(Math.random() > 0.5) return 20;
                        else return 21;
                    }
                    
                
            case 2: 
                    System.out.println("Politica 2: Llenado indistinto, priorizar salida 2.");
                    
                    if(transiciones.get(0) == 9) //si es el hilo 3
                    {
                        if(Math.random() > 0.5) return 9;
                        return 10;
                    } else  //es el hilo 7
                    {
                        if(Math.random() > 0.7) return 20;
                        else return 21;
                    }
                    
            default:
                    System.out.println("Politica default: aleatoriamente.");
                    
                    if(transiciones.get(0) == 9) //si es el hilo 3
                    {
                        if(Math.random() > 0.5) return 9;
                        return 10;
                    } else  //es el hilo 7
                    {
                        if(Math.random() > 0.5) return 20;
                        else return 21;
                    }
        }
    }
}
