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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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

    public ReportPage(ArrayList<Machine> machines, User user) {
        super(machines, user);
        buildPage();
    }

    /**
     * Draws the top section of the machine selection bar
     * 
     * @return HBOX for the top of the bar
     * @author jrb617
     */
    private HBox drawReportBar() {
        HBox contents = new HBox();
        contents.setPadding(new Insets(10, 10, 10, 15));
        contents.setBackground(new Background(
                new BackgroundFill(Color.web("245494"), new CornerRadii(10, 10, 0, 0, false), new Insets(0))));
        contents.setSpacing(10);
        contents.setPrefSize(950, 90);
        contents.setMaxSize(950, 90);
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
     * Draws the scrollable machine grid
     * 
     * REQUIRES INTGRATION
     * 
     * @author jrb617
     */
    public void drawReportButtons() {

        reportMachines.clear();
        BorderPane machineBackground = new BorderPane();
        ScrollPane machineScroll = new ScrollPane();
        machineScroll.setPrefSize(950, 530);
        machineScroll.setMaxSize(950, 530);

        FlowPane machineGrid = new FlowPane();
        machineGrid.setBackground(
                new Background(new BackgroundFill(Color.LIGHTSKYBLUE, new CornerRadii(0), new Insets(0))));
        machineGrid.setPrefWidth(935);
        machineGrid.setPrefHeight(528);
        machineGrid.setPadding(new Insets(15, 15, 15, 15));
        machineGrid.setVgap(15);
        machineGrid.setHgap(15);

        for (Machine machine : machines) {
            if (machine.getReport() != null) {
                reportMachines.add(machine);
            }
        }

        for (Machine machine : reportMachines) {

            ButtonWrapper button = new ButtonWrapper();
            button.setButtonHeight(60);
            button.setButtonWidth(905);

            button.setCornerRadius(10);
            button.setFontColour(Color.WHITE);
            button.setFontSize(20);
            button.setContentDisplay(ContentDisplay.TOP);

            button.setBackgroundColour(Color.RED);
            button.setHoverColour(Color.DARKRED);
            button.setClickcolour(Color.INDIANRED);
            button.setAlignment(Pos.TOP_LEFT);

            BorderPane pane = new BorderPane();
            Font boxFont = new Font(15);
            HBox infoBox = new HBox(10);
            Label machineName = new Label(machine.getName());
            Label userName = new Label(machine.getReport().getUser().getName());
            Label description = new Label(machine.getReport().getDescription());
            Label spacer = new Label("|");
            spacer.setFont(boxFont);
            machineName.setFont(new Font(20));
            userName.setFont(boxFont);
            description.setFont(boxFont);

            infoBox.getChildren().addAll(userName, spacer, description);
            infoBox.setAlignment(Pos.BOTTOM_LEFT);

            button.setGraphic(pane);
            button.setContentDisplay(ContentDisplay.BOTTOM);
            pane.setTop(machineName);
            pane.setBottom(infoBox);
            button.setOnAction((new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Run");
                    if (machine.getStatus().equals("OFFLINE")) {
                        drawReportInfo(machine);
                    }
                }
            }));
            machineGrid.getChildren().add(button);
        }

        machineScroll.setContent(machineGrid);
        machineBackground.setTop(drawReportBar());
        machineBackground.setBottom(machineScroll);
        BorderPane.setMargin(machineBackground, new Insets(5));
        setCenter(machineBackground);
    }

    private void drawReportInfo(Machine machine) {
        VBox infoBox = new VBox();
        infoBox.setPrefWidth(320);
        infoBox.setMaxHeight(630);
        infoBox.setPadding(new Insets(20, 10, 10, 10));
        infoBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(10), new Insets(5))));

        TextBox machineName = new TextBox();

        machineName.setBoxWidth(300);
        machineName.setBoxHeight(50);
        machineName.setText(machine.getName());
        machineName.setFontColour(Color.BLACK);
        machineName.setFontName("Calibri");
        machineName.setFontSize(200);
        machineName.setTextAlignment(TextAlignment.CENTER);

        ////////////////////////////

        // General information

        VBox genInfoBox = new VBox();
        genInfoBox.setAlignment(Pos.TOP_LEFT);
        genInfoBox.setSpacing(10);
        genInfoBox.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(10), new Insets(5))));
        genInfoBox.setPadding(new Insets(8));

        Label genTitle = new Label("General Information");

        Label serialNum = new Label("Serial Number: " + machine.getSerialNumber());
        Label location = new Label("Location: " + machine.getRoom());
        Hyperlink datasheet = new Hyperlink("Datasheet");
        datasheet.setOnAction(events.getHyperlinkEvent("https://www.google.com/"));
        datasheet.setTextFill(Color.BLACK);
        Hyperlink purchaseLink = new Hyperlink("Purchase Link");
        purchaseLink.setOnAction(events.getHyperlinkEvent("https://www.google.com/"));
        purchaseLink.setTextFill(Color.BLACK);

        genInfoBox.getChildren().addAll(serialNum, location, datasheet, purchaseLink);

        ///////////////////////////////

        // User Information

        VBox userInfoBox = new VBox();
        userInfoBox.setAlignment(Pos.TOP_LEFT);
        userInfoBox.setSpacing(10);
        userInfoBox.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(10), new Insets(5))));
        userInfoBox.setPadding(new Insets(8));

        Label userTitle = new Label("Submitted by:");

        Label userName = new Label("User Name: " + machine.getReport().getUser().getName());
        Label email = new Label("Email: " + machine.getReport().getUser().getEmail());
        Label role = new Label("Role: " + machine.getReport().getUser().getRole());

        userInfoBox.getChildren().addAll(userName, email, role);

        ///////////////////////////////

        // Description

        Label descTitle = new Label("Description: ");

        TextArea desc = new TextArea();
        desc.setMaxWidth(300);
        desc.setPrefHeight(10);
        desc.setWrapText(true);
        desc.setText(machine.getReport().getDescription());
        desc.setEditable(false);

        infoBox.getChildren().addAll(machineName, new Label(), genTitle, genInfoBox, new Label(), userTitle,
                userInfoBox, new Label(), descTitle, desc);
        //////////////////////////////////

        // Solution

        TextArea solution = new TextArea();
        solution.setMaxWidth(300);
        solution.setPrefHeight(100);
        solution.setWrapText(true);
        solution.setPromptText("Describe the solution to the problem: ");

        ButtonWrapper completeBtn = new ButtonWrapper();
        completeBtn.setCornerRadius(5);
        completeBtn.setButtonWidth(300);
        completeBtn.setButtonHeight(40);
        completeBtn.setFontName("Arial");
        completeBtn.setText("Log as Complete");
        completeBtn.setBackgroundColour("fbb12eff");
        completeBtn.setClickcolour(Color.WHITE);
        completeBtn.setHoverColour("#ff8c00ff");
        completeBtn.setPositionX(150);
        completeBtn.setPositionY(30);
        completeBtn.setFontColour("#ffffffff");
        completeBtn.setFontSize(20);
        completeBtn.removeBorder();
        completeBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (solution.getText().equals("")) {
                    solution.setPromptText("A description of the pereprmed repairs is required: ");

                }
                else {
                    machine.archiveReport();
                    machine.setStatus("ONLINE");
                    ;
                    drawReportButtons();
                    setRight(null);
                }
            }
        }));

        ButtonWrapper closeBtn = new ButtonWrapper();
        closeBtn.setCornerRadius(5);
        closeBtn.setButtonWidth(300);
        closeBtn.setButtonHeight(40);
        closeBtn.setFontName("Arial");
        closeBtn.setText("Cancel Report");
        closeBtn.setBackgroundColour("fbb12eff");
        closeBtn.setClickcolour(Color.WHITE);
        closeBtn.setHoverColour("#ff8c00ff");
        closeBtn.setPositionX(150);
        closeBtn.setPositionY(30);
        closeBtn.setFontColour("#ffffffff");
        closeBtn.setFontSize(20);
        closeBtn.removeBorder();
        closeBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRight(null);
            }
        }));

        infoBox.getChildren().addAll(new Label(), solution, new Label(), completeBtn, new Label(), closeBtn);

        setRight(infoBox);

    }

    /**
     * Creates and adds all nodes to the root BorderPane REQUIRES INTGRATION
     * 
     * @author jrb617
     */
    @Override
    public void buildPage() {
        setTop(drawMenuBar());
        drawReportButtons();
    }

}
