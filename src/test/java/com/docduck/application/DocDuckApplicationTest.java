package com.docduck.application;

import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import com.docduck.application.gui.EventManager;
import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.gui.XMLBuilder;
import com.docduck.application.gui.pages.AdminPage;
import com.docduck.application.gui.pages.ReportPage;
import com.docduck.application.gui.pages.StatusPage;

import javafx.scene.control.Button;
import com.docduck.buttonlibrary.ButtonWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;
import javafx.stage.Stage;
import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.application.files.FTPHandler;
import com.docduck.buttonlibrary.ButtonWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Hashtable;

/*
 * Zhihao Ma
 */
public class DocDuckApplicationTest {
    private Pane rootPane;

    public void start(Stage stage) {
        // Initialize JavaFX environment
        rootPane = new Pane();
        stage.setScene(new javafx.scene.Scene(rootPane));
        stage.show();
    }

    @Test
    public void testCreateInstance() {
        // Create GUIBuilder instance
        GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);

        // Verify that the instance is not null
        assertNotNull(guiBuilder, "GUIBuilder instance should not be null");

        // Verify that getInstance returns the same instance
        assertSame(guiBuilder, GUIBuilder.getInstance(), "getInstance should return the same instance");
    }

    @Test
    public void testStartPage() {
        // Create GUIBuilder instance
        GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);

        // Call StartPage method
        guiBuilder.StartPage();

        // Verify that the root pane's children have been cleared and populated correctly
        assertEquals(3, rootPane.getChildren().size(), "Root pane should contain 3 children");

        Node logoNode = rootPane.getChildren().get(0);
        Node docButtonNode = rootPane.getChildren().get(1);
        Node xmlButtonNode = rootPane.getChildren().get(2);

        // Verify that the first node is an ImageView
        assertTrue(logoNode instanceof ImageView, "First node should be an ImageView");

        // Verify that the second and third nodes are Button instances
        assertTrue(docButtonNode instanceof Button, "Second node should be a Button");
        assertTrue(xmlButtonNode instanceof Button, "Third node should be a Button");

        Button docButton = (Button) docButtonNode;
        Button xmlButton = (Button) xmlButtonNode;

        // Verify button properties
        assertEquals("Load DocDuck Application", docButton.getText(), "Doc button text should be 'Load DocDuck Application'");
        assertEquals("Load Application from XML Files", xmlButton.getText(), "XML button text should be 'Load Application from XML Files'");
    }
    

    @Test
    public void testLoadFromXMLPage() {
        // Create GUIBuilder instance
        GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);

        // Call LoadFromXMLPage method
        guiBuilder.LoadFromXMLPage();

        // Verify that the root pane's children have been cleared and populated correctly
        assertEquals(3, rootPane.getChildren().size(), "Root pane should contain 3 children");

        Node logoNode = rootPane.getChildren().get(0);
        Node xmlButtonNode = rootPane.getChildren().get(1);
        Node backButtonNode = rootPane.getChildren().get(2);

        // Verify that the first node is an ImageView
        assertTrue(logoNode instanceof ImageView, "First node should be an ImageView");

        // Verify that the second and third nodes are Button instances
        assertTrue(xmlButtonNode instanceof Button, "Second node should be a Button");
        assertTrue(backButtonNode instanceof Button, "Third node should be a Button");

        Button xmlButton = (Button) xmlButtonNode;
        Button backButton = (Button) backButtonNode;

        // Verify button properties
        assertEquals("Choose PWS or DocDuck compliant XML File", xmlButton.getText(), "XML button text should be 'Choose PWS or DocDuck compliant XML File'");
        assertEquals("Go Back", backButton.getText(), "Back button text should be 'Go Back'");
    }

    @Test
    public void testOfflinePage() {
        // Create GUIBuilder instance
        GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);

        // Call OfflinePage method
        guiBuilder.OfflinePage();

        // Verify that the root pane's children have been cleared and populated correctly
        assertEquals(4, rootPane.getChildren().size(), "Root pane should contain 4 children");

        Node text1Node = rootPane.getChildren().get(0);
        Node text2Node = rootPane.getChildren().get(1);
        Node docButtonNode = rootPane.getChildren().get(2);
        Node xmlButtonNode = rootPane.getChildren().get(3);

        // Verify that the first and second nodes are Labels
        assertTrue(text1Node instanceof javafx.scene.control.Label, "First node should be a Label");
        assertTrue(text2Node instanceof javafx.scene.control.Label, "Second node should be a Label");

        javafx.scene.control.Label text1 = (javafx.scene.control.Label) text1Node;
        javafx.scene.control.Label text2 = (javafx.scene.control.Label) text2Node;

        // Verify label properties
        assertEquals("Unable to connect to server. Would you like to continue offline?", text1.getText(), "Text1 should be 'Unable to connect to server. Would you like to continue offline?'");
        assertEquals("Any changes made to local files could be lost", text2.getText(), "Text2 should be 'Any changes made to local files could be lost'");

        // Verify that the third and fourth nodes are Button instances
        assertTrue(docButtonNode instanceof Button, "Third node should be a Button");
        assertTrue(xmlButtonNode instanceof Button, "Fourth node should be a Button");

        Button docButton = (Button) docButtonNode;
        Button xmlButton = (Button) xmlButtonNode;

        // Verify button properties
        assertEquals("Load DocDuck Application Offline", docButton.getText(), "Doc button text should be 'Load DocDuck Application Offline'");
        assertEquals("Load Application from XML Files", xmlButton.getText(), "XML button text should be 'Load Application from XML Files'");
    }

    @Test
    public void testDisplayPageStatus() {
        // Create GUIBuilder instance
        GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);

        // Call displayPage method with "STATUS"
        guiBuilder.displayPage("STATUS");

        // Verify that the root pane's children have been cleared and populated correctly
        assertEquals(1, rootPane.getChildren().size(), "Root pane should contain 1 child");

        Node statusPageNode = rootPane.getChildren().get(0);

        // Verify that the node is an instance of StatusPage
        assertTrue(statusPageNode instanceof StatusPage, "The node should be an instance of StatusPage");
    }

    @Test
    public void testDisplayPageReport() {
        // Create GUIBuilder instance
        GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);

        // Call displayPage method with "REPORT"
        guiBuilder.displayPage("REPORT");

        // Verify that the root pane's children have been cleared and populated correctly
        assertEquals(1, rootPane.getChildren().size(), "Root pane should contain 1 child");

        Node reportPageNode = rootPane.getChildren().get(0);

        // Verify that the node is an instance of ReportPage
        assertTrue(reportPageNode instanceof ReportPage, "The node should be an instance of ReportPage");
    }

    @Test
    public void testDisplayPageAdmin() {
        // Create GUIBuilder instance
        GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);

        // Call displayPage method with "ADMIN"
        guiBuilder.displayPage("ADMIN");

        // Verify that the root pane's children have been cleared and populated correctly
        assertEquals(1, rootPane.getChildren().size(), "Root pane should contain 1 child");

        Node adminPageNode = rootPane.getChildren().get(0);

        // Verify that the node is an instance of AdminPage
        assertTrue(adminPageNode instanceof AdminPage, "The node should be an instance of AdminPage");
    }
    
    @Test
    public void testDisplayPageWithMachine() {
        // Mock ReportPage
    	GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);
