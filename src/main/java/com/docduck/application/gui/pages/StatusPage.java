package com.docduck.application.gui.pages;

import java.util.ArrayList;
import java.util.List;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.application.gui.EventManager;
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

    
    // This contains a list of nodes for the status page
    private List nodeList = null;

    private ComboBox<String> comboBox;

    private ArrayList<Machine> machines;

    private BorderPane borderPane;
    
    public StatusPage() {
        super();
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

    @Override
    public BorderPane buildPage() {

        borderPane = new BorderPane();

        populateMachineData();
        user = new User("Bob", "bob@york.ac.uk", "OPERATOR");
        setupRoomSelect();
        drawRoomSelect();

        borderPane.setTop(drawMenuBar());
        drawMachineButtons();

        return(borderPane);

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
    
}
