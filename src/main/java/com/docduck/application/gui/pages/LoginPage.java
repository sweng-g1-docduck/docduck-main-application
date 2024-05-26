package com.docduck.application.gui.pages;

import java.util.ArrayList;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBoxField;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginPage extends Page {

    private final Color signInBtnColour = Color.web("#fbb12eff");
    private final Color signInBtnHoverColour = Color.web("#0056b3ff");
    private final Color signInbtnClickColour = btnClickColour;

    private final Color signUpBtnColour = Color.web("#fbb12eff");
    private final Color signUpBtnHoverColour = Color.web("#ff8c00ff");
    private final Color signUpBtnClickColour = Color.web("#ffffffff");

    public LoginPage(ArrayList<Machine> machines, User user) {
        super(machines, user);

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

        signInBtn.setFontSize(14);
        signInBtn.setBackgroundColour(signInBtnColour);
        signInBtn.setHoverColour(signInBtnHoverColour);
        signInBtn.setClickcolour(signInbtnClickColour);

        signUpBtn.setFontSize(14);
        signUpBtn.setBackgroundColour(signUpBtnColour);
        signUpBtn.setHoverColour(signUpBtnHoverColour);
        signUpBtn.setClickcolour(signUpBtnClickColour);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(signUpBtn, signInBtn);

        int imageWidth = 400;
        Image logo = new Image("/docducklogo.png");

        ImageView view = new ImageView(logo);
        view.setFitWidth(imageWidth);
        view.setPreserveRatio(true);

        TextBoxField usernameBox = drawTextField(300, 40, "Username");
        TextBoxField passwordBox = drawTextField(300, 40, "Password");

        contents.setSpacing(15);
        contents.getChildren().addAll(view, usernameBox, passwordBox, buttons);
        setAlignment(contents, Pos.CENTER);
        setCenter(contents);
        setBackground(new Background(new BackgroundFill(barColour, new CornerRadii(10), new Insets(5))));

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

    // layout: logo, two fields, 2 buttons, forgot password
    public void drawSigningButtons() {
        HBox buttons = new HBox();

        ButtonWrapper signInBtn = drawButtonWrapper(80, 40, "Sign In");
        ButtonWrapper signUpBtn = drawButtonWrapper(80, 40, "Sign Up");

        signInBtn.setFontSize(14);
        signInBtn.setBackgroundColour(signInBtnColour);
        signInBtn.setHoverColour(signInBtnHoverColour);
        signInBtn.setClickcolour(signInbtnClickColour);

        signUpBtn.setFontSize(14);
        signUpBtn.setBackgroundColour(signUpBtnColour);
        signUpBtn.setHoverColour(signUpBtnHoverColour);
        signUpBtn.setClickcolour(signUpBtnClickColour);

        buttons.getChildren().addAll(signUpBtn, signInBtn);

    }

    public void drawLogo() {
        int imageWidth = 400;
        Image logo = new Image("/docducklogo.png");

        ImageView view = new ImageView(logo);
        view.setFitWidth(imageWidth);
        view.setPreserveRatio(true);

    }

}
