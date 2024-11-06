package domfeu2e51105;

import java.io.Console;
import java.io.File;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomModify1Feu2e5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File inputFile = new File("hallgatoFEU2E5.xml");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputFile);
			
			Node hallgatok = document.getFirstChild();
			Node hallgato = document.getElementsByTagName("hallgato").item(0);
			
			NamedNodeMap attr = hallgato.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			nodeAttr.setTextContent("01");
			
			NodeList list = hallgato.getChildNodes();
			
			for (int temp = 0; temp < list.getLength(); temp++) {
				Node node = list.item(temp);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					
					if ("keresztnev".equals(element.getNodeName())) {
						if ("Piroska".equals(element.getTextContent())) {
							element.setTextContent("olivia");
						}
					}
					
					if ("vezeteknev".equals(element.getNodeName())) {
						if ("Kiss".equals(element.getTextContent())) {
							element.setTextContent("Vigh");
						}
					}
				}
			
				
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source =  new DOMSource(document);
			
			System.out.println("---Módosított Fájl---");
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
