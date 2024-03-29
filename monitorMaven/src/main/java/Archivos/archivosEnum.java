/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivos;

/**
 *
 * @author sebastian
 */
public enum archivosEnum {
    HILOS("Hilos","./src/main/java/Archivos/hilos.txt"),
    INCIDENCIA_MAS("IncidenciaMas", "./src/main/java/Archivos/incidencia_mas.txt"),
    INCIDENCIA_MENOS("IncidenciaMenos", "./src/main/java/Archivos/incidencia_menos.txt"),
    INHIBIDAS("Inhibidas", "./src/main/java/Archivos/inhibidas.txt"),
    INTERVALOS("Intervalos","./src/main/java/Archivos/intervalos.txt"),
    MARCADO("Marcado","./src/main/java/Archivos/marcado.txt"),
    OUTPUT("output","./src/main/java/Archivos/output.txt"),
    PINVARIANTES("pInvariantes","./src/main/java/Archivos/pInvariantes.txt"),
    TINVARIANTES("tInvariantes","./src/main/java/Archivos/tInvariantes.txt");    
    
    private String path;
    private String nombreArchivo;
    
    archivosEnum(String nombreArchivo, String path){
        this.path = path;
        this.nombreArchivo = nombreArchivo;
    }
    
    public String getPath(){
        return path;
    }
    
    public String getNombre(){
        return nombreArchivo;
    }
}
