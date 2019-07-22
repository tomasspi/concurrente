/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Mr. Green
 */
public class RedDePetriTest {
    
    RedDePetri rdp = RedDePetri.getRdP();
    
    int Invariantes[] = { 3, 3, 3, 3, 1, 1, 1, 1, 2, 30, 30, 60 };
    int plazas[][] = 
    {   {0, 3}, 
        {1, 4}, 
        {1, 5}, 
        {12, 13}, 
        {6, 9}, 
        {7, 10}, 
        {8, 11}, 
        {25, 26}, 
        {14, 15, 16, 23}, 
        {14, 17, 19, 21}, 
        {15, 18, 20, 22}, 
        {13, 14, 15, 17, 18, 21, 22, 23, 24, 25, 27, 28, 3, 4, 5, 6, 7, 8},
    };

    /**
     * Verifica los invariantes de plazas de la red.
     */
    @Test
    public void pInvariantes() 
    {
        System.out.println("--- TEST DE INVARIANTES DE PLAZAS ---");

        int marcado[] = rdp.getMarcado();
        int result[] = new int[13];
        
        for(int i = 0; i < Invariantes.length; i++)
        {
            for(int j = 0; j < plazas.length; j++)
            {
                for(int k = 0; j < plazas[0].length; k++)
                {
                    result[i] += marcado[plazas[j][k]];
                }
            }
        }
        
        assertEquals(Invariantes, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
