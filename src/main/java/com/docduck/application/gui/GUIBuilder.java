package com.docduck.application.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.application.files.FTPHandler;
import com.docduck.application.gui.pages.Page;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import com.docduck.application.gui.pages.StatusPage;

public class GUIBuilder {

    private Pane root;
    private static FTPHandler ftpHandler;
    private static GUIBuilder instance = null;
    private static XMLBuilder xmlBuilder;
    private static EventManager events;
    public double CURRENT_WINDOW_WIDTH = 1296;
    public double CURRENT_WINDOW_HEIGHT = 759;
    private Scale scale;
    protected Hashtable<String, Hashtable<String, Object>> xmlData = new Hashtable<>();
    private BorderPane borderPane;
    private ComboBox<String> comboBox;
    private ArrayList<Machine> machines;
    private User user;
    private ArrayList<Page> pageList = new ArrayList<Page>();

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

    private HBox drawMenuBar() {

        HBox menuBar = new HBox();
        menuBar.setPadding(new Insets(15, 15, 15, 15));
        menuBar.setBackground(
                new Background(new BackgroundFill(Color.web("245494"), new CornerRadii(10), new Insets(5))));
        menuBar.setSpacing(15);
        menuBar.setPrefSize(1280, 90);
        menuBar.setAlignment(Pos.CENTER_LEFT);

        ButtonWrapper overviewButton = new ButtonWrapper();
        overviewButton.setCornerRadius(5);
        overviewButton.setButtonWidth(200);
        overviewButton.setButtonHeight(60);
        overviewButton.setFontName("Arial");
        overviewButton.setText("Machine Overview");
        overviewButton.setBackgroundColour("fbb12eff");
        overviewButton.setClickcolour(Color.WHITE);
        overviewButton.setHoverColour("#ff8c00ff");
        overviewButton.setPositionX(150);
        overviewButton.setPositionY(30);
        overviewButton.setFontColour("#ffffffff");
        overviewButton.setFontSize(20);
        overviewButton.removeBorder();
        overviewButton.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println(root.getHeight());
//                System.out.println(root.getWidth());
            }
        }));

        ButtonWrapper logOutButton = new ButtonWrapper();
        logOutButton.setCornerRadius(5);
        logOutButton.setButtonWidth(120);
        logOutButton.setButtonHeight(60);
        logOutButton.setFontName("Arial");
        logOutButton.setText("Log Out");
        logOutButton.setBackgroundColour("fbb12eff");
        logOutButton.setClickcolour(Color.WHITE);
        logOutButton.setHoverColour("#ff8c00ff");
        logOutButton.setPositionX(150);
        logOutButton.setPositionY(30);
        logOutButton.setFontColour("#ffffffff");
        logOutButton.setFontSize(20);
        logOutButton.removeBorder();

        menuBar.getChildren().addAll(overviewButton);

        if (user.getRole().equals("ENGINEER") || user.getRole().equals("ADMIN")) {

            ButtonWrapper reportButton = new ButtonWrapper();
            reportButton.setCornerRadius(5);
            reportButton.setButtonWidth(240);
            reportButton.setButtonHeight(60);
            reportButton.setFontName("Arial");
            reportButton.setText("Maintainance Reports");
            reportButton.setBackgroundColour("fbb12eff");
            reportButton.setClickcolour(Color.WHITE);
            reportButton.setHoverColour("#ff8c00ff");
            reportButton.setPositionX(290);
            reportButton.setPositionY(30);
            reportButton.setFontColour("#ffffffff");
            reportButton.setFontSize(20);
            reportButton.removeBorder();

            ButtonWrapper partButton = new ButtonWrapper();
            partButton.setCornerRadius(5);
            partButton.setButtonWidth(160);
            partButton.setButtonHeight(60);
            partButton.setFontName("Arial");
            partButton.setText("Part Search");
            partButton.setBackgroundColour("fbb12eff");
            partButton.setClickcolour(Color.WHITE);
            partButton.setHoverColour("#ff8c00ff");
            partButton.setPositionX(290);
            partButton.setPositionY(30);
            partButton.setFontColour("#ffffffff");
            partButton.setFontSize(20);
            partButton.removeBorder();
            menuBar.getChildren().addAll(reportButton, partButton);
        }

        if (user.getRole().equals("ADMIN")) {

            ButtonWrapper settingsButton = new ButtonWrapper();
            settingsButton.setCornerRadius(5);
            settingsButton.setButtonWidth(120);
            settingsButton.setButtonHeight(60);
            settingsButton.setFontName("Arial");
            settingsButton.setText("Settings");
            settingsButton.setBackgroundColour("fbb12eff");
            settingsButton.setClickcolour(Color.WHITE);
            settingsButton.setHoverColour("#ff8c00ff");
            settingsButton.setPositionX(290);
            settingsButton.setPositionY(30);
            settingsButton.setFontColour("#ffffffff");
            settingsButton.setFontSize(20);
            settingsButton.removeBorder();
            menuBar.getChildren().add(settingsButton);
        }
        Pane spacer = new Pane();
        spacer.setPrefWidth(1000);

        menuBar.getChildren().addAll(spacer, logOutButton);

        return menuBar;

    }

    private HBox drawMachineBar() {
        HBox contents = new HBox();
        contents.setPadding(new Insets(10, 10, 10, 15));
        contents.setBackground(new Background(
                new BackgroundFill(Color.web("245494"), new CornerRadii(10, 10, 0, 0, false), new Insets(0))));
        contents.setSpacing(10);
        contents.setPrefSize(950, 45);
        contents.setMaxSize(950, 45);
        contents.setAlignment(Pos.CENTER_LEFT);
        return contents;

    }

    private void setupRoomSelect() {
        comboBox = new ComboBox<String>();
        comboBox.getItems().addAll("Room 1", "Room 2", "Room 3");
        comboBox.setValue("Room 1");
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                drawMachineButtons();

            }
        });
    }

    private HBox drawRoomSelect() {
        HBox contents = new HBox();
        contents.setPadding(new Insets(10, 10, 10, 15));
        contents.setBackground(new Background(
                new BackgroundFill(Color.web("245494"), new CornerRadii(0, 0, 0, 0, false), new Insets(0))));
        contents.setSpacing(10);
        contents.setPrefSize(950, 45);
        contents.setMaxSize(950, 45);
        contents.setAlignment(Pos.CENTER_LEFT);
        contents.getChildren().add(comboBox);
        BorderPane.setAlignment(contents, Pos.CENTER_LEFT);
        return contents;
    }

    private void drawMachineButtons() {

        BorderPane back = new BorderPane();
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(950, 530);
        s1.setMaxSize(950, 530);

        FlowPane pane = new FlowPane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, new CornerRadii(0), new Insets(0))));
        pane.setPrefWidth(935);
        pane.setPrefHeight(528);
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setVgap(15);
        pane.setHgap(15);

        Image img = new Image("/docducklogo.png");

        for (Machine machine : machines) {

            if (machine.getRoom().equals(comboBox.getValue())) {

                ImageView view1 = new ImageView(img);
                view1.setFitWidth(290);
                view1.setPreserveRatio(true);

                ButtonWrapper button = new ButtonWrapper();
                button.setButtonHeight(200);
                button.setButtonWidth(290);
                button.setGraphic(view1);

                button.setCornerRadius(20);
                button.setFontColour(Color.WHITE);
                button.setFontSize(20);
                button.setContentDisplay(ContentDisplay.TOP);
                switch (machine.getStatus()) {
                case "ONLINE":
                    button.setBackgroundColour(Color.LIMEGREEN);
                    button.setHoverColour(Color.GREEN);
                    button.setClickcolour(Color.DARKGREEN);
                    break;
                case "MAINTENANCE":
                    button.setBackgroundColour(Color.ORANGE);
                    button.setHoverColour(Color.DARKORANGE);
                    button.setClickcolour(Color.ORANGERED);
                    if (user.getRole().equals("OPERATOR")) {
                        button.setDisable(true);
                    }
                    break;
                case "OFFLINE":
                    button.setBackgroundColour(Color.RED);
                    button.setHoverColour(Color.DARKRED);
                    button.setClickcolour(Color.INDIANRED);
                    if (user.getRole().equals("OPERATOR")) {
                        button.setDisable(true);
                    }
                    break;
                }

                button.setText(machine.getName());
                button.setOnAction((new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (machine.getStatus().equals("ONLINE")) {
                            drawReport(machine);
                        }
                    }
                }));
                pane.getChildren().add(button);

            }
        }

        s1.setContent(pane);
        back.setTop(drawMachineBar());
        back.setCenter(drawRoomSelect());
        back.setBottom(s1);
        BorderPane.setMargin(back, new Insets(5));
        borderPane.setCenter(back);
    }

    private void drawReport(Machine machine) {

//        TranslateTransition fadeOut = new TranslateTransition(); 
//        fadeOut.setDuration(Duration.millis(500)); 
//        fadeOut.setNode(borderPane.getRight()); 
//        fadeOut.setByX(350); 
//        fadeOut.setCycleCount(1); 
//        fadeOut.setAutoReverse(false); 
//        fadeOut.play();  

        VBox reportBox = new VBox();
        reportBox.setAlignment(Pos.TOP_CENTER);
        reportBox.setPrefWidth(320);
        reportBox.setPadding(new Insets(20, 10, 10, 10));
        reportBox.setSpacing(10);
        reportBox
                .setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(10), new Insets(5))));

        TextBox title = new TextBox();

        title.setBoxWidth(300);
        title.setBoxHeight(50);
        title.setText(machine.getName());
        title.setFontColour(Color.BLACK);
        title.setFontName("Calibri");
        title.setFontSize(200);
        title.setTextAlignment(TextAlignment.CENTER);
        title.removeBackground();
        title.removeBorder();

        TextArea area = new TextArea();
        area.setMaxWidth(300);
        area.setMaxHeight(300);
        area.setWrapText(true);
        area.setPromptText("Decribe the machine fault");

        ButtonWrapper mediaBtn = new ButtonWrapper();
        mediaBtn.setCornerRadius(5);
        mediaBtn.setButtonWidth(200);
        mediaBtn.setButtonHeight(40);
        mediaBtn.setFontName("Arial");
        mediaBtn.setText("Attach Media");
        mediaBtn.setBackgroundColour("fbb12eff");
        mediaBtn.setClickcolour(Color.WHITE);
        mediaBtn.setHoverColour("#ff8c00ff");
        mediaBtn.setPositionX(150);
        mediaBtn.setPositionY(30);
        mediaBtn.setFontColour("#ffffffff");
        mediaBtn.setFontSize(20);
        mediaBtn.removeBorder();
        mediaBtn.setOnAction(events.getActionEvent("chooseMedia"));

        ButtonWrapper submitBtn = new ButtonWrapper();
        submitBtn.setCornerRadius(5);
        submitBtn.setButtonWidth(200);
        submitBtn.setButtonHeight(40);
        submitBtn.setFontName("Arial");
        submitBtn.setText("Submit Report");
        submitBtn.setBackgroundColour("fbb12eff");
        submitBtn.setClickcolour(Color.WHITE);
        submitBtn.setHoverColour("#ff8c00ff");
        submitBtn.setPositionX(150);
        submitBtn.setPositionY(30);
        submitBtn.setFontColour("#ffffffff");
        submitBtn.setFontSize(20);
        submitBtn.removeBorder();
        submitBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (area.getText().equals("")) {
                    area.setPromptText("A description of the fault is required");
                }
                else {
                    // Submit report to XML
                    machine.setStatus("OFFLINE");
                    borderPane.setRight(null);
                    drawMachineButtons();
                    for (Machine machine : machines) {
                        System.out.println(machine.toString());
                    }

                }
            }
        }));

        ButtonWrapper cancelBtn = new ButtonWrapper();
        cancelBtn.setCornerRadius(5);
        cancelBtn.setButtonWidth(200);
        cancelBtn.setButtonHeight(40);
        cancelBtn.setFontName("Arial");
        cancelBtn.setText("Cancel Report");
        cancelBtn.setBackgroundColour("fbb12eff");
        cancelBtn.setClickcolour(Color.WHITE);
        cancelBtn.setHoverColour("#ff8c00ff");
        cancelBtn.setPositionX(150);
        cancelBtn.setPositionY(30);
        cancelBtn.setFontColour("#ffffffff");
        cancelBtn.setFontSize(20);
        cancelBtn.removeBorder();
        cancelBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                borderPane.setRight(null);
            }
        }));

        reportBox.getChildren().addAll(title, area, mediaBtn, submitBtn, cancelBtn);
        borderPane.setRight(reportBox);

