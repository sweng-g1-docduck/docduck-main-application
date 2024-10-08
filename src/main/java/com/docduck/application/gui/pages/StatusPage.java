package com.docduck.application.gui.pages;

import com.docduck.application.data.Machine;
import com.docduck.application.data.Report;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Draws the status of all machines in a grid with colour showing their status.
 * the user can generate and submit a report to log the machine offline
 * 
 * @author jrb617 lw2380 wab513
 */
public class StatusPage extends Page {

    private ComboBox<String> roomSelectBox;
    private final int reportWidth = 320;
    private final int machineBoxWidth = 1270 - reportWidth;
    private Machine currentMachine;

    private final Color onlineButtonColour = Color.web("#4ff080");
    private final Color onlineHoverColour = Color.web("#20ec5e");
    private final Color onlineClickColour = Color.web("#11c849");

    private final Color maintenanceButtonColour = Color.web("#f0db5b");
    private final Color maintenanceHoverColour = Color.web("#ecd12c");
    private final Color maintenanceClickColour = Color.web("#d2b713");

    private final Color offlineButtonColour = Color.web("#ed665c");
    private final Color offlineHoverColour = Color.web("#e83c28");
    private final Color offlineClickColour = Color.web("#c82815");

    private final Color infoTextColour = darkTextColour;

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
        title.setFontColour(darkTextColour);
        title.setBoxWidth(200);

        contents.getChildren().add(title);
        return contents;

    }

    /**
     * Initialises the room selection dropdown box
     * 
     * @author jrb617
     */
    public void setupRoomSelect() {

        ArrayList<String> allLocations = new ArrayList<>();
        for (Machine machine : machines) {
            if (!allLocations.contains(machine.getLocation())) {
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
     * Draws the scrollable machine grid containing buttons for each machine
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

        ArrayList<ButtonWrapper> buttons = new ArrayList<>();
        for (Machine machine : machines) {

            if (machine.getLocation().equals(roomSelectBox.getValue()) || roomSelectBox.getValue().equals("All")) {

                ButtonWrapper button = new ButtonWrapper();
                double buttonWidth = (machineBoxWidth - 75) / 3;
                try {
                    File imageFile = new File(getDocDuckWorkingDirectory() + machine.getImageRef());
                    Image img = new Image(imageFile.toURI().toString());
                    ImageView view1 = new ImageView(img);
                    view1.setFitWidth(buttonWidth - 40);
                    view1.setFitHeight(160);
                    view1.setPreserveRatio(true);
                    button.setGraphic(view1);
                }
                catch (NullPointerException | IllegalArgumentException | IOException e) {
                    e.printStackTrace();
                    System.err.println("The path to the machine image could not be found");
                }

                button.setButtonHeight(200);
                button.setButtonWidth(buttonWidth);

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
     * 
     * @param machine The machine which's report is being generated
     * @author jrb617
     */
    private void drawReport(Machine machine) {

        VBox reportBox = new VBox();
        reportBox.setAlignment(Pos.TOP_CENTER);
        reportBox.setPrefWidth(reportWidth);
        reportBox.setPadding(new Insets(20, 10, 10, 10));
        reportBox.setSpacing(10);
        reportBox.setBackground(new Background(new BackgroundFill(boxColour, new CornerRadii(10), new Insets(5))));

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

        TextArea titleBox = new TextArea();
        titleBox.setMaxWidth(reportWidth - 20);
        titleBox.setMaxHeight(20);
        titleBox.setWrapText(true);
        titleBox.setPromptText("Report Title");

        TextArea descriptionBox = new TextArea();
        descriptionBox.setMaxWidth(reportWidth - 20);
        descriptionBox.setMaxHeight(reportWidth - 20);
        descriptionBox.setWrapText(true);
        descriptionBox.setPromptText("Describe the machine fault");

        ButtonWrapper mediaBtn = drawButtonWrapper(reportWidth - 120, 40, "Attach media");
        mediaBtn.setOnAction(events.getActionEvent("chooseMedia"));

        ButtonWrapper submitBtn = drawButtonWrapper(reportWidth - 120, 40, "Submit Report");
        submitBtn.setOnAction((event -> {

            boolean canSubmit = true;

            if (titleBox.getText().strip().isEmpty()) {
                titleBox.setText("");
                titleBox.setPromptText("A title is required");
                canSubmit = false;
            }

            if (descriptionBox.getText().strip().isEmpty()) {
                descriptionBox.setText("");
                descriptionBox.setPromptText("A description of the fault is required");
                canSubmit = false;
            }

            if (canSubmit) {
                // Submit report to XML
                machine.setStatus("OFFLINE");
                setRight(null);
                Report report = new Report(user, titleBox.getText(), descriptionBox.getText(),
                        events.getUploadedMediaFileName(), machine.getId());
                machine.addReport(report);
                drawMachineButtons();
            }
        }));

        ButtonWrapper cancelBtn = drawButtonWrapper(reportWidth - 120, 40, "Cancel");
        cancelBtn.setOnAction((event -> setRight(null)));

        reportBox.getChildren().addAll(machineName, titleBox, descriptionBox, mediaBtn, submitBtn, cancelBtn);
        setRight(reportBox);

    }

    /**
     * Draws the Machine Information Side Panel
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
        infoBox.setBackground(new Background(new BackgroundFill(boxColour, new CornerRadii(10), new Insets(5))));

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
        dataPane.setBackground(new Background(new BackgroundFill(barColour, new CornerRadii(10), new Insets(5))));

        VBox machineInfo = drawMachineInfoBox(machine);

        Pane spacer = new Pane();
        spacer.setPrefHeight(1000);

        VBox btnBox = new VBox();
        btnBox.setAlignment(Pos.TOP_CENTER);
        btnBox.setSpacing(10);
        btnBox.setPadding(new Insets(0, 0, 10, 0));

        ButtonWrapper reportBtn = drawButtonWrapper(reportWidth - 120, 40, "Generate Report");
        reportBtn.setOnAction((event -> drawReport(machine)));

        ButtonWrapper cancelBtn = drawButtonWrapper(reportWidth - 120, 40, "Close");
        cancelBtn.setOnAction((event -> setRight(null)));

        dataPane.setTop(machineInfo);
        btnBox.getChildren().addAll(reportBtn, cancelBtn);
        dataPane.setBottom(btnBox);
        dataPane.setMaxHeight(540);
        dataPane.setMinHeight(540);
        infoBox.getChildren().addAll(machineName, dataPane);
        setRight(infoBox);
    }

    /**
     * Creates and adds all nodes to the root BorderPane
     * 
     * @author jrb617
     */
    @Override
    public void buildPage() {

        setupRoomSelect();
        setTop(drawMenuBar());
        drawMachineButtons();
    }

        /**
     * Gets the working directory of the application
     *
     * @return String woith the working directory
     * @throws IOException
     * @author wab513
     */
    private String getDocDuckWorkingDirectory() throws IOException {
        String workingDirectory;
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            workingDirectory = System.getenv("AppData");
        }
        else {
            workingDirectory = null;
        }

        return workingDirectory;
    }

}
