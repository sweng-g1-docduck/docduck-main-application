package com.docduck.application.gui.pages;

import java.util.ArrayList;

import com.docduck.application.data.Machine;
import com.docduck.application.data.Report;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
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

public class StatusPage extends Page {

    private ComboBox<String> roomSelectBox;
    private final int reportWidth = 320;
    private final int machineBoxWidth = 1270 - reportWidth;
    private Machine currentMachine;

    private final Color onlineButtonColour = Color.LIMEGREEN;
    private final Color onlineHoverColour = Color.GREEN;
    private final Color onlineClickColour = Color.DARKGREEN;

    private final Color maintenanceButtonColour = Color.ORANGE;
    private final Color maintenanceHoverColour = Color.DARKORANGE;
    private final Color maintenanceClickColour = Color.ORANGERED;

    private final Color offlineButtonColour = Color.RED;
    private final Color offlineHoverColour = Color.DARKRED;
    private final Color offlineClickColour = Color.INDIANRED;

    private final Color infoTextColour = lightTextColour;

    public StatusPage(ArrayList<Machine> machines, User user) {
        super(machines, user);

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
        int machineBoxHeight = 45;
        
        contents.setPadding(new Insets(10, 10, 10, 15));
        contents.setBackground(
                new Background(new BackgroundFill(barColour, new CornerRadii(10, 10, 0, 0, false), new Insets(0))));
        contents.setSpacing(10);
        contents.setPrefSize(machineBoxWidth, machineBoxHeight);
        contents.setMaxSize(machineBoxWidth, machineBoxHeight);
        contents.setAlignment(Pos.CENTER_LEFT);

        TextBox title = new TextBox();
        title.setText("Machine Overview");
        title.setFontSize(20);
        title.setFontColour(lightTextColour);
        title.setBoxWidth(200);

        contents.getChildren().add(title);
        return contents;

    }

    /**
     * Initialises the room selection dropdown box
     * <p>
     * REQUIRES INTGRATION
     * 
     * @author jrb617
     */
    private void setupRoomSelect() {
        
        ArrayList<String> allLocations= new ArrayList<>();
        for (Machine machine: machines) {
            if (!allLocations.contains(machine.getLocation())){
                allLocations.add(machine.getLocation());
            }
        }

        allLocations.sort(String::compareToIgnoreCase);
        
        roomSelectBox = new ComboBox<>();
        roomSelectBox.getItems().add("All");
        roomSelectBox.getItems().addAll(allLocations);
        roomSelectBox.setValue("All");
        roomSelectBox.valueProperty().addListener((ov, t, t1) -> drawMachineButtons());
        
    }

    /**
     * Draws the Bottom section of the machine selection bar containing the dropdown
     * menu
     * 
     * @return HBox Containing the room selection dropdown
     * @author jrb617
     */
    private HBox drawRoomSelect() {
        HBox contents = new HBox();
        contents.setPadding(new Insets(10, 10, 10, 15));
        contents.setBackground(
                new Background(new BackgroundFill(barColour, new CornerRadii(0, 0, 0, 0, false), new Insets(0))));
        contents.setSpacing(10);
        contents.setPrefSize(machineBoxWidth, 45);
        contents.setMaxSize(machineBoxWidth, 45);
        contents.setAlignment(Pos.CENTER_LEFT);
        contents.getChildren().add(roomSelectBox);
        BorderPane.setAlignment(contents, Pos.CENTER_LEFT);
        return contents;
    }

