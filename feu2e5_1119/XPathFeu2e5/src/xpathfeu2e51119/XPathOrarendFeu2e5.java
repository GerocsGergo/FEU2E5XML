package xpathfeu2e51119;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathOrarendFeu2e5 {

	public static void main(String[] args) {
		try {
			File xmlFile = new File("orarendFeu2e5.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();
			cleanDocument(document.getDocumentElement());
			//1. m�dos�t�s
			XPathModify.modifyMajor(document, "Prog terv");
			//2. m�dos�t�s
			XPathModify.modifyLectureName(document);
			//3. m�dos�t�s
			XPathModify.modifyPlace(document,"03", "XXXVII");
			//Ki�r�s konzolra
			System.out.println("<?xml version\"1.0\" encoding=\"utf-8\" ?>"+formatElement(document.getDocumentElement(), 0));
			//Ki�r�s f�jlba
			writeDocumentToFile(document, new File("orarendFeu2e51.xml"));
			
			
		} catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException | TransformerException e) {
			e.printStackTrace();
		}
	}
	public static void writeDocumentToFile(Document document, File output) throws TransformerException, IOException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(document);
		StreamResult outFile = new StreamResult(output);
		transformer.transform(source, outFile);
	
	}
	private static void cleanDocument(Node root) {
		NodeList nodes = root.getChildNodes();
		List<Node> toDelete = new ArrayList<>();
		for(int i=0; i<nodes.getLength(); i++) {
			if(nodes.item(i).getNodeType()==Node.TEXT_NODE && nodes.item(i).getTextContent().strip().equals("")) {
				toDelete.add(nodes.item(i));
			}else {
				cleanDocument(nodes.item(i));
			}
		}
		for(Node node: toDelete) {
			root.removeChild(node);
		}
	}
	public static String formatElement(Node node, int indent) {
		//Ha node nem elem, �res Stringgel t�r�nk vissza
		if (node.getNodeType() != Node.ELEMENT_NODE) {
			return "";
		}
		//Egy�bk�nt fel�p�tj�k az xml elemet
		String output = "\n";
		output += indent(indent)+"<" + ((Element) node).getTagName();
		//Attrib�tumok form�zott felv�tele, ha vannak
		if (node.hasAttributes()) {
			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				Node attribute = node.getAttributes().item(i);
				output += " " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"";
			}
		}
		output += ">";
		//Gyerekelemek feldolgoz�sa
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			//Sz�veges tartalom
			if(children.item(i).getNodeType()==Node.TEXT_NODE) return output+=node.getTextContent()+"</" + ((Element) node).getTagName() + ">";
			//Gyerekelem
			if(children.item(i).getNodeType()==Node.ELEMENT_NODE)output+=formatElement(children.item(i), indent+1);
		}
		output+="\n"+indent(indent)+"</" + ((Element) node).getTagName() + ">";

		return output;
	}
	
	private static String indent(int indent) {
		return "   ".repeat(indent);
	}
	

	private static class XPathModify{
		//Minden �r�hoz tartoz� szak m�dos�t�sa
		public static void modifyMajor(Document document, String newMajor) throws XPathExpressionException {
			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = "GG_orarend/ora/szak";
			NodeList result = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i< result.getLength(); i++) {
				Element node = (Element)result.item(i);
				node.setTextContent(newMajor);
				formatElement(node, 0);
				
			}
		}
		//El�ad�sok nev�nek v�g�re (E) f�zve
		public static void modifyLectureName(Document document) throws XPathExpressionException {
			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = "GG_orarend/ora[@tipus='el�ad�s']/targy";
			NodeList result = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i< result.getLength(); i++) {
				Element node = (Element)result.item(i);
				node.setTextContent(node.getTextContent()+" (BL)");
				
			}
		}
		//Adott id-j� �ra helysz�n�nek m�dos�t�sa
		public static void modifyPlace(Document document, String id, String newPlace) throws XPathExpressionException {
			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = "GG_orarend/ora[position()="+id+"]/helyszin";
			NodeList result = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i< result.getLength(); i++) {
				Element node = (Element)result.item(i);
				node.setTextContent(newPlace);
				
			}
		}
	}
}
