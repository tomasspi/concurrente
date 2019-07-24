package monitor;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sun.applet.Main;

class RedDePetriTest {

	RedDePetri rdp = RedDePetri.getRdP();
	
	int marcado[] = rdp.getMarcado(); 

    /**
     * Verifica los invariantes de plazas de la red.
     */
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
