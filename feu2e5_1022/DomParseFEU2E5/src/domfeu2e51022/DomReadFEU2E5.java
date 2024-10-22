package domfeu2e51022;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadFEU2E5 {

	public static void main(String[] args) {
		try {
			File xmlFile = new File("hallgatoFEU2E5.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			
			document.getDocumentElement().normalize();
			
			System.out.println("Gyökér elem: " + document.getDocumentElement().getNodeName());
			
			NodeList nList = document.getElementsByTagName("hallgato");
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				System.out.println();
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) nNode;
					String uid = elem.getAttribute("id");
					
					Node node1 = elem.getElementsByTagName("keresztnev").item(0);
					String fname = node1.getTextContent();
					
					Node node2 = elem.getElementsByTagName("vezeteknev").item(0);
					String lname = node2.getTextContent();
					
					Node node3 = elem.getElementsByTagName("foglalkozas").item(0);
					String pname = node3.getTextContent();
					
					System.out.printf("User id: %s%n", uid);
					System.out.printf("vezeteknev: %s%n", fname);
					System.out.printf("keresztnev: %s%n", lname);
					System.out.printf("foglalkozas: %s%n", pname);
					
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
