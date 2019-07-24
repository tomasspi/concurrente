package monitor;

import archivos.Archivos;
import java.util.ArrayList;

/**
 * Lógica del sistema.
 *
 * @author N.P.
 * @version 2018
 */


public class RedDePetri 
{    
    private static RedDePetri RdP = null;
    
    private int incidencia_menos[][], incidencia_mas[][], inhibicion[][]; //¡¡¡¡AL CAMBIAR MATRIZ POR INHIBICION, HAY QUE CAMBIAR LA CARGA DE ARVHICOS!!!!!!!
    private int intervalos[][], isTemporal[], isInhibida[];
    private int v_sensibilizadas[], marcadoInicial[], marcado[]; 
    private int vs_extendido[];
    private int columna[];
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> Tomas
    /*************/
    private int[][] mIncidencia;
    private int[][] mIntervalos;
    private int[][] vMarcado;
    /*************/
<<<<<<< HEAD

=======
    
    private final RealMatrix incidencia_menos, incidencia_mas, m_intervalos;
    private RealVector v_sensibilizadas, v_disparo, v_marcado; 
    private RealVector vs_temporales, vs_inhibidores, vs_extendido;
>>>>>>> Sebastian
>>>>>>> Tomas
=======
=======
    boolean cartel;
>>>>>>> Tomas
    ArrayList<Tiempos> transicion;
    ArrayList<Integer> secuenciaDisparos;

>>>>>>> Tomas
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
        System.out.println("Cargando archivos...");
        Archivos arch = new Archivos();
        
        arch.leer();
        
<<<<<<< HEAD
<<<<<<< HEAD
        System.out.println("Cargando archivos...");
        cargarArchivo2();
=======
<<<<<<< HEAD
        cargarArchivos();
>>>>>>> Tomas
=======
        plazas = arch.getFilas();
        transiciones = arch.getColumnas();
>>>>>>> Tomas
        
        inhibicion = new int[plazas][transiciones];
        incidencia_menos = new int[plazas][transiciones];
        incidencia_mas = new int[plazas][transiciones];
        intervalos = new int[transiciones][2];
        
        marcadoInicial = new int[plazas];
        marcado = new int[plazas];
        columna = new int[plazas];
        
        isTemporal = new int[transiciones];        
        isInhibida = new int[transiciones]; 
        v_sensibilizadas = new int[transiciones];
        vs_extendido = new int[transiciones];
        secuenciaDisparos = new ArrayList<>();
        
        separarIncidencia(arch);
        armarIncidenciaCombi(arch);
        
        cargarTiempos(arch);
        
        transicion = new ArrayList<>(transiciones);
        
        for(int i = 0; i < transiciones; i++)
        {
            transicion.add(i,null);
            if(isTemporal[i] == 1) transicion.set(i,new Tiempos(i,intervalos[i][0],intervalos[i][1]));
        }
        marcado = marcadoInicial;
<<<<<<< HEAD
        /*printMatriz(incidencia_menos,"Incidencia (menos)");
        printMatriz(incidencia_mas,"Incidencia (mas)");
<<<<<<< HEAD
        printMatrices();
        //printVector(v_marcado,"Marcado");
=======
        printMatriz(intervalos,"Intervalos temporales");
        printVector(marcado,"marcado inicial");
        printVector(isTemporal, "transiciones con tiempo");*/
>>>>>>> Tomas
=======

        actualizarExtendida();
        System.out.println();
        System.out.println();
>>>>>>> Tomas
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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
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
<<<<<<< HEAD
                if(e.getVariable().equals("mIncidencia")){
                    mIncidencia = new int[cantFilas][cantColumnas];
                    /* Aprovecha el parse de la matriz de incidencia para 
                    setear la cantidad de plazas y transiciones. */
                    setPlazas(cantColumnas);
                    setTransiciones(cantFilas);
                }
=======
                if(e.getVariable().equals("mIncidencia")) mIncidencia = new int[cantFilas][cantColumnas];
>>>>>>> Tomas
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
<<<<<<< HEAD
        printMatrices();  
=======
        System.out.println(Arrays.deepToString(mIncidencia));  
        System.out.println(Arrays.deepToString(mIntervalos));  
        System.out.println(Arrays.deepToString(vMarcado));  
>>>>>>> Tomas
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
<<<<<<< HEAD
        int counter = 0;
        
