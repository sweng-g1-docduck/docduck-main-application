package com.docduck.application.gui.pages;

import java.util.ArrayList;
import java.util.List;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.application.gui.EventManager;
import com.docduck.buttonlibrary.ButtonWrapper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Page extends BorderPane {

    /**
     * This class will contain the basic page structure for the main application
     * This will include the main menu and a container to contain each specific
     * pages content
     */

    protected final EventManager events;
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

        setMinWidth(1280);
        setMinHeight(720);
    }
    
    public Page() {
        super();
        this.events = EventManager.getInstance();

        setMinWidth(1280);
        setMinHeight(720);
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

        ButtonWrapper overviewBtn = drawButtonWrapper(200, 60, "Machine Overview");
        overviewBtn.setOnAction(events.getActionEvent("statusPage"));

        menuBar.getChildren().addAll(overviewBtn);

        if (user.getRole().equals("ENGINEER") || user.getRole().equals("ADMIN")) {

            ButtonWrapper reportBtn = drawButtonWrapper(240, 60, "Maintainance Reports");
            reportBtn.setOnAction(events.getActionEvent("reportPage"));

            ButtonWrapper partBtn = drawButtonWrapper(160, 60, "Part Search");
            partBtn.setDisable(true);
            menuBar.getChildren().addAll(reportBtn, partBtn);
        }

        if (user.getRole().equals("ADMIN")) {

            ButtonWrapper adminBtn = drawButtonWrapper(120, 60, "Admin");
            adminBtn.setOnAction(events.getActionEvent("adminPage"));
            menuBar.getChildren().add(adminBtn);
        }

        Pane spacer = new Pane();
        spacer.setPrefWidth(1000);

        ButtonWrapper logOutBtn = drawButtonWrapper(120, 60, "Log Out");
        logOutBtn.setOnAction(events.getActionEvent("loginPage"));
        menuBar.getChildren().addAll(spacer, logOutBtn);

        return menuBar;

    }

    protected VBox drawMachineInfoBox(Machine machine) {

        Color textColour = lightTextColour;

        VBox genInfoBox = new VBox();
        genInfoBox.setAlignment(Pos.TOP_LEFT);
        genInfoBox.setSpacing(10);
        genInfoBox.setBackground(new Background(new BackgroundFill(boxColour, new CornerRadii(10), new Insets(5))));
        genInfoBox.setPadding(new Insets(8));

        Label serialNum = new Label("Serial Number: " + machine.getSerialNumber());
        serialNum.setFont(new Font(fontName, smallFontSize));
        serialNum.setTextFill(textColour);

        Label location = new Label("Location: " + machine.getLocation());
        location.setFont(new Font(fontName, smallFontSize));
        location.setTextFill(textColour);

        Hyperlink datasheet = new Hyperlink("Datasheet");
        datasheet.setOnAction(events.getHyperlinkEvent(machine.getDatasheetRef()));
        datasheet.setTextFill(textColour);
        datasheet.setFont(new Font(fontName, smallFontSize));
        datasheet.setTranslateX(-4);
        datasheet.setTranslateY(-2);

        Hyperlink purchaseLink = new Hyperlink("Purchase Link");
        purchaseLink.setOnAction(events.getHyperlinkEvent(machine.getPurchaseLocationRef()));
        purchaseLink.setFont(new Font(fontName, smallFontSize));
        purchaseLink.setTextFill(textColour);
        purchaseLink.setTranslateX(-4);
        purchaseLink.setTranslateY(-6);

        genInfoBox.getChildren().addAll(serialNum, location, datasheet, purchaseLink);

        return genInfoBox;
    }

    protected ButtonWrapper drawButtonWrapper(int width, int height, String text) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(width);
        button.setButtonHeight(height);
        button.setFontName(fontName);
        button.setText(text);
        button.setBackgroundColour(btnColour);
        button.setClickcolour(btnClickColour);
        button.setHoverColour(btnHoverColour);
        button.setFontColour(lightTextColour);
        button.setFontSize(20);
        button.removeBorder();
        return button;

    }

    protected Label drawSubText(String title, Color textColour) {
        Label subTitle = new Label(title);
        subTitle.setFont(new Font(fontName, smallFontSize));
        subTitle.setTextFill(textColour);
        return subTitle;
    }

}
