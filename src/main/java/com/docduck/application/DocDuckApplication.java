package com.docduck.application;

import java.io.File;

import com.docduck.application.data.User;
import com.docduck.application.files.FTPHandler;
import com.docduck.application.gui.EventManager;
import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.gui.XMLBuilder;

import com.docduck.application.xmldom.JDOMDataHandlerNotInitialised;
import com.docduck.application.xmldom.XMLJDOMDataHandler;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DocDuckApplication extends Application {

    private static Pane root;
    private static GUIBuilder guiBuilder;
    private static FTPHandler ftpHandler;

    @Override
    public void start(Stage stage) {

        System.out.println("Starting DocDuck Application");

        // XMLJDOM example method
        jdom2Example();

        File f = new File("/DocDuckDataExample.xml");
        System.out.println(f);

        root = new Pane();
        XMLBuilder xmlBuilder = XMLBuilder.createInstance(root);
        guiBuilder = GUIBuilder.createInstance(root);
        ftpHandler = FTPHandler.createInstance();
        EventManager eventManager = EventManager.createInstance(root, this.getHostServices(), stage);
        guiBuilder.updateInstances();
        eventManager.updateInstances();
        xmlBuilder.updateInstances();
        ftpHandler.updateInstances();

        Scene scene = new Scene(root, 1280, 720, Color.web("245494"));

        stage.setMinHeight(759);
        stage.setMinWidth(1296);
        stage.setHeight(759);
        stage.setWidth(1296);

        System.out.println();

        stage.setTitle("DocDuck");
        stage.setScene(scene);

        stage.show();
        guiBuilder.buildPages();

        guiBuilder.displayPage("STATUS");
//        guiBuilder.LoginPage();
//        ftpHandler.startApp();
        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> guiBuilder.scaleNodes(root,
                stage.getWidth(), stage.getHeight());

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);

    }

    /**
     * An example method to show how the JDOM2 API Implementation works for the
     * DocDuck Application
     * 
     * @author William-A-B
     */
    private void jdom2Example() {

        XMLJDOMDataHandler dom;

        try {
            dom = XMLJDOMDataHandler.getInstance();
        } catch (JDOMDataHandlerNotInitialised e) {
            e.printError();
            dom = XMLJDOMDataHandler.createNewInstance("DocDuckData.xml", "DocDuckSchema.xsd", true, true);
            dom.setupJDOM();
        }
        dom.addNewUser(new User("Billy", "billy", "password", "bob321@york.ac.uk", "OPERATOR"));
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
