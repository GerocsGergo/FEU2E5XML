package domfeu2e51105;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomModify2Feu2e5 {

	public static void main(String[] args) {
			
		try {
			File inputFile = new File("orarendFEU2E5.xml");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputFile);
			
			Node orarend = document.getFirstChild();
			Node ora = document.getElementsByTagName("ora").item(0);
			
			NamedNodeMap attr = ora.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			nodeAttr.setTextContent("01");
			
			
			
			Node oraado = createUserElement(document, "óraadó", "Nagy Károly");
			ora.appendChild(oraado);
			
			
			NodeList list = orarend.getChildNodes();
			
			for (int temp = 0; temp < list.getLength(); temp++) {
				Node node = list.item(temp);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					element.setAttribute("tipus", "eloadas");
				}
			
				
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source =  new DOMSource(document);
			
			System.out.println("---Módosított Fájl---");
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
			
			
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount}", "2");
			
			File myFile = new File("orarendModifyFEU2E5.xml");
			StreamResult file = new StreamResult(myFile);

			transformer.transform(source, file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static Node createUserElement(Document doc, String name, String value) {
		
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		
		return node;
	}

}
