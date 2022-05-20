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
public class Auto {
    
    private String tipus;
    private String szin;
    
    public Auto(String tipus, String szin){
        this.tipus=tipus;
        this.szin=szin;
    }
    
    
    
    public String gettipus(){
        
        return this.tipus;
    }
    
    public String getszin(){
        
        return this.szin;
    }
	
	
	public String getadatok(){
	
		return this.tipus+"/"+this.szin;
	}	
    
	@Override
    public String toString() {
        return "Auto{" + "tipus=" + tipus + ", szin=" + szin + "}";
    }
}
