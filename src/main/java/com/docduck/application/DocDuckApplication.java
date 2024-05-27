package com.docduck.application;

import com.docduck.application.files.FTPHandler;
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

    public static final int MIN_WINDOW_HEIGHT = 759;
    public static final int MIN_WINDOW_WIDTH = 1296;
    public static final int DEFAULT_WINDOW_HEIGHT = 759;
    public static final int DEFAULT_WINDOW_WIDTH = 1296;
    private static Pane root;
    private static GUIBuilder guiBuilder;
    private static FTPHandler ftpHandler;

    @Override
    public void start(Stage stage) {

        System.out.println("Starting DocDuck Application");

        root = new Pane();
        XMLBuilder xmlBuilder = XMLBuilder.createInstance(root);
        guiBuilder = GUIBuilder.createInstance(root);
        ftpHandler = FTPHandler.createInstance();
        EventManager eventManager = EventManager.createInstance(root, this.getHostServices(), stage);
        guiBuilder.updateInstances();
        eventManager.updateInstances();
        xmlBuilder.updateInstances();
        ftpHandler.updateInstances();

        Scene scene = new Scene(root, 1280, 720, Color.BEIGE);

        stage.setMinHeight(MIN_WINDOW_HEIGHT);
        stage.setMinWidth(MIN_WINDOW_WIDTH);
        stage.setHeight(DEFAULT_WINDOW_HEIGHT);
        stage.setWidth(DEFAULT_WINDOW_WIDTH);

        stage.setTitle("DocDuck");
        stage.setScene(scene);

        stage.show();
        guiBuilder.buildLoginPage();

        guiBuilder.displayPage("LOGIN");
//        guiBuilder.StartPage();
//        ftpHandler.startApp();

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> guiBuilder.scaleNodes(root,
                stage.getWidth(), stage.getHeight());

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
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
