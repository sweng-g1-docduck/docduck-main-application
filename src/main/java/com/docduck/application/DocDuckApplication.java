package com.docduck.application;

import org.w3c.dom.NodeList;

import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.utils.InvalidID;
import com.docduck.application.xmldom.XMLDOM;
import com.docduck.application.xmldom.XMLDOMDataHandler;
import com.docduck.application.xmlreader.XMLReader;

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

        xmlExample();

//        FTPHandler FTPHandler = new FTPHandler();
//        FTPHandler.downloadAllFiles();
        XMLReader myReader = new XMLReader("src/main/resources/docduck-application-slides.xml",
                "src/main/resources/DocDuckStandardSchema.xsd", true);
        myReader.readXML();
//        myReader.printXMLData();

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
//        stage.show();

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> builder
                .scaleNodes(stage.getWidth(), stage.getHeight());

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void xmlExample() {
        XMLDOM xmlDom = new XMLDOM("src/main/resources/DocDuckDataExample.xml",
                "src/main/resources/DocDuckStandardSchema_WithData.xsd", true);

        try {
            xmlDom.setupDOM();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        xmlDom.printDOMTree();

//        System.out.println(xmlDom.findNodeValue("docduckData"));
//        System.out.println(xmlDom.getText(xmlDom.findSubNode("name", xmlDom.getParentNode("userData", 1))));

        try {
            System.out.println(xmlDom.getChildNodeValue("name", xmlDom.getNode("machine", 1)));
            System.out.println(xmlDom.getChildNodeValue("name", xmlDom.getNode("component", 1)));
            System.out.println(xmlDom.getChildNodeValue("title", xmlDom.getNode("report", 1)));
            System.out.println(xmlDom.getChildNodeValue("description", xmlDom.getNode("report", 1)));
            System.out.println(xmlDom.getChildNodeValue("name", xmlDom.getNode("user", 2)));
            System.out.println(xmlDom.getChildNodeValue("role", xmlDom.getNode("user", 2)));
        }
        catch (InvalidID e) {

            if (e.isIDInvalid() == true) {
                System.out.println("The provided ID is not valid");
            }
        }

        XMLDOMDataHandler data = new XMLDOMDataHandler("src/main/resources/DocDuckDataExample.xml",
                "src/main/resources/DocDuckStandardSchema_WithData.xsd", true);

        try {
            data.setupDOM();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        NodeList machineOneData = data.getMachineData(1);

        data.printNodeListValues((data.getComponentPartData(1, 1, 1)));

        System.out.println(data.getNodeListNodeValue(machineOneData, "name"));

        data.printNodeListValues(data.getPartData(2));

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
