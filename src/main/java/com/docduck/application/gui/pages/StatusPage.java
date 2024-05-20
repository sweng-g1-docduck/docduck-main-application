package com.docduck.application.gui.pages;

import java.util.ArrayList;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
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
import javafx.scene.text.TextAlignment;

public class StatusPage extends Page {

    private ComboBox<String> roomSelectBox;
    private ArrayList<Machine> machines;

    public StatusPage() {
        super();
        buildPage();
    }

    /**
     * Draws the top section of the machine selection bar
     * 
     * @return HBOX for the top of the bar
     * @author jrb617
     */
    private HBox drawMachineBar() {
        HBox contents = new HBox();
        contents.setPadding(new Insets(10, 10, 10, 15));
        contents.setBackground(new Background(
                new BackgroundFill(Color.web("245494"), new CornerRadii(10, 10, 0, 0, false), new Insets(0))));
        contents.setSpacing(10);
        contents.setPrefSize(950, 45);
        contents.setMaxSize(950, 45);
        contents.setAlignment(Pos.CENTER_LEFT);
        
        TextBox title = new TextBox();
        title.setText("Machine Overview");
        title.setFontSize(20);
        title.setFontColour(Color.WHITE);
        title.setBoxWidth(200);
        
        contents.getChildren().add(title);
        return contents;

    }

    /**
     * Initialises the room selection dropdown box
     * 
     *  REQUIRES INTGRATION
     *  @author jrb617
     */
    private void setupRoomSelect() {
        roomSelectBox = new ComboBox<String>();
        roomSelectBox.getItems().addAll("Room 1", "Room 2", "Room 3");
        roomSelectBox.setValue("Room 1");
        roomSelectBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                drawMachineButtons();

            }
        });
    }

    /**
     * Draws the room selection dropdown box
     * 
     * @return HBox Containing the room selection dropdown
     * @author jrb617
     */
    private HBox drawRoomSelect() {
        HBox contents = new HBox();
        contents.setPadding(new Insets(10, 10, 10, 15));
        contents.setBackground(new Background(
                new BackgroundFill(Color.web("245494"), new CornerRadii(0, 0, 0, 0, false), new Insets(0))));
        contents.setSpacing(10);
        contents.setPrefSize(950, 45);
        contents.setMaxSize(950, 45);
        contents.setAlignment(Pos.CENTER_LEFT);
        contents.getChildren().add(roomSelectBox);
        BorderPane.setAlignment(contents, Pos.CENTER_LEFT);
        return contents;
    }

    /**
     * Draws the scrollable machine grid
     * 
     * REQUIRES INTGRATION
     * @author jrb617
     */
    private void drawMachineButtons() {

        BorderPane machineBackground = new BorderPane();
        ScrollPane machineScroll = new ScrollPane();
        machineScroll.setPrefSize(950, 530);
        machineScroll.setMaxSize(950, 530);

        FlowPane machineGrid = new FlowPane();
        machineGrid.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, new CornerRadii(0), new Insets(0))));
        machineGrid.setPrefWidth(935);
        machineGrid.setPrefHeight(528);
        machineGrid.setPadding(new Insets(15, 15, 15, 15));
        machineGrid.setVgap(15);
        machineGrid.setHgap(15);

        Image img = new Image("/docducklogo.png");

        for (Machine machine : machines) {

            if (machine.getRoom().equals(roomSelectBox.getValue())) {

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
                            drawMachineInfo(machine);
                        }
                    }
                }));
                machineGrid.getChildren().add(button);

            }
        }

        machineScroll.setContent(machineGrid);
        machineBackground.setTop(drawMachineBar());
        machineBackground.setCenter(drawRoomSelect());
        machineBackground.setBottom(machineScroll);
        BorderPane.setMargin(machineBackground, new Insets(5));
        setCenter(machineBackground);
    }

    /**
     * Draws the machine report page
     * 
     * REQUIRES INTGRATION
     * @param machine
     * @author jrb617
     */
    private void drawReport(Machine machine) {

        /*
         * Experimental animation code
         */
//        TranslateTransition fadeOut = new TranslateTransition(); 
//        fadeOut.setDuration(Duration.millis(500)); 
//        fadeOut.setNode(getRight()); 
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

        TextBox machineName = new TextBox();

        machineName.setBoxWidth(300);
        machineName.setBoxHeight(50);
        machineName.setText(machine.getName());
        machineName.setFontColour(Color.BLACK);
        machineName.setFontName("Calibri");
        machineName.setFontSize(200);
        machineName.setTextAlignment(TextAlignment.CENTER);
        machineName.removeBackground();
        machineName.removeBorder();

        TextArea descriptionBox = new TextArea();
        descriptionBox.setMaxWidth(300);
        descriptionBox.setMaxHeight(300);
        descriptionBox.setWrapText(true);
        descriptionBox.setPromptText("Decribe the machine fault");

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
                if (descriptionBox.getText().equals("")) {
                    descriptionBox.setPromptText("A description of the fault is required");
                }
                else {
                    // Submit report to XML
                    machine.setStatus("OFFLINE");
                    setRight(null);
                    drawMachineButtons();

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
                setRight(null);
            }
        }));

        reportBox.getChildren().addAll(machineName, descriptionBox, mediaBtn, submitBtn, cancelBtn);
        setRight(reportBox);

        /*
         * Experimental animation code
         */
