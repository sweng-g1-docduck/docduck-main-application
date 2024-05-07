package com.docduck.application.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import com.docduck.application.files.FTPHandler;
import com.docduck.buttonlibrary.ButtonWrapper;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;

public class GUIBuilder {

    private Pane root;
    private static FTPHandler ftpHandler;
    private static GUIBuilder instance = null;
    private static XMLBuilder xmlBuilder;
    private static EventManager events;
    public double CURRENT_WINDOW_WIDTH = 1280;
    public double CURRENT_WINDOW_HEIGHT = 720;
    private Scale scale;
    protected Hashtable<String, Hashtable<String, Object>> xmlData = new Hashtable<>();

    private GUIBuilder(Pane root) {
        this.root = root;
        this.scale = new Scale();
    }

    public static GUIBuilder createInstance(Pane root) {

        if (instance == null) {
            instance = new GUIBuilder(root);
        }
        return instance;
    }

    public static GUIBuilder getInstance() {
        return instance;
    }

    public void updateInstances() {
        xmlBuilder = XMLBuilder.getInstance();
        events = EventManager.getInstance();
        ftpHandler = FTPHandler.getInstance();
    }

    public void setData(Hashtable<String, Hashtable<String, Object>> xmlData) {
        this.xmlData = xmlData;
    }

