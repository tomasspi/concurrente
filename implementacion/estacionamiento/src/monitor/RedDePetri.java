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


public class RedDePetri 
{    
    private static RedDePetri RdP = null;
    ArrayList<ArrayList<Integer>> matriz;
    
    private final RealMatrix incidencia_menos, incidencia_mas, m_intervalos;
    private RealVector v_sensibilizadas, v_disparo, v_marcado; 
    private RealVector vs_temporales, vs_inhibidores, vs_extendido;
    private int plazas, transiciones;
    
    public static RedDePetri getRdP() //Singleton
    {
        if(RdP == null) RdP = new RedDePetri();
        return RdP;
    }
    
    private RedDePetri() // Constructor
    {
        matriz = new ArrayList<>();
        
        cargarArchivo();
        
        incidencia_menos = new Array2DRowRealMatrix(plazas,transiciones);
        incidencia_mas = new Array2DRowRealMatrix(plazas,transiciones);
        m_intervalos = new Array2DRowRealMatrix(plazas,2);
        
        v_marcado = new ArrayRealVector(plazas);
        
        for(int i = 0; i < plazas; i++)
        {
            for(int j = 0; j < transiciones; j++)
            {
                if(matriz.get(i).get(j) <= 0) incidencia_menos.setEntry(i,j, matriz.get(i).get(j));
                else if (matriz.get(i).get(j) >= 0) incidencia_mas.setEntry(i,j, matriz.get(i).get(j));
            }
            
            //La matriz de intervalos se encuentra luego de la ultima transicion
            for(int k = transiciones; k < transiciones + 2; k++)
            {
                int l = 0;
                m_intervalos.setEntry(i,l, matriz.get(i).get(k));
                l++;
            }
            
            //El vector de marcado inicial es la última columna de la matriz extendida, por lo tanto cargo esa columna al vector.
            v_marcado.setEntry(i,matriz.get(i).get(transiciones+3));
        }   
        
        printMatriz(incidencia_mas,"Incidencia");
        printVector(v_marcado,"Marcado");
    }
    
    private void printMatriz(RealMatrix m, String nombre)
    {
        System.out.println("\nMatriz de " + nombre + ": ");
        for(int i = 0; i < m.getRowDimension(); i++)
        {
            for(int j = 0; j < m.getColumnDimension(); j++)
            {
                System.out.print(m.getEntry(i,j) + " ");
            }
            System.out.println();
        }
    }
    
    private void printVector(RealVector v, String nombre)
    {
        System.out.println("\nVector de "+ nombre + ": ");
        for(int i = 0; i < v.getDimension(); i++)
        {
            System.out.println(v.getEntry(i) + " ");
        }        
        
        System.out.println("\n");
    }
    
    private void cargarArchivo()
    {
        // Carga la matriz desde un archivo de texto plano.
        ArrayList<Integer> columna = new ArrayList<Integer>();
        try
        {
            Scanner input = new Scanner(new File("./matriz.txt").getAbsoluteFile());
            while(input.hasNextLine()) 
            {            
                Scanner columnas = new Scanner(input.nextLine());
                
                
                while(columnas.hasNextInt())
                {
                    columna.add(columnas.nextInt());
                }               
            matriz.add(columna);
            columnas.close();
            }
            
            plazas = matriz.size();
            transiciones = matriz.get(0).size() - 4;
            
            input.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean disparo() // Proximo estado = Estado Actual + I * (sigma and Ex)
    {
        
        return false;
    }
    
    public boolean isSensibilizada(int t) // Preguntar si la transicion esta sensibilizada
    {
        return false;
    }

    public void getSensibilizadas()
    {
        ;
    }
    
    public void actualizar()
    {
        System.out.println("Aca actualizamos los vectores Ex.");
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