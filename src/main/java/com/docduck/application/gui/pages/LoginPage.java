package com.docduck.application.gui.pages;

import com.docduck.application.data.User;
import com.docduck.application.gui.GUIBuilder;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBoxPassword;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class LoginPage extends Page {

    private final Color signInBtnColour = btnColour;
    private final Color signInBtnHoverColour = btnHoverColour;
    private final Color signInbtnClickColour = btnClickColour;

    private final Color signUpBtnColour = btnColour;
    private final Color signUpBtnHoverColour = btnHoverColour;
    private final Color signUpBtnClickColour = btnClickColour;
    private ArrayList<User> allUsers;

    public LoginPage(ArrayList<User> allUsers) {
        super();
        this.allUsers = allUsers;

        buildPage();
    }

    @Override
    public void buildPage() {
        drawLoginComponents();
    }

    public void drawLoginComponents() {

        VBox contents = new VBox();
        contents.setAlignment(Pos.CENTER);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        ButtonWrapper signInBtn = drawButtonWrapper(80, 40, "Sign In");
        ButtonWrapper signUpBtn = drawButtonWrapper(80, 40, "Sign Up");

        TextBoxField usernameField = drawTextField(300, 40, "Username");

        HBox passwordBox = new HBox();
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.setSpacing(10);
        StackPane stackedPasswordBox = new StackPane();
        TextBoxPassword passwordField = new TextBoxPassword();

        passwordField.setBoxWidth(300);
        passwordField.setBoxHeight(40);
        passwordField.setPromptText("Password");
        passwordField.setFontName(fontName);
        passwordField.setBackgroundColour(Color.WHITE);
        passwordField.setFontColour(darkTextColour);
        passwordField.setBorderColour(Color.BLACK);
        passwordField.setBorderWidth(2);
        passwordField.setCornerRadius(10);
        passwordField.setFontSize(smallFontSize);
        passwordField.setHoverColour(btnHoverColour);
        passwordField.setVisible(true);
        passwordField.hideText();
        passwordField.createButton();

        stackedPasswordBox.getChildren().addAll(passwordField, passwordField.returnPasswordField());
        passwordBox.getChildren().addAll(stackedPasswordBox, passwordField.returnButton());
        passwordBox.setTranslateX(25);

        signInBtn.setFontSize(14);
        signInBtn.setBackgroundColour(signInBtnColour);
        signInBtn.setHoverColour(signInBtnHoverColour);
        signInBtn.setClickcolour(signInbtnClickColour);

        Label incorrectLogin = drawSubText("Incorrect Username or password", Color.RED);
        incorrectLogin.setVisible(false);

        EventHandler<ActionEvent> loginEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean incorrectUser = true;
                for (User user : allUsers) {
                    if (user.getUsername().equals(usernameField.getText())
                            && user.getPasswordHash().equals(user.hashPassword(passwordField.getText()))) {
                        GUIBuilder.getInstance().buildPages(user);
                        GUIBuilder.getInstance().displayPage("STATUS");
                        usernameField.clear();
                        passwordField.clear();
                        incorrectUser = false;
                        incorrectLogin.setVisible(false);
                    }
                }
                if (incorrectUser) {
                    incorrectLogin.setVisible(true);
                }
            }
        };

        signInBtn.setOnAction(loginEvent);
        passwordField.setOnAction(loginEvent);

        signUpBtn.setFontSize(14);
        signUpBtn.setBackgroundColour(signUpBtnColour);
        signUpBtn.setHoverColour(signUpBtnHoverColour);
        signUpBtn.setClickcolour(signUpBtnClickColour);
        signUpBtn.setOnAction(events.getHyperlinkEvent("https://docduck.wabetteridge.co.uk"));

        buttons.setSpacing(10);
        buttons.getChildren().addAll(signUpBtn, signInBtn);

        int imageWidth = 400;
        Image logo = new Image("/docducklogo.png");

        ImageView view = new ImageView(logo);
        view.setFitWidth(imageWidth);
        view.setPreserveRatio(true);

        contents.setSpacing(15);
        contents.getChildren().addAll(view, usernameField, passwordBox, buttons, incorrectLogin);

        ButtonWrapper xmlButton = drawButtonWrapper(250, 40, "Build from PWS XML");
        xmlButton.setOnAction(events.getActionEvent("chooseXML"));
        xmlButton.setTranslateY(-20);

        BorderPane.setAlignment(contents, Pos.CENTER);
        setCenter(contents);
        BorderPane.setAlignment(xmlButton, Pos.CENTER);
        setBottom(xmlButton);
        setBackground(new Background(new BackgroundFill(docDuckBlue, new CornerRadii(10), new Insets(5))));

    }

    public TextBoxField drawTextField(int width, int height, String promptText) {
        TextBoxField textField = new TextBoxField();
        textField.setBoxWidth(width);
        textField.setBoxHeight(height);
        textField.setPromptText(promptText);
        textField.setFontName(fontName);
        textField.setBackgroundColour(Color.WHITE);
        textField.setFontColour(darkTextColour);
        textField.setBorderColour(Color.BLACK);
        textField.setBorderWidth(2);
        textField.setCornerRadius(10);
        textField.setFontSize(smallFontSize);
        textField.setHoverColour(btnHoverColour);
        return textField;
    }

}