//        TranslateTransition translateTransition = new TranslateTransition();
//        translateTransition.setDuration(Duration.millis(500));
//        translateTransition.setNode(reportBox);
//        translateTransition.setFromX(350);
//        translateTransition.setByX(-350);
//        translateTransition.setCycleCount(1);
//        translateTransition.setAutoReverse(false);
//        fadeOut.setOnFinished(e -> {setRight(reportBox); translateTransition.play();}); 
    }

    /**
     * Draws the Machine Information Side Panel
     * 
     * REQUIRES INTGRATION
     * @param machine Name of machine selected
     * @author jrb617
     */
    private void drawMachineInfo(Machine machine) {
        VBox infoBox = new VBox();
        infoBox.setPrefWidth(320);
        infoBox.setMaxHeight(630);
        infoBox.setPadding(new Insets(20, 10, 10, 10));
        infoBox.setSpacing(10);
        infoBox
                .setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(10), new Insets(5))));

        TextBox machineName = new TextBox();

        machineName.setBoxWidth(300);
        machineName.setBoxHeight(50);
        machineName.setText(machine.getName());
        machineName.setFontColour(Color.BLACK);
        machineName.setFontName("Calibri");
        machineName.setFontSize(200);
        machineName.setTextAlignment(TextAlignment.CENTER);
        machineName.removeBackground();
        machineName.removeBorder();

        
        BorderPane dataPane = new BorderPane();
        dataPane.setPrefWidth(310);
        
        dataPane.setPadding(new Insets(20, 10, 10, 10));
        dataPane.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(10), new Insets(5))));
        
        VBox dataBox = new VBox();
        dataBox.setAlignment(Pos.TOP_LEFT);
        dataBox.setSpacing(10);
        
        TextBox textBox = new TextBox();
        textBox.setBoxWidth(280);
        textBox.setLineSpacing(4);
        textBox.setFontSize(15);
        textBox.setText("Serial Number: " + machine.getSerialNumber() + "\nLocation: " + machine.getRoom());

        Hyperlink datasheet = new Hyperlink("Datasheet");
        datasheet.setOnAction(events.getHyperlinkEvent("https://www.google.com/"));
        datasheet.setTextFill(Color.BLACK);
        Hyperlink purchaseLink = new Hyperlink("Purchase Link");
        purchaseLink.setOnAction(events.getHyperlinkEvent("https://www.google.com/"));
        purchaseLink.setTextFill(Color.BLACK);

        Pane spacer = new Pane();
        spacer.setPrefHeight(1000);

        VBox btnBox = new VBox();
        btnBox.setAlignment(Pos.TOP_CENTER);
        btnBox.setSpacing(10);
        btnBox.setPadding(new Insets(0,0,10,0));
        
        ButtonWrapper reportBtn = new ButtonWrapper();
        reportBtn.setCornerRadius(5);
        reportBtn.setButtonWidth(200);
        reportBtn.setButtonHeight(40);
        reportBtn.setFontName("Arial");
        reportBtn.setText("Generate Report");
        reportBtn.setBackgroundColour("fbb12eff");
        reportBtn.setClickcolour(Color.WHITE);
        reportBtn.setHoverColour("#ff8c00ff");
        reportBtn.setPositionX(150);
        reportBtn.setPositionY(30);
        reportBtn.setFontColour("#ffffffff");
        reportBtn.setFontSize(20);
        reportBtn.removeBorder();
        reportBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drawReport(machine);
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
                setRight(null);
            }
        }));
        dataBox.getChildren().addAll(textBox, datasheet, purchaseLink);
        dataPane.setTop(dataBox);
        btnBox.getChildren().addAll(reportBtn, cancelBtn);
        dataPane.setBottom(btnBox);
        dataPane.setMaxHeight(540);
        dataPane.setMinHeight(540);
        infoBox.getChildren().addAll(machineName,dataPane);
        setRight(infoBox);
    }

    /**
     * Creates and adds all nodes to the root BorderPane
     * REQUIRES INTGRATION
     * 
     * @author jrb617
     */
    @Override
    public void buildPage() {
        populateMachineData();
        user = new User("Bob", "bob@york.ac.uk", "ADMIN","password");
        setupRoomSelect();
        drawRoomSelect();
        setTop(drawMenuBar());
        drawMachineButtons();
    }

    /**
     * Temporary Method to populate MAchine class, to be removed during integration
     * 
     * @author jrb617
     */
    private void populateMachineData() {
        machines = new ArrayList<Machine>();
        machines.add(new Machine("Machine One", "Room 1", "ONLINE", "1","1"));
        machines.add(new Machine("Machine Two", "Room 2", "ONLINE", "2", "2"));
        machines.add(new Machine("Machine Three", "Room 1", "MAINTENANCE", "3", "2"));
        machines.add(new Machine("Machine Four", "Room 2", "ONLINE", "4", "2"));
        machines.add(new Machine("Machine Five", "Room 1", "OFFLINE", "5", "2"));
        machines.add(new Machine("Machine Six", "Room 2", "ONLINE", "6", "2"));
        machines.add(new Machine("Machine Seven", "Room 1", "ONLINE", "7", "2"));
        machines.add(new Machine("Machine Eight", "Room 2", "ONLINE", "8", "2"));
        machines.add(new Machine("Machine Nine", "Room 1", "ONLINE", "9", "2"));
    }

}
