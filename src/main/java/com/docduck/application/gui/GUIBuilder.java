package com.docduck.application.gui;

import com.docduck.application.data.BaseData;
import com.docduck.application.data.Machine;
import com.docduck.application.data.Report;
import com.docduck.application.data.User;
import com.docduck.application.files.FTPHandler;
import com.docduck.application.gui.pages.AdminPage;
import com.docduck.application.gui.pages.ReportPage;
import com.docduck.application.gui.pages.StatusPage;
import com.docduck.application.xmldom.ElementDataNotRemoved;
import com.docduck.buttonlibrary.ButtonWrapper;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

public class GUIBuilder {

    private final Pane root;
    private static GUIBuilder instance = null;
    private static EventManager events;
    public double CURRENT_WINDOW_WIDTH = 1296;
    public double CURRENT_WINDOW_HEIGHT = 759;
    private final Scale scale;
    protected Hashtable<String, Hashtable<String, Object>> xmlData = new Hashtable<>();
    private StatusPage statusPage;
    private ReportPage reportPage;
    private ArrayList<Machine> machines;
    private User user;

    private AdminPage adminPage;
    private ArrayList<User> allUsers;

    private GUIBuilder(Pane root) {
        this.root = root;
        this.scale = new Scale();
    }

    public static GUIBuilder createInstance(Pane root) {

        if (instance == null) {
            instance = new GUIBuilder(root);
        }
        return instance;
    }

    public static GUIBuilder getInstance() {
        return instance;
    }

    public void updateInstances() {
        XMLBuilder xmlBuilder = XMLBuilder.getInstance();
        events = EventManager.getInstance();
        FTPHandler ftpHandler = FTPHandler.getInstance();
    }

    public void setData(Hashtable<String, Hashtable<String, Object>> xmlData) {
        this.xmlData = xmlData;
    }

    public void StartPage() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: #1f5398");
        ImageView logo = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/docducklogo.png"))));
        logo.setLayoutX(390);
        logo.setLayoutY(80);
        logo.setFitWidth(500);
        logo.setPreserveRatio(true);
        ButtonWrapper doc = new ButtonWrapper();
        ButtonWrapper xml = new ButtonWrapper();
        doc.setText("Load DocDuck Application");
        xml.setText("Load Application from XML Files");
        doc.setBackgroundColour("#fbb12eff");
        xml.setBackgroundColour("#fbb12eff");
        doc.setCornerRadius(10);
        xml.setCornerRadius(10);
        doc.setButtonWidth(210);
        xml.setButtonWidth(230);
        doc.setButtonHeight(70);
        xml.setButtonHeight(70);
        doc.setFontName("Arial");
        xml.setFontName("Arial");
        doc.setFontSize(14);
        xml.setFontSize(14);
        doc.setFontColour(Color.web("#292929"));
        xml.setFontColour(Color.web("#292929"));
        doc.setPositionX(400);
        xml.setPositionX(650);
        doc.setPositionY(470);
        xml.setPositionY(470);
        doc.setBorderWidth(2);
        xml.setBorderWidth(2);
        xml.setClickcolour(Color.web("#ffffffff"));
        xml.setHoverColour(Color.web("#ff8c00ff"));
        doc.setClickcolour(Color.web("#ffffffff"));
        doc.setHoverColour(Color.web("#ff8c00ff"));
        xml.setOnAction(events.getActionEvent("xmlPage"));
        doc.setOnAction(events.getActionEvent("loadApp"));
        root.getChildren().addAll(logo, doc, xml);
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    public void LoadFromXMLPage() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: #1f5398");
        ImageView logo = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/docducklogo.png"))));
        logo.setLayoutX(390);
        logo.setLayoutY(80);
        logo.setFitWidth(500);
        logo.setPreserveRatio(true);
        ButtonWrapper xml = new ButtonWrapper();
        xml.setText("Choose PWS or DocDuck compliant XML File");
        xml.setBackgroundColour("#fbb12eff");
        xml.setCornerRadius(10);
        xml.setButtonWidth(400);
        xml.setButtonHeight(70);
        xml.setFontName("Arial");
        xml.setFontSize(14);
        xml.setFontColour(Color.web("#292929"));
        xml.setPositionX(440);
        xml.setPositionY(440);
        xml.setBorderWidth(2);
        xml.setClickcolour(Color.web("#ffffffff"));
        xml.setHoverColour(Color.web("#ff8c00ff"));
        xml.setOnAction(events.getActionEvent("chooseXML"));
        ButtonWrapper back = new ButtonWrapper();
        back.setText("Go Back");
        back.setBackgroundColour("#fbb12eff");
        back.setCornerRadius(10);
        back.setButtonWidth(80);
        back.setButtonHeight(40);
        back.setFontName("Arial");
        back.setFontSize(12);
        back.setFontColour(Color.web("#292929"));
        back.setPositionX(600);
        back.setPositionY(550);
        back.setBorderWidth(2);
        back.setClickcolour(Color.web("#ffffffff"));
        back.setHoverColour(Color.web("#ff8c00ff"));
        back.setOnAction(events.getActionEvent("goBack"));
        root.getChildren().addAll(logo, xml, back);
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    public void OfflinePage() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: #1f5398");
        Label text1 = new Label("Unable to connect to server. Would you like to continue offline?");
        text1.setFont(new Font("Arial", 30));
        text1.setStyle("-fx-text-fill: #FFFFFF");
        text1.setLayoutX(250);
        text1.setLayoutY(300);
        Label text2 = new Label("Any changes made to local files could be lost");
        text2.setFont(new Font("Arial", 30));
        text2.setStyle("-fx-text-fill: #FFFFFF");
        text2.setLayoutX(350);
        text2.setLayoutY(350);
        ButtonWrapper doc = new ButtonWrapper();
        ButtonWrapper xml = new ButtonWrapper();
        doc.setText("Load DocDuck Application Offline");
        xml.setText("Load Application from XML Files");
        doc.setBackgroundColour("#fbb12eff");
        xml.setBackgroundColour("#fbb12eff");
        doc.setCornerRadius(10);
        xml.setCornerRadius(10);
        doc.setButtonWidth(250);
        xml.setButtonWidth(230);
        doc.setButtonHeight(70);
        xml.setButtonHeight(70);
        doc.setFontName("Arial");
        xml.setFontName("Arial");
        doc.setFontSize(14);
        xml.setFontSize(14);
        doc.setFontColour(Color.web("#292929"));
        xml.setFontColour(Color.web("#292929"));
        doc.setPositionX(385);
        xml.setPositionX(650);
        doc.setPositionY(470);
        xml.setPositionY(470);
        doc.setBorderWidth(2);
        xml.setBorderWidth(2);
        xml.setClickcolour(Color.web("#ffffffff"));
        xml.setHoverColour(Color.web("#ff8c00ff"));
        doc.setClickcolour(Color.web("#ffffffff"));
        doc.setHoverColour(Color.web("#ff8c00ff"));
        xml.setOnAction(events.getActionEvent("xmlPage"));
        doc.setOnAction(events.getActionEvent("loadAppOffline"));
        root.getChildren().addAll(text1, text2, doc, xml);
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    public void LoginPage() {

    }

