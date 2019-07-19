package monitor;

import archivos.Archivos;
import java.util.ArrayList;

/**
 * Lógica del sistema.
 *
 * @author N.P.
 * @version 2018
 */


public class RedDePetri 
{    
    private static RedDePetri RdP = null;
    
    private int incidencia_menos[][], incidencia_mas[][];
    private int intervalos[][], isTemporal[];
    private int v_sensibilizadas[], marcadoInicial[], marcado[]; 
    private int vs_extendido[];
    private int columna[];
    boolean cartel;
    ArrayList<Tiempos> transicion;

    private int plazas, transiciones;
    
    public static RedDePetri getRdP() //Singleton
    {
        if(RdP == null) RdP = new RedDePetri();
        return RdP;
    }
    
    private RedDePetri() // Constructor
    {
        System.out.println("Cargando archivos...");
        Archivos arch = new Archivos();
        
        arch.leer();
        
        plazas = arch.getFilas();
        transiciones = arch.getColumnas();
        
        incidencia_menos = new int[plazas][transiciones];
        incidencia_mas = new int[plazas][transiciones];
        intervalos = new int[transiciones][2];
        
        marcadoInicial = new int[plazas];
        marcado = new int[plazas];
        columna = new int[plazas];
        
        isTemporal = new int[transiciones];        
        v_sensibilizadas = new int[transiciones];
        vs_extendido = new int[transiciones];
        
        separarIncidencia(arch);
        
        cargarTiempos(arch);
        
        transicion = new ArrayList<>(transiciones);
        
        for(int i = 0; i < transiciones; i++)
        {
            transicion.add(i,null);
            if(isTemporal[i] == 1) transicion.set(i,new Tiempos(i,intervalos[i][0],intervalos[i][1]));
        }
        marcado = marcadoInicial;
        
        actualizarSensibilizadas();
        actualizarExtendida();
        printVector(marcado,"Marcado actual");
        /*printMatriz(incidencia_menos,"Incidencia (menos)");
        printMatriz(incidencia_mas,"Incidencia (mas)");
        printMatriz(intervalos,"Intervalos temporales");
        printVector(marcado,"marcado inicial");
        printVector(isTemporal, "transiciones con tiempo");*/
    }
    
    private void printMatriz(int m[][], String nombre)
    {
        System.out.println("\nMatriz de " + nombre + ": ");
        for (int[] e : m) 
        {
            for (int j = 0; j < m[0].length; j++) 
            {
                System.out.print(e[j] + " ");
            }
            System.out.println();
        }
    }
    
    private void printVector(int v[], String nombre)
    {
        System.out.println("\nVector de "+ nombre + ": ");
        for(int i = 0; i < v.length; i++)
        {
            System.out.println(v[i] + " ");
        }        
        System.out.println("\n");
    }

    public boolean disparar(int t) // Proximo estado = Estado Actual + I * (sigma and Ex)
    {
        if(isSensibilizada(t))
        {
            /**
             *   Como se dispara una sola transicion, se agarra directamente
             *   la columna de la matriz de incidencia correspondiente
             *   para sacar o poner tokens.
             */
            getColumna(incidencia_menos,t);
            marcado = sumar(marcado,columna); //Quita los tokens de la plaza
            getColumna(incidencia_mas,t);
            marcado = sumar(marcado,columna); //Pone los tokens en la otra plaza
            
            actualizarSensibilizadas();
            actualizarExtendida();
            manejarCartel();
            return true;
        }
        else {
            System.out.println("No se realizó el disparo de 'T" + t + ".");
            return false;
        }
    }

    public boolean isSensibilizada(int t) // Pregunta si la transicion esta sensibilizada
    {
        return (vs_extendido[t] != 0);
    }

