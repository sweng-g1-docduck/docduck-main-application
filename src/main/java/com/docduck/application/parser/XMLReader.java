package com.docduck.application.parser;

import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.docduck.xml.Parser;

public class XMLReader {

    private Hashtable<String, Hashtable<String, Object>> xmlData = new Hashtable<>();
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

    public void printXMLData() {

        String id = "id";
        String textBox = "textBox";
        String shape = "shape";
        String image = "image";
        String audio = "audio";
        String video = "video";

        String delimiter = "-";
        int occurance = 1;

        boolean hasDataRemaining = true;

        System.out.println("\nPRINTING XML DATA\n-----------------");

        while (hasDataRemaining == true) {

            hasDataRemaining = false;

            if (xmlData.containsKey(id + delimiter + occurance) == true) {
                System.out.println("ID: " + xmlData.get(id + delimiter + occurance));
                hasDataRemaining = true;
            }

            if (xmlData.containsKey(textBox + delimiter + occurance) == true) {
                System.out.println("Text Box " + occurance + ": " + xmlData.get(textBox + delimiter + occurance));
                hasDataRemaining = true;
            }

            if (xmlData.containsKey(shape + delimiter + occurance) == true) {
                System.out.println("Shape " + occurance + ": " + xmlData.get(shape + delimiter + occurance));
                hasDataRemaining = true;
            }

            if (xmlData.containsKey(image + delimiter + occurance) == true) {
                System.out.println("Image " + occurance + ": " + xmlData.get(image + delimiter + occurance));
                hasDataRemaining = true;
            }

            if (xmlData.containsKey(audio + delimiter + occurance) == true) {
                System.out.println("Audio " + occurance + ": " + xmlData.get(audio + delimiter + occurance));
                hasDataRemaining = true;
            }

            if (xmlData.containsKey(video + delimiter + occurance) == true) {
                System.out.println("Video " + occurance + ": " + xmlData.get(video + delimiter + occurance));
                hasDataRemaining = true;
            }

            occurance++;
        }

    }

}
