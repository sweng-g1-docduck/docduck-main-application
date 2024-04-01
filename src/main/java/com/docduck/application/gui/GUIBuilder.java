package com.docduck.application.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;

import com.docduck.textlibrary.*;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class GUIBuilder {

    private Hashtable<String, Hashtable<String, Object>> xmlData = new Hashtable<>();
    private Pane root;
    private HostServices hostServices;
    private static GUIBuilder instance;
    private EventManager events = EventManager.createInstance(getInstance());

    private GUIBuilder(Hashtable<String, Hashtable<String, Object>> xmlData, Pane root, HostServices hostServices) {
        this.xmlData = xmlData;
        this.root = root;
        this.hostServices = hostServices;
    }
    
    public static GUIBuilder createInstance(Hashtable<String, Hashtable<String, Object>> xmlData, Pane root, HostServices hostServices) {
    	if (instance == null) {
    	instance = new GUIBuilder(xmlData,root,hostServices);
    	}
    	return instance;
    }
    
    public static GUIBuilder getInstance() {
    	return instance;
    }

    public Node[] buildSlide(int slideNumber) {
        boolean hasDataRemaining = true;
        String textField = "textField";
        String textBox = "textBox";
        String shape = "shape";
        String image = "image";
        String audio = "audio";
        String video = "video";
        String delimiter = "-";
        String button = "button";
        String hyperlink = "hyperlink";
        String backgroundColour = "backgroundColour";
        int occurance = 1;
        ArrayList<Node> nodeList = new ArrayList<>();

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

	        if (xmlData.containsKey(textField + delimiter + slideNumber + delimiter + occurance) == true) {
	            Hashtable<String, Object> textFieldData = xmlData
	                    .get(textField + delimiter + slideNumber + delimiter + occurance);
	           
	            if ((boolean)textFieldData.get("password")) {
	            	TextBoxPassword textfield = new TextBoxPassword();
	            
	            	textfield.setWidth((Double) textFieldData.get("width"));
	            	textfield.setHeight((Double) textFieldData.get("height"));
	            	textfield.setPositionX((Integer) textFieldData.get("xCoordinate"));
	            	textfield.setPositionY((Integer) textFieldData.get("yCoordinate"));
	            	textfield.setFont((String) textFieldData.get("font"));
	            	textfield.setFontColour((String) textFieldData.get("fontColour"));
	            	textfield.setFontSize((Integer) textFieldData.get("fontSize"));
	            	textfield.addBorder();
	            	textfield.setBorderColour((String) textFieldData.get("borderColour"));
	            	Double borderWidth = (Double) textFieldData.get("borderWidth");
	            	textfield.setBorderWidth(borderWidth.intValue());
	            	textfield.setCornerRadius((Integer) textFieldData.get("cornerRadius"));
	            	textfield.setBackgroundColour((String) textFieldData.get("backgroundColour"));
	            	textfield.setPromptTextColour((String) textFieldData.get("promptTextColour"));
	            	textfield.setHighlightColour((String) textFieldData.get("highlightColour"));
	            	textfield.setHighlightFontColour((String) textFieldData.get("fontHighlightColour"));
	            	
	            	if (textFieldData.get("promptText") != null) {
	            		textfield.setPromptText((String) textFieldData.get("promptText"));
	            	}
	            	if (textFieldData.get("initialText") != null) {
	            		textfield.setInitialContent((String) textFieldData.get("initialText"));
	            	}
	            	if (textFieldData.get("hoverColour") != null) {
	            		textfield.setHoverColour((String) textFieldData.get("hoverColour"));
	            	}
	            	if (textFieldData.get("selectColour") != null) {
	            		textfield.setSelectColour((String) textFieldData.get("selectColour"));
	            	}
	            	
	            	//Could add text origin?
	            	
	            	nodeList.add(textfield.returnPasswordField());
	            	nodeList.add(textfield.returnTextField());
	            	if ((boolean) textFieldData.get("passwordButton")) {
	            		textfield.createButton();
	            		nodeList.add(textfield.returnButton());
	            	}
	            }
	            else {
	            	TextBoxField textfield = new TextBoxField();

	            	textfield.setWidth((Double) textFieldData.get("width"));
	            	textfield.setHeight((Double) textFieldData.get("height"));
	            	textfield.setPositionX((Integer) textFieldData.get("xCoordinate"));
	            	textfield.setPositionY((Integer) textFieldData.get("yCoordinate"));
	            	textfield.setFont((String) textFieldData.get("font"));
	            	textfield.setFontColour((String) textFieldData.get("fontColour"));
	            	textfield.setFontSize((Integer) textFieldData.get("fontSize"));
	            	textfield.addBorder();
	            	textfield.setBorderColour((String) textFieldData.get("borderColour"));
	            	Double borderWidth = (Double) textFieldData.get("borderWidth");
	            	textfield.setBorderWidth(borderWidth.intValue());
	            	textfield.setCornerRadius((Integer) textFieldData.get("cornerRadius"));
	            	textfield.setBackgroundColour((String) textFieldData.get("backgroundColour"));
	            	textfield.setPromptTextColour((String) textFieldData.get("promptTextColour"));
	            	textfield.setHighlightColour((String) textFieldData.get("highlightColour"));
	            	textfield.setHighlightFontColour((String) textFieldData.get("fontHighlightColour"));
	            	
	            	if (textFieldData.get("promptText") != null) {
	            		textfield.setPromptText((String) textFieldData.get("promptText"));
	            	}
	            	if (textFieldData.get("initialText") != null) {
	            		textfield.setInitialContent((String) textFieldData.get("initialText"));
	            	}
	            	if (textFieldData.get("hoverColour") != null) {
	            		textfield.setHoverColour((String) textFieldData.get("hoverColour"));
	            	}
	            	if (textFieldData.get("selectColour") != null) {
	            		textfield.setSelectColour((String) textFieldData.get("selectColour"));
	            	}
	            	
	            	//Could add text origin?
	            	
	            	nodeList.add(textfield.returnTextField());
	            }
	            
	            hasDataRemaining = true;
	        }
            
            if (xmlData.containsKey(button + delimiter + slideNumber + delimiter + occurance) == true) {
            	Hashtable<String, Object> buttonData = xmlData.
            			get(button + delimiter + slideNumber + delimiter + occurance);
            	Button b = new Button();
            	b.setPrefWidth((Double) buttonData.get("width"));
            	b.setPrefHeight((Double) buttonData.get("height"));
            	
            	
                hasDataRemaining = true;
            }
            
            if (xmlData.containsKey(backgroundColour + delimiter + slideNumber + delimiter + occurance) == true) {
            	Hashtable<String, Object> backgroundColourData = xmlData.
            			get(backgroundColour + delimiter + slideNumber + delimiter + occurance);
            	String colour = (String) backgroundColourData.get("colour");
            	root.setStyle("-fx-background-color: " + colour);
            	
                hasDataRemaining = true;
            }
            
            if (xmlData.containsKey(hyperlink + delimiter + slideNumber + delimiter + occurance) == true) {
            	Hashtable<String, Object> hyperlinkData = xmlData.
            			get(hyperlink + delimiter + slideNumber + delimiter + occurance);
            	Hyperlink link = new Hyperlink((String) hyperlinkData.get("URL"));
            	
            	link.setStyle("-fx-color: " + (String) hyperlinkData.get("fontColour"));
            	link.setStyle("-fx-font: " + (String) hyperlinkData.get("font"));
            	link.setStyle("-fx-font-size: " + (Integer) hyperlinkData.get("fontSize"));
            	link.setLineSpacing((Double) hyperlinkData.get("lineSpacing"));
            	Integer xCoordinate = (Integer) hyperlinkData.get("xCoordinate");
            	Integer yCoordinate = (Integer) hyperlinkData.get("yCoordinate");
            	link.setLayoutX(xCoordinate.doubleValue());
            	link.setLayoutY(yCoordinate.doubleValue());
            	
            	if (hyperlinkData.get("text") != null) {
            		link.setText((String) hyperlinkData.get("text"));
            	}
            	
            	EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {
        			public void handle(ActionEvent e) {
        				hostServices.showDocument((String) hyperlinkData.get("URL"));
        			}
        		};
            	
            	link.setOnAction(e);
            	nodeList.add(link);
            	
                hasDataRemaining = true;
            }
            
            if (xmlData.containsKey(textBox + delimiter + slideNumber + delimiter + occurance) == true) {
                TextBox textbox = new TextBox();
                Hashtable<String, Object> textBoxData = xmlData
                        .get(textBox + delimiter + slideNumber + delimiter + occurance);
                textbox.setWidth((Double) textBoxData.get("width"));
                textbox.setHeight((Double) textBoxData.get("height"));
                textbox.setFont((String) textBoxData.get("font"));
                textbox.setContent((String) textBoxData.get("text"));
                textbox.setFontSize((Integer) textBoxData.get("fontSize"));
                textbox.setFontColour((String) textBoxData.get("fontColour"));
                textbox.setCharacterSpacing((Double) textBoxData.get("characterSpacing"));
                textbox.setLineSpacing((Double) textBoxData.get("lineSpacing"));
                textbox.setPositionX((Integer) textBoxData.get("xCoordinate"));
                textbox.setPositionY((Integer) textBoxData.get("yCoordinate"));
                nodeList.add(textbox.returnBox());
                nodeList.add(textbox.returnText());
                hasDataRemaining = true;
            }

            if (xmlData.containsKey(shape + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Shape-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(shape + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
            }

            if (xmlData.containsKey(image + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Image-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(image + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
            }

            if (xmlData.containsKey(audio + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Audio-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(audio + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
            }

            if (xmlData.containsKey(video + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Video-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(video + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
            }
            occurance++;
        }
        // Convert ArrayList to array
        Node[] nodes = new Node[nodeList.size()];

        for (int i = 0; i < nodeList.size(); i++) {
            nodes[i] = nodeList.get(i);
        }
        return nodes;
    }
}
