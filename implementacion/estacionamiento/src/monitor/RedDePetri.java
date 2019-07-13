package monitor;

import archivos.archivosEnum;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    ArrayList<ArrayList<Integer>> matriz, intervalos;
    ArrayList<Integer> marcado;
    
    /*************/
    private int[][] mIncidencia;
    private int[][] mIntervalos;
    private int[][] vMarcado;
    /*************/
    
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
        marcado = new ArrayList<>();
        intervalos = new ArrayList<>();
        
        System.out.println("Cargando archivos...");
        cargarArchivo2();
        
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
            
//            //La matriz de intervalos se encuentra luego de la ultima transicion
//            for(int k = transiciones; k < transiciones + 2; k++)
//            {
//                int l = 0;
//                m_intervalos.setEntry(i,l, matriz.get(i).get(k));
//                l++;
//            }
//            
//            //El vector de marcado inicial es la última columna de la matriz extendida, por lo tanto cargo esa columna al vector.
//            v_marcado.setEntry(i,matriz.get(i).get(transiciones+3));
        }   
        
        printMatriz(incidencia_menos,"Incidencia (menos)");
        printMatriz(incidencia_mas,"Incidencia (mas)");
        printM();
        //printVector(v_marcado,"Marcado");
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
        for(archivosEnum e : archivosEnum.values()){
            try
            {
                Scanner input = new Scanner(new File(e.getNombre()).getAbsoluteFile());
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

                if(e.getNombre().contains("incidencia")){
                    plazas = matriz.size();
                    transiciones = matriz.get(0).size();
                }

                input.close();
            } catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Alternativa de carga de archivos usando matrices primitivas.
     */
    private void cargarArchivo2(){
        for(archivosEnum e : archivosEnum.values()){
            
            // Path del archivo a abrir.
            String fileName = e.getPath();

            // Variable que almacena una fila entera.
            String fila;
            
            // Variable que almacenará el txt.
            String txt = "";
            
            // Variable que almacena la cantidad de columnas en cada archivo.
            int cantColumnas = 0;
            int cantFilas = 0;
            
            try {
                /* Se abre el archivo desde el path. */
                FileReader fileReader = new FileReader(fileName);
                //System.out.println("Se abrió el archivo "+e.getNombre());

                /* Wrap al archivo usando BufferedReader. */
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                
                /* Se leen los archivos. */
                while((fila = bufferedReader.readLine()) != null) {
                    cantFilas++;
                    /* Quita los espacios entre los numeros (si los hay). */
                    fila = fila.replaceAll("\\s","");
                    //System.out.println("Fila: "+fila);
                    /* Copia el txt a un String. */
                    txt = txt.concat(fila);
                    cantColumnas = fila.length();
                    //System.out.println("La fila tiene "+cantColumnas+" columnas"+"\n");
                }
                
                /* Inicializa las variables globales de cada matriz. */
                if(e.getVariable().equals("mIncidencia")) mIncidencia = new int[cantFilas][cantColumnas];
                if(e.getVariable().equals("mIntervalos")) mIntervalos = new int[cantFilas][cantColumnas];
                if(e.getVariable().equals("vMarcado")) vMarcado = new int[cantFilas][cantColumnas];
                
                /* Construye las matrices. */
                builder(e, cantColumnas, cantFilas, txt);
                
                /* Se cierra el archivo. */
                bufferedReader.close();      
                //System.out.println("Se cerró "+e.getNombre()+"\n");
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                    "Unable to open file '" + 
                    fileName + "'");                
            }
            catch(IOException ex) {
                System.out.println(
                    "Error reading file '" 
                    + fileName + "'");                  
                // Or we could just do this: 
                // ex.printStackTrace();
            }
        }
        
        System.out.println("La matrices cargadas son:");
        System.out.println(Arrays.deepToString(mIncidencia));  
        System.out.println(Arrays.deepToString(mIntervalos));  
        System.out.println(Arrays.deepToString(vMarcado));  
    }
    
    /**
     * Arma las matrices luego de haber leido los archivos.
     * @param e - El Enum con la informacion del archivo abierto.
     * @param cantColumnas - Cantidad de columnas del archivo abierto.
     * @param cantFilas - Cantidad de filas del archivo abierto
     * @param txt - String con la copia del contenido del archivo.
     */
    private void builder(archivosEnum e, int cantColumnas, int cantFilas, String txt){
        /* Variable auxilar para armar cada matriz. */
        int counter;
        
        /* Se arma la matriz */
        counter = 0;
        //System.out.println("Es una matriz de "+cantColumnas+" columnas y "+cantFilas+" filas");
        //System.out.println("Se armará una matriz con: "+txt);
        for(int i=0;i<cantFilas;i++){
            for(int j=0;j<cantColumnas;j++){
                //System.out.println("Armando matriz...");
                
                /* De char a int */
                int ij = Character.getNumericValue(txt.charAt(counter));
                //System.out.println("Elemento: "+ij);
                //System.out.println("En posicion: "+i+j);
                switch(e.getCaso()){
                    case 1: /* Matriz de incidencia */
                            mIncidencia[i][j] = ij;
                            break;
                    case 2: /* Matriz de intervalos */
                            mIntervalos[i][j] = ij;
                            break;
                    case 3: /* Vector de marcado */ 
                            vMarcado[i][j] = ij;
                            break;
                }
                counter++;
            }
        }
    }
    
    private void printM()
    {
        System.out.println("\nMatriz de incidencia: ");
        for(int i = 0; i < matriz.size(); i++)
        {
            for(int j = 0; j < matriz.get(0).size(); j++)
            {
                System.out.print(matriz.get(i).get(j) + " ");
            }
            System.out.println();
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