package com.docduck.application.gui.pages;

import java.util.ArrayList;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ReportPage extends Page {

    private ArrayList<Machine> reportMachines = new ArrayList<Machine>();
    private final int reportDescWidth = 320;
    private final int reportBoxWidth = 1270 - reportDescWidth;
    private Machine currentMachine;

    private final Color buttonColour = Color.RED;
    private final Color buttonHoverColour = Color.DARKRED;
    private final Color buttonClickColour = Color.INDIANRED;

    private final Color reportTextColour = lightTextColour;
    private final Color btnTextColour = darkTextColour;

    public ReportPage(ArrayList<Machine> machines, User user) {
        super(machines, user);
        buildPage();
    }

    /**
     * Draws reports bar
     * 
     * @return HBOX for the top of the bar
     * @author jrb617
     */
    private HBox drawReportBar() {
        HBox contents = new HBox();
        contents.setPadding(new Insets(10, 10, 10, 15));
        contents.setBackground(
                new Background(new BackgroundFill(barColour, new CornerRadii(10, 10, 0, 0, false), new Insets(0))));
        contents.setSpacing(10);
        contents.setPrefSize(reportBoxWidth, 90);
        contents.setMaxSize(reportBoxWidth, 90);
        contents.setAlignment(Pos.CENTER_LEFT);

        TextBox title = new TextBox();
        title.setFontName(fontName);
        title.setText("Machine Overview");
        title.setFontSize(20);
        title.setFontColour(lightTextColour);
        title.setBoxWidth(200);

        contents.getChildren().add(title);
        return contents;

    }

    /**
     * Draws the scrollable report grid
     * 
     * REQUIRES INTGRATION
     * 
     * @author jrb617
     */
    public void drawReportButtons() {

        reportMachines.clear();
        BorderPane machineBackground = new BorderPane();
        ScrollPane machineScroll = new ScrollPane();
        machineScroll.setPrefSize(reportBoxWidth, 530);
        machineScroll.setMaxSize(reportBoxWidth, 530);

        FlowPane machineGrid = new FlowPane();
        machineGrid
                .setBackground(new Background(new BackgroundFill(backgroundColour, new CornerRadii(0), new Insets(0))));
        machineGrid.setPrefWidth(reportBoxWidth - 15);
        machineGrid.setPrefHeight(528);
        machineGrid.setPadding(new Insets(15, 15, 15, 15));
        machineGrid.setVgap(15);
        machineGrid.setHgap(15);

        for (Machine machine : machines) {
            if (machine.getReport() != null) {
                reportMachines.add(machine);
            }
        }

        ArrayList<ButtonWrapper> buttons = new ArrayList<ButtonWrapper>();
        for (Machine machine : reportMachines) {

            ButtonWrapper button = new ButtonWrapper();
            button.setButtonHeight(60);
            button.setButtonWidth(reportBoxWidth - 45);

            button.setCornerRadius(10);
            button.setContentDisplay(ContentDisplay.TOP);

            button.setBackgroundColour(buttonColour);
            button.setHoverColour(buttonHoverColour);
            button.setClickcolour(buttonClickColour);
            button.setAlignment(Pos.TOP_LEFT);

            BorderPane pane = new BorderPane();
            HBox infoBox = new HBox(10);
            Label machineName = new Label(machine.getName());
            machineName.setFont(new Font(fontName, smallFontSize));
            machineName.setTextFill(btnTextColour);

            Label location = new Label(machine.getRoom());
            location.setFont(new Font(fontName, smallFontSize));
            location.setTextFill(btnTextColour);

            Label userName = new Label(machine.getReport().getUser().getName());
            userName.setFont(new Font(fontName, smallFontSize));
            userName.setTextFill(btnTextColour);
            
            Label description = new Label(machine.getReport().getDescription());
            description.setFont(new Font(fontName, smallFontSize));
            description.setTextFill(btnTextColour);

            Label spacer = new Label("|");
            spacer.setFont(new Font(fontName, smallFontSize));
            spacer.setTextFill(btnTextColour);

            machineName.setFont(new Font(20));
            Label spacer2 = new Label("|");
            spacer2.setFont(new Font(fontName, smallFontSize));
            spacer2.setTextFill(btnTextColour);

            infoBox.getChildren().addAll(location, spacer, userName, spacer2, description);
            infoBox.setAlignment(Pos.BOTTOM_LEFT);

            button.setGraphic(pane);
            button.setContentDisplay(ContentDisplay.BOTTOM);
            pane.setTop(machineName);
            pane.setBottom(infoBox);
            button.setOnAction((new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (ButtonWrapper button : buttons) {
                        if (button.getBackground().getFills().get(0).getFill().toString()
                                .equals(buttonClickColour.toString())) {
                            button.setBackgroundColour(buttonColour);
                            button.setBorderWidth(1);
                        }
                    }
                    button.setBackgroundColour(buttonClickColour);
                    button.setBorderWidth(3);
                    if (currentMachine != machine) {
                        currentMachine = machine;
                        drawReportInfo(machine);
                    }
                }
            }));
            buttons.add(button);
            machineGrid.getChildren().add(button);
        }

        machineScroll.setContent(machineGrid);
        machineBackground.setTop(drawReportBar());
        machineBackground.setBottom(machineScroll);
        BorderPane.setMargin(machineBackground, new Insets(5));
        setCenter(machineBackground);
    }

    /**
     * Displays all the information from the report related to the machine
     * 
     * @param machine The machine which has had a report created
     * @author jrb617
     */
    public void drawReportInfo(Machine machine) {

        ScrollPane reportScroll = new ScrollPane();
        reportScroll.setPrefSize(reportDescWidth, 625);
        reportScroll.setMaxSize(reportDescWidth, 625);

        VBox infoBox = new VBox();
        infoBox.setPrefWidth(reportDescWidth - 15);
        infoBox.setMaxHeight(630);
        infoBox.setPadding(new Insets(20, 10, 10, 10));
        infoBox.setBackground(new Background(new BackgroundFill(backgroundColour, new CornerRadii(10), new Insets(5))));

        TextBox machineName = new TextBox();

        machineName.setFontName(fontName);
        machineName.setBoxWidth(reportDescWidth - 35);
        machineName.setBoxHeight(50);
        machineName.setText(machine.getName());
        machineName.setFontColour(reportTextColour);
        machineName.setFontName(fontName);
        machineName.setFontSize(200);
        machineName.setTextAlignment(TextAlignment.CENTER);

        ///////////////////////////////

        // General information

        VBox genInfoBox = new VBox();
        genInfoBox.setAlignment(Pos.TOP_LEFT);
        genInfoBox.setSpacing(10);
        genInfoBox.setBackground(new Background(new BackgroundFill(boxColour, new CornerRadii(10), new Insets(5))));
        genInfoBox.setPadding(new Insets(8));

        Label genTitle = new Label("General Information");
        genTitle.setFont(new Font(fontName, smallFontSize));
        genTitle.setTextFill(reportTextColour);

        Label serialNum = new Label("Serial Number: " + machine.getSerialNumber());
        serialNum.setFont(new Font(fontName, smallFontSize));
        serialNum.setTextFill(reportTextColour);

        Label location = new Label("Location: " + machine.getRoom());
        location.setFont(new Font(fontName, smallFontSize));
        location.setTextFill(reportTextColour);

        Hyperlink datasheet = new Hyperlink("Datasheet");
        datasheet.setOnAction(events.getHyperlinkEvent("https://www.google.com/"));
        datasheet.setTextFill(reportTextColour);
        datasheet.setFont(new Font(fontName, smallFontSize));
        datasheet.setTranslateX(-4);
        datasheet.setTranslateY(-2);
        
        Hyperlink purchaseLink = new Hyperlink("Purchase Link");
        purchaseLink.setOnAction(events.getHyperlinkEvent("https://www.google.com/"));
        purchaseLink.setFont(new Font(fontName, smallFontSize));
        purchaseLink.setTextFill(reportTextColour);
        purchaseLink.setTranslateX(-4);
        purchaseLink.setTranslateY(-6);

        genInfoBox.getChildren().addAll(serialNum, location, datasheet, purchaseLink);

        ///////////////////////////////

        // User Information

        VBox userInfoBox = new VBox();
        userInfoBox.setAlignment(Pos.TOP_LEFT);
        userInfoBox.setSpacing(10);
        userInfoBox.setBackground(new Background(new BackgroundFill(boxColour, new CornerRadii(10), new Insets(5))));
        userInfoBox.setPadding(new Insets(8));

        Label userTitle = new Label("Submitted by:");
        userTitle.setFont(new Font(fontName, smallFontSize));
        userTitle.setTextFill(reportTextColour);

        Label userName = new Label("User Name: " + machine.getReport().getUser().getName());
        userName.setFont(new Font(fontName, smallFontSize));
        userName.setTextFill(reportTextColour);
        Label email = new Label("Email: " + machine.getReport().getUser().getEmail());
        email.setFont(new Font(fontName, smallFontSize));
        email.setTextFill(reportTextColour);
        Label role = new Label("Role: " + machine.getReport().getUser().getRole());
        role.setFont(new Font(fontName, smallFontSize));
        role.setTextFill(reportTextColour);

        userInfoBox.getChildren().addAll(userName, email, role);

        ///////////////////////////////

        // Description

        Label descTitle = new Label("Description: ");
        descTitle.setFont(new Font(fontName, smallFontSize));
        descTitle.setTextFill(reportTextColour);

        TextArea desc = new TextArea();
        desc.setMaxWidth(reportDescWidth - 35);
        desc.setPrefHeight(10);
        desc.setWrapText(true);
        desc.setText(machine.getReport().getDescription());
        desc.setEditable(false);
        desc.setFont(new Font(fontName, smallFontSize));

        infoBox.getChildren().addAll(machineName, new Label(), genTitle, genInfoBox, new Label(), userTitle,
                userInfoBox, new Label(), descTitle, desc);

        ///////////////////////////////

        // Attached Media

        Label mediaTitle = new Label("Attached Media: ");
        mediaTitle.setFont(new Font(fontName, smallFontSize));
        mediaTitle.setTextFill(reportTextColour);

        if (machine.getReport().getPathToFile() != null) {
            infoBox.getChildren().addAll(new Label(), mediaTitle, new Label(),
                    drawAttachedMedia(machine.getReport().getPathToFile()));
        }

        ///////////////////////////////

        // Solution

        TextArea solution = new TextArea();
        solution.setMaxWidth(reportDescWidth - 35);
        solution.setPrefHeight(100);
        solution.setWrapText(true);
        solution.setPromptText("Performed repairs");
        solution.setFont(new Font(fontName, smallFontSize));

        ButtonWrapper completeBtn = new ButtonWrapper();
        completeBtn.setCornerRadius(5);
        completeBtn.setButtonWidth(reportDescWidth - 35);
        completeBtn.setButtonHeight(40);
        completeBtn.setFontName(fontName);
        completeBtn.setText("Log as Complete");
        completeBtn.setBackgroundColour(btnColour);
        completeBtn.setClickcolour(btnClickColour);
        completeBtn.setHoverColour(btnHoverColour);
        completeBtn.setPositionX(150);
        completeBtn.setPositionY(30);
        completeBtn.setFontColour(lightTextColour);
        completeBtn.setFontSize(20);
        completeBtn.removeBorder();
        completeBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (solution.getText().equals("")) {
                    solution.setPromptText("A description of the performed repairs is required");

                }
                else {
                    machine.archiveReport();
                    machine.setStatus("ONLINE");
                    drawReportButtons();
                    setRight(null);
                }
            }
        }));

        ButtonWrapper closeBtn = new ButtonWrapper();
        closeBtn.setCornerRadius(5);
        closeBtn.setButtonWidth(reportDescWidth - 35);
        closeBtn.setButtonHeight(40);
        closeBtn.setFontName(fontName);
        closeBtn.setText("Close");
        closeBtn.setBackgroundColour(btnColour);
        closeBtn.setClickcolour(btnClickColour);
        closeBtn.setHoverColour(btnHoverColour);
        closeBtn.setPositionX(150);
        closeBtn.setPositionY(30);
        closeBtn.setFontColour(lightTextColour);
        closeBtn.setFontSize(20);
        closeBtn.removeBorder();
        closeBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRight(null);
                drawReportButtons();
            }
        }));

        infoBox.getChildren().addAll(new Label(), solution, new Label(), completeBtn, new Label(), closeBtn);
        reportScroll.setContent(infoBox);
        setRight(reportScroll);

    }

    /**
     * Creates and adds all nodes to the root BorderPane
     * 
     * @author jrb617
     */
    @Override
    public void buildPage() {
        setTop(drawMenuBar());
        drawReportButtons();
    }

    /**
     * Creates a node specific to the desired media, img for and image
     * TODO audio and video
     * 
     * @param filePath The path to the file
     * @return Node of the correct type to display the data
     */
    private Node drawAttachedMedia(String filePath) {
        String extension = getExtension(filePath);
        if (extension.equals("png") || extension.equals("jpeg")) {
            Image img = new Image(filePath);
            ImageView view1 = new ImageView(img);
            view1.setFitWidth(reportDescWidth - 35);
            view1.setPreserveRatio(true);
            return view1;
        }

        else if (extension.equals("mp3")) {

        }
        return null;
    }

    /**
     * Gets the extension from the specified filepath
     * 
     * @param filePath The name of the filepath to the desired file
     * @return String extension of the file e.g "png"
     */
    private String getExtension(String filePath) {
        String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        return fileExtension;
    }

}
