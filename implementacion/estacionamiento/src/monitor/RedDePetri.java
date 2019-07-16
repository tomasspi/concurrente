package monitor;

import archivos.Archivos;
import archivos.archivosEnum;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Lógica del sistema.
 *
 * @author Sebastian Navarro & Tomas Piñero
 * @version 2018
 */


public class RedDePetri 
{    
    private static RedDePetri RdP = null;
    
    private int incidencia_menos[][], incidencia_mas[][];
    private int intervalos[][];
    private int v_sensibilizadas[], marcado[]; 
    private int vs_temporales[], vs_extendido[];
    private int columna[];

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
        intervalos = new int[plazas][2];
        
        marcado = new int[plazas];
        columna = new int[plazas];
        
        v_sensibilizadas = new int[transiciones];
        vs_temporales = new int[transiciones];
        vs_extendido = new int[transiciones];
        
        separarIncidencia(arch);
        
        cargarDatos(arch);
        
        printMatriz(incidencia_menos,"Incidencia (menos)");
        printMatriz(incidencia_mas,"Incidencia (mas)");
        printMatriz(intervalos,"Intervalos temporales");
        printVector(marcado,"Marcado incial");
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

    public void disparar(int t) // Proximo estado = Estado Actual + I * (sigma and Ex)
    {
        if(isSensibilizada(t))
        {
            /*
             *   Como se dispara una sola transicion, se agarra directamente
             *   la columna de la matriz de incidencia correspondiente
             *   para sacar o poner tokens.
             */
            getColumna(incidencia_menos,t);
            marcado = sumar(marcado,columna); //Quita los tokens de la plaza
            getColumna(incidencia_mas,t);
            marcado = sumar(marcado,columna); //Pone los tokens en la otra plaza
            
            actualizar();
        }
        else System.out.println("La transición 'T" + t + "' no está sensibilizada.");
    }

    public boolean isSensibilizada(int t) // Pregunta si la transicion esta sensibilizada
    {
        return (vs_extendido[t] != 0);
    }
    
    private void armarExtendido()
    {
        
    }
    
    public void actualizar()
    {
        System.out.println("Actualizando vector de sensibilizadas.");
        
        //Actualiza E (sensibilizadas comunes)
        for(int i = 0; i < plazas; i++)
        {
            for(int j = 0; j < transiciones; j++)
            {
                if(incidencia_menos[i][j] != 0 && marcado[i] != 0) v_sensibilizadas[i] = 1;
                else v_sensibilizadas[i] = 0;
            }
        }
        
        System.out.println("Actualizando vector de sensibilizadas con tiempo.");
        //Actualiza temporales
        
        System.out.println("Vector de sensibilizadas extendido actualizado.");
    }
    
    /*
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
    
    public void setPlazas(int plazas)
    {
        this.plazas = plazas;
    }
    
    public void setTransiciones(int transiciones)
    {
        this.transiciones = transiciones;
    }

    private void separarIncidencia(Archivos arch) 
    {      
        for(int i = 0; i < plazas; i++)
        {
            for(int j = 0; j < transiciones; j++)
            {
                if(arch.getIncidencia().get(i).get(j) <= 0) incidencia_menos[i][j] = arch.getIncidencia().get(i).get(j);
                else if (arch.getIncidencia().get(i).get(j) >= 0) incidencia_mas[i][j] = arch.getIncidencia().get(i).get(j);
            }
        }  
    }

    private void cargarDatos(Archivos arch) 
    {
        for(int i = 0; i < plazas; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                intervalos[i][j] = arch.getIntervalos().get(i).get(j);
            }
            marcado[i] = arch.getMarcado().get(i);
        } 
    }
}