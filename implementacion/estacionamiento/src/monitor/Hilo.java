/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mr. Green
 */
public class Hilo implements Runnable {
    
    ArrayList<Integer> transiciones;
    
    public Hilo(ArrayList<Integer> lista) 
    {
        for(int i = 0; i < lista.size(); i++)
        {
            transiciones.add(lista.get(i));
        }
    }
    
    
    
    @Override
    public void run() {
        while(true)
        {
            for(int i = 0; i < transiciones.size(); i++)
            {
                //disparar(transiciones.get(i));
            }
        }
    }    
}
