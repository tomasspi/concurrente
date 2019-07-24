package archivos;

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
import monitor.RedDePetri;

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
    int filas, columnas;
    ArrayList<ArrayList<Integer>> matriz, intervalos, hilos;
    ArrayList<Integer> marcado;
    
    public void leer()
    {
        for(archivosEnum e : archivosEnum.values())
        {
            try
            {
                Scanner input = new Scanner(new File(e.getPath()).getAbsoluteFile());
                
                switch(e.getNombre())
                {
                    case "Incidencia":
                        matriz = new ArrayList<ArrayList<Integer>>();
                        while(input.hasNextLine()) 
                        {            
                            Scanner fila = new Scanner(input.nextLine());
                            ArrayList<Integer> columna = new ArrayList<>();

                            while(fila.hasNextInt()) 
                            {
                                columna.add(fila.nextInt());
                            }               
                            matriz.add(columna);
                            fila.close();
                        }
                        input.close();
                        
                        filas = matriz.size();
                        columnas = matriz.get(0).size();
                        
                        System.out.println("Matriz de incidencia cargada exitosamente.");
                        System.out.println("Plazas: "+ matriz.size() + "\nTransiciones: " + matriz.get(0).size());
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
                }                
            } catch (FileNotFoundException ex){
                System.out.println("Error al cargar archivo.");
                ex.getMessage();
            }
        }
    }
    
    public void leerHilos()
    {
        hilos = new ArrayList<>();
        int columnaAnterior = 0;
        try
        {
            Scanner input = new Scanner(new File("./src/archivos/hilos.txt"));
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
            
        } catch (FileNotFoundException e)
        {
            System.out.println("Error al cargar archivo.");
            e.getMessage();
        }
    }
    
    public void printToFile()
    {
        String disparos = RedDePetri.getRdP().getSecuenciaDisparos().toString(); 
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("./src/archivos/output.txt");
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
    
    public ArrayList<ArrayList<Integer>> getIncidencia()
    {
        return matriz;
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
}
