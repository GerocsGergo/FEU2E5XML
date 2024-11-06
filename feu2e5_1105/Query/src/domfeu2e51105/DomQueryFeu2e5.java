package domfeu2e51105;

import java.io.File;

import javax.lang.model.element.ExecutableElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomQueryFeu2e5 {

	public static void main(String[] args) {
		
		try {
				
			File inputFile = new File("hallgatoFEU2E5.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(inputFile);
			
			doc.getDocumentElement().normalize();
			
			System.out.println("Gyökér elem: ");
			
			System.out.println(doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("hallgato");
			System.out.println("---------------");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nAktuális elem: ");
				System.out.println(nNode.getNodeName());
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element eElement = (Element) nNode;
					System.out.println(eElement.getAttribute("hallgato"));
					
					NodeList hallgatoNevLista =  eElement.getElementsByTagName("vezeteknev");
					
					for (int count = 0; count < hallgatoNevLista.getLength(); count++) {
						
						Node node1 = hallgatoNevLista.item(count);
						
						if (node1.getNodeType() == node1.ELEMENT_NODE) {
							
							Element hallgat = (Element) node1;
							System.out.println("vezeteknev: ");
							System.out.println(hallgat.getTextContent());
							
						}
					}
				}
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