        /* Se arma la matriz */
=======
        int counter;
        
        /* Se arma la matriz */
        counter = 0;
>>>>>>> Tomas
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
<<<<<<< HEAD
=======
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
>>>>>>> Tomas
            }
        }
    }
    
    private void printMatrices()
    {
        System.out.println("Matriz de incidencia: "+Arrays.deepToString(mIncidencia));  
        System.out.println("Matriz de intervalos: "+Arrays.deepToString(mIntervalos));  
        System.out.println("Vector de marcadO: "+Arrays.deepToString(vMarcado)); 
    }
    
    public void testPrint(){
        System.out.println("###################Impresion de RdP###################");
        System.out.println("Matriz de incidencia: "+Arrays.deepToString(mIncidencia));  
        System.out.println("Matriz de intervalos: "+Arrays.deepToString(mIntervalos));  
        System.out.println("Vector de marcadO: "+Arrays.deepToString(vMarcado)); 
        System.out.println("Plazas: "+plazas+"\nTransiciones: "+transiciones);
        System.out.println("#####################################################");
    }
    
=======
>>>>>>> Tomas
    public void disparar(int t) // Proximo estado = Estado Actual + I * (sigma and Ex)
=======
    public boolean disparar(int t) // Proximo estado = Estado Actual + I * (sigma and Ex)
>>>>>>> Tomas
=======
    public long disparar(int t) // Proximo estado = Estado Actual + I * (sigma and Ex)
>>>>>>> Tomas
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
            verificarCartel();
            //printVector(marcado,"marcado actual");
            secuenciaDisparos.add(t);
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

    public boolean isSensibilizada(int t) // Pregunta si la transicion esta sensibilizada
    {
        return (vs_extendido[t] != 0);
    }
    
    
    
    /**
     * El siguiente metodo simula un LEFTJOIN() de una columna de incidencia menos con el vector 
     * marcado
     */
    public void actualizarSensibilizadas() //¡¡¡EL 23 DEL 7 HICE CAMBIOS ACA, REVISAR!!!!
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
                    
                    /* --- PRIMER CAMBIO --- si el elemento es inhibidor, se lo identifica */ 
                    if(inhibicion[j][i] != 0) isInhibida[i] = 1;
                    else isInhibida[i] = 0;
                }
                
                int k = 0;
                /* si los elementos tomados son tambien elementos distintos de cero en 'marcado' */
                /* aumenta el contador */
                while(k < elemento && marcado[index[k]] > 0) k++;
                /* si hay la misma cantidad de elementos en ambos vectores entonces esta */
                /* sensibilizada */
                if(k == elemento) v_sensibilizadas[i] = 1;
                else v_sensibilizadas[i] = 0;
                
                /* --- SEGUNDO CAMBIO --- si no esta sensibilizada y es inhibidor, SÍ está sensibilizada */
                if(v_sensibilizadas[i] == 0 && isInhibida[i] == 1) v_sensibilizadas[i] = 1;
                /* si es temporal se le da inicio al contador */
                if(v_sensibilizadas[i] == 1 && isTemporal[i] == 1) transicion.get(i).setTS();
            }
        }
    }
    
    public void actualizarExtendida()
    {
        actualizarSensibilizadas();
        for(int i=0;i<transiciones;i++){
            vs_extendido[i] = v_sensibilizadas[i];
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
    
<<<<<<< HEAD
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
<<<<<<< HEAD

    
    /**
     * Setea la cantidad de plazas en la variable global 'plazas'.
     * @param plazas - Cantidad de plazas.
     */
    private void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    /**
     * Setea la cantidad de transiciones en la variable global 'transiciones'.
     * @param transiciones - Cantidad de transiciones.
     */
    private void setTransiciones(int transiciones) {
        this.transiciones = transiciones;
    }
}
=======
    
=======
>>>>>>> Tomas
    /**
     * Separa la matriz de incidencia en dos (menos y mas)
     * y a su vez carga el marcado inicial.
     * @param arch - txt a leer.
     */
    private void separarIncidencia(Archivos arch) 
    {     
        for(int i = 0; i < plazas; i++)
        {
            for(int j = 0; j < transiciones; j++)
            {
                if(arch.getIncidencia().get(i).get(j) <= 0) incidencia_menos[i][j] = arch.getIncidencia().get(i).get(j);
                else if (arch.getIncidencia().get(i).get(j) >= 0) incidencia_mas[i][j] = arch.getIncidencia().get(i).get(j);
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
                if(intervalos[i][0] != 0 || intervalos[i][1] != 0) isTemporal[i] = 1;
                else isTemporal[i] = 0;
            }
        } 
    }
<<<<<<< HEAD
<<<<<<< HEAD
}
>>>>>>> Tomas
=======
    private void manejarCartel()
