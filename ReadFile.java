package com.api.framework;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ReadFile{


public static ArrayList<String> readtxtAsList(String fileName) throws ParserConfigurationException, SAXException, IOException {
	BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName + ".txt"));
	ArrayList<String> list= new ArrayList<String>();
	try {
		

		String line = br.readLine();
		
		while (line != null) {
		list.add(line);
		line = br.readLine();
		}

	} finally {
		br.close();
	}
	return list;

}

}