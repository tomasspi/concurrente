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
    ArrayList<Integer> disparos;
    int id;
    /**
     * Constructor
     */
    public Executioner(ArrayList<Integer> disparos){
        id = -1;
        this.disparos = disparos;
    }
    
    // EL PROBLEMA ESTA ACA, CORREGIR.
    @Override
    public void run(){
        while(!rdp.getSecuenciaAuxiliar().isEmpty())
        {            
            for(int i = 0; i < disparos.size(); i++){
                Monitor.getMonitor().dispararRestantes(disparos.get(i));
                rdp.tInvariantes();
            }
            System.out.println("RESTANTES: " + disparos.toString());
            //Monitor.getMonitor().dispararTransicion(disparos);
            //rdp.tInvariantes();
        }
    }
    
    @Override
    public long getId()
    {
        return id;
    }
}
