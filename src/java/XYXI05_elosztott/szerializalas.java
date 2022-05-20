/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYXI05_elosztott;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author csaba
 */
public class szerializalas {
    
    public static String kiir(Cegtulajdonos tulaj){
       String eredmeny="Hiba történt a szerializálás során";
        
        try {
           String path=tulaj.getnev();
           path=path.trim();
           path=path.replaceAll(" ", "_") + ".bin";
            ObjectOutputStream objKi = new ObjectOutputStream(new FileOutputStream( path ));
            objKi.writeObject(tulaj);
            objKi.close();
            eredmeny="Kiírás történt a következő fájlba: "+path;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(szerializalas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(szerializalas.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return eredmeny;
    }
  
    
    
     public static Cegtulajdonos beolvas(String nev){
        
        String path=nev;
        Cegtulajdonos tulaj = null;
        File f;
           
        path=path.trim();
        path=path.replaceAll(" ", "_") + ".bin";

        f= new File(path); 
        if (f.exists() && f.isFile() ){           
            try {
                ObjectInputStream objBe = new ObjectInputStream(new FileInputStream("path"));
                tulaj = (Cegtulajdonos)objBe.readObject();
                objBe.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(szerializalas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(szerializalas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(szerializalas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
        return tulaj;   
    }
    
}
