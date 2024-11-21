package hu.domparse.feu2e5;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMModifyFeu2e5 {

	public static void main(String[] args) {
		try {
			Document document = DOMReadFeu2e5.parseXML("XMLFeu2e5.xml");
			
			//1. A tanterem 2. helyszín átírása tanterem 102.-re
			System.out.println("1. módosítás:");
			modifyOraHely(document, "tanterem 2.", "tanterem 102.");
			
			//2. A Kiss Attila nevű diák tagságának passzívra állítása.
			System.out.println("\n2. módosítás");
			setToPassive(document, "Kiss Attila");
			
			//3. Kiss Béla átírása Kiss Andrásra és születési évének átírása 1980-ra
			System.out.println("\n3. módosítás");
			modifyOwnerNameAndBirth(document, "Kiss Béla", "Kiss Andrásra", "1980");
			
			//4. A 2. tanár 1. email címének törlése.
			System.out.println("\n4. módosítás");
			deleteTanarEmail(document, 2, 1);
			
			
			System.out.println("A módosított dokumentum:");
			System.out.println(DOMReadFeu2e5.formatXML(document));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	//Egy adott sorszámú tanár adott sorszámú email címének törlése, ha létezik
		private static void deleteTanarEmail(Document document, int tanarNum, int emailNum) {
			//Az adott sorszámú tanár megkeresése
			Element root = document.getDocumentElement();
			NodeList tanarok = root.getElementsByTagName("tanár");
			if(tanarok.getLength()>= tanarNum) {
				Element tanar = (Element)tanarok.item(tanarNum-1);
				//Tanár kiírása változtatás előtt
				System.out.println("\nELŐTTE:"+DOMReadFeu2e5.formatElement(tanar, 0));
				//Adott sorszámú email megkeresése
				Element contact = (Element)tanar.getElementsByTagName("elérhetőség").item(0);
				NodeList emails = contact.getElementsByTagName("email");
				if(emails.getLength()>= emailNum) {
					Element email = (Element)emails.item(emailNum-1);
					//Email törlése
					contact.insertBefore(document.createComment("Törölt email helye"), email);
					contact.removeChild(email);
				}
				//Tanár kiírása változtatás után
				System.out.println("\nUTÁNA:"+DOMReadFeu2e5.formatElement(tanar, 0));
			}
		}
	
	//Egy adott nevű szülő nevének és születési évének megváltoztatása
		private static void modifyOwnerNameAndBirth(Document document, String oldName, String newName, String newBirthYear) {
			//Végigmegyünk az összes szülőn, akinek a neve oldName, azt newName-re változtatjuk
			Element root = document.getDocumentElement();
			NodeList szulok = root.getElementsByTagName("szülő");
			for(int i=0; i<szulok.getLength(); i++) {
				Element szulo = (Element)szulok.item(i);
				Element name = (Element)szulo.getElementsByTagName("név").item(0);
				if(name.getTextContent().equals(oldName)) {
					//A név és a születési év átírása és előtte-utána adatok kiírása
					System.out.println("\nELŐTTE:"+DOMReadFeu2e5.formatElement(szulo, 0));
					szulo.insertBefore(document.createComment("Átírt név és születési év"), name);
					name.setTextContent(newName);
					Element birthYear = (Element)szulo.getElementsByTagName("születési_év").item(0);
					birthYear.setTextContent(newBirthYear);
					System.out.println("\nUTÁNA:"+DOMReadFeu2e5.formatElement(szulo, 0));
				}
			}
		}
	
	// Egy adott nevű diák státuszának passziválása
	private static void setToPassive(Document document, String diakName) {
	    // A diakName nevű diák tagságának kódját lekérjük
	    String membershipId = "";
	    Element root = document.getDocumentElement();
	    NodeList diakok = root.getElementsByTagName("diák");
	    for (int i = 0; i < diakok.getLength(); i++) {
	        Element diak = (Element) diakok.item(i);
	        Element name = (Element) diak.getElementsByTagName("név").item(0);
	        if (name.getTextContent().equals(diakName)) {
	            membershipId = diak.getAttribute("tagság");
	        }
	    }

	    // Az adott kódú tagságot passziváljuk és kiírjuk az előtte-utána összehasonlítást
	    NodeList memberships = root.getElementsByTagName("tagság");
	    for (int i = 0; i < memberships.getLength(); i++) {
	        Element membership = (Element) memberships.item(i);
	        if (membership.getAttribute("Tagkód").equals(membershipId)) {
	            // Előtt
	            System.out.println("\nELŐTTE: " + DOMReadFeu2e5.formatElement(membership, 0));
	            
	            // Keresd meg az "aktív" gyerekelemet
	            NodeList aktívNodes = membership.getElementsByTagName("aktív");
	            if (aktívNodes.getLength() > 0) {
	                Element aktív = (Element) aktívNodes.item(0);
	                // Módosítsuk a gyerek elem szövegét
	                aktív.setTextContent("nem");
	            } else {
	                // Ha nincs aktív gyerek elem, hozd létre
	                Element aktív = document.createElement("aktív");
	                aktív.setTextContent("nem");
	                membership.appendChild(aktív);
	            }

	            membership.appendChild(document.createComment("Passzivált Diák"));
	            System.out.println("\nUTÁNA: " + DOMReadFeu2e5.formatElement(membership, 0));
	        }
	    }
	}

	
	//Egy óra helyszín nevének átírása minden előfordulásnál
		private static void modifyOraHely(Document document, String oldPlaceName, String newPlaceName) {
			//Az összes adott nevű helyszínnel rendelkező óra megkeresése
			Element root = document.getDocumentElement();
			NodeList orak = root.getElementsByTagName("óra");
			System.out.println("\nELŐTTE:");
			for(int i=0; i<orak.getLength(); i++) {
				Element ora = (Element)orak.item(i);
				//Eredeti órák kiírása
				System.out.println(DOMReadFeu2e5.formatElement(ora, 0).stripLeading());
				Element place = (Element)ora.getElementsByTagName("helyszín").item(0);
				if(place.getTextContent().equals(oldPlaceName)) {
					//A helyszínek átírása
					place.setTextContent(newPlaceName);
					ora.insertBefore(document.createComment("Átírt helyszín"), place);
				}
			}
			//A módosítás hatásának kiírása
			System.out.println("\nUTÁNA:");
			for(int i=0; i<orak.getLength(); i++) {
				Element ora = (Element)orak.item(i);
				System.out.println(DOMReadFeu2e5.formatElement(ora, 0).stripLeading());
			}
			
		}

}
