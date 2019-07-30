package Monitor;

import Archivos.Archivos;
import MaquinaDeEstado.MaqEstado;
import java.util.ArrayList;
import java.lang.Exception;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Lógica del sistema.
 *
 * @author N.P.
 * @version 2018
 */


public class RedDePetri 
{    
    private static RedDePetri RdP = null;
    private Archivos archivos;
    private int incidencia_menos[][], incidencia_mas[][], inhibicion[][]; 
    private int intervalos[][], isTemporal[], vs_inhibidas[];
    private int v_sensibilizadas[], marcadoInicial[], marcado[]; 
    private int vs_extendido[], columna[];
    
    ArrayList<Tiempos> transicion;
    ArrayList<ArrayList<Integer>> pinvariantes, tinvariantes;
    ArrayList<Integer> secuenciaDisparos, secuenciaAuxiliar;

    private int plazas, transiciones;
    private boolean pinvariante = true;
    
    public static RedDePetri getRdP() //Singleton
    {
        if(RdP == null) RdP = new RedDePetri();
        return RdP;
    }
    
    
    /* public es private */
    private RedDePetri() // Constructor
    {
        archivos = Archivos.getArchivos();
        
        plazas = archivos.getFilas();
        transiciones = archivos.getColumnas();
        
        inhibicion = new int[plazas][transiciones];
        incidencia_menos = new int[plazas][transiciones];
        incidencia_mas = new int[plazas][transiciones];
        intervalos = new int[transiciones][2];
        
        marcadoInicial = new int[plazas];
        marcado = new int[plazas];
        columna = new int[plazas];
        
        isTemporal = new int[transiciones];        
        v_sensibilizadas = new int[transiciones];
        vs_inhibidas = new int[transiciones];        
        vs_extendido = new int[transiciones];
        secuenciaDisparos = new ArrayList<>();
        secuenciaAuxiliar = new ArrayList<>();
        pinvariantes = archivos.getPinvariantes();
        tinvariantes = archivos.getTinvariantes();
        
        cargarMatrices(archivos);
        cargarTiempos(archivos);
        
        transicion = new ArrayList<>(transiciones);
        
        for(int i = 0; i < transiciones; i++)
        {
            transicion.add(i,null);
            transicion.set(i,new Tiempos(i,intervalos[i][0],intervalos[i][1]));
        }
        marcado = marcadoInicial;
        
        actualizarExtendida();
        
        System.out.println();
        System.out.println();
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
            System.out.println("T"+i+": "+v[i] + " ");
        }        
        System.out.println("\n");
    }

    public long disparar(int t) // Proximo estado = Estado Actual + I * (sigma and Ex)
    {
        actualizarExtendida();
        if(isSensibilizada(t))
        {
            /**
             *   Como se dispara una sola transicion, se agarra directamente
             *   la columna de la matriz de incidencia correspondiente
             *   para sacar o poner tokens.
             */
            getColumna(incidencia_menos,t);
            marcado = sumar(marcado,columna); //Quita los tokens de la plaza
            getColumna(incidencia_mas,t);
            marcado = sumar(marcado,columna); //Pone los tokens en la otra plaza
            
            //actualizarSensibilizadas();
            actualizarExtendida();
            //printVector(marcado,"marcado actual");
            secuenciaDisparos.add(t);
            secuenciaAuxiliar.add(t);
//            System.out.println("Se realiz� el disparo de 'T" + t + ".");
            if(pinvariante == true) pInvariante();
            return 0;
        }
        else if(transicion.get(t).estaAntes())
        {
            /* no se pudo disparar por no llegar al alpha, debe enviarse señal para hacer sleep */
            return transicion.get(t).cuantoDormir();
        }
        else {
            /* 
             * No se pudo disparar: o porque no esta sensibilizada, o porque supero el beta. 
             * Cualquiera de los 2 sea el motivo, es indistinto. El cronometro ya se reinicio.
             */ 
            return -2;
        }
    }    

    /**
     * Getter de secuencia de disparo auxiliar para hilo de id: -1
     * @return 
     */
    public ArrayList<Integer> getSecuenciaAuxiliar() {
        return secuenciaAuxiliar;
    }
    
    /**
     * 
     * @param t
     * @return 
     */
    public boolean isSensibilizada(int t) // Pregunta si la transicion esta sensibilizada
    {
        return (vs_extendido[t] != 0);
    }    
    
    /**
     * El siguiente metodo simula un LEFTJOIN() de una columna de incidencia menos con el vector 
     * marcado
     */
    public void actualizarSensibilizadas() 
    {
        //System.out.println("Actualizando vector de sensibilizadas.");
        
        int index[] = new int[plazas];
        
        for(int i = 0; i < transiciones; i++)
        {
            int elemento = 0;
            
            for(int j = 0; j < plazas; j++)
            {
                /* toma cada fila distinta de cero en la columna 'i' de la incidencia menos */
                if(incidencia_menos[j][i] != 0)
                {
                    /* almacena que elemento 'j' es distinto de cero en esa columna */
                    index[elemento] = j;
                    /* se lleva un conteo de cuantos elementos son distintos de cero */
                    elemento++;
                }
            }
            int k = 0;
            /* si los elementos tomados son tambien elementos distintos de cero en 'marcado' */
            /* aumenta el contador */
            while(k < elemento && marcado[index[k]] > 0) k++;
            /* si hay la misma cantidad de elementos en ambos vectores entonces esta */
            /* sensibilizada */
            if(k == elemento) v_sensibilizadas[i] = 1;
            else v_sensibilizadas[i] = 0;

            /* si es temporal se le da inicio al contador */
            if(v_sensibilizadas[i] == 1 && isTemporal[i] == 1) transicion.get(i).setTS();
        }
    }
    
    public void actualizarInhibidas()
    {
        int index[] = new int[plazas];
        
        for(int i = 0; i < transiciones; i++)
        {
            int elemento = 0;
            
            for(int j = 0; j < plazas; j++)
            {
                /* toma cada fila distinta de cero en la columna 'i' de la incidencia menos */
                if(inhibicion[j][i] != 0)
                {
                    /* almacena que elemento 'j' es distinto de cero en esa columna */
                    index[elemento] = j;
                    /* se lleva un conteo de cuantos elementos son distintos de cero */
                    elemento++;
                }                
            }
            
            int k = 0;
            /* si los elementos tomados son tambien elementos iguales a cero en 'marcado' */
            /* aumenta el contador */
            while(k < elemento && marcado[index[k]] == 0) k++;
            /* si hay la misma cantidad de elementos en ambos vectores entonces esta */
            /* sensibilizada */
            if(k == elemento) vs_inhibidas[i] = 1;
            else vs_inhibidas[i] = 0;
        }
    }
    
    public void actualizarExtendida()
    {
        actualizarSensibilizadas();
        actualizarInhibidas();
        for(int i=0;i<transiciones;i++){
            vs_extendido[i] = v_sensibilizadas[i] * vs_inhibidas[i];
        }
        
        for(int i = 0; i < transiciones; i++)
        {
            if(vs_extendido[i] == 1 && isTemporal[i] ==1){
                if(!transicion.get(i).checkVentana()) vs_extendido[i] = 0;
            }
        }
    }
    
    /**
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
        int sigma[]= new int[plazas];
        
        for(int i = 0; i < plazas; i++)
        {
            sigma[i] = a[i] + b[i];
        }   
        return sigma;
    }
    
    /**
     * Carga las incidencias
     * y a su vez carga el marcado inicial.
     * @param arch - txt a leer.
     */ 
    private void cargarMatrices(Archivos arch)
    {
        for(int i = 0; i < plazas; i++)
        {
            for(int j = 0; j < transiciones; j++)
            {
                incidencia_menos[i][j] = arch.getIncidenciaMenos().get(i).get(j);
                incidencia_mas[i][j] = arch.getIncidenciaMas().get(i).get(j);
                inhibicion[i][j] = arch.getInhibidas().get(i).get(j);
            }
            marcadoInicial[i] = arch.getMarcado().get(i);
        }  
    }

    /**
     * Carga los intervalos de tiempo desde un archivo txt
     * @param arch - txt a leer
     */
    private void cargarTiempos(Archivos arch) 
    {
        for(int i = 0; i < transiciones; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                intervalos[i][j] = arch.getIntervalos().get(i).get(j);
                if(intervalos[i][1] != 0) isTemporal[i] = 1;
                else isTemporal[i] = 0;
            }
        } 
    }
    
    public int[] getMarcadoInicial()
    {
        return marcadoInicial;
    }  
    
    public int[] getSensibilizadas(){
        return v_sensibilizadas;
    }
        
    public int[] getExtendido()
    {
        actualizarExtendida();
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
    
    public boolean isTemporal(int t)
    {
        if(isTemporal[t] == 0) return false;
        else return true;
    }
    
    public int[] getMarcado(){
        return marcado;
    }

    /**
     * Verifica los invariantes de plaza.
     */
    public void pInvariante(){        
        for(int i = 0; i < pinvariantes.size(); i++){
            int suma = 0;
            for(int j = 0; j < pinvariantes.get(i).size(); j++){
                if(j < pinvariantes.get(i).size()-1){
                    int indice = pinvariantes.get(i).get(j);
                    suma += marcado[indice];
                }
                else if(j == pinvariantes.get(i).size()-1){
                    if(suma != pinvariantes.get(i).get(j)) pinvariante  = false;
                }
            }
        }        
        pinvariante = true;
    }
    
    /**
     * Verifica los invariantes
     */
    public void tInvariantes(){
        
        Iterator sec  = secuenciaAuxiliar.iterator();
        
        int encontrado;
        int cantFilas = tinvariantes.size();
        int contador = 0;
        int antes = 1,despues = 0;
        while(antes != despues){
            System.out.println("\nComienza ciclo de busqueda");
        //while(!secuenciaAuxiliar.isEmpty()){
            antes = secuenciaAuxiliar.size();
            for(int i = 0; i<cantFilas; i++){
                ArrayList<Integer> coincidencias = new ArrayList<>();
                
                int cantColumnas = tinvariantes.get(i).size();
                
                for(int j = 0; j<cantColumnas; j++){
                    
                    int buscar = tinvariantes.get(i).get(j);
                    
                    while(sec.hasNext()){
                        
                        Integer invariante = (Integer)sec.next();
                        encontrado = invariante.intValue();
                        
                        if(encontrado == buscar){
                            coincidencias.add(encontrado);
                            System.out.print("Se encontró el disparo: ");
                            System.out.print(encontrado+", ");
                            break;
                        }
                    }
                }
                /* elimina la secuencia encontrada de los disparos */
                if(coincidencias.size() == cantColumnas){
                    System.out.println("Toda la secuencia encontrada. Se removerá de Auxiliar: ");
                    System.out.println("A remover: "+coincidencias.toString()+"\n");
                    for(int k = 0;k<coincidencias.size();k++){
                        secuenciaAuxiliar.remove(coincidencias.get(k));
                    }
                    contador++;
                }
                sec = secuenciaAuxiliar.iterator();
                despues = secuenciaAuxiliar.size();
            }
            System.out.println("Auxiliar actual: "+secuenciaAuxiliar.toString());
            //System.out.println("Encontré " + contador + " t-invariantes.");
            System.out.println("De tamaño: " + secuenciaAuxiliar.size());
            System.out.println("Comienza otro ciclo de busqueda");
            System.out.println("------------------------------------------------------");
            System.out.println("------------------------------------------------------");
        }
    }
    
    /**
     * 
     * @return 
     */
    public boolean getPInvariantes()
    {
    	return pinvariante;
    }
    
    /**
     * 
     */
    public void print4testings(){
        System.out.println("#######################################################################");
//        printMatriz(incidencia_menos,"Incidencia (menos)");
//        printMatriz(incidencia_mas,"Incidencia (mas)");
//        printMatriz(inhibicion, "inhibicion");
//        printMatriz(intervalos,"Intervalos temporales");
          printVector(marcado,"marcado actual");
//        printVector(isTemporal, "transiciones con tiempo");
//        printVector(v_sensibilizadas, "sensibilizadas");
//        printVector(vs_inhibidas, "inhibicion");
        printVector(vs_extendido,"extendido");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
    
    /**
     * 
     */
    public void printSecuenciaDisparos(){
        for(int i=1;i<secuenciaDisparos.size()+1;i++)
        {
            System.out.print("T"+secuenciaDisparos.get(i-1)+"   ");
            if((i%10)==0)System.out.print("\n");
        }
        //System.out.println("\nDisparos: "+secuenciaDisparos.size());
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Integer> getSecuenciaDisparos()
    {
        return secuenciaDisparos;
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<ArrayList<Integer>> getTInvariantes(){
        
        return tinvariantes;
    }
    
    /**
     * 
     */
    public void deleteRdP(){
        RdP = null;
    }
}
