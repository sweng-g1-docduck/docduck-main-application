package com.docduck.application;

import com.docduck.application.files.FTPHandler;
import com.docduck.application.gui.EventManager;
import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.gui.XMLBuilder;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DocDuckApplication extends Application {

    private static Pane root;
    private static XMLBuilder xmlBuilder;
    private static GUIBuilder guiBuilder;
    private static FTPHandler ftpHandler;
    private static EventManager eventManager;
    
    @Override
    public void start(Stage stage) {
        System.out.println("Starting DocDuck Application");
        root = new Pane();
        xmlBuilder = XMLBuilder.createInstance(root);
        guiBuilder = GUIBuilder.createInstance(root);
        ftpHandler = FTPHandler.createInstance();
        eventManager = EventManager.createInstance(root, this.getHostServices(), stage);
        guiBuilder.updateInstances();
        eventManager.updateInstances();
        xmlBuilder.updateInstances();
        ftpHandler.updateInstances();
        Scene scene = new Scene(root, 1280, 720, Color.BEIGE);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        stage.setHeight(720);
        stage.setWidth(1280);
        stage.setTitle("DocDuck");
        stage.setScene(scene);
        stage.show();
        guiBuilder.StartPage();

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> guiBuilder
                .scaleNodes(root, stage.getWidth(), stage.getHeight());

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
        
        // ORDER OF PROGRAM
        // Load up JavaFX
        // Needs to check if there are any xml files to display a slide or slideshow
        // If there are xml files, find ids of each and order them
        // Display ID 1 slide
        // If buttons, add in their actions, do they go to slide 2? etc.

    }
    
    @Override
    public void stop() {
        // Executed when the application shuts down
    	ftpHandler.stopFileUpdates();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
