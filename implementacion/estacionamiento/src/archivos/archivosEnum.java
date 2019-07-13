/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package archivos;

/**
 *
 * @author sebastian
 */
public enum archivosEnum {
    INCIDENCIA("Incidencia", "./src/archivos/incidencia.txt", 1, "mIncidencia"),
    INTERVALOS("Intervalos","./src/archivos/intervalos.txt", 2, "mIntervalos"),
    MARCADO("Marcado","./src/archivos/marcado.txt", 3, "vMarcado");
    
    private String path;
    private String nombreArchivo;
    private int caso;
    private String nombreVariable;
    
    archivosEnum(String nombreArchivo, String path, int caso, String nombreVariable){
        this.path = path;
        this.nombreArchivo = nombreArchivo;
        this.caso = caso;
        this.nombreVariable = nombreVariable;
    }
    
    public String getPath(){
        return path;
    }
    
    public String getNombre(){
        return nombreArchivo;
    }
    
    public int getCaso(){
        return caso;
    }
    
    public String getVariable(){
        return nombreVariable;
    }
            
}
