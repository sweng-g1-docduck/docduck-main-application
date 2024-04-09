package com.docduck.application.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.graphicslibrary.Ellipse;
import com.docduck.graphicslibrary.Rectangle;
import com.docduck.graphicslibrary.RegularShape;
import com.docduck.textlibrary.TextBox;
import com.docduck.textlibrary.TextBox.Origin;
import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBoxPassword;

import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GUIBuilder {

    private Hashtable<String, Hashtable<String, Object>> xmlData = new Hashtable<>();
    private Pane root;
    private HostServices hostServices;
    private static GUIBuilder instance;
    private static EventManager events;
    private double OLD_CENTER_X = 640;
    private double OLD_CENTER_Y = 360;
    private double OLD_WIDTH = 1280;
    private double OLD_HEIGHT = 720;

    private GUIBuilder(Hashtable<String, Hashtable<String, Object>> xmlData, Pane root, HostServices hostServices) {
        this.xmlData = xmlData;
        this.root = root;
        this.hostServices = hostServices;
    }

    public static GUIBuilder createInstance(Hashtable<String, Hashtable<String, Object>> xmlData, Pane root,
            HostServices hostServices) {

        if (instance == null) {
            instance = new GUIBuilder(xmlData, root, hostServices);
        }
        events = EventManager.createInstance(getInstance(), hostServices, root);
        return instance;
    }

    public static GUIBuilder getInstance() {
        return instance;
    }

    public void buildSlide(int slideNumber) {

        ArrayList<Node> nodeList = new ArrayList<>();

        root.getChildren().clear();
        root.setStyle("-fx-background-color: #f8f4f4");

        nodeList = buildShapes(nodeList, slideNumber);
        nodeList = buildTextBoxes(nodeList, slideNumber);
        nodeList = buildImages(nodeList, slideNumber);
        nodeList = buildAudio(nodeList, slideNumber);
        nodeList = buildVideos(nodeList, slideNumber);
        nodeList = buildButtons(nodeList, slideNumber);
        nodeList = buildHyperlinks(nodeList, slideNumber);
        nodeList = buildBackgroundColours(nodeList, slideNumber);
        nodeList = buildTextFields(nodeList, slideNumber);

        root.getChildren().addAll(nodeList);
    }

    private ArrayList<Node> buildTextBoxes(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String textBox = "textBox";
        final String delimiter = "-";

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

            if (xmlData.containsKey(textBox + delimiter + slideNumber + delimiter + occurance) == true) {
                TextBox textbox = new TextBox();
                Hashtable<String, Object> textBoxData = xmlData
                        .get(textBox + delimiter + slideNumber + delimiter + occurance);

                if (textBoxData.get("hasBackground") != null) {
                    Boolean hasBackground = (Boolean) textBoxData.get("hasBackground");

                    if (hasBackground) {
                        textbox.addBackground();
                    }
                    else {
                        textbox.removeBackground();
                    }
                }

                if (textBoxData.get("cornerRadius") != null) {
                    textbox.setCornerRadius((Double) textBoxData.get("cornerRadius"));
                }

                if (textBoxData.get("backgroundColour") != null) {
                    textbox.setBackgroundColour((String) textBoxData.get("backgroundColour"));
                }

                if (textBoxData.get("textOrigin") != null) {
                    String textOrigin = (String) textBoxData.get("textOrigin");

                    switch (textOrigin) {
                    case "CORNER":
                        textbox.setTextOrigin(Origin.CORNER);
                        break;
                    case "CENTRE":
                        textbox.setTextOrigin(Origin.CENTRE);
                        break;
                    }
                }

                textbox.setBoxWidth((Double) textBoxData.get("width"));
                textbox.setBoxHeight((Double) textBoxData.get("height"));
                textbox.setFontName((String) textBoxData.get("font"));
                textbox.setContent((String) textBoxData.get("text"));
                textbox.setFontSize((Integer) textBoxData.get("fontSize"));
                textbox.setFontColour((String) textBoxData.get("fontColour"));
                textbox.setCharacterSpacing((Double) textBoxData.get("characterSpacing"));
                textbox.setLineSpacing((Double) textBoxData.get("lineSpacing"));
                textbox.setPositionX((Integer) textBoxData.get("xCoordinate"));
                textbox.setPositionY((Integer) textBoxData.get("yCoordinate"));
                textbox.addBorder();
                textbox.setBorderColour((String) textBoxData.get("borderColour"));
                Double borderWidth = (Double) textBoxData.get("borderWidth");
                textbox.setBorderWidth(borderWidth.intValue());
                textbox.setMargin(borderWidth / 2.0);

                nodeList.add(textbox.returnBackground());
                nodeList.add(textbox);
                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    private ArrayList<Node> buildShapes(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String shape = "shape";
        final String delimiter = "-";

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

            if (xmlData.containsKey(shape + delimiter + slideNumber + delimiter + occurance) == true) {

                Hashtable<String, Object> shapeData = xmlData
                        .get(shape + delimiter + slideNumber + delimiter + occurance);

                String shapeType = (String) shapeData.get("type");

                switch (shapeType) {
                case "circle":
                    Circle circle = new Circle();
                    circle.setLayoutX((Integer) shapeData.get("xCoordinate"));
                    circle.setLayoutY((Integer) shapeData.get("yCoordinate"));
                    circle.setScaleX((Double) shapeData.get("shapeScale"));
//                    circle.setStroke(Color.web((String) shapeData.get("borderColour")));
                    nodeList.add(circle);
                    break;
                case "ellipse":
                    Ellipse ellipse = new Ellipse(50, Color.web((String) shapeData.get("shapeColour")), 100.0,
                            (Integer) shapeData.get("xCoordinate"), (Integer) shapeData.get("yCoordinate"));
                    ellipse.setScaleX((Double) shapeData.get("shapeScale"));
                    ellipse.rotate((Double) shapeData.get("shapeRotation"));
                    ellipse.setStroke(Color.web((String) shapeData.get("borderColour")),
                            (Double) shapeData.get("borderWidth"));
                    nodeList.add(ellipse);
                    break;
                case "lineSegment":
//                    LineSegment line = new LineSegment((Double) shapeData.get("xCoordinate"),
//                            (Double) shapeData.get("yCoordinate"), OLD_CENTER_X, OLD_CENTER_X, null, OLD_CENTER_X,
//                            OLD_CENTER_X);
                    break;
                case "triangle":
                    RegularShape triangle = new RegularShape(3, 50, Color.web((String) shapeData.get("shapeColour")),
                            100.0, (Integer) shapeData.get("xCoordinate"), (Integer) shapeData.get("yCoordinate"));
                    triangle.setScaleX((Double) shapeData.get("shapeScale"));
                    triangle.rotate((Double) shapeData.get("shapeRotation"));
                    triangle.setStroke(Color.web((String) shapeData.get("borderColour")),
                            (Double) shapeData.get("borderWidth"));
                    nodeList.add(triangle);
                    break;
                case "square":
                    RegularShape square = new RegularShape(4, 50, Color.web((String) shapeData.get("shapeColour")),
                            100.0, (Double) shapeData.get("xCoordinate"), (Double) shapeData.get("yCoordinate"));
                    square.setScaleX((Double) shapeData.get("shapeScale"));
                    square.rotate((Double) shapeData.get("shapeRotation"));
                    square.setStroke(Color.web((String) shapeData.get("borderColour")),
                            (Double) shapeData.get("borderWidth"));
                    nodeList.add(square);
                    break;
                case "rectangle":
                    Rectangle rectangle = new Rectangle(100.0, 50.0, Color.web((String) shapeData.get("shapeColour")),
                            100.0, (Integer) shapeData.get("xCoordinate"), (Integer) shapeData.get("yCoordinate"));
                    rectangle.setLayoutX((Integer) shapeData.get("xCoordinate"));
                    rectangle.setLayoutY((Integer) shapeData.get("yCoordinate"));
                    rectangle.rotate((Double) shapeData.get("shapeRotation"));
                    rectangle.setScaleX((Double) shapeData.get("shapeScale"));
                    rectangle.setStroke(Color.web((String) shapeData.get("borderColour")),
                            (Double) shapeData.get("borderWidth"));
                    nodeList.add(rectangle);
                    break;
                }

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    private ArrayList<Node> buildImages(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String image = "image";
        final String delimiter = "-";

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

            if (xmlData.containsKey(image + delimiter + slideNumber + delimiter + occurance) == true) {
                Hashtable<String, Object> imageData = xmlData
                        .get(image + delimiter + slideNumber + delimiter + occurance);

                // Hard coded logo as Image Library not done yet
                ImageView logo = new ImageView(
                        new Image(getClass().getResourceAsStream((String) imageData.get("imageURL"))));
//                ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/docducklogo.png")));
                logo.setLayoutX((int) imageData.get("xCoordinate"));
                logo.setLayoutY((int) imageData.get("yCoordinate"));
                logo.setFitWidth((Double) imageData.get("imageScale"));
                logo.setPreserveRatio(true);
                nodeList.add(logo);

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    private ArrayList<Node> buildAudio(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String audio = "audio";
        final String delimiter = "-";

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

            if (xmlData.containsKey(audio + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Audio-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(audio + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    private ArrayList<Node> buildVideos(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String video = "video";
        final String delimiter = "-";

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

            if (xmlData.containsKey(video + delimiter + slideNumber + delimiter + occurance) == true) {
                System.out.println("Video-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(video + delimiter + slideNumber + delimiter + occurance));
                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    private ArrayList<Node> buildButtons(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String button = "button";
        final String delimiter = "-";

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

            if (xmlData.containsKey(button + delimiter + slideNumber + delimiter + occurance) == true) {
                Hashtable<String, Object> buttonData = xmlData
                        .get(button + delimiter + slideNumber + delimiter + occurance);
                ButtonWrapper b = new ButtonWrapper();

                if ((boolean) buttonData.get("hasBackground")) {
                    b.addBackground();
                    b.setBackgroundColour((String) buttonData.get("backgroundColour"));
                }
                else {
                    b.removeBackground();
                }

                if (buttonData.get("eventID") != null) {
                    b.setOnAction(events.getActionEvent((String) buttonData.get("eventID")));
                }

                if (buttonData.get("text") != null) {
                    b.setText((String) buttonData.get("text"));
                }

                if (buttonData.get("hoverColour") != null) {
                    b.setHoverColour((String) buttonData.get("hoverColour"));
                }

                if (buttonData.get("clickColour") != null) {
                    b.setClickcolour((String) buttonData.get("clickColour"));
                }

                b.addBorder();
                b.setBorderColour((String) buttonData.get("borderColour"));
                Double borderWidth = (Double) buttonData.get("borderWidth");
                b.setBorderWidth(borderWidth.intValue());
                b.setButtonWidth((Double) buttonData.get("width"));
                b.setButtonHeight((Double) buttonData.get("height"));
                b.setCornerRadius((Integer) buttonData.get("cornerRadius"));
                b.setFontName((String) buttonData.get("font"));
                b.setFontColour((String) buttonData.get("fontColour"));
                b.setFontSize((Integer) buttonData.get("fontSize"));
                b.setPositionX((Integer) buttonData.get("xCoordinate"));
                b.setPositionY((Integer) buttonData.get("yCoordinate"));

                nodeList.add(b);

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    private ArrayList<Node> buildHyperlinks(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String hyperlink = "hyperlink";
        final String delimiter = "-";

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

            if (xmlData.containsKey(hyperlink + delimiter + slideNumber + delimiter + occurance) == true) {
                Hashtable<String, Object> hyperlinkData = xmlData
                        .get(hyperlink + delimiter + slideNumber + delimiter + occurance);
                Hyperlink link = new Hyperlink((String) hyperlinkData.get("URL"));

                link.setStyle("-fx-color: " + (String) hyperlinkData.get("fontColour"));
                link.setStyle("-fx-font: " + (String) hyperlinkData.get("font"));
                link.setStyle("-fx-font-size: " + hyperlinkData.get("fontSize"));
                link.setLineSpacing((Double) hyperlinkData.get("lineSpacing"));
                Integer xCoordinate = (Integer) hyperlinkData.get("xCoordinate");
                Integer yCoordinate = (Integer) hyperlinkData.get("yCoordinate");
                link.setLayoutX(xCoordinate.doubleValue());
                link.setLayoutY(yCoordinate.doubleValue());

                if (hyperlinkData.get("text") != null) {
                    link.setText((String) hyperlinkData.get("text"));
                }

                EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent e) {
                        hostServices.showDocument((String) hyperlinkData.get("URL"));
                    }
                };

                link.setOnAction(e);
                nodeList.add(link);

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    private ArrayList<Node> buildBackgroundColours(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String backgroundColour = "backgroundColour";
        final String delimiter = "-";

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

            if (xmlData.containsKey(backgroundColour + delimiter + slideNumber + delimiter + occurance) == true) {
                Hashtable<String, Object> backgroundColourData = xmlData
                        .get(backgroundColour + delimiter + slideNumber + delimiter + occurance);
                String colour = (String) backgroundColourData.get("colour");
                root.setStyle("-fx-background-color: " + colour);

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    private ArrayList<Node> buildTextFields(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String textField = "textField";
        final String delimiter = "-";

        while (hasDataRemaining == true) {
            hasDataRemaining = false;

            if (xmlData.containsKey(textField + delimiter + slideNumber + delimiter + occurance) == true) {
                Hashtable<String, Object> textFieldData = xmlData
                        .get(textField + delimiter + slideNumber + delimiter + occurance);

                if ((boolean) textFieldData.get("password")) {
                    TextBoxPassword textfield = new TextBoxPassword();

                    textfield.setBoxWidth((Double) textFieldData.get("width"));
                    textfield.setBoxHeight((Double) textFieldData.get("height"));
                    textfield.setPositionX((Integer) textFieldData.get("xCoordinate"));
                    textfield.setPositionY((Integer) textFieldData.get("yCoordinate"));
                    textfield.setFontName((String) textFieldData.get("font"));
                    textfield.setFontColour((String) textFieldData.get("fontColour"));
                    textfield.setFontSize((Integer) textFieldData.get("fontSize"));
                    textfield.addBorder();
                    textfield.setBorderColour((String) textFieldData.get("borderColour"));
                    Double borderWidth = (Double) textFieldData.get("borderWidth");
                    textfield.setBorderWidth(borderWidth.intValue());
                    textfield.setCornerRadius((Integer) textFieldData.get("cornerRadius"));
                    textfield.setBackgroundColour((String) textFieldData.get("backgroundColour"));
                    textfield.setPromptTextColour((String) textFieldData.get("promptTextColour"));
                    textfield.setHighlightColour((String) textFieldData.get("highlightColour"));
                    textfield.setHighlightFontColour((String) textFieldData.get("fontHighlightColour"));

                    if (textFieldData.get("promptText") != null) {
                        textfield.setPromptText((String) textFieldData.get("promptText"));
                    }

                    if (textFieldData.get("initialText") != null) {
                        textfield.setText((String) textFieldData.get("initialText"));
                    }

                    if (textFieldData.get("hoverColour") != null) {
                        textfield.setHoverColour((String) textFieldData.get("hoverColour"));
                    }

                    if (textFieldData.get("selectColour") != null) {
                        textfield.setSelectColour((String) textFieldData.get("selectColour"));
                    }

                    // Could add text origin?

                    nodeList.add(textfield.returnPasswordField());
                    nodeList.add(textfield);

                    if ((boolean) textFieldData.get("passwordButton")) {
                        textfield.createButton();
                        nodeList.add(textfield.returnButton());
                    }
                }
                else {
                    TextBoxField textfield = new TextBoxField();

                    textfield.setBoxWidth((Double) textFieldData.get("width"));
                    textfield.setBoxHeight((Double) textFieldData.get("height"));
                    textfield.setPositionX((Integer) textFieldData.get("xCoordinate"));
                    textfield.setPositionY((Integer) textFieldData.get("yCoordinate"));
                    textfield.setFontName((String) textFieldData.get("font"));
                    textfield.setFontColour((String) textFieldData.get("fontColour"));
                    textfield.setFontSize((Integer) textFieldData.get("fontSize"));
                    textfield.addBorder();
                    textfield.setBorderColour((String) textFieldData.get("borderColour"));
                    Double borderWidth = (Double) textFieldData.get("borderWidth");
                    textfield.setBorderWidth(borderWidth.intValue());
                    textfield.setCornerRadius((Integer) textFieldData.get("cornerRadius"));
                    textfield.setBackgroundColour((String) textFieldData.get("backgroundColour"));
                    textfield.setPromptTextColour((String) textFieldData.get("promptTextColour"));
                    textfield.setHighlightColour((String) textFieldData.get("highlightColour"));
                    textfield.setHighlightFontColour((String) textFieldData.get("fontHighlightColour"));

                    if (textFieldData.get("promptText") != null) {
                        textfield.setPromptText((String) textFieldData.get("promptText"));
                    }

                    if (textFieldData.get("initialText") != null) {
                        textfield.setText((String) textFieldData.get("initialText"));
                    }

                    if (textFieldData.get("hoverColour") != null) {
                        textfield.setHoverColour((String) textFieldData.get("hoverColour"));
                    }

                    if (textFieldData.get("selectColour") != null) {
                        textfield.setSelectColour((String) textFieldData.get("selectColour"));
                    }

                    // Could add text origin?
                    textfield.setOnKeyTyped(events.getKeyEvent((String) textFieldData.get("promptText")));

                    nodeList.add(textfield);
                }

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    public void scaleNodes(double windowWidth, double windowHeight) {
        double NEW_CENTER_X = windowWidth / 2;
        double NEW_CENTER_Y = windowHeight / 2;
        double WIDTH = 1280;
        double HEIGHT = 720;
        double widthScale = windowWidth / WIDTH;
        double heightScale = windowHeight / HEIGHT;
        ObservableList<Node> nodes = root.getChildren();

        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);

            if (widthScale < heightScale) {
                node.setScaleY(widthScale);
                node.setScaleX(widthScale);

            }
            else {
                node.setScaleY(heightScale);
                node.setScaleX(heightScale);
            }
            double nodeWidth = node.getLayoutBounds().getWidth();
            double nodeHeight = node.getLayoutBounds().getHeight();
            double nodeCenterX = node.getLayoutX() + nodeWidth / 2;
            double nodeCenterY = node.getLayoutY() + nodeHeight / 2;

            // double newX = ((nodeCenterX-OLD_CENTER_X)*(windowWidth/OLD_WIDTH)) +
            // NEW_CENTER_X - nodeWidth/2;
            double newX = (nodeCenterX * windowWidth / OLD_WIDTH) - nodeWidth / 2;

            if (node.layoutXProperty().isBound() != true) {
                node.setLayoutX(newX);
            }
            // double newY = ((nodeCenterY-OLD_CENTER_Y)*(windowHeight/OLD_HEIGHT)) +
            // NEW_CENTER_Y - nodeHeight/2;
            double newY = (nodeCenterY * windowHeight / OLD_HEIGHT) - nodeHeight / 2;

            if (node.layoutYProperty().isBound() != true) {
                node.setLayoutY(newY);
            }
        }
        OLD_CENTER_X = NEW_CENTER_X;
        OLD_CENTER_Y = NEW_CENTER_Y;
        OLD_WIDTH = windowWidth;
        OLD_HEIGHT = windowHeight;
    }
}
