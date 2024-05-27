package com.docduck.application.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.graphicslibrary.Ellipse;
import com.docduck.graphicslibrary.Rectangle;
import com.docduck.graphicslibrary.RegularShape;
import com.docduck.textlibrary.TextBox;
import com.docduck.textlibrary.TextBox.Origin;
import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBoxPassword;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import uk.co.bookcook.BCMediaPlayer;

public class XMLBuilder {

    private Hashtable<String, Hashtable<String, Object>> xmlData = new Hashtable<>();
    private final Pane root;
    private static XMLBuilder instance = null;
    private static EventManager events;
    private static GUIBuilder guiBuilder;

    private XMLBuilder(Pane root) {
        this.root = root;
    }

    public static XMLBuilder createInstance(Pane root) {

        if (instance == null) {
            instance = new XMLBuilder(root);
        }
        return instance;
    }

    public static XMLBuilder getInstance() {
        return instance;
    }

    public void updateInstances() {
        events = EventManager.getInstance();
        guiBuilder = GUIBuilder.getInstance();
    }

    public void setData(Hashtable<String, Hashtable<String, Object>> xmlData) {
        this.xmlData = xmlData;
    }

    public void buildCustomXML(int slideNumber, int slideCount) {
        ButtonWrapper prev = new ButtonWrapper();
        ButtonWrapper next = new ButtonWrapper();
        prev.setText("Previous Slide");
        next.setText("Next Slide");
        prev.setBackgroundColour("#FFFFFF");
        next.setBackgroundColour("#FFFFFF");
        prev.setCornerRadius(3);
        next.setCornerRadius(3);
        prev.setButtonWidth(80);
        next.setButtonWidth(60);
        prev.setButtonHeight(20);
        next.setButtonHeight(20);
        prev.setFontName("Arial");
        next.setFontName("Arial");
        prev.setFontSize(10);
        next.setFontSize(10);
        prev.setFontColour(Color.web("#292929"));
        next.setFontColour(Color.web("#292929"));
        prev.setPositionX(5);
        next.setPositionX(1200);
        prev.setPositionY(657);
        next.setPositionY(657);
        prev.setBorderWidth(1);
        next.setBorderWidth(1);
        next.setClickcolour(Color.web("#ffffffff"));
        next.setHoverColour(Color.web("#9c9c9c"));
        prev.setClickcolour(Color.web("#ffffffff"));
        prev.setHoverColour(Color.web("#9c9c9c"));
        next.setOnAction(events.getSlideEvent(slideNumber + 1, slideCount));
        prev.setOnAction(events.getSlideEvent(slideNumber - 1, slideCount));

        prev.setVisible(slideNumber > 1);

        next.setVisible(slideNumber < slideCount);
        root.setStyle("-fx-background-color: #FFFFFF");
        root.getChildren().clear();
        root.getChildren().addAll(buildSlide(slideNumber));
        root.getChildren().addAll(next, prev);
        guiBuilder.scaleNodes(root, guiBuilder.CURRENT_WINDOW_WIDTH, guiBuilder.CURRENT_WINDOW_HEIGHT);
    }

    public ArrayList<Node> buildSlide(int slideNumber) {

        ArrayList<Node> nodeList = new ArrayList<>();

        buildShapes(nodeList, slideNumber);
        buildTextBoxes(nodeList, slideNumber);
        buildImages(nodeList, slideNumber);
        buildAudio(nodeList, slideNumber);
        buildVideos(nodeList, slideNumber);
        buildButtons(nodeList, slideNumber);
        buildHyperlinks(nodeList, slideNumber);
        buildBackgroundColours(nodeList, slideNumber);
        buildTextFields(nodeList, slideNumber);

        return nodeList;
    }

