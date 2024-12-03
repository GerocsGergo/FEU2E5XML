package hu.domparse.feu2e5;

import java.io.File;
import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DOMWriteFeu2e5 {

	public static void main(String[] args) {
		 try {
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document document = builder.newDocument();
	            
	            // iskola DOM fa felépítése
	            createIskola(document);
	            
	            // Kiírás konzolra és fájlba a DomRead osztályt felhasználva
	            File newXmlFile = new File("XMLFeu2e52.xml");
	            StreamResult xmlToWrite = new StreamResult(newXmlFile);
	            DOMReadFeu2e5.writeDocument(document, xmlToWrite);
	            StreamResult console = new StreamResult(System.out);
	            System.out.println("A felépített dokumentum:\n");
	            DOMReadFeu2e5.writeDocument(document, console);
	            
	        } catch (ParserConfigurationException | TransformerException | DOMException | ParseException e) {
	            e.printStackTrace();
	        }

	}

	private static void createIskola(Document document) throws DOMException, ParseException {
		Element root = document.createElement("iskola");
        document.appendChild(root);
        
        //Diákok felvétele
        root.appendChild(document.createComment("Diákok"));
        root.appendChild(createDiak(document, "10", "100", "1000", "Kiss Attila", "90", "2016-12-17", "fiú"));
        root.appendChild(createDiak(document, "11", "110", "1001", "Nagy Mária", "65", "2016-10-15", "lány"));
        root.appendChild(createDiak(document, "12", "120", "1002", "Szép Tamás", "110", "2016-06-06", "fiú"));
        root.appendChild(createDiak(document, "13", "120", "1003", "Szép Anita", "115", "2016-01-05", "lány"));
        
        //Tagság felvétele
        root.appendChild(document.createComment("tagság adatok"));
        root.appendChild(createTagsag(document, "1000", "2016-09-01", "normál", "igen"));
        root.appendChild(createTagsag(document, "1001", "2015-09-01", "kedvezményes", "igen"));
        root.appendChild(createTagsag(document, "1002", "2013-09-01", "normál", "igen"));
        root.appendChild(createTagsag(document, "1003", "2013-09-01", "kedvezményes", "nem"));
        
        
        //Szülők felvétele
        root.appendChild(document.createComment("szülők"));
        root.appendChild(createSzulo(document, "100", "Kiss Béla", "1985", "+36506060768", new String[]{"belakiss@gmail.com", "kissbela@citromail.hu"}));
        root.appendChild(createSzulo(document, "110", "Nagy Adrienn", "1991", "+36701234987", new String[]{"n.adrienn@gmail.com"}));
        root.appendChild(createSzulo(document, "120", "Szép János", "1990", "+36208877665", new String[]{"szepjancsi@szepmail.hu", "jancsi.szep@gmail.com", "jancsi.szep@freemail.com"}));
        
        
        //Órák felvétele
        root.appendChild(document.createComment("Órák"));
        root.appendChild(createOra(document, "1200", "1100", new String[]{"Matematika alapjai"}, "tanterem 101.", "2024-10-30", "13", "14"));
        root.appendChild(createOra(document, "1201", "1100", new String[]{"osztás", "szorzás"}, "tanterem 2.", "2024-10-15", "9", "10"));
        root.appendChild(createOra(document, "1202", "1101", new String[]{"Kidobós", "cooper teszt"}, "tornaterem", "2024-09-02", "12", "13"));
        root.appendChild(createOra(document, "1203", "1102", new String[]{"fogócska"}, "udvar", "2024-11-25", "11", "12"));
        
        
        //Részvételek felvétele
        root.appendChild(document.createComment("részvételi adatok"));
        root.appendChild(createReszvetel(document, "10", "1200", "3", null));
        root.appendChild(createReszvetel(document, "10", "1201", "4", "pirospont"));
        root.appendChild(createReszvetel(document, "11", "1201", "5", "matrica"));
        root.appendChild(createReszvetel(document, "12", "1200", "1", "feketepont"));
        root.appendChild(createReszvetel(document, "12", "1202", "3", null));
        root.appendChild(createReszvetel(document, "13", "1203", "5", "pirospont"));
        
        
        //Tanárok felvétele
        root.appendChild(document.createComment("tanárok"));
        root.appendChild(createTanar(document, "1100", "Takács Péter", "Matematika", "5000", "+36702525625", new String[]{"t.p@gmail.com"}));
        root.appendChild(createTanar(document, "1101", "Kovács Zoltán", "Testnevelés", "2500", "+36301233456", new String[]{"kovizol@gmail.com", "kovacs.z@freemail.hu"}));
        root.appendChild(createTanar(document, "1102", "Lédig Anna", "Viselkedés Pszichológia", "9500", "+36708996574", new String[]{"ledig@gmail.com"}));
       
        
	}
	
	//Új diak elem készítése
	 private static Element createDiak(Document document, String diakId, String szuloId, String tagsag, String nev, String IQ, String szuldatum, String nem) throws ParseException {
	        Element diakElement = document.createElement("diák");
	        diakElement.setAttribute("Dkód", diakId);
	        diakElement.setAttribute("szülő", szuloId);
	        diakElement.setAttribute("tagság", tagsag);

	        diakElement.appendChild(createTextElement(document, "név", nev));
	        diakElement.appendChild(createTextElement(document, "IQ", IQ));
	        diakElement.appendChild(createTextElement(document, "születési_dátum", szuldatum));
	        diakElement.appendChild(createTextElement(document, "nem", nem));

	        return diakElement;
	    }
	 
	 
	//Új tagsag elem készítése
	 private static Element createTagsag(Document document, String tagId, String Aktiv, String elsonap, String kedvezmeny) throws ParseException {
	        Element tagsagElement = document.createElement("tagság");
	        tagsagElement.setAttribute("Tagkód", tagId);

	        tagsagElement.appendChild(createTextElement(document, "kezdőnap", elsonap));
	        tagsagElement.appendChild(createTextElement(document, "kedvezmény", kedvezmeny));
	        tagsagElement.appendChild(createTextElement(document, "aktív", Aktiv));

	        return tagsagElement;
	    }
	
	//Új szülö elem készítése
	    private static Element createSzulo(Document document, String szuloId, String nev, String szulev, String telefon, String[] emailek) {
	        Element szuloElement = document.createElement("szülő");
	        szuloElement.setAttribute("Szkód", szuloId);
	                
	        szuloElement.appendChild(createTextElement(document, "név", nev));
	        szuloElement.appendChild(createTextElement(document, "születési_év", szulev));
	        szuloElement.appendChild(createContact(document, telefon, emailek));

	        return szuloElement;
	    }
	    
	    
	  //Új óra elem készítése
	    private static Element createOra(Document document, String oraId, String tanarId, String[] temak, String hely, String datum, String kezdet, String veg) {
	        Element oraElement = document.createElement("óra");
	        oraElement.setAttribute("Ókód", oraId);
	        oraElement.setAttribute("tanár", tanarId);

	        for (String t : temak) {
	            oraElement.appendChild(createTextElement(document, "téma", t.trim()));
	        }
	        oraElement.appendChild(createTextElement(document, "helyszín", hely));
	        Element idopontElement = document.createElement("időpont");
	        idopontElement.appendChild(createTextElement(document, "dátum", datum));
	        idopontElement.appendChild(createTextElement(document, "kezdet", kezdet));
	        idopontElement.appendChild(createTextElement(document, "vég", veg));
	        oraElement.appendChild(idopontElement);

	        return oraElement;
	    }
	    
	  //Új részvétel elem készítése
	    private static Element createReszvetel(Document document, String diakId, String tanarId, String értékelés, String jutalom) {
	        Element reszvetelElement = document.createElement("részvétel");
	        reszvetelElement.setAttribute("diák", diakId);
	        reszvetelElement.setAttribute("óra", tanarId);

	        reszvetelElement.appendChild(createTextElement(document, "értékelés", értékelés));
	        if (jutalom != null) {
	            reszvetelElement.appendChild(createTextElement(document, "jutalom", jutalom));
	        }

	        return reszvetelElement;
	    }
	    
	    //Új tanár elem készítése
	    private static Element createTanar(Document document, String tanarId, String nev,  String szak, String ber, String telefon, String emailek[]) {
	        Element tanarElement = document.createElement("tanár");
	        tanarElement.setAttribute("Tankód", tanarId);

	        tanarElement.appendChild(createTextElement(document, "név", nev));
	        tanarElement.appendChild(createContact(document, telefon, emailek));
	        tanarElement.appendChild(createTextElement(document, "szakterület", szak));	    
	        tanarElement.appendChild(createTextElement(document, "órabér", ber));

	        return tanarElement;
	    }
	    
	  //Új elérhetőség elem készítése
	    private static Element createContact(Document document, String telefon, String[] emailek) {
	        Element contactElement = document.createElement("elérhetőség");

	        for (String email : emailek) {
	            contactElement.appendChild(createTextElement(document, "email", email));
	        }
	        contactElement.appendChild(createTextElement(document, "telefon", telefon));

	        return contactElement;
	    }
	    
	    //Új szöveges elem készítése
	    private static Element createTextElement(Document document, String tagName, String textContent) {
	        Element element = document.createElement(tagName);
	        element.appendChild(document.createTextNode(textContent));
	        return element;
	    }
	}
	
