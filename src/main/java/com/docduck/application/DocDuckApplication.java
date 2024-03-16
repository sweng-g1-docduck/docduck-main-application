package com.docduck.application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DocDuckApplication extends Application {

    public DocDuckApplication() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        DocDuckApplication myApp = new DocDuckApplication();

        primaryStage.setScene(myApp.initialise());
        primaryStage.setTitle("DocDuck");
        primaryStage.show();
    }

    private Scene initialise() {

        Button button = new Button();
        // Setting text to the button
        button.setText("Sample Button");
        // Setting the location of the button
        button.setTranslateX(150);
        button.setTranslateY(60);
        button.setOnAction(e -> System.out.println("Hello World!"));
        // Setting the stage
        Group root = new Group(button);
        Scene scene = new Scene(root, 595, 150, Color.BEIGE);

        return scene;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
