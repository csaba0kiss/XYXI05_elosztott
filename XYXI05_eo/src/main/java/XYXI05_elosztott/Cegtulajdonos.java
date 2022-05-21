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
public class Cegtulajdonos  extends Ember implements Ceg{
    
    public Cegtulajdonos(String neve, String autotipusa, String autoszine){
       super( neve, autotipusa, autoszine); 
    }
    
    @Override
    public void autotvesz(String tipus, String szin){
        autolist.add(new Auto(tipus,szin));
    }
    
    
    @Override
    public void autotelad(Auto auto){ //objektum szerint
        autolist.remove(auto);
        
    }
    
    /*
    @Override
    public void autotelad(int index){ //index szerint
        autolist.remove(index);
    }
    */
    
    @Override
    public String vagyonjelent(){ //saját magát kell odaadja szerializáláshoz
        
        String jelentes;
        
        jelentes=szerializalas.kiir(this);
        return jelentes;
    }
    
     @Override
    public String toString() {
        return "Cegtulajdonos{" + "nev=" + this.getnev()  + "autotipus=" + this.gettipus() + ", autoszin=" + this.getszin() + ", autolist=" + this.autolist + "}";
    }
    
    
}
