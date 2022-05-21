/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYXI05_elosztott;
import java.util.LinkedList;
import java.util.ListIterator;
import java.io.Serializable;

/**
 *
 * @author csaba
 */
public class Ember extends Szemely {
    
    private String autotipus;
    private String autoszin;
    LinkedList<Auto> autolist = new LinkedList<Auto>();
    
    public Ember(String neve, String autotipusa, String autoszine){
       super(neve);
       this.autotipus=autotipusa;
       this.autoszin=autoszine;
       autolist.add(new Auto(autotipusa,autoszine));
       
    }
    
    
    public String gettipus(){
        
        return autolist.getFirst().gettipus();
            
        //return this.autotipus;
    }
    
    public String getszin(){
        
        return autolist.getFirst().getszin();
        
        //return this.autoszin;
    }
    
    @Override
    public String toString() {
        return "Ember{" + "nev=" + this.getnev()  + "autotipus=" + this.gettipus() + ", autoszin=" + this.getszin() + ", autolist=" + autolist + "}";
    } 
    
}
