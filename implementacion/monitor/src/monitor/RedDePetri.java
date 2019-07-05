package monitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Lógica del sistema.
 *
 * @author Sebastian Navarro & Tomas Piñero
 * @version 2018
 */


public class RedDePetri {
    
    private static RedDePetri RdP;
    private int plazas, transiciones;
    private RealMatrix I, intervalo;
    private RealVector Ex, E, Z, B, sigma, marcado; //Ex extendidad, E sensibilizadas sin tiempo, Z sensibilizadas con tiempo, B sensibilizadas por inhibidor, sigma es el disparo;
    private boolean condicion;
    
    public static RedDePetri getRdP() //Singleton
    {
        if(RdP == null) RdP = new RedDePetri();
        return RdP;
    }
    
    private RedDePetri() // Constructor
    {
        // Carga la matriz desde un archivo de texto plano.
        try
        {
            Scanner input = new Scanner(new File("matriz.txt").getAbsoluteFile());
            ArrayList<ArrayList<Integer>> matriz = new ArrayList<ArrayList<Integer>>();
            while(input.hasNextLine()) 
            {            
                Scanner columnas = new Scanner(input.nextLine());
                ArrayList<Integer> columna = new ArrayList<Integer>();
                
                while(columnas.hasNextInt())
                {
                    columna.add(columnas.nextInt());
                }               
            matriz.add(columna);
            columnas.close();
            }
            
            plazas = matriz.size();
            transiciones = matriz.get(0).size();
        
            //Seteo la matriz de incidencia.
            I = new Array2DRowRealMatrix(plazas,transiciones);
        
        System.out.println("La Matriz de incidencia es: \n");
        
        for(int i = 0; i < plazas; i++){
            for(int j = 0; j < transiciones; j++){
                I.setEntry(i,j, matriz.get(i).get(j));
                System.out.print(I.getEntry(i, j) + " ");
            }
            System.out.println();
        }
        
            input.close();
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void setIntervalo(ArrayList<ArrayList<Integer>> matriz)
    {
        /*
         * La matriz de las transiciones que tienen tiempo es de Mx2. Como es la que sigue a la matriz de incidencia,
         * tomo desde la ultima columna de la matriz solo dos columnas.
         * ~Tom
         */
        
        System.out.println("\nLa matriz temporal es:\n");
        
        intervalo = new Array2DRowRealMatrix(plazas,2);
        
        for(int i = 0; i < plazas; i++) 
        {
            for(int j = 0; j < 2; j++) 
            {
                intervalo.setEntry(i,j, matriz.get(i).get(j));
                System.out.print(intervalo.getEntry(i, j) + " ");
            }
            System.out.println();
        }
    }

    private void setMarcadoInicial(ArrayList<ArrayList<Integer>> matriz) 
    {
        /*
         * El vector de marcado inicial es la última columna de la matriz extendida, por lo tanto cargo esa columna al vector.
         * ~Tom
         */
        System.out.println("\nEl marcado inicial es:\n");
        
        marcado = new ArrayRealVector(plazas);
        
        for(int i = 0; i < plazas; i++) 
        {
            marcado.setEntry(i,matriz.get(i).get(transiciones-1));
            System.out.print(marcado.getEntry(i));
        }
    }
    
    public boolean disparo() // Proximo estado = Estado Actual + I * (sigma and Ex)
    {
        
        return false;
    }
    
    public boolean estaSensibilizada(int t) // Preguntar si la transicion esta sensibilizada
    {
        if(condicion) return true;
        else return false;
    }
    
    public boolean testVentanaTiempo() // devolver true cuando esta en un tiempo entre alfa y beta, sino false
    {
        return true;
    }

    public void getSensibilizadas()
    {
        ;
    }

    public int getPlazas()
    {
        return plazas;
    }
    
    public int getTransiciones()
    {
        return transiciones;
    }
}