    public void actualizarSensibilizadas()
    {
        System.out.println("Actualizando vector de sensibilizadas.");
        
        int index[] = new int[plazas];
        
        for(int i = 0; i < transiciones; i++)
        {
            int elemento = 0;
            
            for(int j = 0; j < plazas; j++)
            {
                if(incidencia_menos[j][i] != 0)
                {
                    index[elemento] = j;
                    elemento++;
                }
                
                int k = 0;
                
                while(k < elemento && marcado[index[k]] > 0) k++;
                
                if(k == elemento) v_sensibilizadas[i] = 1;
                else v_sensibilizadas[i] = 0;
                
                if(v_sensibilizadas[i] == 1 && isTemporal[i] == 1) transicion.get(i).setTS();
            }
        }
        //printVector(v_sensibilizadas, "sensibilizadas");        
        System.out.println("Vector de sensibilizadas actualizado.");
    }
    
    public void actualizarExtendida()
    {
        for(int i = 0; i < transiciones; i++)
        {
            for(int j = 0; j < plazas; j++)
            {                
                vs_extendido[i] = v_sensibilizadas[i];
                
                if(vs_extendido[i] == 1 && isTemporal[i] == 1) 
                {
                    if(!transicion.get(i).checkVentana()) vs_extendido[i] = 0;
                }
            }
        }         
        //printVector(vs_extendido,"extendido");
        System.out.println("Vector de sensibilizadas extendido actualizado.");
    }
    
    /**
     *  Obtiene la columna a sumar al marcado actual. Esto es el resutlado 
     *  de la multiplicación "I * (sigma and Ex)", al ser sigma un vector
     *  con 1 en la posición de la transición que se desea disparar y 0 en 
     *  las demás posiciones, la multiplicación siempre devolverá una 
     *  columna de la matriz.
     *  @param m - Matriz de la cual se desea extraer la columna.
     *  @param t - Transición que se desea disparar.
     */
    private void getColumna(int[][] m, int t) 
    {
        for(int i = 0; i < plazas; i++)
        {
            columna[i] = m[i][t];
        }
    }
    
    /*
     *  @return Suma de los dos vectores.
     */
    private int[] sumar(int[] a, int[] b)
    {
        int sigma[]= new int[plazas];
        
        for(int i = 0; i < plazas; i++)
        {
            sigma[i] = a[i] + b[i];
        }   
        return sigma;
    }
    
    /**
     * Separa la matriz de incidencia en dos (menos y mas)
     * y a su vez carga el marcado inicial.
     * @param arch - txt a leer.
     */
    private void separarIncidencia(Archivos arch) 
    {     
        for(int i = 0; i < plazas; i++)
        {
            for(int j = 0; j < transiciones; j++)
            {
                if(arch.getIncidencia().get(i).get(j) <= 0) incidencia_menos[i][j] = arch.getIncidencia().get(i).get(j);
                else if (arch.getIncidencia().get(i).get(j) >= 0) incidencia_mas[i][j] = arch.getIncidencia().get(i).get(j);
            }
            marcadoInicial[i] = arch.getMarcado().get(i);
        }  
    }

    /**
     * Carga los intervalos de tiempo desde un archivo txt
     * @param arch - txt a leer
     */
    private void cargarTiempos(Archivos arch) 
    {
        for(int i = 0; i < transiciones; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                intervalos[i][j] = arch.getIntervalos().get(i).get(j);
                if(intervalos[i][0] != 0 || intervalos[i][1] != 0) isTemporal[i] = 1;
                else isTemporal[i] = 0;
            }
        } 
    }
    private void manejarCartel()
    {
        cartel = marcado[19] == 0 && marcado[20] == 0;
        if(cartel) System.out.println("NO HAY LUGAR.");
    }
    
    private void reset()
    {
        marcado = marcadoInicial;
    }    
        
    public int[] getSensibilizadas()
    {
        return vs_extendido;
    }
    
    public int getPlazas()
    {
        return plazas;
    }
    
    public int getTransiciones()
    {
        return transiciones;
    }
    
    public Tiempos getTiempo(int t)
    {
        return transicion.get(t);
    }
    
    public boolean isTemporal(int t)
    {
        return isTemporal[t] == 1;
    }
}