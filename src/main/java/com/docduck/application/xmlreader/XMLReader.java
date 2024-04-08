package com.docduck.application.xmlreader;

import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.docduck.xml.Parser;

public class XMLReader {

    protected Hashtable<String, Hashtable<String, Object>> xmlData = new Hashtable<>();
    private Parser myParser;
    private String xmlPath;
    private String schemaPath;
    private boolean validate;

    public XMLReader(String xmlPath, String schemaPath, boolean validate) {
        this.xmlPath = xmlPath;
        this.schemaPath = schemaPath;
        this.validate = validate;

        loadParser();
    }

    public XMLReader() {

    }

    private void loadParser() {
        myParser = new Parser(xmlPath, schemaPath, validate);

    }

    public void readXML() {

        try {
            xmlData = myParser.parse();
        }
        catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public Hashtable<String, Hashtable<String, Object>> getData(){
    	return xmlData;
    }

    public void printXMLData() {

        String id = "id";
        String textBox = "textBox";
        String textField = "textField";
        String button = "button";
        String hyperlink = "hyperlink";
        String backgroundColour = "backgroundColour";
        String shape = "shape";
        String image = "image";
        String audio = "audio";
        String video = "video";

        String delimiter = "-";
        int occurance = 1;
        int slideCount = myParser.getSlideCount();

        System.out.println("\nPRINTING XML DATA\n-----------------");

        for (int i = 1; i < slideCount+1; i++) {
        	
        	boolean hasDataRemaining = true;
        	
	        while (hasDataRemaining == true) {
	            hasDataRemaining = false;
	            
	            if (xmlData.containsKey(id + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("ID-" + i + "-" + occurance + ": " + xmlData.get(id + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	            
	            if (xmlData.containsKey(textBox + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("Text Box-" + i + "-" + occurance + ": " + xmlData.get(textBox + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	            
	            if (xmlData.containsKey(textField + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("Text Field-" + i + "-" + occurance + ": " + xmlData.get(textField + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	            
	            if (xmlData.containsKey(button + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("Button-" + i + "-" + occurance + ": " + xmlData.get(button + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	            
	            if (xmlData.containsKey(backgroundColour + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("Background Colour-" + i + "-" + occurance + ": " + xmlData.get(backgroundColour + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	            
	            if (xmlData.containsKey(hyperlink + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("Hyperlink-" + i + "-" + occurance + ": " + xmlData.get(hyperlink + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	
	            if (xmlData.containsKey(shape + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("Shape-" + i + "-" + occurance + ": " + xmlData.get(shape + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	
	            if (xmlData.containsKey(image + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("Image-" + i + "-" + occurance + ": " + xmlData.get(image + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	
	            if (xmlData.containsKey(audio + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("Audio-" + i + "-" + occurance + ": " + xmlData.get(audio + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	
	            if (xmlData.containsKey(video + delimiter + i + delimiter + occurance) == true) {
	                System.out.println("Video-" + i + "-" + occurance + ": " + xmlData.get(video + delimiter + i + delimiter + occurance));
	                hasDataRemaining = true;
	            }
	
	            occurance++;
	        }
	        
	        occurance = 1;
        }
    }

}
