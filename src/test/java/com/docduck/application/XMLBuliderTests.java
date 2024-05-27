package com.docduck.application;

import com.docduck.application.gui.XMLBuilder;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.graphicslibrary.*;
import com.docduck.textlibrary.TextBox;
import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBoxPassword;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Zhihao Ma 
 */
public class XMLBuliderTests {

    private XMLBuilder xmlBuilder;
    private Pane root;
    private Hashtable<String, Hashtable<String, Object>> testData;

    @BeforeEach
    void setUp() {
        root = new Pane();
        xmlBuilder = XMLBuilder.createInstance(root);
        xmlBuilder.updateInstances();

        // Prepare some test data
        testData = new Hashtable<>();
        Hashtable<String, Object> textBoxData = new Hashtable<>();
        textBoxData.put("hasBackground", true);
        textBoxData.put("cornerRadius", 5.0);
        textBoxData.put("backgroundColour", "#FF0000");
        textBoxData.put("textOrigin", "CORNER");
        textBoxData.put("width", 100.0);
        textBoxData.put("height", 50.0);
        textBoxData.put("font", "Arial");
        textBoxData.put("text", "Sample Text");
        textBoxData.put("fontSize", 12);
        textBoxData.put("fontColour", "#000000");
        textBoxData.put("characterSpacing", 1.0);
        textBoxData.put("lineSpacing", 1.5);
        textBoxData.put("borderColour", "#000000");
        textBoxData.put("borderWidth", 1.0);
        textBoxData.put("xCoordinate", 50);
        textBoxData.put("yCoordinate", 50);
        testData.put("textBox-1-1", textBoxData);

        xmlBuilder.setData(testData);
    }

    @Test
    void testCreateInstance() {
        assertNotNull(xmlBuilder, "XMLBuilder instance should be created");
    }

    @Test
    void testSetData() {
        Hashtable<String, Hashtable<String, Object>> newData = new Hashtable<>();
        xmlBuilder.setData(newData);
        // Use reflection or modify visibility for testing purposes
        // We can't directly access xmlData if it's private without a getter
        // assertEquals(newData, xmlBuilder.xmlData, "Data should be set correctly");
        // Instead, you can verify through method behavior that uses xmlData
        xmlBuilder.buildSlide(1);
        assertEquals(0, root.getChildren().size(), "Root pane should be empty if no data is present");
    }

    @Test
    void testBuildCustomXML() {
        xmlBuilder.buildCustomXML(1, 2);
        assertEquals(3, root.getChildren().size(), "Root pane should contain three children");
    }

    @Test
    void testBuildSlide() {
        ArrayList<Node> nodes = xmlBuilder.buildSlide(1);
        assertEquals(2, nodes.size(), "Slide should contain two nodes");
    }

    @Test
    void testBuildTextBoxes() {
        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Node> result = xmlBuilder.buildTextBoxes(nodeList, 1);
        assertEquals(2, result.size(), "TextBoxes should be built and added to the node list");
    }

    @Test
    void testBuildShapes() {
        Hashtable<String, Object> shapeData = new Hashtable<>();
        shapeData.put("type", "rectangle");
        shapeData.put("shapeColour", "#FF0000FF");
        shapeData.put("shapeRotation", 0.0);
        shapeData.put("xCoordinate", 100);
        shapeData.put("yCoordinate", 100);
        shapeData.put("shapeScale", 1.0);
        shapeData.put("width", 50.0);
        shapeData.put("height", 30.0);
        shapeData.put("borderColour", "#000000");
        shapeData.put("borderWidth", 1.0);
        testData.put("shape-1-1", shapeData);
        xmlBuilder.setData(testData);

        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Node> result = xmlBuilder.buildShapes(nodeList, 1);
        assertEquals(1, result.size(), "Shapes should be built and added to the node list");
    }

    @Test
    void testBuildImages() {
        Hashtable<String, Object> imageData = new Hashtable<>();
        imageData.put("imageURL", "/path/to/image.png");
        imageData.put("xCoordinate", 100);
        imageData.put("yCoordinate", 100);
        imageData.put("imageScale", 100.0);
        testData.put("image-1-1", imageData);
        xmlBuilder.setData(testData);

        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Node> result = xmlBuilder.buildImages(nodeList, 1);
        assertEquals(1, result.size(), "Images should be built and added to the node list");
    }

