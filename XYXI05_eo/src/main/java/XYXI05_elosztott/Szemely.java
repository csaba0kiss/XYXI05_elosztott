/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYXI05_elosztott;
import java.io.Serializable;

/**
 *
 * @author csaba
 */
public abstract class Szemely {
    
    private String nev;
    
    public Szemely(String neve){
        this.nev=neve;
    } 
    
    
    
    public String getnev(){
        return this.nev;
    }
    
    
	@Override
    public String toString() {
        return "Szemely{" + "nev=" + nev + "}";
    }
	
	
}
