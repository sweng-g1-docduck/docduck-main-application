package com.docduck.application.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import com.docduck.application.files.FTPHandler;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBox;
import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBox.Origin;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

    private Pane MenuBar() {
        
        Rectangle border = new Rectangle();
        border.setWidth(1280);
        border.setHeight(90);
        border.setFill(Color.LIGHTGRAY);
        
        Pane pane = new Pane();
        ButtonWrapper statusButton = new ButtonWrapper();
        statusButton.setCornerRadius(5);
        statusButton.setButtonWidth(120);
        statusButton.setButtonHeight(30);
        statusButton.setFontName("Arial");
        statusButton.setText("Status Overview");
        statusButton.setBackgroundColour("fbb12eff");
        statusButton.setClickcolour(Color.WHITE);
        statusButton.setHoverColour("#ff8c00ff");
        statusButton.setPositionX(150);
        statusButton.setPositionY(30);
        statusButton.setFontColour("#ffffffff");
        statusButton.setFontSize(12);
        statusButton.removeBorder();
        
        ButtonWrapper testButton = new ButtonWrapper();
        testButton.setCornerRadius(5);
        testButton.setButtonWidth(80);
        testButton.setButtonHeight(30);
        testButton.setFontName("Arial");
        testButton.setText("Test Page");
        testButton.setBackgroundColour("fbb12eff");
        testButton.setClickcolour(Color.WHITE);
        testButton.setHoverColour("#ff8c00ff");
        testButton.setPositionX(290);
        testButton.setPositionY(30);
        testButton.setFontColour("#ffffffff");
        testButton.setFontSize(12);
        testButton.removeBorder();
        
        pane.getChildren().addAll(border,statusButton,testButton);
        return pane;
   
    }
    
    private ScrollPane CreateMachines() {
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(960, 630);
        
        
        
        /**
         * Scrollpane stuff
         */
        s1.setLayoutY(90);
        
        Pane pane = new Pane();
        pane.setPrefSize(620, 680);

        
        Image img = new Image("/docducklogo.png");
        ImageView view1 = new ImageView(img);
        view1.setFitWidth(300);
        view1.setPreserveRatio(true);
        
        ButtonWrapper button = new ButtonWrapper();
        button.setPositionX(10);
        button.setPositionY(10);
        button.setButtonHeight(200);
        button.setButtonWidth(300);
        button.setGraphic(view1);
        button.setBackgroundColour(Color.BLUE);
        button.setCornerRadius(20);
        button.setText("Machine One");
        button.setFontColour(Color.WHEAT);
        button.setFontSize(20);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setHoverColour(Color.CADETBLUE);
        button.setClickcolour(Color.AQUAMARINE);
        
        ImageView view2 = new ImageView(img);
        view2.setFitWidth(300);
        view2.setPreserveRatio(true);
        
        ButtonWrapper button2 = new ButtonWrapper();
        button2.setPositionX(320);
        button2.setPositionY(10);
        button2.setButtonHeight(200);
        button2.setButtonWidth(300);
        button2.setGraphic(view2);
        button2.setBackgroundColour(Color.BLUE);
        button2.setCornerRadius(20);
        button2.setText("Machine Two");
        button2.setFontColour(Color.WHEAT);
        button2.setFontSize(20);
        button2.setContentDisplay(ContentDisplay.TOP);
        button2.setHoverColour(Color.CADETBLUE);
        button2.setClickcolour(Color.AQUAMARINE);
        
        ImageView view3 = new ImageView(img);
        view3.setFitWidth(300);
        view3.setPreserveRatio(true);
        
        ButtonWrapper button3 = new ButtonWrapper();
        button3.setPositionX(630);
        button3.setPositionY(10);
        button3.setButtonHeight(200);
        button3.setButtonWidth(300);
        button3.setGraphic(view3);
        button3.setBackgroundColour(Color.BLUE);
        button3.setCornerRadius(20);
        button3.setText("Machine Three");
        button3.setFontColour(Color.WHEAT);
        button3.setFontSize(20);
        button3.setContentDisplay(ContentDisplay.TOP);
        button3.setHoverColour(Color.CADETBLUE);
        button3.setClickcolour(Color.AQUAMARINE);
        
        ImageView view4 = new ImageView(img);
        view4.setFitWidth(300);
        view4.setPreserveRatio(true);
        
        ButtonWrapper button4 = new ButtonWrapper();
        button4.setPositionX(10);
        button4.setPositionY(220);
        button4.setButtonHeight(200);
        button4.setButtonWidth(300);
        button4.setGraphic(view4);
        button4.setBackgroundColour(Color.BLUE);
        button4.setCornerRadius(20);
        button4.setText("Machine Four");
        button4.setFontColour(Color.WHEAT);
        button4.setFontSize(20);
        button4.setContentDisplay(ContentDisplay.TOP);
        button4.setHoverColour(Color.CADETBLUE);
        button4.setClickcolour(Color.AQUAMARINE);
        
        ImageView view5 = new ImageView(img);
        view5.setFitWidth(300);
        view5.setPreserveRatio(true);
        
        ButtonWrapper button5 = new ButtonWrapper();
        button5.setPositionX(320);
        button5.setPositionY(220);
        button5.setButtonHeight(200);
        button5.setButtonWidth(300);
        button5.setGraphic(view5);
        button5.setBackgroundColour(Color.BLUE);
        button5.setCornerRadius(20);
        button5.setText("Machine Five");
        button5.setFontColour(Color.WHEAT);
        button5.setFontSize(20);
        button5.setContentDisplay(ContentDisplay.TOP);
        button5.setHoverColour(Color.CADETBLUE);
        button5.setClickcolour(Color.AQUAMARINE);
        
        ImageView view6 = new ImageView(img);
        view6.setFitWidth(300);
        view6.setPreserveRatio(true);
        
        ButtonWrapper button6 = new ButtonWrapper();
        button6.setPositionX(630);
        button6.setPositionY(220);
        button6.setButtonHeight(200);
        button6.setButtonWidth(300);
        button6.setGraphic(view6);
        button6.setBackgroundColour(Color.BLUE);
        button6.setCornerRadius(20);
        button6.setText("Machine Six");
        button6.setFontColour(Color.WHEAT);
        button6.setFontSize(20);
        button6.setContentDisplay(ContentDisplay.TOP);
        button6.setHoverColour(Color.CADETBLUE);
        button6.setClickcolour(Color.AQUAMARINE);
        
        ImageView view7 = new ImageView(img);
        view7.setFitWidth(300);
        view7.setPreserveRatio(true);
        
        ButtonWrapper button7 = new ButtonWrapper();
        button7.setPositionX(10);
        button7.setPositionY(430);
        button7.setButtonHeight(200);
        button7.setButtonWidth(300);
        button7.setGraphic(view7);
        button7.setBackgroundColour(Color.BLUE);
        button7.setCornerRadius(20);
        button7.setText("Machine Seven");
        button7.setFontColour(Color.WHEAT);
        button7.setFontSize(20);
        button7.setContentDisplay(ContentDisplay.TOP);
        button7.setHoverColour(Color.CADETBLUE);
        button7.setClickcolour(Color.AQUAMARINE);
        
        ImageView view8 = new ImageView(img);
        view8.setFitWidth(300);
        view8.setPreserveRatio(true);
        
        ButtonWrapper button8 = new ButtonWrapper();
        button8.setPositionX(320);
        button8.setPositionY(430);
        button8.setButtonHeight(200);
        button8.setButtonWidth(300);
        button8.setGraphic(view8);
        button8.setBackgroundColour(Color.BLUE);
        button8.setCornerRadius(20);
        button8.setText("Machine Eight");
        button8.setFontColour(Color.WHEAT);
        button8.setFontSize(20);
        button8.setContentDisplay(ContentDisplay.TOP);
        button8.setHoverColour(Color.CADETBLUE);
        button8.setClickcolour(Color.AQUAMARINE);
        
        ImageView view9 = new ImageView(img);
        view9.setFitWidth(300);
        view9.setPreserveRatio(true);
        
        ButtonWrapper button9 = new ButtonWrapper();
        button9.setPositionX(630);
        button9.setPositionY(430);
        button9.setButtonHeight(200);
        button9.setButtonWidth(300);
        button9.setGraphic(view9);
        button9.setBackgroundColour(Color.BLUE);
        button9.setCornerRadius(20);
        button9.setText("Machine Nine");
        button9.setFontColour(Color.WHEAT);
        button9.setFontSize(20);
        button9.setContentDisplay(ContentDisplay.TOP);
        button9.setHoverColour(Color.CADETBLUE);
        button9.setClickcolour(Color.AQUAMARINE);
        
        pane.getChildren().addAll(button, button2, button3, button4, button5, button6, button7, button8, button9);
        s1.setContent(pane);
        return s1;
    }
    
    private Pane CreateReport() {
        Pane pane = new Pane();
        Rectangle background = new Rectangle(960,90,320,630);
        background.setArcHeight(20);
        background.setArcWidth(20);
        background.setFill(Color.LIGHTSLATEGRAY);
        
        TextBox title = new TextBox();
        title.setPositionX(970);
        title.setPositionY(100);
        title.setBoxWidth(300);
        title.setBoxHeight(50);
        title.setText("Machine X");
        title.setFontColour(Color.BLACK);
        title.setFontName("Calibri");
//        title.setTextOrigin(Origin.CENTRE);
        title.setFontSize(200);
        title.setTextAlignment(TextAlignment.CENTER);
        title.removeBackground();
        title.removeBorder();
        
        TextBoxField info = new TextBoxField(970,160,"Enter Information");
        info.setBoxWidth(300);
        info.setBoxHeight(300);
        info.setAlignment(Pos.TOP_LEFT);
        info.setFontSize(15);
        info.setCornerRadius(20);

        
        TextArea area = new TextArea();
        area.setLayoutX(970);
        area.setLayoutY(160);
        area.setMaxWidth(300);
        area.setMaxHeight(300);
        area.setWrapText(true);
        
        pane.getChildren().addAll(title.returnBackground(),title,area);
        return pane;
    }
    
    public void StatusPage() {
//        SplitPane split = new SplitPane();
        Pane status = new Pane();
        status.getChildren().addAll(xmlBuilder.buildSlide(2));
//        split.getItems().addAll(Menu(), status);
        root.getChildren().clear();
        
        Rectangle r = new Rectangle(960, 10);
        root.getChildren().addAll(MenuBar(),CreateMachines());
        root.getChildren().addAll(status);
        
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