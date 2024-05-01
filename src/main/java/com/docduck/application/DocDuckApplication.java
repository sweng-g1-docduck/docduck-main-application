package com.docduck.application;

import com.docduck.application.gui.EventManager;
import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.gui.XMLBuilder;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DocDuckApplication extends Application {

    private static Pane root;

    @Override
    public void start(Stage stage) {
        System.out.println("Starting DocDuck Application");
        root = new Pane();
        XMLBuilder xmlBuilder = XMLBuilder.createInstance(root);
        GUIBuilder guiBuilder = GUIBuilder.createInstance(root);
        EventManager eventManager = EventManager.createInstance(root, this.getHostServices(), stage);
        guiBuilder.updateInstances();
        eventManager.updateInstances();
        xmlBuilder.updateInstances();
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

    public static void main(String[] args) {
        launch(args);
    }
}
