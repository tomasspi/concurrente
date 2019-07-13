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
    
    private final int incidencia_menos[][], incidencia_mas[][], m_intervalos[][];
    private int v_sensibilizadas[], v_marcado[]; 
    private int vs_temporales[], vs_extendido[];
    private int columna[];
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
        
        incidencia_menos = new int[plazas][transiciones];
        incidencia_mas = new int[plazas][transiciones];
        m_intervalos = new int[plazas][2];
        
        v_marcado = new int[plazas];
        columna = new int[plazas];
        
        v_sensibilizadas = new int[transiciones];
        vs_temporales = new int[transiciones];
        vs_extendido = new int[transiciones];
        
        for(int i = 0; i < plazas; i++)
        {
            for(int j = 0; j < transiciones; j++)
            {
                if(matriz.get(i).get(j) <= 0) incidencia_menos[i][j] = matriz.get(i).get(j);
                else if (matriz.get(i).get(j) >= 0) incidencia_mas[i][j] = matriz.get(i).get(j);
            }
            
            for(int k = 0; k < 2; k++)
            {
                m_intervalos[i][k] = intervalos.get(i).get(k);
            }
            
            v_marcado[i] = marcado.get(i);
        }   
        
        printMatriz(incidencia_menos,"Incidencia (menos)");
        printMatriz(incidencia_mas,"Incidencia (mas)");
        printM();
        //printVector(v_marcado,"Marcado");
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
    
    private void cargarArchivos()
    {
        // Carga la matriz desde un archivo de texto plano.
        for(archivosEnum e : archivosEnum.values())
        {
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
            v_marcado = sumar(v_marcado,columna);
            getColumna(incidencia_mas,t);
            v_marcado = sumar(v_marcado,columna);
            
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
                if(incidencia_menos[i][j] != 0 && v_marcado[i] != 0) v_sensibilizadas[i] = 1;
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
        int sigma[] = new int[plazas];
        
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
}
