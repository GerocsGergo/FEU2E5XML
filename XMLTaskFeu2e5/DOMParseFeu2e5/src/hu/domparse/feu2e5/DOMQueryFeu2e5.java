package hu.domparse.feu2e5;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMQueryFeu2e5 {

	public static void main(String[] args) {
		try {
			Document document = DOMReadFeu2e5.parseXML("XMLFeu2e5.xml");
			
			// 1. A 2016-ban vagy utána született diákok adatai
			String diakData = getDiakNamesBornAfter(document, 2016);
			System.out.println("1. lekérdezés:");
			System.out.println("A 2016-ban vagy utána született diákok adatai: " + diakData);
			
			// 2. Az aktív tagságok száma
			System.out.println("\n2. lekérdezés:");
			System.out.println("Az aktív tagok száma: " + countActiveMembers(document));
			
			// 3. A tanárok órabéreinek átlaga
			System.out.println("\n3. lekérdezés:");
			System.out.println("A tanárok átlagos órabére: " + getAvgTanarBer(document) + " Ft/óra");
			
			// 4. Az összes kiosztott jutalom típusa
			List<String> prizes = getAllPrizes(document);
			System.out.println("\n4. lekérdezés:");
			System.out.println("A kiosztott jutalmak típusai: "+prizes);
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	//Adott évben/évet követően született diákok adatai strukturáltan
		private static String getDiakNamesBornAfter(Document document, int year) {
			String output = "";
			Element root = document.getDocumentElement();
			//Ciklus az összes diákra
			NodeList diakok = root.getElementsByTagName("diák");
			for (int i = 0; i < diakok.getLength(); i++) {
				Element diak = (Element) diakok.item(i);
				Element birthDateEl = (Element) diak.getElementsByTagName("születési_dátum").item(0);
				//A születési év kivétele a diák születési dátumából
				int birthYear = Integer.parseInt(birthDateEl.getTextContent().split("-")[0].trim());
				//Ha a születési évnél nem nagyobb a paraméterként megadott év, akkor a diák adatai a kimenetbe kerülnek
				if (birthYear >= year) {
					output+=DOMReadFeu2e5.formatElement(diak, 0);
				}
			}
			return output;
		}
		
		//Aktív tagságú diákok száma
		private static int countActiveMembers(Document document) {
		    int counter = 0;
		    Element root = document.getDocumentElement();
		    // Ciklus az összes tagságra
		    NodeList memberships = root.getElementsByTagName("tagság");
		    for (int i = 0; i < memberships.getLength(); i++) {
		        Element membership = (Element) memberships.item(i);
		        // Az "aktív" elem értékének lekérése
		        NodeList activeNodes = membership.getElementsByTagName("aktív");
		        if (activeNodes.getLength() > 0) { // Ellenőrzés, hogy létezik-e az "aktív" elem
		            Element activeElement = (Element) activeNodes.item(0);
		            String activeValue = activeElement.getTextContent();
		            // Ha az "aktív" elem értéke nem "nem", a számláló növelése
		            if (!"nem".equals(activeValue)) {
		                counter++;
		            }
		        } else {
		            // Ha nincs "aktív" elem, az alapértelmezett "igen"-nek tekintjük
		            counter++;
		        }
		    }
		    return counter;
		}
		
		//A Tanárok átlagfizetése Ft/órában megadva
		private static int getAvgTanarBer(Document document) {
			int tanarCount = 0;
			int wageSum = 0;
			Element root = document.getDocumentElement();
			NodeList tanarok = root.getElementsByTagName("tanár");
			// Tanárok számának meghatározása
			tanarCount = tanarok.getLength();
			// Ciklus az összes tanárra
			for (int i = 0; i < tanarok.getLength(); i++) {
				Element tanar = (Element) tanarok.item(i);
				//Tanár órabérének hozzáadása az összeghez
				Element wage = (Element) tanar.getElementsByTagName("órabér").item(0);
				wageSum += Integer.parseInt(wage.getTextContent().trim());
			}
			//Átlag számítás, felfelé kerekítés egészre
			return (int) Math.ceil(wageSum / (1.0 * tanarCount));
		}
		
		//Az összes jutalom típus kilistázása
		private static List<String> getAllPrizes(Document document) {
			List<String> allPrizes = new ArrayList<>();
			Element root = document.getDocumentElement();
			NodeList participations = root.getElementsByTagName("részvétel");
			//Ciklus az összes részvételre
			for (int i = 0; i < participations.getLength(); i++) {
				Element participation = (Element) participations.item(i);
				//Ha létezik jutalom gyerekelem, és a tartalma még nincs a listában, akkor felvétel
				NodeList prizes = participation.getElementsByTagName("jutalom");
				if (prizes.getLength()>0) {
					Element prize = (Element) prizes.item(0);
					if(!allPrizes.contains(prize.getTextContent())) {
						allPrizes.add(prize.getTextContent());
					}
				}
			}
			return allPrizes;
		}
		


}
