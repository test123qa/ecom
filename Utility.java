package com.api.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Utility {

	private static Document doc;

	public static String readxml(String fileName) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File("src/main/resources" + fileName + ".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(fXmlFile);
		XMLUtil ut = new XMLUtil();
		String message = ut.getStringFromDocument(doc);
		return message;

	}

	public static String readtxt(String fileName) throws ParserConfigurationException, SAXException, IOException {
		BufferedReader br = new BufferedReader(new FileReader("src/main/resources" + fileName + ".txt"));
		String everything;
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		} finally {
			br.close();
		}
		return everything;

	}

	public static void updatexml(String tag, String input)
			throws ParserConfigurationException, SAXException, IOException {
		Node node = doc.getElementsByTagName(tag).item(0);
		node.setTextContent(input);
	}

	public static String Getxml(String tag) throws ParserConfigurationException, SAXException, IOException {
		Node node = doc.getElementsByTagName(tag).item(0);
		return node.getTextContent().toString();
	}

	public static String getupdatexml() throws ParserConfigurationException, SAXException, IOException {
		XMLUtil ut = new XMLUtil();
		String message = ut.getStringFromDocument(doc);
		return message;
	}

	public static String getTagValueFromOutput(String mess, String tag)
			throws ParserConfigurationException, SAXException, IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(mess)));
			Node node = document.getElementsByTagName(tag).item(0);
			return node.getTextContent();
		} catch (Exception e) {
			return "BLANK/NO TAG";
		}
	}
}