    public ArrayList<Node> buildTextBoxes(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String textBox = "textBox";
        final String delimiter = "-";

        while (hasDataRemaining) {
            hasDataRemaining = false;

            if (xmlData.containsKey(textBox + delimiter + slideNumber + delimiter + occurance)) {
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
                textbox.addBorder();
                textbox.setBorderColour((String) textBoxData.get("borderColour"));
                Double borderWidth = (Double) textBoxData.get("borderWidth");
                textbox.setBorderWidth(borderWidth.intValue());
                textbox.setMargin(borderWidth / 2.0);
                textbox.setPositionX((Integer) textBoxData.get("xCoordinate"));
                textbox.setPositionY((Integer) textBoxData.get("yCoordinate"));
                nodeList.add(textbox.returnBackground());
                nodeList.add(textbox);
                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    public ArrayList<Node> buildShapes(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String shape = "shape";
        final String delimiter = "-";

        while (hasDataRemaining) {
            hasDataRemaining = false;

            if (xmlData.containsKey(shape + delimiter + slideNumber + delimiter + occurance)) {

                Hashtable<String, Object> shapeData = xmlData
                        .get(shape + delimiter + slideNumber + delimiter + occurance);

                Double SHAPE_SCALE_RANGE = 100.0;
                String shapeType = (String) shapeData.get("type");
                String c = (String) shapeData.get("shapeColour");
                String shapeColour = c.substring(0, c.length() - 2);
                Double shapeRotation = (Double) shapeData.get("shapeRotation");
                Integer x = (Integer) shapeData.get("xCoordinate");
                Double xCoordinate = x.doubleValue();
                Integer y = (Integer) shapeData.get("yCoordinate");
                Double yCoordinate = y.doubleValue();
                Double shapeScale = (Double) shapeData.get("shapeScale");
                String o = c.substring(c.length() - 2);
                Long hex = Long.parseLong(o, 16);
                Double shapeOpacity = hex.doubleValue() / 255;
                Double radius;
                Double radius2;
                Double width;
                Double height;
                String borderColour = (String) shapeData.get("borderColour");
                Double borderWidth = (Double) shapeData.get("borderWidth");

                if (shapeData.get("radius") != null && shapeData.get("radius2") != null) {
                    radius = (Double) shapeData.get("radius");
                    radius2 = (Double) shapeData.get("radius2");
                }
                else if (shapeData.get("radius") != null && shapeData.get("radius2") == null) {
                    radius = (Double) shapeData.get("radius");
                    radius2 = 1.5 * radius;
                }
                else if (shapeData.get("radius") == null && shapeData.get("radius2") != null) {
                    radius2 = (Double) shapeData.get("radius2");
                    radius = radius2 / 1.5;
                }
                else {
                    radius = 720 * shapeScale / SHAPE_SCALE_RANGE;
                    radius2 = 1.5 * radius;
                }

                if (shapeData.get("width") != null && shapeData.get("height") != null) {
                    width = (Double) shapeData.get("width");
                    height = (Double) shapeData.get("height");
                }
                else if (shapeData.get("width") != null && shapeData.get("height") == null) {
                    width = (Double) shapeData.get("width");
                    height = width / 1.5;
                }
                else if (shapeData.get("width") == null && shapeData.get("height") != null) {
                    height = (Double) shapeData.get("height");
                    width = height * 1.5;
                }
                else {
                    height = 720 * shapeScale / SHAPE_SCALE_RANGE;
                    width = 1.5 * height;
                }

                switch (shapeType) {
                case "circle":
                    Ellipse circle = new Ellipse(radius, Color.web(shapeColour), shapeOpacity, xCoordinate,
                            yCoordinate);
                    circle.rotate(shapeRotation);
                    circle.setStroke(Color.web(borderColour), borderWidth);

                    nodeList.add(circle);
                    break;
                case "ellipse":
                    Ellipse ellipse = new Ellipse(radius, radius2, Color.web(shapeColour), shapeOpacity, xCoordinate,
                            yCoordinate);
                    ellipse.rotate(shapeRotation);
                    ellipse.setStroke(Color.web(borderColour), borderWidth);

                    nodeList.add(ellipse);
                    break;
                case "lineSegment":
                    break;
                case "triangle":
                    RegularShape triangle = new RegularShape(3, radius.intValue(), Color.web(shapeColour), shapeOpacity,
                            xCoordinate, yCoordinate);
                    triangle.rotate(shapeRotation);
                    triangle.setStroke(Color.web(borderColour), borderWidth);
                    nodeList.add(triangle);
                    break;
                case "square":
                    RegularShape square = new RegularShape(4, radius.intValue(), Color.web(shapeColour), shapeOpacity,
                            xCoordinate, yCoordinate);
                    square.rotate(shapeRotation);
                    square.setStroke(Color.web(borderColour), borderWidth);
                    nodeList.add(square);
                    break;
                case "pentagon":
                    RegularShape pentagon = new RegularShape(5, radius.intValue(), Color.web(shapeColour), shapeOpacity,
                            xCoordinate, yCoordinate);
                    pentagon.rotate(shapeRotation);
                    pentagon.setStroke(Color.web(borderColour), borderWidth);
                    nodeList.add(pentagon);
                    break;
                case "hexagon":
                    RegularShape hexagon = new RegularShape(6, radius.intValue(), Color.web(shapeColour), shapeOpacity,
                            xCoordinate, yCoordinate);
                    hexagon.rotate(shapeRotation);
                    hexagon.setStroke(Color.web(borderColour), borderWidth);
                    nodeList.add(hexagon);
                    break;
                case "rectangle":
                    Rectangle rectangle = new Rectangle(width, height, Color.web(shapeColour), shapeOpacity,
                            xCoordinate, yCoordinate);
                    rectangle.rotate(shapeRotation);
                    rectangle.setStroke(Color.web(borderColour), borderWidth);
                    nodeList.add(rectangle);
                    break;
                }

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    public ArrayList<Node> buildImages(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String image = "image";
        final String delimiter = "-";

        while (hasDataRemaining) {
            hasDataRemaining = false;

            if (xmlData.containsKey(image + delimiter + slideNumber + delimiter + occurance)) {
                Hashtable<String, Object> imageData = xmlData
                        .get(image + delimiter + slideNumber + delimiter + occurance);

                // Hard coded logo as Image Library not done yet
                ImageView logo = null;

                try {
                    logo = new ImageView(
                            new Image(new FileInputStream("src/main/resources" + imageData.get("imageURL"))));
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
//                ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/docducklogo.png")));
                Objects.requireNonNull(logo).setLayoutX((int) imageData.get("xCoordinate"));
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

        while (hasDataRemaining) {
            hasDataRemaining = false;

            if (xmlData.containsKey(audio + delimiter + slideNumber + delimiter + occurance)) {
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

        while (hasDataRemaining) {
            hasDataRemaining = false;

            if (xmlData.containsKey(video + delimiter + slideNumber + delimiter + occurance)) {
                System.out.println("Video-" + slideNumber + "-" + occurance + ": "
                        + xmlData.get(video + delimiter + slideNumber + delimiter + occurance));

                Hashtable<String, Object> videoData = xmlData
                        .get(video + delimiter + slideNumber + delimiter + occurance);

                String videoURL = "/src/main/resources" + videoData.get("videoURL");
                Integer videoStartTime = (Integer) videoData.get("videoStartTime");
                Integer videoStopTime = (Integer) videoData.get("videoStopTime");
                Double videoVolume = (Double) videoData.get("videoVolume");
                Boolean videoLooping = (Boolean) videoData.get("videoLooping");
                Double videoScale = (Double) videoData.get("videoScale");
                Double xCoordinate = (Double) videoData.get("xCoordinate");
                Double yCoordinate = (Double) videoData.get("yCoordinate");
                Double borderWidth = (Double) videoData.get("BorderWidth");
                String borderColour = (String) videoData.get("borderColour");

                BCMediaPlayer v = new BCMediaPlayer(videoURL);

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    public ArrayList<Node> buildButtons(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String button = "button";
        final String delimiter = "-";

        while (hasDataRemaining) {
            hasDataRemaining = false;

            if (xmlData.containsKey(button + delimiter + slideNumber + delimiter + occurance)) {
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
                b.setLayoutX((Integer) buttonData.get("xCoordinate"));
                b.setLayoutY((Integer) buttonData.get("yCoordinate"));

                nodeList.add(b);

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    public ArrayList<Node> buildHyperlinks(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String hyperlink = "hyperlink";
        final String delimiter = "-";

        while (hasDataRemaining) {
            hasDataRemaining = false;

            if (xmlData.containsKey(hyperlink + delimiter + slideNumber + delimiter + occurance)) {
                Hashtable<String, Object> hyperlinkData = xmlData
                        .get(hyperlink + delimiter + slideNumber + delimiter + occurance);
                Hyperlink link = new Hyperlink((String) hyperlinkData.get("URL"));

                link.setStyle("-fx-color: " + hyperlinkData.get("fontColour"));
                link.setStyle("-fx-font: " + hyperlinkData.get("font"));
                link.setStyle("-fx-font-size: " + hyperlinkData.get("fontSize"));
                link.setLineSpacing((Double) hyperlinkData.get("lineSpacing"));
                Integer xCoordinate = (Integer) hyperlinkData.get("xCoordinate");
                Integer yCoordinate = (Integer) hyperlinkData.get("yCoordinate");
                link.setLayoutX(xCoordinate.doubleValue());
                link.setLayoutY(yCoordinate.doubleValue());

                if (hyperlinkData.get("text") != null) {
                    link.setText((String) hyperlinkData.get("text"));
                }

                link.setOnAction(events.getHyperlinkEvent((String) hyperlinkData.get("URL")));
                nodeList.add(link);

                hasDataRemaining = true;
                occurance++;
            }
        }
        return nodeList;
    }

    public ArrayList<Node> buildBackgroundColours(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String backgroundColour = "backgroundColour";
        final String delimiter = "-";

        while (hasDataRemaining) {
            hasDataRemaining = false;

            if (xmlData.containsKey(backgroundColour + delimiter + slideNumber + delimiter + occurance)) {
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

    public ArrayList<Node> buildTextFields(ArrayList<Node> nodeList, int slideNumber) {
        int occurance = 1;
        boolean hasDataRemaining = true;
        final String textField = "textField";
        final String delimiter = "-";

        while (hasDataRemaining) {
            hasDataRemaining = false;

            if (xmlData.containsKey(textField + delimiter + slideNumber + delimiter + occurance)) {
                Hashtable<String, Object> textFieldData = xmlData
                        .get(textField + delimiter + slideNumber + delimiter + occurance);

                if ((boolean) textFieldData.get("password")) {
                    TextBoxPassword textfield = new TextBoxPassword();

                    textfield.setBoxWidth((Double) textFieldData.get("width"));
                    textfield.setBoxHeight((Double) textFieldData.get("height"));
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
                    textfield.setPositionX((Integer) textFieldData.get("xCoordinate"));
                    textfield.setPositionY((Integer) textFieldData.get("yCoordinate"));

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
                    textfield.setPositionX((Integer) textFieldData.get("xCoordinate"));
                    textfield.setPositionY((Integer) textFieldData.get("yCoordinate"));

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
}
}
