/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Archivos.Archivos;
import MaquinaDeEstado.MaqEstado;
import Monitor.Executioner;
import Monitor.Hilo;
import Monitor.Monitor;
import Monitor.RedDePetri;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author root
 */
public class MainTest {
    /* común a cualquier test */
    Archivos archivos = Archivos.getArchivos();
    
    /*variantes en cada test*/
    RedDePetri rdp;
    Monitor monitor;
    Hilo hilos[];
    
    public MainTest() {
    }
    
    //@BeforeClass
    @org.junit.BeforeClass
    public static void setUpClass() {
    }
    
    //@AfterClass
    @org.junit.AfterClass
    public static void tearDownClass() {
    }
    
    //@Before
    @org.junit.Before
    public void setUp() {
        //rdp = RedDePetri.getRdP();
    }
    
    //@After
    @org.junit.After
    public void tearDown() {
//        rdp.deleteRdP();
//        monitor = null;
//        hilos = null;
    }

    /**
     * Test of main method, of class Main.
     * @throws java.lang.Exception
     */
    @org.junit.Test
    public void MaqEstadoTest(){
        rdp = RedDePetri.getRdP();
        System.out.println("Test de sistema 'MaqEstadoTest'");
        /*Crea el gestor de monitor*/
        monitor = Monitor.getMonitor();      
        
        int cantidadHilos = archivos.getHilos().size();
        hilos = new Hilo[cantidadHilos];

        for(int i = 0; i < cantidadHilos; i++) 
        {
            hilos[i] = new Hilo(i,archivos.getHilos().get(i));
        }

        /*Comienzan los hilos*/
        for(int i = 0; i< cantidadHilos;i++) hilos[i].start();

        System.out.println("Disparando...\n");
        
        /*El hilo padre espera la finalizacion de disparos*/
        for(Thread t: hilos) try {
            t.join();
        } catch (InterruptedException ex) {
            ex.getMessage();
        }      
        System.out.println("Finalizaron disparos");
        
        System.out.println(rdp.getSecuenciaAuxiliar().toString());
        System.out.println("De forma ordenada: ");
        Collections.sort(rdp.getSecuenciaAuxiliar());
        System.out.println(rdp.getSecuenciaAuxiliar());
        System.out.println("Cantidad de disparos: "+rdp.getSecuenciaAuxiliar().size());
        System.out.println("\n\n");
        
        //MaqEstado estadoInicial = MaqEstado.E0;
        MaqEstado estadoFinal = MaqEstado.EFinal;
        //MaqEstado estado = estadoInicial;
        ArrayList<ArrayList<Integer>> secuenciaParcial = new ArrayList<>();
        ArrayList<Integer> columna;
        int secuenciaCompleta = 0;
        
        /* hasta que el arrayList de secuencia auxilar quede vacio */
        while(!rdp.getSecuenciaAuxiliar().isEmpty()){
            /* inicializa la maquina en el estado E0 */
            MaqEstado estado = MaqEstado.E0;
            /* barre secuencia de disparos usando la maquina de estados */
            System.out.println("---------------------------------------------");
            System.out.println("Inicia maquina de estados ");
            for(Integer variable : rdp.getSecuenciaAuxiliar()){
                variable = variable.intValue();
                estado = estado.siguienteEstado(variable);
            }
            System.out.println("\nFinalizo maquina de estados, registro: ");
            System.out.println(MaqEstado.getRegistro());

            /* si la maquina de estados llego al estado final, borra la secuencia */
            if(estado == estadoFinal){
                secuenciaCompleta++;
                System.out.println("\nLlegó a un estado final\n");
                System.out.println("Tamaño de secAuxiliar antes: "+rdp.getSecuenciaAuxiliar().size());
                for(Integer delete : MaqEstado.getRegistro()){
                    rdp.getSecuenciaAuxiliar().remove(delete);
                }
                System.out.println("Tamaño de secAuxiliar despues: "+rdp.getSecuenciaAuxiliar().size());
                System.out.println("---------------------------------------------");
            }
            /* si la maquina de quedó clavada, se almacena la secuencia parcial */
            else {
                System.out.println("\nNo llegó a un estado final\n");
                System.out.println("Tamaño de secAuxiliar antes: "+rdp.getSecuenciaAuxiliar().size());
                columna = new ArrayList<>();
                for(Integer delete : MaqEstado.getRegistro()){
                    columna.add(delete);
                    //secuenciaParcial.get(fila).add(delete);
                    //fila++;
                    rdp.getSecuenciaAuxiliar().remove(delete);
                }
                secuenciaParcial.add(columna);
                System.out.println("Tamaño de secAuxiliar despues: "+rdp.getSecuenciaAuxiliar().size());
                System.out.println("ArrayList de secuenciaParcial: ");
                System.out.println(secuenciaParcial);
                System.out.println("---------------------------------------------");
            }
            /* reinicia el registro de la maquina de estados */
            MaqEstado.reiniciarRegistro();
        }
        /* imprime resultados de analisis */
        System.out.println("\nFINALMENTE: ");
        System.out.println(secuenciaParcial);
        System.out.println("Cantidad de secuencias completas: "+secuenciaCompleta);
        System.out.println("Cantidad de secuencias parciales: "+secuenciaParcial.size());
        System.out.println();
        
        rdp.deleteRdP();
        monitor = null;
        hilos = null;
    }
}