    /**
     * Draws the scrollable machine grid
     * <p>
     * REQUIRES INTGRATION
     * 
     * @author jrb617
     */
    public void drawMachineButtons() {

        BorderPane machineBackground = new BorderPane();
        ScrollPane machineScroll = new ScrollPane();
        machineScroll.setPrefSize(machineBoxWidth, 530);
        machineScroll.setMaxSize(machineBoxWidth, 530);

        FlowPane machineGrid = new FlowPane();
        machineGrid
                .setBackground(new Background(new BackgroundFill(backgroundColour, new CornerRadii(0), new Insets(0))));
        machineGrid.setPrefWidth(machineBoxWidth - 15);
        machineGrid.setPrefHeight(528);
        machineGrid.setPadding(new Insets(15, 15, 15, 15));
        machineGrid.setVgap(15);
        machineGrid.setHgap(15);

        Image img = new Image("/docducklogo.png");

        ArrayList<ButtonWrapper> buttons = new ArrayList<>();
        for (Machine machine : machines) {

            if (machine.getLocation().equals(roomSelectBox.getValue()) || roomSelectBox.getValue().equals("All")) {

                ImageView view1 = new ImageView(img);
                double buttonWidth = (machineBoxWidth - 75) / 3;
                view1.setFitWidth(buttonWidth);
                view1.setPreserveRatio(true);

                ButtonWrapper button = new ButtonWrapper();
                button.setButtonHeight(200);
                button.setButtonWidth(buttonWidth);
                button.setGraphic(view1);

                button.setCornerRadius(20);
                button.setFontColour(lightTextColour);
                button.setFontSize(20);
                button.setContentDisplay(ContentDisplay.TOP);
                switch (machine.getStatus()) {
                case "ONLINE":
                    button.setBackgroundColour(onlineButtonColour);
                    button.setHoverColour(onlineHoverColour);
                    button.setClickcolour(onlineClickColour);
                    break;
                case "MAINTENANCE":
                    button.setBackgroundColour(maintenanceButtonColour);
                    button.setHoverColour(maintenanceHoverColour);
                    button.setClickcolour(maintenanceClickColour);
                    if (user.getRole().equals("OPERATOR")) {
                        button.setDisable(true);
                    }
                    break;
                case "OFFLINE":
                    button.setBackgroundColour(offlineButtonColour);
                    button.setHoverColour(offlineHoverColour);
                    button.setClickcolour(offlineClickColour);
                    if (user.getRole().equals("OPERATOR")) {
                        button.setDisable(true);
                    }
                    break;
                }

                button.setText(machine.getName());

                if (machine.getStatus().equals("ONLINE")) {
                    button.setOnAction((event -> {
                        for (ButtonWrapper button1 : buttons) {
                            if (button1.getBackground().getFills().get(0).getFill().toString()
                                    .equals(onlineClickColour.toString())) {
                                button1.setBackgroundColour(onlineButtonColour);
                                button1.setBorderWidth(1);
                            }
                        }
                        button.setBackgroundColour(onlineClickColour);
                        button.setBorderWidth(3);
                        if (currentMachine != machine) {
                            currentMachine = machine;
                            drawMachineInfo(machine);
                        }

                    }));
                }

                else if (machine.getStatus().equals("OFFLINE") || machine.getStatus().equals("MAINTENANCE")) {
                    button.setOnAction(events.getActionEvent("reportPage", machine));
                }

                buttons.add(button);

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
     * <p>
     * REQUIRES INTGRATION
     * 
     * @param machine A machine in the database
     * @author jrb617
     */
    private void drawReport(Machine machine) {

        VBox reportBox = new VBox();
        reportBox.setAlignment(Pos.TOP_CENTER);
        reportBox.setPrefWidth(reportWidth);
        reportBox.setPadding(new Insets(20, 10, 10, 10));
        reportBox.setSpacing(10);
        reportBox.setBackground(
                new Background(new BackgroundFill(backgroundColour, new CornerRadii(10), new Insets(5))));

        TextBox machineName = new TextBox();

        machineName.setBoxWidth(reportWidth - 20);
        machineName.setBoxHeight(50);
        machineName.setText(machine.getName());
        machineName.setFontColour(darkTextColour);
        machineName.setFontName(fontName);
        machineName.setFontSize(200);
        machineName.setTextAlignment(TextAlignment.CENTER);
        machineName.removeBackground();
        machineName.removeBorder();

        TextArea descriptionBox = new TextArea();
        descriptionBox.setMaxWidth(reportWidth - 20);
        descriptionBox.setMaxHeight(reportWidth - 20);
        descriptionBox.setWrapText(true);
        descriptionBox.setPromptText("Decribe the machine fault");

        ButtonWrapper mediaBtn = new ButtonWrapper();
        mediaBtn.setCornerRadius(5);
        mediaBtn.setButtonWidth(reportWidth - 120);
        mediaBtn.setButtonHeight(40);
        mediaBtn.setFontName(fontName);
        mediaBtn.setText("Attach Media");
        mediaBtn.setBackgroundColour(btnColour);
        mediaBtn.setClickcolour(btnClickColour);
        mediaBtn.setHoverColour(btnHoverColour);
        mediaBtn.setPositionX(150);
        mediaBtn.setPositionY(30);
        mediaBtn.setFontColour(lightTextColour);
        mediaBtn.setFontSize(20);
        mediaBtn.removeBorder();
        mediaBtn.setOnAction(events.getActionEvent("chooseMedia"));

        ButtonWrapper submitBtn = new ButtonWrapper();
        submitBtn.setCornerRadius(5);
        submitBtn.setButtonWidth(reportWidth - 120);
        submitBtn.setButtonHeight(40);
        submitBtn.setFontName(fontName);
        submitBtn.setText("Submit Report");
        submitBtn.setBackgroundColour(btnColour);
        submitBtn.setClickcolour(btnClickColour);
        submitBtn.setHoverColour(btnHoverColour);
        submitBtn.setPositionX(150);
        submitBtn.setPositionY(30);
        submitBtn.setFontColour(lightTextColour);
        submitBtn.setFontSize(20);
        submitBtn.removeBorder();
        submitBtn.setOnAction((event -> {
            if (descriptionBox.getText().isEmpty()) {
                descriptionBox.setPromptText("A description of the fault is required");
            }
            else {
                // Submit report to XML
                machine.setStatus("OFFLINE");
                setRight(null);
                Report report = new Report(machineBoxWidth, user, descriptionBox.getText(), fontName, fontName);
                machine.addReport(report);
                drawMachineButtons();

            }
        }));

        ButtonWrapper cancelBtn = new ButtonWrapper();
        cancelBtn.setCornerRadius(5);
        cancelBtn.setButtonWidth(reportWidth - 120);
        cancelBtn.setButtonHeight(40);
        cancelBtn.setFontName(fontName);
        cancelBtn.setText("Cancel Report");
        cancelBtn.setBackgroundColour(btnColour);
        cancelBtn.setClickcolour(btnClickColour);
        cancelBtn.setHoverColour(btnHoverColour);
        cancelBtn.setPositionX(150);
        cancelBtn.setPositionY(30);
        cancelBtn.setFontColour(lightTextColour);
        cancelBtn.setFontSize(20);
        cancelBtn.removeBorder();
        cancelBtn.setOnAction((event -> setRight(null)));

        reportBox.getChildren().addAll(machineName, descriptionBox, mediaBtn, submitBtn, cancelBtn);
        setRight(reportBox);

    }

    /**
     * Draws the Machine Information Side Panel
     * <p>
     * REQUIRES INTGRATION
     * 
     * @param machine Name of machine selected
     * @author jrb617
     */
    private void drawMachineInfo(Machine machine) {
        VBox infoBox = new VBox();
        infoBox.setPrefWidth(reportWidth);
        infoBox.setMaxHeight(630);
        infoBox.setPadding(new Insets(20, 10, 10, 10));
        infoBox.setSpacing(10);
        infoBox.setBackground(new Background(new BackgroundFill(backgroundColour, new CornerRadii(10), new Insets(5))));

        TextBox machineName = new TextBox();

        machineName.setBoxWidth(reportWidth - 20);
        machineName.setBoxHeight(50);
        machineName.setText(machine.getName());
        machineName.setFontColour(infoTextColour);
        machineName.setFontName(fontName);
        machineName.setFontSize(200);
        machineName.setTextAlignment(TextAlignment.CENTER);
        machineName.removeBackground();
        machineName.removeBorder();

        BorderPane dataPane = new BorderPane();
        dataPane.setPrefWidth(reportWidth - 10);

        dataPane.setPadding(new Insets(20, 10, 10, 10));
        dataPane.setBackground(new Background(new BackgroundFill(boxColour, new CornerRadii(10), new Insets(5))));

        VBox dataBox = new VBox();
        dataBox.setAlignment(Pos.TOP_LEFT);
        dataBox.setSpacing(10);

        Label serialNum = new Label("Serial Number: " + machine.getSerialNumber());
        serialNum.setFont(new Font(fontName, smallFontSize));
        serialNum.setTextFill(infoTextColour);

        Label location = new Label("Location: " + machine.getLocation());
        location.setFont(new Font(fontName, smallFontSize));
        location.setTextFill(infoTextColour);

        Hyperlink datasheet = new Hyperlink("Datasheet");
        datasheet.setOnAction(events.getHyperlinkEvent(machine.getDatasheet()));
        datasheet.setTextFill(infoTextColour);
        datasheet.setFont(new Font(fontName, smallFontSize));
        datasheet.setTranslateX(-4);
        datasheet.setTranslateY(-2);

        Hyperlink purchaseLink = new Hyperlink("Purchase Link");
        purchaseLink.setOnAction(events.getHyperlinkEvent(machine.getPurchaseLocation()));
        purchaseLink.setFont(new Font(fontName, smallFontSize));
        purchaseLink.setTextFill(infoTextColour);
        purchaseLink.setTranslateX(-4);
        purchaseLink.setTranslateY(-6);

        Pane spacer = new Pane();
        spacer.setPrefHeight(1000);

        VBox btnBox = new VBox();
        btnBox.setAlignment(Pos.TOP_CENTER);
        btnBox.setSpacing(10);
        btnBox.setPadding(new Insets(0, 0, 10, 0));

        ButtonWrapper reportBtn = new ButtonWrapper();
        reportBtn.setCornerRadius(5);
        reportBtn.setButtonWidth(reportWidth - 120);
        reportBtn.setButtonHeight(40);
        reportBtn.setFontName(fontName);
        reportBtn.setText("Generate Report");
        reportBtn.setBackgroundColour(btnColour);
        reportBtn.setClickcolour(btnClickColour);
        reportBtn.setHoverColour(btnHoverColour);
        reportBtn.setPositionX(150);
        reportBtn.setPositionY(30);
        reportBtn.setFontColour(lightTextColour);
        reportBtn.setFontSize(20);
        reportBtn.removeBorder();
        reportBtn.setOnAction((event -> drawReport(machine)));

        ButtonWrapper cancelBtn = new ButtonWrapper();
        cancelBtn.setCornerRadius(5);
        cancelBtn.setButtonWidth(reportWidth - 120);
        cancelBtn.setButtonHeight(40);
        cancelBtn.setFontName(fontName);
        cancelBtn.setText("Cancel Report");
        cancelBtn.setBackgroundColour(btnColour);
        cancelBtn.setClickcolour(btnClickColour);
        cancelBtn.setHoverColour(btnHoverColour);
        cancelBtn.setPositionX(150);
        cancelBtn.setPositionY(30);
        cancelBtn.setFontColour(lightTextColour);
        cancelBtn.setFontSize(20);
        cancelBtn.removeBorder();
        cancelBtn.setOnAction((event -> setRight(null)));
        dataBox.getChildren().addAll(serialNum, location, datasheet, purchaseLink);
        dataPane.setTop(dataBox);
        btnBox.getChildren().addAll(reportBtn, cancelBtn);
        dataPane.setBottom(btnBox);
        dataPane.setMaxHeight(540);
        dataPane.setMinHeight(540);
        infoBox.getChildren().addAll(machineName, dataPane);
        setRight(infoBox);
    }

    /**
     * Creates and adds all nodes to the root BorderPane REQUIRES INTGRATION
     * 
     * @author jrb617
     */
    @Override
    public void buildPage() {
      
        setupRoomSelect();
        drawRoomSelect();
        setTop(drawMenuBar());
        drawMachineButtons();
    }

}