    public void scaleNodes(Parent container, double windowWidth, double windowHeight) {
        double WIDTH = 1296;
        double HEIGHT = 759;
        double widthScale = windowWidth / WIDTH;
        double heightScale = windowHeight / HEIGHT;
        ObservableList<Node> nodes = null;

        if (container instanceof Pane) {
            Pane pane = (Pane) container;
            nodes = pane.getChildren();
        }

        if (widthScale < heightScale) {
            scale.setX(widthScale);
            scale.setY(widthScale);
        }
        else {
            scale.setX(heightScale);
            scale.setY(heightScale);
        }

        for (int i = 0; i < Objects.requireNonNull(nodes).size(); i++) {
            Node node = nodes.get(i);

            if (!node.getTransforms().contains(scale)) {
                node.getTransforms().add(scale);
            }

            double newX = (node.getLayoutX() * (widthScale - 1));
            node.setTranslateX(newX);

            double newY = (node.getLayoutY() * (heightScale - 1));
            node.setTranslateY(newY);
        }
        CURRENT_WINDOW_WIDTH = windowWidth;
        CURRENT_WINDOW_HEIGHT = windowHeight;
    }

    /**
     * Displays the desired page
     * 
     * @param pageName Name of the page to be displayed in ALL CAPS
     */
    public void displayPage(String pageName) {
        root.getChildren().clear();

        switch (pageName) {

        case "STATUS":
            root.getChildren().add(statusPage);
            statusPage.drawMachineButtons();
            break;

        case "REPORT":
            root.getChildren().add(reportPage);
            reportPage.drawReportButtons();
            break;

        case "ADMIN":
            root.getChildren().add(adminPage);
            scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
            break;
        }
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    public void displayPage(String pageName, Machine machine) {
        root.getChildren().clear();

        if (pageName.equals("REPORT")) {
            root.getChildren().add(reportPage);
            reportPage.drawReportButtons();
            reportPage.drawReportInfo(machine);
        }
        scaleNodes(root, CURRENT_WINDOW_WIDTH, CURRENT_WINDOW_HEIGHT);
    }

    /**
     * Builds all the pages
     */
    public void buildPages() {
        populateData();
        statusPage = new StatusPage(machines, user);
        reportPage = new ReportPage(machines, user);
        adminPage = new AdminPage(machines, user, allUsers); // needs integrating (move user and machines out of admin page and use below)
    }

    private void populateData() {

        user = new User("Name", "bob1", "passwordHash", "bob@york.ac.uk", "ADMIN");

        BaseData bd = new BaseData();
        machines = bd.setupMachineDataFromXML();
        allUsers = bd.setupUserDataFromXML();
        ArrayList<Report>allReports = bd.setupReportDataFromXML();

        for (Machine machine :  machines) {
            for (Report report : allReports) {
                for (User user :  allUsers) {
                    if (user.getId() == report.getUserID()) {
                        report.setUser(user);
                        break;
                    }
                }
                
                if (report.getMachineID() == machine.getId()) {
                    machine.addReport(report);
                    break;
                }
            }
        }

        for (User user : allUsers) {
            try {
                user.deleteUser();
            } catch (ElementDataNotRemoved e) {
                e.printErrorMessage();
                throw new RuntimeException(e);
            }
        }

    }
    
}