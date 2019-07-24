package Archivos;

import java.io.File;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import Monitor.RedDePetri;

/** Esta clase es la encargada de leer toda la información
 *  necesaria para la creación de la Red de Petri:
 *   - Matriz de incidencia.
 *   - Intervalos temporales.
 *   - Cantidad de hilos a utilizar.
 * 
 * @author N.P.
 */



public class Archivos
{
    
    private static Archivos archivos = null;
    int filas, columnas;
    ArrayList<ArrayList<Integer>> intervalos, hilos, matriz_imas, matriz_imenos, inhibidas;
    ArrayList<Integer> marcado;
    
    /* singleton */
    public static Archivos getArchivos() //Singleton
    {
        if(archivos == null) archivos = new Archivos();
        return archivos;
    }
    
    /* constructor */
    private Archivos() 
    {
        for(archivosEnum e : archivosEnum.values())
        {
            try
            {
                Scanner input = new Scanner(new File(e.getPath()).getAbsoluteFile());
                
                switch(e.getNombre())
                {   
                    case "IncidenciaMas":
                        matriz_imas = new ArrayList<ArrayList<Integer>>();
                        while(input.hasNextLine()) 
                        {            
                            Scanner fila = new Scanner(input.nextLine());
                            ArrayList<Integer> columna = new ArrayList<>();

                            while(fila.hasNextInt()) 
                            {
                                columna.add(fila.nextInt());
                            }               
                            matriz_imas.add(columna);
                            fila.close();
                        }
                        input.close();
                        
                        filas = matriz_imas.size();
                        columnas = matriz_imas.get(0).size();
                        
                        System.out.println("Matriz de incidencia mas cargada exitosamente.");
                        System.out.println("Plazas: "+ matriz_imas.size() + "\nTransiciones: " + matriz_imas.get(0).size());
                        break;
                        
                        
                        
                    case "IncidenciaMenos":
                        matriz_imenos = new ArrayList<ArrayList<Integer>>();
                        while(input.hasNextLine()) 
                        {            
                            Scanner fila = new Scanner(input.nextLine());
                            ArrayList<Integer> columna = new ArrayList<>();

                            while(fila.hasNextInt()) 
                            {
                                columna.add(fila.nextInt());
                            }               
                            matriz_imenos.add(columna);
                            fila.close();
                        }
                        input.close();
                        
                        filas = matriz_imenos.size();
                        columnas = matriz_imenos.get(0).size();
                        
                        System.out.println("Matriz de incidencia menos cargada exitosamente.");
                        System.out.println("Plazas: "+ matriz_imenos.size() + "\nTransiciones: " + matriz_imenos.get(0).size());
                        break;
                        
                    case "Intervalos":
                        intervalos = new ArrayList<ArrayList<Integer>>();
                        while(input.hasNextLine()) 
                        {            
                            Scanner filas = new Scanner(input.nextLine());
                            ArrayList<Integer> columna = new ArrayList<>();

                            while(filas.hasNextInt()) 
                            {
                                columna.add(filas.nextInt());
                            }               

                            intervalos.add(columna);
                            filas.close();
                        }
                        input.close();
                        break;
                        
                    case "Marcado":
                        marcado = new ArrayList<>();
                        while(input.hasNextInt()) 
                        {
                            marcado.add(input.nextInt());
                        }
                        input.close();
                        break;  
                        
                    case "Hilos":
                        hilos = new ArrayList<>();
                        int columnaAnterior = 0;
                        while(input.hasNextLine())
                        {
                            Scanner fila = new Scanner(input.nextLine());
                            ArrayList<Integer> columna = new ArrayList<>();

                            while(fila.hasNextInt()) 
                            {
                                columna.add(fila.nextInt());                    
                            }               

                            hilos.add(columna);
                            columnaAnterior = columna.size();
                            fila.close();                
                        }

                        input.close();
                        break;
                        
                    case "Inhibidas":
                        inhibidas = new ArrayList<ArrayList<Integer>>();
                        while(input.hasNextLine()) 
                        {            
                            Scanner fila = new Scanner(input.nextLine());
                            ArrayList<Integer> columna = new ArrayList<>();

                            while(fila.hasNextInt()) 
                            {
                                columna.add(fila.nextInt());
                            }               
                            inhibidas.add(columna);
                            fila.close();
                        }
                        input.close();
                        
                        System.out.println("Matriz de inhibidas cargada exitosamente.");
                        break;
                }                
            } catch (FileNotFoundException ex){
                System.out.println("Error al cargar archivo.");
                ex.getMessage();
            }
        }
    }
  
    public void printToFile()
    {
        String disparos = RedDePetri.getRdP().getSecuenciaDisparos().toString(); 
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("./src/main/java/Archivos/output.txt");
            fileWriter.write(disparos);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getFilas()
    {
        return filas;
    }
    
    public int getColumnas()
    {
        return columnas;
    }
    
    public ArrayList<ArrayList<Integer>> getIncidenciaMenos()
    {
        return matriz_imenos;
    }
    
    public ArrayList<ArrayList<Integer>> getIncidenciaMas()
    {
        return matriz_imas;
    }
    
    public ArrayList<ArrayList<Integer>> getIntervalos()
    {
        return intervalos;
    }
    
    public ArrayList<ArrayList<Integer>> getHilos()
    {
        return hilos;
    }
    
    public ArrayList<Integer> getMarcado()
    {
        return marcado;
    }
    
    public ArrayList<ArrayList<Integer>> getInhibidas()
    {
        return inhibidas;
    }
}
