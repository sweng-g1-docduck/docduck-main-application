package com.docduck.application.gui.pages;

import java.util.ArrayList;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBoxField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginPage extends Page {

    private final Color signInBtnColour = Color.web("#007bffff");
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

        int imageWidth = 400;
        Image logo = new Image("/docducklogo.png");

        ImageView view = new ImageView(logo);
        view.setFitWidth(imageWidth);
        view.setPreserveRatio(true);

        TextBoxField usernameBox = drawTextField(300, 40, "Sign In");
        TextBoxField passwordBox = drawTextField(300, 40, "Sign Up");

        contents.getChildren().addAll(view, usernameBox, passwordBox, buttons);

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

//    public void drawTextBoxFields() {
//
//        TextBoxField usernameBox = drawTextField(300, 40, "Sign In");
//        TextBoxField passwordBox = drawTextField(300, 40, "Sign Up");
//
//    }

    // layout: logo, two fields, 2 buttons, forgot password
    public void drawSigningButtons() {
        HBox buttons = new HBox();

        // ButtonWrapper signInBtn = new ButtonWrapper();

        ButtonWrapper signInBtn = drawButtonWrapper(80, 40, "Sign In");
        ButtonWrapper signUpBtn = drawButtonWrapper(80, 40, "Sign Up");
//        signInBtn.setButtonHeight(40);
//        signInBtn.setButtonWidth(80);
//        signInBtn.setCornerRadius(10);
//        signInBtn.setFontColour(lightTextColour);
        signInBtn.setFontSize(14);
//        signInBtn.setFontName(fontName);
//        //signInBtn.setAlignment(Pos.CENTER_RIGHT);
        signInBtn.setBackgroundColour(signInBtnColour);
        signInBtn.setHoverColour(signInBtnHoverColour);
        signInBtn.setClickcolour(signInbtnClickColour);
//        signInBtn.setText("Sign In");

//        
//        ButtonWrapper signUpBtn = new ButtonWrapper();
//        signUpBtn.setButtonHeight(40);
//        signUpBtn.setButtonWidth(80);
//        signUpBtn.setCornerRadius(10);
//        signUpBtn.setFontColour(lightTextColour);
        signUpBtn.setFontSize(14);
//        signUpBtn.setFontName(fontName);
        signUpBtn.setBackgroundColour(signUpBtnColour);
        signUpBtn.setHoverColour(signUpBtnHoverColour);
        signUpBtn.setClickcolour(signUpBtnClickColour);
//        signUpBtn.setText("Sign Up"); 

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
