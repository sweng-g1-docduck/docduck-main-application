package com.docduck.application.gui.pages;

import java.util.ArrayList;
import java.util.List;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.application.gui.EventManager;
import com.docduck.buttonlibrary.ButtonWrapper;

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

    protected Machine machine; // admin (needs integrating)
    List<User> userList = new ArrayList<>(); // admin (needs integrating)
    List<Machine> machineList = new ArrayList<>(); // admin (needs integrating)

    protected ArrayList<Machine> machines;

    protected final Color barColour = Color.web("245494");
    protected final Color backgroundColour = Color.LIGHTSLATEGREY;
    protected final Color boxColour = Color.LIGHTGREY;
    protected final Color btnColour = Color.web("fbb12eff");
    protected final Color btnHoverColour = Color.web("#ff8c00ff");
    protected final Color btnClickColour = Color.web("fbb12eff");
    protected final Color lightTextColour = Color.WHITE;
    protected final Color darkTextColour = Color.BLACK;
    protected final String fontName = "Arial";
    protected final int smallFontSize = 12;

    public Page(ArrayList<Machine> machines, User user) {
        super();
        this.machines = machines;
        this.user = user;
        this.events = EventManager.getInstance();
        setBackground(new Background(new BackgroundFill(Color.web("#245494"), CornerRadii.EMPTY, Insets.EMPTY)));
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

                new Background(new BackgroundFill(barColour, new CornerRadii(10), new Insets(5))));

        menuBar.setSpacing(15);
        menuBar.setPrefSize(1280, 90);
        menuBar.setAlignment(Pos.CENTER_LEFT);

        ButtonWrapper overviewBtn = new ButtonWrapper();
        overviewBtn.setCornerRadius(5);
        overviewBtn.setButtonWidth(200);
        overviewBtn.setButtonHeight(60);
        overviewBtn.setFontName(fontName);
        overviewBtn.setText("Machine Overview");
        overviewBtn.setBackgroundColour(btnColour);
        overviewBtn.setClickcolour(btnClickColour);
        overviewBtn.setHoverColour(btnHoverColour);
        overviewBtn.setPositionX(150);
        overviewBtn.setPositionY(30);
        overviewBtn.setFontColour(lightTextColour);
        overviewBtn.setFontSize(20);
        overviewBtn.removeBorder();
        overviewBtn.setOnAction(events.getActionEvent("statusPage"));

        ButtonWrapper logOutBtn = new ButtonWrapper();
        logOutBtn.setCornerRadius(5);
        logOutBtn.setButtonWidth(120);
        logOutBtn.setButtonHeight(60);
        logOutBtn.setFontName(fontName);
        logOutBtn.setText("Log Out");
        logOutBtn.setBackgroundColour(btnColour);
        logOutBtn.setClickcolour(btnClickColour);
        logOutBtn.setHoverColour(btnHoverColour);
        logOutBtn.setPositionX(150);
        logOutBtn.setPositionY(30);
        logOutBtn.setFontColour(lightTextColour);
        logOutBtn.setFontSize(20);
        logOutBtn.removeBorder();

        menuBar.getChildren().addAll(overviewBtn);

        if (user.getRole().equals("ENGINEER") || user.getRole().equals("ADMIN")) {

            ButtonWrapper reportBtn = new ButtonWrapper();
            reportBtn.setCornerRadius(5);
            reportBtn.setButtonWidth(240);
            reportBtn.setButtonHeight(60);
            reportBtn.setFontName(fontName);
            reportBtn.setText("Maintainance Reports");
            reportBtn.setBackgroundColour(btnColour);
            reportBtn.setClickcolour(btnClickColour);
            reportBtn.setHoverColour(btnHoverColour);
            reportBtn.setFontColour(lightTextColour);
            reportBtn.setFontSize(20);
            reportBtn.removeBorder();
            reportBtn.setOnAction(events.getActionEvent("reportPage"));

            ButtonWrapper partBtn = new ButtonWrapper();
            partBtn.setCornerRadius(5);
            partBtn.setButtonWidth(160);
            partBtn.setButtonHeight(60);
            partBtn.setFontName(fontName);
            partBtn.setText("Part Search");
            partBtn.setBackgroundColour(btnColour);
            partBtn.setClickcolour(btnClickColour);
            partBtn.setHoverColour(btnHoverColour);
            partBtn.setFontColour(lightTextColour);
            partBtn.setFontSize(20);
            partBtn.removeBorder();
            partBtn.setDisable(true);
            menuBar.getChildren().addAll(reportBtn, partBtn);
        }

        if (user.getRole().equals("ADMIN")) {

            ButtonWrapper settingsBtn = new ButtonWrapper();
            settingsBtn.setCornerRadius(5);
            settingsBtn.setButtonWidth(120);
            settingsBtn.setButtonHeight(60);
            settingsBtn.setFontName(fontName);
            settingsBtn.setText("Settings");
            settingsBtn.setBackgroundColour(btnColour);
            settingsBtn.setClickcolour(btnClickColour);
            settingsBtn.setHoverColour(btnHoverColour);
            settingsBtn.setFontColour(lightTextColour);
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
