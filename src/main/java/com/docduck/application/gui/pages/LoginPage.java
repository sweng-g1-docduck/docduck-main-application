package com.docduck.application.gui.pages;

import java.util.ArrayList;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.application.gui.GUIBuilder;
import com.docduck.buttonlibrary.ButtonWrapper;
import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBoxPassword;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginPage extends Page {

    private final Color signInBtnColour = Color.web("#fbb12eff");
    private final Color signInBtnHoverColour = Color.web("#0056b3ff");
    private final Color signInbtnClickColour = btnClickColour;

    private final Color signUpBtnColour = Color.web("#fbb12eff");
    private final Color signUpBtnHoverColour = Color.web("#ff8c00ff");
    private final Color signUpBtnClickColour = Color.web("#ffffffff");
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

        TextBoxField usernameBox = drawTextField(300, 40, "Username");
        TextBoxField passwordBox = drawTextField(300, 40, "Password");
        
//        HBox showAndHide = new HBox();
//        StackPane pane = new StackPane();
//        TextBoxPassword passwordField = new TextBoxPassword();
        
//        passwordField.setBoxWidth(300);
//        passwordField.setBoxHeight(40);       
//        passwordField.setPromptText("Password");
//        passwordField.setFontName(fontName);
//        passwordField.setBackgroundColour(Color.WHITE);
//        passwordField.setFontColour(darkTextColour);
//        passwordField.setBorderColour(Color.BLACK);
//        passwordField.setBorderWidth(2);
//        passwordField.setCornerRadius(10);
//        passwordField.setFontSize(smallFontSize);
//        passwordField.setHoverColour(btnHoverColour);
//        passwordField.setVisible(true);
//        passwordField.hideText();
//        passwordField.setLayoutX(0);
//        passwordField.setLayoutY(0);
//        
        
//        passwordField.createButton();
//        Button hideBtn = passwordField.returnButton();
       // passwordField.returnButton();
//        showAndHide.getChildren().addAll(passwordField, hideBtn);
        
//        PasswordField pw = passwordField.returnPasswordField();
//        pw.setLayoutX(0);
//        pw.setLayoutY(0);
        
//        pane.getChildren().add(pw);
//        pane.getChildren().add(passwordField);
        
        
        
        
        signInBtn.setFontSize(14);
        signInBtn.setBackgroundColour(signInBtnColour);
        signInBtn.setHoverColour(signInBtnHoverColour);
        signInBtn.setClickcolour(signInbtnClickColour);
        signInBtn.setOnAction((event -> {
            
            for(User user : allUsers) {
                if(user.getUsername().equals(usernameBox.getText()) && user.getPasswordHash().equals(user.hashPassword(passwordBox.getText()))) {
                    GUIBuilder.getInstance().buildPages(user);
                    GUIBuilder.getInstance().displayPage("STATUS");
                    usernameBox.clear();
                    passwordBox.clear();
                }
            }
        }));

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
        contents.getChildren().addAll(view, usernameBox, passwordBox, buttons);

        BorderPane.setAlignment(contents, Pos.CENTER);
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
      
 

}
