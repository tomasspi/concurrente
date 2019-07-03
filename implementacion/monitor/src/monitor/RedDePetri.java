package monitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

//import assets.org.apache.commons.math3.*;
/**
 * Write a description of class RedDePetri here.
 *
 * @author Sebastian Navarro & Tomas Piñero
 * @version 2018
 */


public class RedDePetri {
    
    private static RedDePetri RdP;
    private int plazas, transiciones;
    private RealMatrix matrizIncidencia;
    private int[][] TransicionesTiempo;
    private int[] vectorDeEstado;
    private int[] vectorSensibilizadas;
    private int[] vectorSensibilizadasConTiempo;
    private int[] columna;
    private boolean ventana, condicion;
    
    private RedDePetri() 
    {
        /*
         * Creación de los vectores por medio de un txt.
         */
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
        matrizIncidencia = new Array2DRowRealMatrix(plazas,transiciones);
        
        System.out.println("La Matriz de incidencia es: \n");
        
        for(int i = 0; i < plazas; i++){
            for(int j = 0; j < transiciones; j++){
                matrizIncidencia.setEntry(i,j, matriz.get(i).get(j));
                System.out.print(matrizIncidencia.getEntry(i, j) + " ");
            }
            System.out.println();
        }
        
            input.close();
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static RedDePetri getRdP() 
    {
        if(RdP == null) RdP = new RedDePetri();
        return RdP;
    }
    
    public boolean disparo() 
    {
        return false;
    }
    
    public void calculoDeVectorEstado() 
    {
        /*double[][] matrixData2 = { {1,2,3}, {2,5,6}, {1,5, 7}};
        RealMatrix n = new Array2DRowRealMatrix(matrixData2);*/
    }
    
    public void setEsperando() {
        
    }
    
    public void resetEsperando()
    {
        
    }
    
    public boolean estaSensibilizada()
    {
        if(condicion) return true;
        else return false;
    }
    
    public boolean testVentanaTiempo() 
    {

        return true;
    }

    private int[][] setTransicionesTiempo(ArrayList<ArrayList<Integer>> matriz)
    {
        
        /*
         * La matriz de las transiciones que tienen tiempo es de Mx2. Como es la que sigue a la matriz de incidencia,
         * tomo desde la ultima columna de la matriz solo dos columnas.
         * ~Tom
         */
        
        System.out.println("\nLa matriz temporal es:\n");
        
        TransicionesTiempo = new int[plazas][2];
        
        for(int i = 0; i < plazas; i++) {
            for(int j = 0; j < 2; j++) {
                TransicionesTiempo[i][j] = matriz.get(i).get(transiciones-3+j);
                System.out.print(TransicionesTiempo[i][j] + " ");
            }
            System.out.println();
        }       
        return TransicionesTiempo;
    }

    private int[] setVectorDeEstado(ArrayList<ArrayList<Integer>> matriz) 
    {
        
        /*
         * El vector de marcado inicial es la última columna de la matriz extendida, por lo tanto cargo esa columna al vector.
         * ~Tom
         */
        System.out.println("\nEl marcado inicial es:\n");
        
        vectorDeEstado = new int[plazas];
        
        for(int i = 0; i < plazas; i++) {
            vectorDeEstado[i] = matriz.get(i).get(transiciones-1);
            System.out.println(vectorDeEstado[i]);
        }
        return vectorDeEstado;
    }

    public void setVectorSensibilizadas(int[] vectorSensibilizadas)
    {
        this.vectorSensibilizadas = vectorSensibilizadas;
    }

    public void setVectorSensibilizadasConTiempo(int[] vectorSensibilizadasConTiempo) 
    {
        this.vectorSensibilizadasConTiempo = vectorSensibilizadasConTiempo;
    }

    public int[] getColumna() 
    {
        return columna;
    }
    
    public int[] getSensibilizadas()
    {
        return vectorSensibilizadas;
    }

    public void setColumna(int[] columna) 
    {
        this.columna = columna;
    }

    public boolean isCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) 
    {
        this.condicion = condicion;
    }
    
    public void setVentana(boolean ventana)
    {
        this.ventana = ventana;
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