    public void StartPage() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: #1f5398");
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/docducklogo.png")));
        logo.setLayoutX(390);
        logo.setLayoutY(80);
        logo.setFitWidth(500);
        logo.setPreserveRatio(true);
        ButtonWrapper doc = new ButtonWrapper();
        ButtonWrapper xml = new ButtonWrapper();
        doc.setText("Load DocDuck Application");
        xml.setText("Load Application from XML Files");
        doc.setBackgroundColour("#fbb12eff");
        xml.setBackgroundColour("#fbb12eff");
        doc.setCornerRadius(10);
        xml.setCornerRadius(10);
        doc.setButtonWidth(210);
        xml.setButtonWidth(230);
        doc.setButtonHeight(70);
        xml.setButtonHeight(70);
        doc.setFontName("Arial");
        xml.setFontName("Arial");
        doc.setFontSize(14);
        xml.setFontSize(14);
        doc.setFontColour(Color.web("#292929"));
        xml.setFontColour(Color.web("#292929"));
        doc.setPositionX(400);
        xml.setPositionX(650);
        doc.setPositionY(470);
        xml.setPositionY(470);
        doc.setBorderWidth(2);
        xml.setBorderWidth(2);
        xml.setClickcolour(Color.web("#ffffffff"));
        xml.setHoverColour(Color.web("#ff8c00ff"));
        doc.setClickcolour(Color.web("#ffffffff"));
        doc.setHoverColour(Color.web("#ff8c00ff"));
        xml.setOnAction(events.getActionEvent("xmlPage"));
        doc.setOnAction(events.getActionEvent("loadApp"));
        root.getChildren().addAll(logo, doc, xml);
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    public void LoadFromXMLPage() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: #1f5398");
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/docducklogo.png")));
        logo.setLayoutX(390);
        logo.setLayoutY(80);
        logo.setFitWidth(500);
        logo.setPreserveRatio(true);
        ButtonWrapper xml = new ButtonWrapper();
        xml.setText("Choose PWS or DocDuck compliant XML File");
        xml.setBackgroundColour("#fbb12eff");
        xml.setCornerRadius(10);
        xml.setButtonWidth(400);
        xml.setButtonHeight(70);
        xml.setFontName("Arial");
        xml.setFontSize(14);
        xml.setFontColour(Color.web("#292929"));
        xml.setPositionX(440);
        xml.setPositionY(440);
        xml.setBorderWidth(2);
        xml.setClickcolour(Color.web("#ffffffff"));
        xml.setHoverColour(Color.web("#ff8c00ff"));
        xml.setOnAction(events.getActionEvent("chooseXML"));
        ButtonWrapper back = new ButtonWrapper();
        back.setText("Go Back");
        back.setBackgroundColour("#fbb12eff");
        back.setCornerRadius(10);
        back.setButtonWidth(80);
        back.setButtonHeight(40);
        back.setFontName("Arial");
        back.setFontSize(12);
        back.setFontColour(Color.web("#292929"));
        back.setPositionX(600);
        back.setPositionY(550);
        back.setBorderWidth(2);
        back.setClickcolour(Color.web("#ffffffff"));
        back.setHoverColour(Color.web("#ff8c00ff"));
        back.setOnAction(events.getActionEvent("goBack"));
        root.getChildren().addAll(logo, xml, back);
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    public void OfflinePage() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: #1f5398");
        Label text1 = new Label("Unable to connect to server. Would you like to continue offline?");
        text1.setFont(new Font("Arial", 30));
        text1.setStyle("-fx-text-fill: #FFFFFF");
        text1.setLayoutX(250);
        text1.setLayoutY(300);
        Label text2 = new Label("Any changes made to local files could be lost");
        text2.setFont(new Font("Arial", 30));
        text2.setStyle("-fx-text-fill: #FFFFFF");
        text2.setLayoutX(350);
        text2.setLayoutY(350);
        ButtonWrapper doc = new ButtonWrapper();
        ButtonWrapper xml = new ButtonWrapper();
        doc.setText("Load DocDuck Application Offline");
        xml.setText("Load Application from XML Files");
        doc.setBackgroundColour("#fbb12eff");
        xml.setBackgroundColour("#fbb12eff");
        doc.setCornerRadius(10);
        xml.setCornerRadius(10);
        doc.setButtonWidth(250);
        xml.setButtonWidth(230);
        doc.setButtonHeight(70);
        xml.setButtonHeight(70);
        doc.setFontName("Arial");
        xml.setFontName("Arial");
        doc.setFontSize(14);
        xml.setFontSize(14);
        doc.setFontColour(Color.web("#292929"));
        xml.setFontColour(Color.web("#292929"));
        doc.setPositionX(385);
        xml.setPositionX(650);
        doc.setPositionY(470);
        xml.setPositionY(470);
        doc.setBorderWidth(2);
        xml.setBorderWidth(2);
        xml.setClickcolour(Color.web("#ffffffff"));
        xml.setHoverColour(Color.web("#ff8c00ff"));
        doc.setClickcolour(Color.web("#ffffffff"));
        doc.setHoverColour(Color.web("#ff8c00ff"));
        xml.setOnAction(events.getActionEvent("xmlPage"));
        doc.setOnAction(events.getActionEvent("loadAppOffline"));
        root.getChildren().addAll(text1, text2, doc, xml);
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    public void LoginPage() {
        root.getChildren().clear();
        ArrayList<Node> nodes = xmlBuilder.buildSlide(1);
        root.getChildren().addAll(nodes);
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    public HBox Menu() {
        // create a HBox
        HBox hbox = new HBox(10);

        // setAlignment
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: #FFFFFF");
        hbox.prefWidth(1280);
        hbox.minWidth(1280);
        hbox.maxWidth(1280);

        // create a label
        Label label = new Label("This is a Menu example");

        // add label to hbox
        hbox.getChildren().add(label);

        // add buttons to HBox
        for (int i = 0; i < 5; i++) {
            ButtonWrapper b = new ButtonWrapper();
            b.setText("Button " + (i + 1));
            hbox.getChildren().add(b);
        }
        return hbox;
    }

    public void StatusPage() {
        SplitPane split = new SplitPane();
        Pane status = new Pane();
        status.getChildren().addAll(xmlBuilder.buildSlide(2));
        split.getItems().addAll(Menu(), status);
        root.getChildren().clear();
        root.getChildren().addAll(split);
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    public void scaleNodes(Parent container, double windowWidth, double windowHeight) {
        double WIDTH = 1280;
        double HEIGHT = 720;
        double widthScale = windowWidth / WIDTH;
        double heightScale = windowHeight / HEIGHT;
        ObservableList<Node> nodes = null;

        if (container instanceof Pane) {
            Pane pane = (Pane) container;
            nodes = pane.getChildren();
        }

        if (widthScale < heightScale) {
            scale.setX(widthScale);
            scale.setY(widthScale);
        }
        else {
            scale.setX(heightScale);
            scale.setY(heightScale);
        }

        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);

            if (!node.getTransforms().contains(scale)) {
                node.getTransforms().add(scale);
            }

            double newX = (node.getLayoutX() * (widthScale - 1));
            node.setTranslateX(newX);

            double newY = (node.getLayoutY() * (heightScale - 1));
            node.setTranslateY(newY);
        }
        CURRENT_WINDOW_WIDTH = windowWidth;
        CURRENT_WINDOW_HEIGHT = windowHeight;
    }
}