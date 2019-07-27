/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;

import java.util.ArrayList;

/**
 *
 * @author root
 */
public class Executioner extends Thread {
    
    RedDePetri rdp = RedDePetri.getRdP();
    ArrayList<ArrayList<Integer>> restantes;
    ArrayList<Integer> disparos;
    int id;
    /**
     * Constructor
     */
    public Executioner(){
        id = -1;
        disparos = new ArrayList<>();
        restantes = new ArrayList<>();
        
        for(int i = 0; i < rdp.getTInvariantes().size(); i++){
            for(int j = 0; j < rdp.getTInvariantes().get(i).size(); j++){
                restantes.add(rdp.getTInvariantes().get(j));
            }
        }
    }
    
    // EL PROBLEMA ESTA ACA, CORREGIR.
    @Override
    public void run(){
        while(!rdp.getSecuenciaAuxiliar().isEmpty()){
            
            for(int i = 0; i < restantes.size(); i++){
                restantes.get(i).removeAll(rdp.getSecuenciaAuxiliar());
            }
            
            for(int i = 0; i < restantes.size(); i++){
                for(int j = 0; j < restantes.get(i).size(); j++){
                    disparos.add(restantes.get(i).get(j));
                }
            }
            System.out.println("RESTANTES: " + restantes.toString());
            Monitor.getMonitor().dispararTransicion(disparos);
            rdp.tInvariantes();
        }
    }
    
    @Override
    public long getId()
    {
        return id;
    }
}
