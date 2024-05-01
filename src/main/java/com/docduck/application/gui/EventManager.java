package com.docduck.application.gui;

import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBoxPassword;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.File;

import com.docduck.application.files.FTPHandler;
import com.docduck.application.xmlreader.XMLReader;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EventManager {

    private static XMLBuilder xmlBuilder;
    private static GUIBuilder guiBuilder;
    private HostServices hostServices;
    private Pane root;
    private Stage stage;
    private static EventManager instance;

    private EventManager(Pane root, HostServices hostServices, Stage stage) {
        this.hostServices = hostServices;
        this.root = root;
        this.stage = stage;
    }

    public static EventManager createInstance(Pane root, HostServices hostServices, Stage stage) {

        if (instance == null) {
            instance = new EventManager(root, hostServices, stage);
        }
        return instance;
    }
    
    public static EventManager getInstance() {
        return instance;
    }
    
    public void updateInstances() {
    	xmlBuilder = XMLBuilder.getInstance();
    	guiBuilder = GUIBuilder.getInstance();
    }

    public EventHandler<ActionEvent> getActionEvent(String eventID) {

        switch (eventID) {
        
        case "xmlPage":
            EventHandler<ActionEvent> xmlPage = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    guiBuilder.LoadFromXMLPage();
                }
            };
            return xmlPage;
            
        case "goBack":
            EventHandler<ActionEvent> goBack = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    guiBuilder.StartPage();
                }
            };
            return goBack;
            
        case "loadApp":
            EventHandler<ActionEvent> loadApp = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    FTPHandler handler = new FTPHandler();
                    handler.downloadAllFiles();
                }
            };
            return loadApp;
           
        case "loadAppOffline":
            EventHandler<ActionEvent> loadAppOffline = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                	XMLReader myReader = new XMLReader("src/main/resources/docduck-application-slides.xml", "src/main/resources/DocDuckStandardSchema.xsd", true);
                	myReader.readXML();
                	xmlBuilder.setData(myReader.getData());
                	guiBuilder.setData(myReader.getData());
                	guiBuilder.LoginPage();
                }
            };
            return loadAppOffline;
            
        case "chooseXML":
            EventHandler<ActionEvent> chooseXML = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                	FileChooser fileChooser = new FileChooser();
                	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
                	File selectedFile = fileChooser.showOpenDialog(stage);
                	if (selectedFile != null) {
                		String filePath = selectedFile.getAbsolutePath();
                		XMLReader myReader = new XMLReader(filePath, "src/main/resources/DocDuckStandardSchema.xsd", true);
                		myReader.readXML();
                		int slideCount = myReader.getSlideCount();
                		xmlBuilder.setData(myReader.getData());
                		xmlBuilder.buildCustomXML(1, slideCount);
                	}
                }
            };
            return chooseXML;
        
        case "signup":
            EventHandler<ActionEvent> signup = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    hostServices.showDocument("https://docduck.000webhostapp.com");
                }
            };
            return signup;

        case "signin":
            EventHandler<ActionEvent> signin = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    // Use node id's to compare and see which node to be used.
                    TextBoxField username = (TextBoxField) root.getChildren().get(4);
                    TextBoxPassword password = (TextBoxPassword) root.getChildren().get(6);

                    if (username.getText().equals("admin") && password.getText().equals("password")) {
                        password.setBorderWidth(0);
                        System.out.println("Logged in!");
                        guiBuilder.StatusPage();
                    }
                    else {
                        password.setBorderWidth(2);
                        password.setBorderColour("#FF0000");
                    }
                }
            };
            return signin;

        case "statusPage":
            EventHandler<ActionEvent> statusPage = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    xmlBuilder.buildSlide(2);
                }
            };
            return statusPage;

        case "testPage":
            EventHandler<ActionEvent> testPage = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    xmlBuilder.buildSlide(3);
                }
            };
            return testPage;
        default:
            EventHandler<ActionEvent> fault = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    System.out.println("ERROR: Event not attached, check eventID");
                }
            };
            return fault;
        }
    }

    public EventHandler<KeyEvent> getKeyEvent(String eventID) {

        switch (eventID) {
        case "Search...":
            EventHandler<KeyEvent> searchEvent = new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    TextBoxField tf = (TextBoxField) event.getSource();
                    tf.getText();
                    System.out.println(tf.getText());

                }
            };
            return searchEvent;
        default:
            EventHandler<KeyEvent> fault = new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    System.out.println("ERROR: Event not attached, check eventID");
                }
            };
            return fault;
        }
    }
    
    public EventHandler<ActionEvent> getHyperlinkEvent(String url) {
    	EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                hostServices.showDocument(url);
            }
        };
        return e;
    }
    
    public EventHandler<ActionEvent> getSlideEvent(int nextSlide, int slideCount) {
    	EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                xmlBuilder.buildCustomXML(nextSlide, slideCount);
            }
        };
        return e;
    }
}
