import java.sql.Timestamp;
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
    private int M, N;
    private RealMatrix matrizIncidencia;
    private int[][] vectorTransicionesTiempo;
    private int[] vectorDeEstado;
    private int[] vectorSensibilizadas;
    private int[] vectorSensibilizadasConTiempo;
    private int[] columna;
    private boolean ventana, condicion;
    
    private RedDePetri() {
        /*
         * Creación de los vectores por medio de un txt.
         */
        try{
            Scanner input = new Scanner(new File("matriz.txt").getAbsoluteFile());
            ArrayList<ArrayList<Integer>> matriz = new ArrayList<ArrayList<Integer>>();
            while(input.hasNextLine()) {
            
                Scanner columnas = new Scanner(input.nextLine());
                ArrayList<Integer> columna = new ArrayList<Integer>();
            
            while(columnas.hasNextInt()) {
                columna.add(columnas.nextInt());
            }               
            matriz.add(columna);
            columnas.close();
        }
            
        M = matriz.size();
        N = matriz.get(0).size();
        
        //Seteo la matriz de incidencia.
        matrizIncidencia = new Array2DRowRealMatrix(M,N);
        
        System.out.println("La Matriz de incidencia es: \n");
        
        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                matrizIncidencia.setEntry(i,j, matriz.get(i).get(j));
                System.out.print(matrizIncidencia.getEntry(i, j) + " ");
            }
            System.out.println();
        }
        
            input.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public static RedDePetri getRdP() {
        if(RdP == null) RdP = new RedDePetri();
        return RdP;
    }
    
    public boolean disparo() {
        return false;
    }
    
    public void calculoDeVectorEstado() {
        double[][] matrixData2 = { {1,2,3}, {2,5,6}, {1,5, 7}};
        RealMatrix n = new Array2DRowRealMatrix(matrixData2);
    }
    
    public void setEsperando() {
        
    }
    
    public void resetEsperando() {
        
    }
    
    public boolean estaSensibilizada(){
        if(condicion) return true;
        else return false;
    }
    
    public boolean testVentanaTiempo() {
        //No hay seguridad de que esto este correcto, probablemente haya que usar otro timestamp. ~Tom
        Timestamp tiempo = new Timestamp(5000);
        Timestamp alfa = new Timestamp(0);
        Timestamp beta = new Timestamp(30000);

        return true;
    }

    /*public void setMatrizIncidencia(ArrayList<ArrayList<Integer>> matriz) {
        
        /*
         * Aca cargo la matriz de incidencia menos las ultimas 3 columnas, que corresponden
         * a las transiciones con tiempo y al marcado inicial.
         * ~Tom
         
        
        M = matriz.size();
        N = matriz.get(0).size();
        
        matrizIncidencia = new int[M][N];
        
        System.out.println("La Matriz de Incidencia es:\n");
        
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N-3; j++) {
                matrizIncidencia[i][j] = matriz.get(i).get(j);
                System.out.print(matrizIncidencia[i][j] + " ");
            }
            System.out.println();
        }
    }*/

    public int[][] setVectorTransicionesTiempo(ArrayList<ArrayList<Integer>> matriz) {
        
        /*
         * La matriz de las transiciones que tienen tiempo es de Mx2. Como es la que sigue a la matriz de incidencia,
         * tomo desde la ultima columna de la matriz solo dos columnas.
         * ~Tom
         */
        
        System.out.println("\nLa matriz temporal es:\n");
        
        vectorTransicionesTiempo = new int[M][2];
        
        for(int i = 0; i < M; i++) {
            for(int j = 0; j < 2; j++) {
                vectorTransicionesTiempo[i][j] = matriz.get(i).get(N-3+j);
                System.out.print(vectorTransicionesTiempo[i][j] + " ");
            }
            System.out.println();
        }       
        return vectorTransicionesTiempo;
    }

    public int[] setVectorDeEstado(ArrayList<ArrayList<Integer>> matriz) {
        
        /*
         * El vector de marcado inicial es la ï¿½ltima columna de la matriz extendida, por lo tanto cargo esa columna al vector.
         * ~Tom
         */
        System.out.println("\nEl marcado inicial es:\n");
        
        vectorDeEstado = new int[M];
        
        for(int i = 0; i < M; i++) {
            vectorDeEstado[i] = matriz.get(i).get(N-1);
            System.out.println(vectorDeEstado[i]);
        }
        return vectorDeEstado;
    }

    public void setVectorSensibilizadas(int[] vectorSensibilizadas) {
        this.vectorSensibilizadas = vectorSensibilizadas;
    }

    public void setVectorSensibilizadasConTiempo(int[] vectorSensibilizadasConTiempo) {
        this.vectorSensibilizadasConTiempo = vectorSensibilizadasConTiempo;
    }

    public int[] getColumna() {
        return columna;
    }
    
    public int[] getSensibilizadas(){
        return null;
       }

    public void setColumna(int[] columna) {
        this.columna = columna;
    }

    public boolean isCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }
    public void setVentana(boolean ventana) {
        this.ventana = ventana;
    }

}
