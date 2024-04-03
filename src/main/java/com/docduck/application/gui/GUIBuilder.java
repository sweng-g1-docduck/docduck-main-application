package com.docduck.application.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import com.docduck.buttonlibrary.*;
import com.docduck.textlibrary.*;
import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class GUIBuilder {

    private Hashtable<String, Hashtable<String, Object>> xmlData = new Hashtable<>();
    private Pane root;
    private HostServices hostServices;
    private static GUIBuilder instance;
    private static EventManager events;
    private double OLD_CENTER_X = 640;
    private double OLD_CENTER_Y = 360;
    private double OLD_WIDTH = 1280;
    private double OLD_HEIGHT = 720;

    private GUIBuilder(Hashtable<String, Hashtable<String, Object>> xmlData, Pane root, HostServices hostServices) {
        this.xmlData = xmlData;
        this.root = root;
        this.hostServices = hostServices;
    }
    
    public static GUIBuilder createInstance(Hashtable<String, Hashtable<String, Object>> xmlData, Pane root, HostServices hostServices) {
    	if (instance == null) {
    	instance = new GUIBuilder(xmlData,root,hostServices);
    	}
    	events = EventManager.createInstance(getInstance(),hostServices,root);
    	return instance;
    }
    
    public static GUIBuilder getInstance() {
    	return instance;
    }

    public void buildSlide(int slideNumber) {
        boolean hasDataRemaining;
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
        int occurance;
        ArrayList<Node> nodeList = new ArrayList<>();
        
        root.getChildren().clear();
        
        occurance = 1;
        hasDataRemaining = true;
        while (hasDataRemaining == true) {
        	hasDataRemaining = false;
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
                occurance++;
            }
        }
        
        occurance = 1;
        hasDataRemaining = true;
        while (hasDataRemaining == true) {
        	hasDataRemaining = false;
            if (xmlData.containsKey(shape + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Shape-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(shape + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
                occurance++;
            }
        }
        
        occurance = 1;
        hasDataRemaining = true;
        while (hasDataRemaining == true) {
        	hasDataRemaining = false;
            if (xmlData.containsKey(image + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Image-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(image + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
                occurance++;
            }
        }
        
        occurance = 1;
        hasDataRemaining = true;
        while (hasDataRemaining == true) {
        	hasDataRemaining = false;
            if (xmlData.containsKey(audio + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Audio-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(audio + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
                occurance++;
            }
        }
        
        occurance = 1;
        hasDataRemaining = true;
        while (hasDataRemaining == true) {
        	hasDataRemaining = false;
            if (xmlData.containsKey(video + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Video-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(video + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
                occurance++;
            }
        }
        
        occurance = 1;
        hasDataRemaining = true;
        while (hasDataRemaining == true) {
            hasDataRemaining = false;
            if (xmlData.containsKey(button + delimiter + slideNumber + delimiter + occurance) == true) {
            	Hashtable<String, Object> buttonData = xmlData.
            			get(button + delimiter + slideNumber + delimiter + occurance);
            	ButtonWrapper b = new ButtonWrapper();
            	
            	if ((boolean)buttonData.get("hasBackground")) {
            		b.addBackground();
            		b.setBackgroundColour((String) buttonData.get("backgroundColour"));
            	}
            	else {
            		b.removeBackground();
            	}
            	if (buttonData.get("eventID") != null) {
            		b.onClick(events.getActionEvent((String) buttonData.get("eventID")));
            	}
            	if (buttonData.get("text") != null) {
            		b.setText((String) buttonData.get("text"));
            	}
            	if (buttonData.get("hoverColour") != null) {
            		 b.setHoverColour((String) buttonData.get("hoverColour"));
            	}
            	if (buttonData.get("clickColour") != null) {
            		b.setClickcolour((String) buttonData.get("clickColour"));
            	}
            	
            	b.addBorder();
            	b.setBorderColour((String) buttonData.get("borderColour"));
            	Double borderWidth = (Double) buttonData.get("borderWidth");
            	b.setBorderWidth(borderWidth.intValue());
            	b.setWidth((Double) buttonData.get("width"));
            	b.setHeight((Double) buttonData.get("height"));
            	b.setCornerRadius((Integer) buttonData.get("cornerRadius"));
            	b.setFont((String) buttonData.get("font"));
            	b.setFontColour((String) buttonData.get("fontColour"));
            	b.setFontSize((Integer) buttonData.get("fontSize"));
            	b.setPositionX((Integer) buttonData.get("xCoordinate"));
            	b.setPositionY((Integer) buttonData.get("yCoordinate"));
            	
            	nodeList.add(b.returnButton());
            	
                hasDataRemaining = true;
                occurance++;
            }
        }
        
        occurance = 1;
        hasDataRemaining = true;
        while (hasDataRemaining == true) {
        	hasDataRemaining = false;
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
                occurance++;
            }
        }
        
        occurance = 1;
        hasDataRemaining = true;
        while (hasDataRemaining == true) {
            hasDataRemaining = false;
            if (xmlData.containsKey(backgroundColour + delimiter + slideNumber + delimiter + occurance) == true) {
            	Hashtable<String, Object> backgroundColourData = xmlData.
            			get(backgroundColour + delimiter + slideNumber + delimiter + occurance);
            	String colour = (String) backgroundColourData.get("colour");
            	root.setStyle("-fx-background-color: " + colour);
            	
                hasDataRemaining = true;
                occurance++;
            }
        }
        
        occurance = 1;
        hasDataRemaining = true;
        while (hasDataRemaining == true) {
            hasDataRemaining = false;
	        if (xmlData.containsKey(textField + delimiter + slideNumber + delimiter + occurance) == true) {
	            Hashtable<String, Object> textFieldData = xmlData
	                    .get(textField + delimiter + slideNumber + delimiter + occurance);
	           
	            if ((boolean)textFieldData.get("password")) {
	            	TextBoxPassword textfield = new TextBoxPassword();
	            
	            	textfield.setBoxWidth((Double) textFieldData.get("width"));
	            	textfield.setBoxHeight((Double) textFieldData.get("height"));
	            	textfield.setPositionX((Integer) textFieldData.get("xCoordinate"));
	            	textfield.setPositionY((Integer) textFieldData.get("yCoordinate"));
	            	textfield.setFontName((String) textFieldData.get("font"));
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
	            		textfield.setText((String) textFieldData.get("initialText"));
	            	}
	            	if (textFieldData.get("hoverColour") != null) {
	            		textfield.setHoverColour((String) textFieldData.get("hoverColour"));
	            	}
	            	if (textFieldData.get("selectColour") != null) {
	            		textfield.setSelectColour((String) textFieldData.get("selectColour"));
	            	}
	            	
	            	//Could add text origin?
	            	
	            	nodeList.add(textfield.returnPasswordField());
	            	nodeList.add(textfield);
	            	if ((boolean) textFieldData.get("passwordButton")) {
	            		textfield.createButton();
	            		nodeList.add(textfield.returnButton());
	            	}
	            }
	            else {
	            	TextBoxField textfield = new TextBoxField();

	            	textfield.setBoxWidth((Double) textFieldData.get("width"));
	            	textfield.setBoxHeight((Double) textFieldData.get("height"));
	            	textfield.setPositionX((Integer) textFieldData.get("xCoordinate"));
	            	textfield.setPositionY((Integer) textFieldData.get("yCoordinate"));
	            	textfield.setFontName((String) textFieldData.get("font"));
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
	            		textfield.setText((String) textFieldData.get("initialText"));
	            	}
	            	if (textFieldData.get("hoverColour") != null) {
	            		textfield.setHoverColour((String) textFieldData.get("hoverColour"));
	            	}
	            	if (textFieldData.get("selectColour") != null) {
	            		textfield.setSelectColour((String) textFieldData.get("selectColour"));
	            	}
	            	
	            	//Could add text origin?
	            	
	            	nodeList.add(textfield);
	            }
	            
	            hasDataRemaining = true;
	            occurance++;
	        }
        }
        root.getChildren().addAll(nodeList);
    }
    
    public void scaleNodes(double windowWidth, double windowHeight){
    	double NEW_CENTER_X = windowWidth/2;
    	double NEW_CENTER_Y = windowHeight/2;
    	double WIDTH = 1280;
    	double HEIGHT = 720;
    	double widthScale = windowWidth/WIDTH;
    	double heightScale = windowHeight/HEIGHT;
    	ObservableList<Node> nodes = root.getChildren();
    	
    	for (int i = 0; i < nodes.size(); i++) {
    		Node node = nodes.get(i);
			if (widthScale < heightScale) {
				node.setScaleY(widthScale);
				node.setScaleX(widthScale);
			}
			else {
				node.setScaleY(heightScale);
				node.setScaleX(heightScale);
			}
    		double nodeWidth = node.getLayoutBounds().getWidth();
    		double nodeHeight = node.getLayoutBounds().getHeight();
    		double nodeCenterX = node.getLayoutX() + nodeWidth/2;
	        double nodeCenterY = node.getLayoutY() + nodeHeight/2;
    		
    		double newX = ((nodeCenterX-OLD_CENTER_X)*(windowWidth/OLD_WIDTH)) + NEW_CENTER_X - nodeWidth/2;
    		if(node.layoutXProperty().isBound() != true) {
    			node.setLayoutX(newX);
    		}
    		double newY = ((nodeCenterY-OLD_CENTER_Y)*(windowHeight/OLD_HEIGHT)) + NEW_CENTER_Y - nodeHeight/2;
    		if(node.layoutYProperty().isBound() != true) {
    			node.setLayoutY(newY);
    		}
    	}
    	OLD_CENTER_X = NEW_CENTER_X;
    	OLD_CENTER_Y = NEW_CENTER_Y;
    	OLD_WIDTH = windowWidth;
    	OLD_HEIGHT = windowHeight;
    }
}
