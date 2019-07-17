package archivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
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
                        
                        System.out.println(Arrays.deepToString(matriz.toArray()));
                        System.out.println("Plazas: "+ matriz.size() + " Transiciones: " + matriz.get(0).size());
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
                        
                        
                        System.out.println(Arrays.deepToString(intervalos.toArray()));
                        System.out.println("Alfa: "+ intervalos.size() + " Beta: " + intervalos.get(0).size());
                        break;
                        
                    case "Marcado":
                        marcado = new ArrayList<>();
                        while(input.hasNextLine()) 
                        {
                            marcado.add(input.nextInt());
                        }
                        input.close();
                        
                        System.out.println(Arrays.deepToString(marcado.toArray()));
                        System.out.println("Tamaño: "+ marcado.size());
                        
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
                if(columna.size() < columnaAnterior) columna.add(null);
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