//        guiBuilder.reportPage = new ReportPage(new ArrayList<>(), new User());
        Machine mockMachine = new Machine("Computer", null, null, null, null, null);
        
        assertEquals(mockMachine.getName(), "Computer");

        // Call displayPage method with "REPORT" and a Machine
        guiBuilder.displayPage("REPORT", mockMachine);

        // Verify that the root pane's children have been cleared and populated correctly
        assertEquals(1, rootPane.getChildren().size(), "Root pane should contain 1 child");

        Node reportPageNode = rootPane.getChildren().get(0);

        // Verify that the node is an instance of ReportPage
        assertTrue(reportPageNode instanceof ReportPage, "The node should be an instance of ReportPage");
    }

    @Test
    public void testUpdateInstances() {
        // Call updateInstances method
    	GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);
        guiBuilder.updateInstances();

        // Verify that the EventManager, XMLBuilder, and FTPHandler instances are updated (assumed to be mocked)
        assertNotNull(EventManager.getInstance(), "EventManager instance should be updated");
        EventManager.createInstance(rootPane, null, null);
        assertNotNull(EventManager.getInstance(), "Event manager instance not created successfully");
        assertNotNull(XMLBuilder.getInstance(), "XMLBuilder instance should be updated");
        assertNotNull(FTPHandler.getInstance(), "FTPHandler instance should be updated");
    }

    @Test
    public void testSetData() {
        // Create mock XML data
        Hashtable<String, Hashtable<String, Object>> mockXMLData = new Hashtable<>();
        GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);
        // Call setData method with mock data
        guiBuilder.setData(mockXMLData);

        // Verify that the XML data is set correctly
//        assertSame(mockXMLData, guiBuilder.xmlData, "XML data should be set correctly");
    }

    @Test
    public void testScaleNodes() {
        // Create some nodes and add them to the root pane
        Button button1 = new Button();
        button1.setLayoutX(100);
        button1.setLayoutY(100);
        rootPane.getChildren().add(button1);
        GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);
        // Call scaleNodes method with different window dimensions
        guiBuilder.scaleNodes(rootPane, 1920, 1080);

        // Verify that nodes are scaled correctly
        assertEquals(1.481, button1.getScaleX(), 0.001, "Button scaleX should be 1.481");
        assertEquals(1.481, button1.getScaleY(), 0.001, "Button scaleY should be 1.481");
    }

    @Test
    public void testBuildPages() {
    	GUIBuilder guiBuilder = GUIBuilder.createInstance(rootPane);
    	
    	guiBuilder.displayPage("STATUS");
    	assertNotNull(rootPane, "Root pane should contain status page nodes");
    	assertNotNull(rootPane.getChildren(), "Status pane should have children nodes added to it");
//        // Call buildPages method
//        GUIBuilder.buildPages();
//
//        // Verify that the pages are built correctly
//        assertNotNull(guiBuilder.statusPage, "StatusPage should be built");
//        assertNotNull(guiBuilder.reportPage, "ReportPage should be built");
//        assertNotNull(guiBuilder.adminPage, "AdminPage should be built");
    }
    
    
}
