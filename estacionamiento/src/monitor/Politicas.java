package monitor;

import java.util.ArrayList;

/**
 * Write a description of class Politicas here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Politicas {

    private final int politica;

    public Politicas(int cual) {
        politica = cual;
    }

public int decidir(int electo)
    {
        switch (politica)
        {
            case 1: 
                    //System.out.println("Politica 1: Priorizar PB, salida indistinta.");
                    
                    if (electo == 9 || electo == 10)
                    {
                        if(Math.random() > 0.7) return 9;
                        else return 10;
                    } else
                    {
                        if(Math.random() > 0.5) return 20;
                        else return 21;
                    }
                
            case 2: 
                    //System.out.println("Politica 2: Llenado indistinto, priorizar salida 2.");
                    
                    if (electo == 20 || electo == 21)
                    {
                        if(Math.random() > 0.7) return 20;
                        else return 21;
                    } else
                    {
                        if(Math.random() > 0.5) return 9;
                        else return 10;
                    }
                    
            default:
                    //System.out.println("Politica default: aleatoriamente.");
                    
                    if (electo == 9 || electo == 10)
                    {
                        if(Math.random() > 0.5) return 9;
                        else return 10;
                    } else
                    {
                        if(Math.random() > 0.5) return 20;
                        else return 21;
                    }
        }
    }

//    public int cual(ArrayList<Integer> transiciones, int[] sensibilizadas) 
//    {
//        for (int i = 0; i < transiciones.size(); i++) 
//        {
//            if (sensibilizadas[transiciones.get(i)] == 1)
//            {
//                return transiciones.get(i);
//            }
//        }
//
//        return transiciones.get(0);
//    }

    public int cual(ArrayList<Integer> transiciones, int[] sensibilizadas) 
    {
        int electo = transiciones.get(0);
        
        for (int i = 0; i < transiciones.size(); i++) 
        {
            if (sensibilizadas[transiciones.get(i)] == 1)
            {
                electo = transiciones.get(i);
                switch (electo) 
                {
                    case 9:
                    case 10:
                    case 20:
                    case 21:
                        return decidir(electo);
                    default:
                        return electo;
                }
            } 
        }

        return electo;
    }
}
