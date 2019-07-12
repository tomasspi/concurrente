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
    INCIDENCIA("./src/archivos/incidencia.txt"),
    INTERVALOS("./src/archivos/intervalos.txt"),
    MARCADO("./src/archivos/marcado.txt");
    
    private String nombreArchivo;
    
    archivosEnum(String nombreArchivo){
        this.nombreArchivo = nombreArchivo;
    }
    
    public String getNombre(){
        return nombreArchivo;
    }
            
}
