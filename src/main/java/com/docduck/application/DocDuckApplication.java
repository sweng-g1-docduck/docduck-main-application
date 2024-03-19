package com.docduck.application;

import com.docduck.application.parser.XMLReader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DocDuckApplication extends Application {

    public static Stage myStage;

    public DocDuckApplication() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Starting DocDuck Application");

        DocDuckApplication myApp = new DocDuckApplication();

        myStage = primaryStage;

        myStage.setScene(myApp.initialise());
        myStage.setTitle("DocDuck Application");
        myStage.show();

        // ORDER OF PROGRAM
        // Load up JavaFX
        // Needs to check if there are any xml files to display a slide or slideshow
        // If there are xml files, find ids of each and order them
        // Display ID 1 slide
        // If buttons, add in their actions, do they go to slide 2? etc.

        XMLReader myReader = new XMLReader("src/main/resources/loginPage.xml", "src/main/resources/Standard.xsd", true);
        myReader.readXML();
        myReader.printXMLData();

    }

    private Scene initialise() {

//        Image logo = new Image("./src/main/resources/docducklogo.png");

        Button button = new Button();
        // Setting text to the button
        button.setText("Sample Button");
        // Setting the location of the button
        button.setTranslateX(150);
        button.setTranslateY(60);
        button.setOnAction(e -> System.out.println("Hello World!"));
        // Setting the stage
        Pane root = new Pane(button);

        Scene scene = new Scene(root, 595, 150, Color.BEIGE);

        return scene;

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return myStage;
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
