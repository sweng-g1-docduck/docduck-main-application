package com.docduck.application;

import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.xmlreader.XMLReader;
import com.docduck.application.files.FTPHandler;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DocDuckApplication extends Application {

    private Node[] nodes;
    private static Pane root;

    @Override
    public void start(Stage stage) {
        System.out.println("Starting DocDuck Application");
        FTPHandler FTPHandler = new FTPHandler();
        FTPHandler.downloadAllFiles();
        XMLReader myReader = new XMLReader("src/main/resources/docduck-application-slides.xml",
                "src/main/resources/DocDuckStandardSchema.xsd", true);
        myReader.readXML();
        myReader.printXMLData();
        root = new Pane();
        GUIBuilder builder = GUIBuilder.createInstance(myReader.getData(), root, this.getHostServices());
        builder.buildSlide(1);
        Scene scene = new Scene(root, 1280, 720, Color.BEIGE);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        stage.setHeight(720);
        stage.setWidth(1280);
        stage.setTitle("DocDuck");
        stage.setScene(scene);
        stage.show();

        // Hard coded logo as Image Library not done yet
//        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/docducklogo.png")));
//        logo.setLayoutX(390); 
//        logo.setLayoutY(80); 
//        logo.setFitWidth(500); 
//        logo.setPreserveRatio(true);
//        root.getChildren().add(logo);

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> builder
                .scaleNodes(stage.getWidth(), stage.getHeight());

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);

        // ORDER OF PROGRAM
        // Load up JavaFX
        // Needs to check if there are any xml files to display a slide or slideshow
        // If there are xml files, find ids of each and order them
        // Display ID 1 slide
        // If buttons, add in their actions, do they go to slide 2? etc.

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
