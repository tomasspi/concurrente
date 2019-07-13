package monitor;

import archivos.archivosEnum;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        
        cargarArchivos();
        
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
                Como se dispara una sola transicion, se agarra directamente
                la columna de la matriz de incidencia correspondiente
                para sacar o poner tokens.
            */
            getColumna(incidencia_menos,t);
            v_marcado = sumar(v_marcado,columna);
            getColumna(incidencia_mas,t);
            v_marcado = sumar(v_marcado,columna);
            
            actualizar();
        }
    }

    public boolean isSensibilizada(int t) // Preguntar si la transicion esta sensibilizada
    {
        return (vs_extendido[t] != 0);
    }
    
    private void armarExtendido()
    {
        
    }
    
    public void actualizar()
    {
        System.out.println("Aca actualizamos los vectores Ex.");
        
        //Actualiza E (sensibilizadas comunes)
        for(int i = 0; i < plazas; i++)
        {
            for(int j = 0; j < transiciones; j++)
            {
                if(incidencia_menos[i][j] != 0 && v_marcado[i] != 0) v_sensibilizadas[i] = 1;
                else v_sensibilizadas[i] = 0;
            }
        }
        
        //Actualiza 
    }
    
    private void getColumna(int[][] m, int t) //Obitene la columna a sumar al marcado actual.
    {
        for(int i = 0; i < plazas; i++)
        {
            columna[i] = m[i][t];
        }
    }
    
    private int[] sumar(int[] a, int[] b)
    {
        int sigma[] = new int[plazas];
        
        for(int i = 0; i < plazas; i++)
        {
            sigma[i] = a[i] + b[i];
        }   
        return sigma;
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