package com.docduck.application;

import com.docduck.application.data.Machine;
import com.docduck.application.files.FTPHandler;
import com.docduck.application.gui.EventManager;
import com.docduck.application.gui.GUIBuilder;
import com.docduck.application.gui.XMLBuilder;
import com.docduck.application.xmlreader.XMLReader;
import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBoxPassword;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
/*
 * Zhihao Ma
 */
public class EventManagerTests {
	
    private EventManager eventManager;
    private Pane root;
    private HostServices hostServices;
    private Stage stage;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX platform
        Platform.startup(() -> {});
    }

    public void start(Stage stage) {
        this.stage = stage;
        this.root = new Pane();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();

        this.hostServices = new HostServices() {
            @Override
            public void showDocument(String uri) {
                // Implement behavior for testing
                System.out.println("Opening URL: " + uri);
            }

            @Override
            public void showDocument(String uri, boolean b) {
                // Implement behavior for testing
                System.out.println("Opening URL: " + uri + " with boolean " + b);
            }

            @Override
            public String getCodeBase() {
                return null;
            }

            @Override
            public String getDocumentBase() {
                return null;
            }
        };

        eventManager = EventManager.createInstance(root, hostServices, stage);

        XMLBuilder.setInstance(new XMLBuilder());
        GUIBuilder.setInstance(new GUIBuilder());
        FTPHandler.setInstance(new FTPHandler());

        eventManager.updateInstances();
    }

    @Test
    void testCreateInstance() {
        assertNotNull(eventManager);
        assertEquals(eventManager, EventManager.getInstance());
    }

    @Test
    void testGetActionEventXmlPage() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("xmlPage");
            event.handle(new ActionEvent());
            assertEquals("XML Page Loaded", GUIBuilder.getInstance().getLastAction());
        });
    }

    @Test
    void testGetActionEventGoBack() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("goBack");
            event.handle(new ActionEvent());
            assertEquals("Start Page", GUIBuilder.getInstance().getLastAction());
        });
    }

    @Test
    void testGetActionEventLoadApp() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("loadApp");
            event.handle(new ActionEvent());
            assertTrue(FTPHandler.getInstance().isAppStarted());
        });
    }

    @Test
    void testGetActionEventLoadAppOffline() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("loadAppOffline");
            event.handle(new ActionEvent());
            assertEquals("Login Page", GUIBuilder.getInstance().getLastAction());
            assertNotNull(XMLBuilder.getInstance().getData());
        });
    }

    @Test
    void testGetActionEventChooseXML() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("chooseXML");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
            File selectedFile = new File("src/test/resources/valid.xml");

            // Simulate file choosing
            if (selectedFile != null) {
                event.handle(new ActionEvent());
                assertNotNull(XMLBuilder.getInstance().getData());
            }
        });
    }

    @Test
    void testGetActionEventChooseMedia() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("chooseMedia");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Media File", "*.png"));
            File selectedFile = new File("src/test/resources/sample.png");

            // Simulate file choosing
            if (selectedFile != null) {
                event.handle(new ActionEvent());
                assertTrue(FTPHandler.getInstance().isFileUploaded());
            }
        });
    }

    @Test
    void testGetActionEventSignUp() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("signup");
            event.handle(new ActionEvent());
            // Check output or any side effect
        });
    }

    @Test
    void testGetActionEventSignIn() {
        Platform.runLater(() -> {
            TextBoxField username = new TextBoxField();
            TextBoxPassword password = new TextBoxPassword();
            root.getChildren().addAll(username, password);

            username.setText("admin");
            password.setText("password");

            EventHandler<ActionEvent> event = eventManager.getActionEvent("signin");
            event.handle(new ActionEvent());

            assertEquals(0, password.getBorderWidth());
        });
    }

    @Test
    void testGetActionEventStatusPage() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("statusPage");
            event.handle(new ActionEvent());
            assertEquals("STATUS", GUIBuilder.getInstance().getCurrentPage());
        });
    }

    @Test
    void testGetActionEventReportPage() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("reportPage");
            event.handle(new ActionEvent());
            assertEquals("REPORT", GUIBuilder.getInstance().getCurrentPage());
        });
    }

    @Test
    void testGetActionEventAdminPage() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getActionEvent("adminPage");
            event.handle(new ActionEvent());
            assertEquals("ADMIN", GUIBuilder.getInstance().getCurrentPage());
        });
    }

    @Test
    void testGetActionEventWithMachine() {
        Platform.runLater(() -> {
            Machine machine = new Machine();
            EventHandler<ActionEvent> event = eventManager.getActionEvent("reportPage", machine);
            event.handle(new ActionEvent());
            assertEquals(machine, GUIBuilder.getInstance().getLastDisplayedMachine());
        });
    }

    @Test
    void testGetKeyEventSearch() {
        Platform.runLater(() -> {
            TextBoxField textField = new TextBoxField();
            textField.setText("search query");

            EventHandler<KeyEvent> event = eventManager.getKeyEvent("Search...");
            KeyEvent keyEvent = new KeyEvent(textField, textField, KeyEvent.KEY_TYPED, "", "", null, false, false, false, false);

            event.handle(keyEvent);
            assertEquals("search query", textField.getText());
        });
    }

    @Test
    void testGetHyperlinkEvent() {
        Platform.runLater(() -> {
            String url = "https://example.com";
            EventHandler<ActionEvent> event = eventManager.getHyperlinkEvent(url);
            event.handle(new ActionEvent());
            // Check output or any side effect
        });
    }

    @Test
    void testGetSlideEvent() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> event = eventManager.getSlideEvent(1, 10);
            event.handle(new ActionEvent());
            // Check output or any side effect
        });
    }
}
