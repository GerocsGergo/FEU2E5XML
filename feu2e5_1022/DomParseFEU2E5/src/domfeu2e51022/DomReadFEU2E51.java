package domfeu2e51022;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadFEU2E51 {
		
		public static void main(String[] args) {
			try {
				File xmlFile = new File("orarendFEU2E5.xml");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(xmlFile);
				
				document.getDocumentElement().normalize();
				
				System.out.println("Gyökér elem: " + document.getDocumentElement().getNodeName());
				
				NodeList nList = document.getElementsByTagName("ora");
				
				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					System.out.println();
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element elem = (Element) nNode;
						String oid = elem.getAttribute("id");
						
						Node node1 = elem.getElementsByTagName("targy").item(0);
						String targy = node1.getTextContent();
						
						Node node2 = elem.getElementsByTagName("idopont").item(0);
						String idopont = node2.getTextContent();
						
						Node node3 = elem.getElementsByTagName("helyszin").item(0);
						String helyszin = node3.getTextContent();
						
						Node node4 = elem.getElementsByTagName("oktato").item(0);
						String oktato = node4.getTextContent();
						
						Node node5 = elem.getElementsByTagName("szak").item(0);
						String szak = node5.getTextContent();
						
						System.out.printf("Ora id: %s%n", oid);
						System.out.printf("Targy: %s%n", targy);
						System.out.printf("Idopont: %s%n", idopont);
						System.out.printf("Helyszin: %s%n", helyszin);
						System.out.printf("Oktato: %s%n", oktato);
						System.out.printf("Szak: %s%n", szak);
						
					}
				}
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
}
	


