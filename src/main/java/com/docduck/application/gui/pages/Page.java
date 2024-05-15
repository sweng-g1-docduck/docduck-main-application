package com.docduck.application.gui.pages;

import java.util.List;

import com.docduck.application.data.User;
import com.docduck.application.gui.EventManager;
import com.docduck.buttonlibrary.ButtonWrapper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Page {

    /**
     * This class will contain the basic page structure for the main application
     * This will include the main menu and a container to contain each specific
     * pages content
     */

    // This contains a list of nodes for the default page
    private List nodeList = null;
    protected EventManager events;
    protected User user;

    public Page() {
        this.events = EventManager.getInstance();
    }

    // return list of nodes
    public Object buildPage() {
        drawMenuBar();
        return null;
    }

    protected HBox drawMenuBar() {

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

}
