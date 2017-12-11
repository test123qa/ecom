package com.api.framework;



import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class ResourceReader {
	 static ResourceBundle bundle;
	 static String  fileName;
	 static Properties prop;
	 static InputStream input;
	public static void readBundle(String value){
		bundle  = ResourceBundle.getBundle(value,Locale.ENGLISH);
		
	}
	
	
	public static String  readValue(String element){
	  String value =bundle.getString(element);
		
		return  value;
		
	}
	public static void loadProperties(String fileName){
		ResourceReader.fileName=fileName;
	}
	public static String getProperty(String key){
		prop = new Properties();
		
		try{
			input = new FileInputStream(fileName);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			return prop.getProperty(key);
		}
		catch(Exception e){
			System.out.println("Unable to read the Property"+e);
		}
		
		return null;
		
		
	}
	
	public static void setProperty(String key, String value){
		prop = new Properties();
		
		try{
			input = new FileInputStream(fileName);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			 prop.setProperty(key,value);
		}
		catch(Exception e){
			System.out.println("Unable to write the Properties to file"+e);
		}
		
	}


}