=======
    
    private void verificarCartel()
>>>>>>> Tomas
    {
        cartel = marcado[19] == 0 && marcado[20] == 0;
        if(cartel) System.out.println("NO HAY LUGAR.");
    }
    
    private void reset()
    {
        marcado = marcadoInicial;
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
    
    public Tiempos getTiempo(int t)
    {
        return transicion.get(t);
    }
    
    public boolean isTemporal(int t)
    {
        if(isTemporal[t] == 0) return false;
        else return true;
    }
    
    public int[] getMarcado(){
        return marcado;
    }

    private void armarIncidenciaCombi(Archivos arch){
        for(int i = 0; i < plazas; i++){
            for(int j = 0; j < transiciones; j++){
                inhibicion[i][j] = arch.getIncidencia().get(i).get(j);
            }
        }
        //printMatriz(incidencia_combi,"Incidencia combinada");
    }
    
    public void pInvariante(){
        int[][] pInv = {{0,3,3},
                        {1,4,3},
                        {10,7,1},
                        {11,8,1},
                        {12,13,3},
                        {2,5,3},
                        {25,26,1},
                        {6,9,1},
                        {14,15,16,23,2},
                        {14,17,19,21,30},
                        {15,18,20,22,30},
                        {13,14,15,17,18,21,22,23,24,25,27,28,3,4,5,6,7,8,60}};
                        
        for(int i=0; i<pInv.length;i++){
            int suma = 0;
            for(int j=0;j<pInv[i].length;j++){
                if(j<pInv[i].length-1){
                    int indice = pInv[i][j];
                    suma += marcado[indice];
                }
                else if(j==pInv[i].length-1){
                    if(suma != pInv[i][j]) pinvariante  = false;
                }
            }
        }
        
        pinvariante = true;
    }
    
    public boolean getPInvariantes()
    {
    	return pinvariante;
    }
    
    public void print4testings(){
        System.out.println("#######################################################################");
        //printMatriz(incidencia_combi,"Incidencia combinada");
        //printMatriz(incidencia_menos,"Incidencia (menos)");
        //printMatriz(incidencia_mas,"Incidencia (mas)");
        printMatriz(intervalos,"Intervalos temporales");
        printVector(marcado,"marcado actual");
        printVector(isTemporal, "transiciones con tiempo");
        printVector(v_sensibilizadas, "sensibilizadas");
        printVector(vs_extendido,"extendido");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
    
    public void printSecuenciaDisparos(){
        for(int i=1;i<secuenciaDisparos.size()+1;i++){
            System.out.print("T"+secuenciaDisparos.get(i-1)+"   ");
            if((i%22)==0)System.out.print("\n");
        }
        System.out.println("\nDisparos: "+secuenciaDisparos.size());
    }
    
    public ArrayList<Integer> getSecuenciaDisparos()
    {
        return secuenciaDisparos;
    }
}
>>>>>>> Tomas
