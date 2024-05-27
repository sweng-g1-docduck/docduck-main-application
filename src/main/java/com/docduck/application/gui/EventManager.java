package com.docduck.application.gui;

import java.io.File;

import com.docduck.application.data.Machine;
import com.docduck.application.files.FTPHandler;
import com.docduck.application.xmlreader.XMLReader;
import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBoxPassword;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EventManager {

    private static XMLBuilder xmlBuilder;
    private static GUIBuilder guiBuilder;
    private static FTPHandler ftpHandler;
    private final HostServices hostServices;
    private final Pane root;
    private final Stage stage;
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
        ftpHandler = FTPHandler.getInstance();
    }

    public EventHandler<ActionEvent> getActionEvent(String eventID) {

        switch (eventID) {

        case "xmlPage":
            return e -> guiBuilder.LoadFromXMLPage();

        case "goBack":
            return e -> guiBuilder.StartPage();

        case "loadAppOffline":
            return e -> {
                XMLReader myReader = new XMLReader("src/main/resources/docduck-application-slides.xml",
                        "src/main/resources/DocDuckStandardSchema.xsd", true);
                myReader.readXML();
                xmlBuilder.setData(myReader.getData());
                guiBuilder.setData(myReader.getData());
                guiBuilder.LoginPage();
            };

        case "chooseXML":
            return e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
                File selectedFile = fileChooser.showOpenDialog(stage);

                if (selectedFile != null) {
                    String filePath = selectedFile.getAbsolutePath();
                    XMLReader myReader = new XMLReader(filePath, "src/main/resources/DocDuckStandardSchema.xsd",
                            true);
                    myReader.readXML();
                    int slideCount = myReader.getSlideCount();
                    xmlBuilder.setData(myReader.getData());
                    xmlBuilder.buildCustomXML(1, slideCount);
                }
            };
            
        case "chooseMedia":
            return e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Media File", "*.png"));
                File selectedFile = fileChooser.showOpenDialog(stage);

                if (selectedFile != null) {
                    String filePath = selectedFile.getAbsolutePath();
                    String filename = selectedFile.getName();
                    System.out.println(filePath);
                    ftpHandler.connect();
                    ftpHandler.uploadFileFromPath(filePath, filename);
                }
            };

        case "signup":
            return e -> hostServices.showDocument("https://docduck.000webhostapp.com");

        case "signin":
            return e -> {
                // Use node id's to compare and see which node to be used.
                TextBoxField username = (TextBoxField) root.getChildren().get(4);
                TextBoxPassword password = (TextBoxPassword) root.getChildren().get(6);

                if (username.getText().equals("admin") && password.getText().equals("password")) {
                    password.setBorderWidth(0);
                    System.out.println("Logged in!");
                } else {
                    password.setBorderWidth(2);
                    password.setBorderColour("#FF0000");
                }
            };

        case "statusPage":
            return e -> guiBuilder.displayPage("STATUS");

        case "reportPage":
            return e -> guiBuilder.displayPage("REPORT");
        case "adminPage":
            return e -> guiBuilder.displayPage("ADMIN");
        case "loginPage":
            return e -> guiBuilder.displayPage("LOGIN");
   
        default:
            return e -> System.out.println("ERROR: Event not attached, check eventID");
        }
    }

    /**
     * Event handler to open the report page to a specific machine
     * 
     * @param eventID The ID of the event
     * @param machine The machine which's report is to be opened
     * @return The action event to attach
     */
    public EventHandler<ActionEvent> getActionEvent(String eventID, Machine machine) {

        if (eventID.equals("reportPage")) {
            return e -> guiBuilder.displayPage("REPORT", machine);
        }

        return e -> System.out.println("ERROR: Event not attached, check eventID");
    }
    
    public EventHandler<KeyEvent> getKeyEvent(String eventID) {

        if (eventID.equals("Search...")) {
            return event -> {
                TextBoxField tf = (TextBoxField) event.getSource();
                tf.getText();
                System.out.println(tf.getText());

            };
        }
        return e -> System.out.println("ERROR: Event not attached, check eventID");
    }

    public EventHandler<ActionEvent> getHyperlinkEvent(String url) {
        return e -> hostServices.showDocument(url);
    }

    public EventHandler<ActionEvent> getSlideEvent(int nextSlide, int slideCount) {
        return e -> xmlBuilder.buildCustomXML(nextSlide, slideCount);
    }
}
