package com.docduck.application.gui.pages;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import uk.co.bookcook.BCMediaControls;
import uk.co.bookcook.BCMediaPlayer;

public class ReportPage extends Page {

    private final ArrayList<Machine> reportMachines = new ArrayList<>();
    private final int reportDescWidth = 320;
    private final int reportBoxWidth = 1270 - reportDescWidth;
    private Machine currentMachine;

    private final Color buttonColour = Color.web("#ed665c");
    private final Color buttonHoverColour = Color.web("#e83c28");
    private final Color buttonClickColour = Color.web("#c82815");

    private final Color reportTextColour = darkTextColour;
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
        title.setFontColour(reportTextColour);
        title.setBoxWidth(200);

        contents.getChildren().add(title);
        return contents;

    }

    /**
     * Draws the scrollable report grid
     * 
     * @author jrb617
     * @apiNote Pending integration
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
            if (machine.getCurrentReport() != null) {
                reportMachines.add(machine);
            }
        }

        ArrayList<ButtonWrapper> buttons = new ArrayList<>();
        for (Machine machine : reportMachines) {

            ButtonWrapper reportButton = new ButtonWrapper();
            reportButton.setButtonHeight(60);
            reportButton.setButtonWidth(reportBoxWidth - 45);
            reportButton.setCornerRadius(10);
            reportButton.setContentDisplay(ContentDisplay.TOP);
            reportButton.setBackgroundColour(buttonColour);
            reportButton.setHoverColour(buttonHoverColour);
            reportButton.setClickcolour(buttonClickColour);
            reportButton.setAlignment(Pos.TOP_LEFT);

            BorderPane pane = new BorderPane();
            HBox infoBox = new HBox(10);

            Label machineName = drawSubText(machine.getName(), btnTextColour);
            machineName.setFont(new Font(fontName, 20));

            Label location = drawSubText(machine.getLocation(), btnTextColour);

            Label userName = drawSubText(machine.getCurrentReport().getUser().getName(), btnTextColour);

            Label faultDescription = drawSubText(machine.getCurrentReport().getTitle(), btnTextColour);

            Label spacer = drawSubText("|", btnTextColour);

            Label spacer2 = drawSubText("|", btnTextColour);

            infoBox.getChildren().addAll(location, spacer, userName, spacer2, faultDescription);
            infoBox.setAlignment(Pos.BOTTOM_LEFT);

            reportButton.setGraphic(pane);
            reportButton.setContentDisplay(ContentDisplay.BOTTOM);
            pane.setTop(machineName);
            pane.setBottom(infoBox);

            // Recolours button to show it has been clicked if not. Draws the report info
            reportButton.setOnAction((event -> {
                for (ButtonWrapper button : buttons) {
                    if (button.getBackground().getFills().get(0).getFill().toString()
                            .equals(buttonClickColour.toString())) {
                        button.setBackgroundColour(buttonColour);
                        button.setBorderWidth(1);
                    }
                }
                reportButton.setBackgroundColour(buttonClickColour);
                reportButton.setBorderWidth(3);
                if (currentMachine != machine) {
                    currentMachine = machine;
                    drawReportInfo(machine);
                }
            }));
            buttons.add(reportButton);
            machineGrid.getChildren().add(reportButton);
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
        infoBox.setBackground(new Background(new BackgroundFill(barColour, new CornerRadii(10), new Insets(5))));

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

        Label genTitle = drawSubText("General Information", reportTextColour);
        VBox genInfoBox = drawMachineInfoBox(machine);

        ///////////////////////////////

        // User Information

        Label userTitle = drawSubText("Submitted by:", reportTextColour);
        VBox userInfoBox = drawUserInfo(machine);

        ///////////////////////////////

        // Fault Description

        Label descTitle = drawSubText("Description: ", reportTextColour);

        TextArea title = new TextArea();
        title.setMaxWidth(reportDescWidth - 35);
        title.setPrefHeight(30);
        title.setMinHeight(30);
        title.setMaxHeight(30);
        title.setWrapText(true);
        title.setText(machine.getCurrentReport().getTitle());
        title.setEditable(false);
        title.setFont(new Font(fontName, smallFontSize));

        TextArea desc = new TextArea();
        desc.setMaxWidth(reportDescWidth - 35);
        desc.setPrefHeight(60);
        desc.setMinHeight(60);
        desc.setMaxHeight(60);
        desc.setWrapText(true);
        desc.setText(machine.getCurrentReport().getDescription());
        desc.setEditable(false);
        desc.setFont(new Font(fontName, smallFontSize));

        infoBox.getChildren().addAll(machineName, new Label(), genTitle, genInfoBox, new Label(), userTitle,
                userInfoBox, new Label(), descTitle, title, desc);

        ///////////////////////////////

        // Attached Media

        try {
            if (drawAttachedMedia(machine.getCurrentReport().getPathToFile()) != null) {

                Label mediaTitle = new Label("Attached Media: ");
                mediaTitle.setFont(new Font(fontName, smallFontSize));
                mediaTitle.setTextFill(reportTextColour);

                infoBox.getChildren().addAll(new Label(), mediaTitle, new Label(),
                        drawAttachedMedia(machine.getCurrentReport().getPathToFile()));

            }
        }
        catch (FileNotFoundException e) {
            System.err.println("Attached file could not be found");
        }

        ///////////////////////////////

        // Solution

        TextArea solution = new TextArea();
        solution.setMaxWidth(reportDescWidth - 35);
        solution.setPrefHeight(100);
        solution.setWrapText(true);
        solution.setPromptText("Performed repairs");
        solution.setFont(new Font(fontName, smallFontSize));

        ButtonWrapper completeBtn = drawButtonWrapper(reportDescWidth - 35, 40, "Log as Complete");
        // Requests a description if not provided, log the machine online if one has
        completeBtn.setOnAction((event -> {
            if (solution.getText().isEmpty()) {
                solution.setPromptText("A description of the performed repairs is required");

            }
            else {
                machine.archiveReport();
                machine.setStatus("ONLINE");
                drawReportButtons();
                setRight(null);
            }
        }));

        ButtonWrapper closeBtn = drawButtonWrapper(reportDescWidth - 35, 40, "Close");
        // Removes the report from the page
        closeBtn.setOnAction((event -> {
            setRight(null);
            drawReportButtons();
        }));

        infoBox.getChildren().addAll(new Label(), solution, new Label(), completeBtn, new Label(), closeBtn);
        reportScroll.setContent(infoBox);
        setRight(reportScroll);

    }

    private VBox drawUserInfo(Machine machine) {
        VBox userInfoBox = new VBox();
        userInfoBox.setAlignment(Pos.TOP_LEFT);
        userInfoBox.setSpacing(10);
        userInfoBox.setBackground(new Background(new BackgroundFill(boxColour, new CornerRadii(10), new Insets(5))));
        userInfoBox.setPadding(new Insets(8));

        Label userName = drawSubText("User Name: " + machine.getCurrentReport().getUser().getName(), reportTextColour);
        Label email = drawSubText("Email: " + machine.getCurrentReport().getUser().getEmail(), reportTextColour);
        Label role = drawSubText("Role: " + machine.getCurrentReport().getUser().getRole(), reportTextColour);

        userInfoBox.getChildren().addAll(userName, email, role);
        return userInfoBox;
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
     * Creates a node specific to the desired media, img for and image TODO audio
     * and video
     * 
     * @param filePath The path to the file
     * @return Node of the correct type to display the data
     */
    private Node drawAttachedMedia(String filePath) throws FileNotFoundException {
        String extension = getExtension(filePath);
        try {
            if (extension.equals("png") || extension.equals("jpeg")) {

                Image img = new Image(filePath);
                ImageView view1 = new ImageView(img);
                view1.setFitWidth(reportDescWidth - 35); // Offset to fit the bar
                view1.setPreserveRatio(true);
                return view1;
            }

            else if (extension.equals("mp4") || extension.equals("mp3")) {
                BCMediaPlayer BCMP = new BCMediaPlayer("src/main/resources/clock.mp4");
                BCMP.setWidth(250);
                BCMediaControls BCMC = new BCMediaControls(BCMP);
                BCMC.setWidth(250);
                BCMC.setIconSpacing(5);

                VBox box = new VBox(BCMP, BCMC);
                box.setAlignment(Pos.CENTER);
                return box;
            }
        }
        catch (NullPointerException | IllegalArgumentException e) {
            throw new FileNotFoundException();
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
        return filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
    }

}
