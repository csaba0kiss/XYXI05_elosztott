/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYXI05_elosztott;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author csaba
 */

public class NewServlet1 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    public LinkedList<Ember> emberek = new LinkedList<Ember>();
    public LinkedList<Cegtulajdonos> cegtulajdonosok = new LinkedList<Cegtulajdonos>();
    public ListIterator lstemberek = emberek.listIterator();
    public ListIterator lstcegtulajdonosok = cegtulajdonosok.listIterator();
	public int emberekindex= -1;
	public int cegtulajdonosokindex= -1;
 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //$Post feldolgozása

		
		
			if (request.getParameter("kezdes") != null) {
				//beléptünk a szervletbe	
			}
			
			//felveszünk egy új embert
			if (request.getParameter("ujember") != null) {

				if( Objects.equals(request.getParameterValues("ceg"),"1") ){ //cégtulajdonos létrehozása
					if(cegtulajdonosokindex== (-1)){cegtulajdonosokindex=0;}
					
					//itt még vizsgálni kell, hogy van-e bin fájl a névhez!!!-akkor nem új objektum lesz, hanem unszerializált
					//Cegtulajdonos beolvas(String nev)
					String leendocegtulajdonosneve = request.getParameter("nev");
					Cegtulajdonos megtalaltcegtulajdonos = szerializalas.beolvas(leendocegtulajdonosneve);
					
					if(megtalaltcegtulajdonos !=null ){ //volt .bin fájl, csinált belőle objektumot
						lstcegtulajdonosok.add(megtalaltcegtulajdonos);
						//és ezzel együtt eldobjuk az összes többi beállított paramétert (kocsi)
					}
					else{ //nem volt bin fájl és nem jött létre belőle objektum, nem találtuk meg a cégtulajdonost
													//Cegtulajdonos(String neve, String autotipusa, String autoszine)
						lstcegtulajdonosok.add( new Cegtulajdonos( leendocegtulajdonosneve, request.getParameter("automarka"), request.getParameter("autoszine")) );
					}	
					//!!! ugyanazzal a névvel fel lehet venni két(több) cégtulajdonost is, amennyiben valamelyikre még nincs serializálás .bin fájlba,
					//mivel csak a bin fájlok ellenőrzése által kerül vizsgálatra a leendő példány.
					//a valóságban lehetséges két azonos nevű cégtulajdonos, de itt a vagyonjelentés csak az utolsó jelentőhöz tartozó állapotot tárolja el (azonos név mellett)
					//így ha törlésre kerülnek, akkor közülük az első kapja meg a vagyont, ami lehet h a másiké.
					//valamint, ha újabb cégtulajdonos jön azonos névvel, akkor a .bin-ből megkapja a vagyont automatikusan...
					//ezzel nem foglalkozom többet, mivel a feladatmegoldás elsősorban az infrastruktúra használatára vonatkozik, nem a logikai pontosságra...

				} 
				else{ //ember létrehozása
					if(emberekindex==(-1)){emberekindex=0;}
					//Ember(String neve, String autotipusa, String autoszine)
					lstemberek.add( new Ember(request.getParameter("nev"),request.getParameter("automarka"),request.getParameter("autoszine")) );
					
				}
			}
			
			//emberek listaléptetése vissza
			if (request.getParameter("emberlistavissza") != null) {
				if(lstemberek.hasPrevious()){
					emberekindex=emberek.indexOf(lstemberek.previous());
				}	
			}
			
			//emberek lista léptetése előre
			if (request.getParameter("emberlistaelore") != null) {
				if(lstemberek.hasNext()){
					emberekindex=emberek.indexOf(lstemberek.next());
				}				
			}
			
			//cégtulajdonosok léptetése vissza
			if (request.getParameter("cegtulajlistavissza") != null) {
				if(lstcegtulajdonosok.hasPrevious()){
					cegtulajdonosokindex=cegtulajdonosok.indexOf(lstcegtulajdonosok.previous());
				}						
			}
			
			//cégtulajdonosok léptetése előre
			if (request.getParameter("cegtulajlistaelore") != null) {
				if(lstcegtulajdonosok.hasNext()){
					cegtulajdonosokindex=cegtulajdonosok.indexOf(lstcegtulajdonosok.next());
				}							
			}
			
			
			//cégtulajdonos új autót vesz
			if (request.getParameter("ujauto") != null) {
				cegtulajdonosok.get(cegtulajdonosokindex).autotvesz( request.getParameter("vasaroltautomarka"), request.getParameter("vasaroltautoszine") );
			}		
			
			//cégtulajdonos eladja az autót
			if (request.getParameter("eladas") != null) {
				cegtulajdonosok.get(cegtulajdonosokindex).autolist.remove(  Integer.parseInt(  request.getParameter("eladottauto") )   );	
			}		
			
			//cégtulajdonos vagyont jelent
			if (request.getParameter("vagyonjelentes") != null) {
				szerializalas.kiir( cegtulajdonosok.get(cegtulajdonosokindex) );	
			}		


			//cégtulajdonos törlésre kerül
			if (request.getParameter("cegtulajtorol") != null) {
				cegtulajdonosok.remove(cegtulajdonosokindex);
			}
			
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//új űrlap kiírása adatokkal együtt
		
		response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
            
				//a kód kizárólag a jobb olvashatóság miatt ilyen kialakítású

				//nyitó
				out.print("<!DOCTYPE html>");
				out.print("<html>");
				out.print("    <head>");
				out.print("        <title>XYXI05_13.feladat</title>");
				out.print("        <meta charset=\"UTF-8\">");
				out.print("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
				out.print("    </head>");
				out.print("    <body>");


				//emberek felvétele (mindig lehetséges)
				out.print("		<form action=\"NewServlet1\" method=\"post\">");
				out.print("        <hr>");
				out.print("		Emberek felvétele<br><br> ");
				out.print("        <table border=\"0\">");
				out.print("            <thead>");
				out.print("                <tr>");
				out.print("                    <th></th>");
				out.print("                   <th></th>");
				out.print("                </tr>");
				out.print("            </thead>");
				out.print("            <tbody>");
				out.print("                <tr>");
				out.print("                    <td>Név</td>");
				out.print("                   <td><input required type=\"text\" id=\"nev\"></td>");
				out.print("                </tr>");
				out.print("                <tr>");
				out.print("                    <td>Autó színe</td>");
				out.print("                    <td><select name=\"autoszine\" size=\"1\">");
				out.print("	<option value=\"Fehér\" selected=\"selected\" >Fehér</option>");
				out.print("	<option value=\"Fekete\">Fekete</option>");
				out.print("        <option value=\"Piros\">Piros</option>");
				out.print("        </select></td>");
				out.print("                </tr>");
				out.print("                <tr>");
				out.print("                   <td>Autómárka</td>");
				out.print("                    <td><select name=\"automarka\" size=\"1\">");
				out.print("                        <option value=\"VW\" selected=\"selected\" >VW</option>");
				out.print("                       <option value=\"Ford\">Ford</option>");
				out.print("                        <option value=\"Ferrari\">Ferrari</option>");
				out.print("                        </select></td>");
				out.print("                </tr>");
				out.print("                <tr>");
				out.print("                    <td>Cégtulajdonos</td>");
				out.print("                    <td><input type=\"checkbox\" id=\"ceg\" name=\"ceg\" value=\"1\"></td>");
				out.print("                </tr>");
				out.print("            </tbody>");
				out.print("        </table>");
				out.print("        <br>");
				out.print("        <br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;");
				out.print("        <input type=\"submit\" id=\"ujember\" value=\"Új ember felvétele\">");
				out.print("		</form>");


				//emberek adatai
					if (emberek.size()>0){ 
						
						
						out.print("		<form action=\"NewServlet1\" method=\"post\">");
						out.print("        <hr>");
						out.print("        <br>");
						out.print("        <input type=\"submit\" id=\"emberlistavissza\" value=\"<\">&emsp;&emsp;Emberek adatai&emsp;&emsp;<input type=\"submit\" id=\"emberlistaelore\" value=\">\">");
						out.print("        <br><br>  <table border=\"0\">");
						out.print("            <thead>");
						out.print("                <tr>");
						out.print("                    <th></th>");
						out.print("                    <th></th>");
						out.print("                </tr>");
						out.print("            </thead>");
						out.print("            <tbody>");
						out.print("                <tr>");
						out.print("                    <td>Név:</td>");
						out.print("                    <td>"+ emberek.get(emberekindex).getnev() +"</td>");
						out.print("                </tr>");
						out.print("                <tr>");
						out.print("                    <td>Autó színe:</td>");
						out.print("                    <td>"+ emberek.get(emberekindex).getszin() +"</td>");
						out.print("                </tr>");
						out.print("                <tr>");
						out.print("                    <td>Autómárka:</td>");
						out.print("                    <td>"+ emberek.get(emberekindex).gettipus() +"</td>");
						out.print("                </tr>");
						out.print("            </tbody>");
						out.print("        </table>");
						out.print("        <br>");
						out.print("        <hr>");
						out.print("		</form>");
					}


				//cégtulajdonosok adatai
					if (cegtulajdonosok.size()>0){ 
						
					
						out.print("		<form action=\"NewServlet1\" method=\"post\">");
						out.print("        <br>");
						out.print("        <input type=\"submit\" id=\"cegtulajlistavissza\" value=\"<\">&emsp;&emsp;Cégtulajdonosok adatai&emsp;&emsp;<input type=\"submit\" id=\"cegtulajlistaelore\" value=\">\">");
						out.print("        <br><br>");
						out.print("        <br> <table border=\"0\">");
						out.print("            <thead>");
						out.print("                <tr>");
						out.print("                    <th></th>");
						out.print("                    <th></th>");
						out.print("                </tr>");
						out.print("            </thead>");
						out.print("            <tbody>");
						out.print("                <tr>");
						out.print("                    <td>Név: </td>");
						out.print("                    <td>"+ cegtulajdonosok.get(cegtulajdonosokindex).getnev() +"</td>");
						out.print("                </tr>");
						out.print("                <tr>");
						out.print("                    <td>Saját Autó színe:</td>");
						out.print("                    <td>"+ cegtulajdonosok.get(cegtulajdonosokindex).getszin() +"</td>");
						out.print("                </tr>");
						out.print("                <tr>");
						out.print("                    <td>Saját Autó márkája:</td>");
						out.print("                    <td>"+ cegtulajdonosok.get(cegtulajdonosokindex).gettipus() +"</td>");
						out.print("                </tr>");
						out.print("            </tbody>");
						out.print("        </table>");
						out.print("        (a cégtulajdonos is ember, és: 1. Minden ember birtokol egy autót)");
						out.print("        <br>");
						out.print("        <br>");
						out.print("        <br>");
						out.print("        <br>");
						out.print("		</form>");


						out.print("		<form action=\"NewServlet1\" method=\"post\">");
						out.print("        <b>Vásárolhat autót</b><br>");
						out.print("        <br> ");
						out.print("        &emsp;Autó színe&emsp; <select name=\"vasaroltautoszine\" size=\"1\">");
						out.print("	      <option value=\"Fehér\" selected=\"selected\" >Fehér</option>");
						out.print("	      <option value=\"Fekete\">Fekete</option>");
						out.print("        <option value=\"Piros\">Piros</option>");
						out.print("        </select>");
						out.print("        <br>");
						out.print("        &emsp;Autómárka &emsp;<select name=\"vasaroltautomarka\" size=\"1\">");
						out.print("	      <option value=\"VW\" selected=\"selected\" >VW</option>");
						out.print("	      <option value=\"Ford\">Ford</option>");
						out.print("        <option value=\"Ferrari\">Ferrari</option>");
						out.print("        </select>");
						out.print("        <br>");
						out.print("        &emsp;<input type=\"submit\" id=\"ujauto\" value=\"Autó vásárlása\">");
						out.print("		</form>");


						out.print("		<form action=\"NewServlet1\" method=\"post\">");
						out.print("        <b>Eladhat autót</b>  (de 1 autóval mindig rendelkeznie kell)<br>");
						out.print("        &emsp;");
						out.print("        <select name=\"eladottauto\" size=\"1\">");							
							
							//lstcegtulajdonosok.get(cegtulajdonosokindex).autolist...
							for(int i=0; i< cegtulajdonosok.get(cegtulajdonosokindex).autolist.size(); i++){
								out.print("	         <option value=\""+ Integer.toString(i) +"\">" + Integer.toString(i)+"/" + cegtulajdonosok.get(cegtulajdonosokindex).autolist.get(i).getadatok() + "</option>");
							}	

						//out.print("	         <option value="0" selected="selected" >Fehér</option>");
						//out.print("	         <option value="1">Fekete</option>");
						//out.print("            <option value="2">Piros</option>");
						
						out.print("        </select><br>");
						out.print("        &emsp;");
						out.print("        <input type=\"submit\" id=\"eladas\" value=\"Autó eladása\">");
						out.print("        <br><br>");
						out.print("		</form>");


						out.print("		<form action=\"NewServlet1\" method=\"post\">");
						out.print("        <input type=\"submit\" id=\"vagyonjelentes\" value=\"Vagyon jelentése\">");
						out.print("        <br><br>");
						out.print("        <input type=\"submit\" id=\"cegtulajtorol\" value=\"Cégtulajdonos törlése\">");
						out.print("        <br><br>");
						out.print("		</form>");
					}

				//záró
				out.print("    </body>");
				out.print("</html>"); //	
			
        }
 
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
		
    }


 
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
