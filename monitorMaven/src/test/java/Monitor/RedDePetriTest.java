/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sebastian
 */
public class RedDePetriTest {
    RedDePetri rdp = RedDePetri.getRdP();
    int marcado[] = rdp.getMarcado(); 
    
    public RedDePetriTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
       
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getRdP method, of class RedDePetri.
     */
    @Test
    public void testGetRdP() {
        System.out.println("getRdP");
        RedDePetri expResult = null;
        RedDePetri result = RedDePetri.getRdP();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void pInvariantes() 
    {
    	System.out.println("--- TEST DE INVARIANTES DE PLAZA ---");
    	
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
    	
    	rdp.disparar(0);
    	rdp.disparar(0);
    	rdp.disparar(0);
    	
    	rdp.disparar(1);
    	rdp.disparar(1);
    	rdp.disparar(1);
    	
    	rdp.disparar(2);
    	rdp.disparar(2);
    	rdp.disparar(2);
    	
    	//boolean cumple;
    	
		 for(int i=0; i<pInv.length;i++)
		 {
		     int suma = 0;
		     for(int j=0;j<pInv[i].length;j++)
		     {
		         if(j<pInv[i].length-1){
		             int indice = pInv[i][j];
		             suma += marcado[indice];
		         }
		         else if(j==pInv[i].length-1)
		         {
		        	 assertFalse(suma != pInv[i][j]);
		         }
		     }
		 }
    }
    
}