    @Test
    void testBuildAudio() {
        // Add test data for audio and validate
        // Use System.out prints or mocks if necessary for validation
    }

    @Test
    void testBuildVideos() {
        // Add test data for videos and validate
    }

    @Test
    void testBuildButtons() {
        Hashtable<String, Object> buttonData = new Hashtable<>();
        buttonData.put("hasBackground", true);
        buttonData.put("backgroundColour", "#FF0000");
        buttonData.put("text", "Click Me");
        buttonData.put("hoverColour", "#00FF00");
        buttonData.put("clickColour", "#0000FF");
        buttonData.put("borderColour", "#000000");
        buttonData.put("borderWidth", 1.0);
        buttonData.put("width", 100.0);
        buttonData.put("height", 50.0);
        buttonData.put("cornerRadius", 5);
        buttonData.put("font", "Arial");
        buttonData.put("fontColour", "#000000");
        buttonData.put("fontSize", 12);
        buttonData.put("xCoordinate", 100);
        buttonData.put("yCoordinate", 100);
        buttonData.put("eventID", "clickEvent");
        testData.put("button-1-1", buttonData);
        xmlBuilder.setData(testData);

        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Node> result = xmlBuilder.buildButtons(nodeList, 1);
        assertEquals(1, result.size(), "Buttons should be built and added to the node list");
    }

    @Test
    void testBuildHyperlinks() {
        Hashtable<String, Object> hyperlinkData = new Hashtable<>();
        hyperlinkData.put("URL", "https://example.com");
        hyperlinkData.put("fontColour", "#FF0000");
        hyperlinkData.put("font", "Arial");
        hyperlinkData.put("fontSize", "12");
        hyperlinkData.put("lineSpacing", 1.5);
        hyperlinkData.put("xCoordinate", 100);
        hyperlinkData.put("yCoordinate", 100);
        hyperlinkData.put("text", "Click here");
        testData.put("hyperlink-1-1", hyperlinkData);
        xmlBuilder.setData(testData);

        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Node> result = xmlBuilder.buildHyperlinks(nodeList, 1);
        assertEquals(1, result.size(), "Hyperlinks should be built and added to the node list");
    }

    @Test
    void testBuildBackgroundColours() {
        Hashtable<String, Object> backgroundColourData = new Hashtable<>();
        backgroundColourData.put("colour", "#FF0000");
        testData.put("backgroundColour-1-1", backgroundColourData);
        xmlBuilder.setData(testData);

        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Node> result = xmlBuilder.buildBackgroundColours(nodeList, 1);
        assertEquals(0, result.size(), "Background colours should not add to the node list");
        assertEquals("-fx-background-color: #FF0000", root.getStyle(), "Background color should be set");
    }

    @Test
    void testBuildTextFields() {
        Hashtable<String, Object> textFieldData = new Hashtable<>();
        textFieldData.put("password", false);
        textFieldData.put("width", 200.0);
        textFieldData.put("height", 30.0);
        textFieldData.put("font", "Arial");
        textFieldData.put("fontColour", "#000000");
        textFieldData.put("fontSize", 14);
        textFieldData.put("borderColour", "#000000");
        textFieldData.put("borderWidth", 1.0);
        textFieldData.put("cornerRadius", 5);
        textFieldData.put("backgroundColour", "#FFFFFF");
        textFieldData.put("promptTextColour", "#CCCCCC");
        textFieldData.put("highlightColour", "#FF0000");
        textFieldData.put("fontHighlightColour", "#FFFFFF");
        textFieldData.put("xCoordinate", 150);
        textFieldData.put("yCoordinate", 150);
        textFieldData.put("promptText", "Enter text");
        textFieldData.put("initialText", "Sample text");
        textFieldData.put("hoverColour", "#00FF00");
        textFieldData.put("selectColour", "#0000FF");
        testData.put("textField-1-1", textFieldData);
        xmlBuilder.setData(testData);

        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Node> result = xmlBuilder.buildTextFields(nodeList, 1);
        assertEquals(1, result.size(), "Text fields should be built and added to the node list");
    }
}
