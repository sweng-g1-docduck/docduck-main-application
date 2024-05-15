package com.docduck.application.gui.pages;

import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AdminPage extends Page {

    public AdminPage() {
        super();
        user = new User("Bob", "bob@york.ac.uk", "ADMIN");
        setTop(drawMenuBar());
        buildPage();
    }

    @Override
    public void buildPage() {
        VBox leftSection = createLeftSection();
        VBox rightSection = createRightSection();

        setLeft(leftSection);
        setCenter(rightSection);

        setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        setPrefHeight(Screen.getPrimary().getBounds().getHeight());

        super.buildPage();
    }

    private VBox createLeftSection() {
        VBox leftSection = new VBox(20);
        leftSection.setBackground(new Background(new BackgroundFill(Color.web("#1f5398"), new CornerRadii(5), new Insets(5,5,5,5))));
        leftSection.setPrefWidth(200); // Increased width

        leftSection.getChildren().addAll(
                createManagerBox("Machine Manager", "Edit Machine", "Add Machine", "Remove Machine"),
                createManagerBox("User Manager", "Edit User", "Add User", "Remove User"),
                createManagerBox("Components Manager", "Edit Component", "Add Component", "Remove Component"),
                createManagerBox("Parts Manager", "Edit Part", "Add Part", "Remove Part")
        );

        leftSection.setPadding(new Insets(10));
        return leftSection;
    }

    private VBox createManagerBox(String header, String... buttons) {
        VBox managerBox = new VBox(10);
        managerBox.setAlignment(Pos.CENTER);
        managerBox.setPadding(new Insets(10));
        managerBox.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5,5,5,5))));
        managerBox.getChildren().add(createManagerHeader(header));
        for (String buttonText : buttons) {
            managerBox.getChildren().add(createButton(buttonText, header));
        }
        return managerBox;
    }

    private Label createManagerHeader(String headerText) {
        Label managerHeader = new Label(headerText);
        managerHeader.setStyle("-fx-font-size: 15px; -fx-text-fill: #333333;");
        return managerHeader;
    }

    private ButtonWrapper createButton(String text, String managerType) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(180);
        button.setButtonHeight(22);
        button.setFontName("Arial");
        button.setText(text);
        button.setBackgroundColour("#fbb12eff");
        button.setClickcolour(Color.WHITE);
        button.setHoverColour("#ff8c00ff");
        button.setFontColour(Color.WHITE);
        button.setFontSize(11);
        button.removeBorder();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openAddWindow(managerType);
            }
        });

        return button;
    }

    private void openAddWindow(String managerType) {
        Stage addStage = new Stage();
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.setTitle("Add " + managerType);
        addStage.setMinWidth(250);

        Label label = new Label("This is the " + managerType);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> addStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        addStage.setScene(scene);

        addStage.show();
    }

    private VBox createRightSection() {
        VBox rightSection = new VBox(20);
        Background rightBackground = new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5,5,5,5)));
        rightSection.setBackground(rightBackground);
        rightSection.setPrefWidth(400); // Fixed width
        rightSection.setMaxHeight(600); // Fixed height

        List<User> userList = generateUserList();
        for (User user : userList) {
            rightSection.getChildren().add(createUserButton(user));
        }

        // Create the inner VBox for buttons with insets
        VBox innerVBox = new VBox(10);
        innerVBox.setPadding(new Insets(10));
        innerVBox.setMaxWidth(Double.MAX_VALUE);
        innerVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(5,5,5,5)))); // Change background color

        // Add buttons to the inner VBox
        for (User user : userList) {
            innerVBox.getChildren().add(createUserButton(user));
        }

        ScrollPane rightScrollPane = new ScrollPane(innerVBox);
        rightScrollPane.setFitToWidth(true);
        rightScrollPane.setPadding(new Insets(10));

        // Set the inset color to match the outer VBox background color
        Background rightInsetBackground = new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5,5,5,5)));
        rightScrollPane.setBackground(rightInsetBackground);

        VBox rightVBox = new VBox(rightScrollPane);
        rightVBox.setMaxWidth(Double.MAX_VALUE);

        return rightVBox;
    }

    private ButtonWrapper createUserButton(User user) {
        ButtonWrapper userButton = new ButtonWrapper();
        userButton.setCornerRadius(5);
        userButton.setButtonWidth(800);
        userButton.setButtonHeight(90);
        userButton.setFont(Font.font("System", 80));
        userButton.setText(user.getName() + " - " + user.getEmail() + " (" + user.getRole() + ")");
        userButton.setBackgroundColour("#fbb12eff");
        userButton.setClickcolour(Color.WHITE);
        userButton.setHoverColour("#ff8c00ff");
        userButton.setFontColour(Color.WHITE);
        userButton.setFontSize(20);
        userButton.removeBorder();
        return userButton;
    }

    private List<User> generateUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("John Doe", "john@example.com", "Admin"));
        userList.add(new User("Jane Smith", "jane@example.com", "Operator"));
        userList.add(new User("Bob Johnson", "bob@example.com", "Engineer"));
        userList.add(new User("Emily Brown", "emily@example.com", "Admin"));
        userList.add(new User("Michael Clark", "michael@example.com", "Operator"));
        userList.add(new User("Alice Green", "alice@example.com", "Engineer"));

        // Add 20 more random users
        for (int i = 0; i < 20; i++) {
            String[] names = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack"};
            String[] roles = {"Admin", "Operator", "Engineer"};
            String name = names[(int) (Math.random() * names.length)];
            String role = roles[(int) (Math.random() * roles.length)];
            userList.add(new User(name + " " + (i + 1), name.toLowerCase() + (i + 1) + "@example.com", role));
        }

        return userList;
    }
}
