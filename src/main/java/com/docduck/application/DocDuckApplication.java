package com.docduck.application;

import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.xmlreader.XMLReader;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DocDuckApplication extends Application {

    private Node[] nodes;
    private Pane root;

    @Override
    public void start(Stage stage) {
        System.out.println("Starting DocDuck Application");

        // Set the stage title and scene, then show the stage
        loadApplicationDesign();
        root.getChildren().addAll(nodes);
        Scene scene = new Scene(root, 800, 800, Color.BEIGE);
        stage.setTitle("DocDuck");
        stage.setScene(scene);
        stage.show();

        // ORDER OF PROGRAM
        // Load up JavaFX
        // Needs to check if there are any xml files to display a slide or slideshow
        // If there are xml files, find ids of each and order them
        // Display ID 1 slide
        // If buttons, add in their actions, do they go to slide 2? etc.

    }

    public void loadApplicationDesign() {
        XMLReader myReader = new XMLReader("src/main/resources/loginPage.xml", "src/main/resources/DocDuckStandardSchema.xsd", true);
        myReader.readXML();
        root = new Pane();
        GUIBuilder builder = new GUIBuilder(myReader.getData());
        nodes = builder.buildSlide(1,root);

    }

    public static void main(String[] args) {
        launch(args);
    }

    // COMMAND LINE ARGUMENTS CODE:
//    Parameters parameters = getParameters();
//    List<String> args = parameters.getRaw();
//
//    String xmlPath = null;
//    String schemaPath = null;
//    boolean validate = false;
//
//    if (args.size() == 0) {
//        System.out.println("Please specify command line arguments with -xml 'PATH_TO_XML' etc.");
//    }
//
//    for (int i = 0; i < args.size(); i++) {
//
//        if (args.get(i) == "-xml") {
//            xmlPath = args.get(i + 1);
//        }
//        else if (args.get(i) == "-xsd") {
//            schemaPath = args.get(i + 1);
//        }
//        else if (args.get(i) == "-validate") {
//            validate = true;
//        }
//    }
//
//    XMLReader myReader = new XMLReader(xmlPath, schemaPath, validate);
}
