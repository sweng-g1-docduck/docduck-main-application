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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Page extends BorderPane {

    /**
     * This class will contain the basic page structure for the main application
     * This will include the main menu and a container to contain each specific
     * pages content
     */

    protected EventManager events;
    protected User user;

    public Page() {
        super();
        this.events = EventManager.getInstance();
    }

    // return list of nodes
    public void buildPage() {
        drawMenuBar();
    }

    /**
     * Draws the Menu bar on the borderPane
     * 
     * @return HBox Containing menu bar elements
     * @author jrb617
     */
    protected HBox drawMenuBar() {

        HBox menuBar = new HBox();
        menuBar.setPadding(new Insets(15, 15, 15, 15));
        menuBar.setBackground(
                new Background(new BackgroundFill(Color.web("245494"), new CornerRadii(10), new Insets(5))));
        menuBar.setSpacing(15);
        menuBar.setPrefSize(1280, 90);
        menuBar.setAlignment(Pos.CENTER_LEFT);

        ButtonWrapper overviewBtn = new ButtonWrapper();
        overviewBtn.setCornerRadius(5);
        overviewBtn.setButtonWidth(200);
        overviewBtn.setButtonHeight(60);
        overviewBtn.setFontName("Arial");
        overviewBtn.setText("Machine Overview");
        overviewBtn.setBackgroundColour("fbb12eff");
        overviewBtn.setClickcolour(Color.WHITE);
        overviewBtn.setHoverColour("#ff8c00ff");
        overviewBtn.setPositionX(150);
        overviewBtn.setPositionY(30);
        overviewBtn.setFontColour("#ffffffff");
        overviewBtn.setFontSize(20);
        overviewBtn.removeBorder();
        overviewBtn.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        }));

        ButtonWrapper logOutBtn = new ButtonWrapper();
        logOutBtn.setCornerRadius(5);
        logOutBtn.setButtonWidth(120);
        logOutBtn.setButtonHeight(60);
        logOutBtn.setFontName("Arial");
        logOutBtn.setText("Log Out");
        logOutBtn.setBackgroundColour("fbb12eff");
        logOutBtn.setClickcolour(Color.WHITE);
        logOutBtn.setHoverColour("#ff8c00ff");
        logOutBtn.setPositionX(150);
        logOutBtn.setPositionY(30);
        logOutBtn.setFontColour("#ffffffff");
        logOutBtn.setFontSize(20);
        logOutBtn.removeBorder();

        menuBar.getChildren().addAll(overviewBtn);

        if (user.getRole().equals("ENGINEER") || user.getRole().equals("ADMIN")) {

            ButtonWrapper reportBtn = new ButtonWrapper();
            reportBtn.setCornerRadius(5);
            reportBtn.setButtonWidth(240);
            reportBtn.setButtonHeight(60);
            reportBtn.setFontName("Arial");
            reportBtn.setText("Maintainance Reports");
            reportBtn.setBackgroundColour("fbb12eff");
            reportBtn.setClickcolour(Color.WHITE);
            reportBtn.setHoverColour("#ff8c00ff");
            reportBtn.setPositionX(290);
            reportBtn.setPositionY(30);
            reportBtn.setFontColour("#ffffffff");
            reportBtn.setFontSize(20);
            reportBtn.removeBorder();

            ButtonWrapper partBtn = new ButtonWrapper();
            partBtn.setCornerRadius(5);
            partBtn.setButtonWidth(160);
            partBtn.setButtonHeight(60);
            partBtn.setFontName("Arial");
            partBtn.setText("Part Search");
            partBtn.setBackgroundColour("fbb12eff");
            partBtn.setClickcolour(Color.WHITE);
            partBtn.setHoverColour("#ff8c00ff");
            partBtn.setPositionX(290);
            partBtn.setPositionY(30);
            partBtn.setFontColour("#ffffffff");
            partBtn.setFontSize(20);
            partBtn.removeBorder();
            menuBar.getChildren().addAll(reportBtn, partBtn);
        }

        if (user.getRole().equals("ADMIN")) {

            ButtonWrapper settingsBtn = new ButtonWrapper();
            settingsBtn.setCornerRadius(5);
            settingsBtn.setButtonWidth(120);
            settingsBtn.setButtonHeight(60);
            settingsBtn.setFontName("Arial");
            settingsBtn.setText("Settings");
            settingsBtn.setBackgroundColour("fbb12eff");
            settingsBtn.setClickcolour(Color.WHITE);
            settingsBtn.setHoverColour("#ff8c00ff");
            settingsBtn.setPositionX(290);
            settingsBtn.setPositionY(30);
            settingsBtn.setFontColour("#ffffffff");
            settingsBtn.setFontSize(20);
            settingsBtn.removeBorder();
            menuBar.getChildren().add(settingsBtn);
        }
        Pane spacer = new Pane();
        spacer.setPrefWidth(1000);

        menuBar.getChildren().addAll(spacer, logOutBtn);

        return menuBar;

    }

}