//        TranslateTransition translateTransition = new TranslateTransition();
//        translateTransition.setDuration(Duration.millis(500));
//        translateTransition.setNode(reportBox);
//        translateTransition.setFromX(350);
//        translateTransition.setByX(-350);
//        translateTransition.setCycleCount(1);
//        translateTransition.setAutoReverse(false);
//        fadeOut.setOnFinished(e -> {borderPane.setRight(reportBox); translateTransition.play();}); 
    }

    public void drawStatusPage() {

        borderPane = new BorderPane();

        populateMachineData();
        user = new User("Bob", "bob@york.ac.uk", "OPERATOR");
        setupRoomSelect();
        drawRoomSelect();

        root.getChildren().clear();
        borderPane.setTop(drawMenuBar());
        drawMachineButtons();

        root.getChildren().addAll(borderPane);
        root.setBackground(new Background(new BackgroundFill(Color.web("4083db"), new CornerRadii(0), new Insets(0))));

        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    private void populateMachineData() {
        machines = new ArrayList<Machine>();
        machines.add(new Machine("Machine One", "Room 1", "ONLINE"));
        machines.add(new Machine("Machine Two", "Room 2", "ONLINE"));
        machines.add(new Machine("Machine Three", "Room 1", "MAINTENANCE"));
        machines.add(new Machine("Machine Four", "Room 2", "ONLINE"));
        machines.add(new Machine("Machine Five", "Room 1", "OFFLINE"));
        machines.add(new Machine("Machine Six", "Room 2", "ONLINE"));
        machines.add(new Machine("Machine Seven", "Room 1", "ONLINE"));
        machines.add(new Machine("Machine Eight", "Room 2", "ONLINE"));
        machines.add(new Machine("Machine Nine", "Room 1", "ONLINE"));
    }

    public void scaleNodes(Parent container, double windowWidth, double windowHeight) {
        double WIDTH = 1296;
        double HEIGHT = 759;
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
    
    // Builds all pages and adds to scene...?
    // Perhaps add to a list of panes
    public void buildPages() {
        createDesiredPages();
        
        for( Page page : pageList ) {
            Object aListOfPagePanes = page.buildPage();
            //aListOfNodes.doSomethingToAddToPane
        }
    }
    private void createDesiredPages() {
        pageList.add(new StatusPage());
        // add more pages
    }
}