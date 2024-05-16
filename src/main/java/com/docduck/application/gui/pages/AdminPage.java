package com.docduck.application.gui.pages;

import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminPage extends Page {

    private VBox userListVBox;
    private List<User> allUsersList;
    private List<User> filteredList; // Added for storing filtered users based on search

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

        super.buildPage();
    }

    private VBox createLeftSection() {
        VBox leftSection = new VBox(25);
        leftSection.setBackground(new Background(new BackgroundFill(Color.web("#1f5398"), new CornerRadii(5), new Insets(0))));

        leftSection.getChildren().addAll(
                createManagerBox("User Manager", "Edit User", "Add User", "Remove User"),
                createManagerBox("Machine Manager", "Edit Machine", "Add Machine", "Remove Machine"),
                createManagerBox("Components Manager", "Edit Component", "Add Component", "Remove Component"),
                createManagerBox("Parts Manager", "Edit Part", "Add Part", "Remove Part")
        );

        leftSection.setPadding(new Insets(5));
        return leftSection;
    }

    private VBox createManagerBox(String header, String... buttons) {
        VBox managerBox = new VBox(5);
        managerBox.setAlignment(Pos.CENTER);
        managerBox.setPadding(new Insets(10));
        managerBox.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5))));
        managerBox.getChildren().add(createManagerHeader(header));
        for (String buttonText : buttons) {
            managerBox.getChildren().add(createButton(buttonText, header));
        }
        return managerBox;
    }

    private Label createManagerHeader(String headerText) {
        Label managerHeader = new Label(headerText);
        managerHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
        return managerHeader;
    }

    private ButtonWrapper createButton(String text, String managerType) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(250);
        button.setButtonHeight(24);
        button.setFontName("Arial");
        button.setText(text);
        button.setBackgroundColour("#fbb12eff");
        button.setClickcolour(Color.WHITE);
        button.setHoverColour("#ff8c00ff");
        button.setFontColour(Color.WHITE);
        button.setFontSize(12);
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
        // Implementation of the openAddWindow method
    }

    private VBox createRightSection() {
        HBox headerBox = new HBox(10);
        headerBox.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5))));
        headerBox.setPadding(new Insets(10));
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label headerLabel = new Label("Users");
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");

        ButtonWrapper allUsersButton = createUserTypeButton("All Users");
        ButtonWrapper adminsButton = createUserTypeButton("Admins");
        ButtonWrapper engineersButton = createUserTypeButton("Engineers");
        ButtonWrapper operatorsButton = createUserTypeButton("Operators");

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Users");
        searchBar.setMaxWidth(400);

        // Add listener to search bar text property
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterUsersByName(newValue);
            }
        });

        headerBox.getChildren().addAll(headerLabel, allUsersButton, adminsButton, engineersButton, operatorsButton, searchBar);

        userListVBox = new VBox(10);
        userListVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(5))));
        userListVBox.setPadding(new Insets(10));

        allUsersList = generateUserList();
        filteredList = new ArrayList<>(allUsersList); // Initialize filtered list with all users
        updateDisplayedUserList(filteredList); // Update the displayed list with all users

        ScrollPane userListScrollPane = new ScrollPane(userListVBox);
        userListScrollPane.setMaxWidth(1000);
        userListScrollPane.setMaxHeight(500);
        userListScrollPane.setPadding(new Insets(20));
        userListScrollPane.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(5))));
        userListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox rightSection = new VBox(5);
        rightSection.getChildren().addAll(headerBox, userListScrollPane);

        return rightSection;
    }

    private void filterUsersByName(String name) {
        List<User> filteredByName = allUsersList.stream()
                .filter(user -> user.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        filteredList = new ArrayList<>(filteredByName); // Update filtered list with users matching the name and role
        updateDisplayedUserList(filteredList); // Update displayed list with filtered users
    }

    private void filterUsersByRole(String role) {
        List<User> filteredByRole = filteredList.stream()
                .filter(user -> user.getRole().equalsIgnoreCase(role) || role.equalsIgnoreCase("All Users"))
                .collect(Collectors.toList());
        updateDisplayedUserList(filteredByRole); // Update displayed list with filtered users by role
    }

    private void updateDisplayedUserList(List<User> users) {
        userListVBox.getChildren().clear();
        for (User user : users) {
            userListVBox.getChildren().add(createUserButton(user));
        }
    }

    private ButtonWrapper createUserButton(User user) {
        ButtonWrapper userButton = new ButtonWrapper();
        userButton.setCornerRadius(5);
        userButton.setButtonWidth(900);
        userButton.setButtonHeight(70);
        userButton.setFont(Font.font("System", 80));
        userButton.setText(user.getName() + " - " + user.getEmail() + " (" + user.getRole() + ")");
        userButton.setBackgroundColour("#fbb12eff");
        userButton.setClickcolour(Color.WHITE);
        userButton.setHoverColour("#ff8c00ff");
        userButton.setFontColour(Color.WHITE);
        userButton.setFontSize(18);
        userButton.removeBorder();
        return userButton;
    }

    private ButtonWrapper createUserTypeButton(String userType) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(100); // Adjust the width as needed
        button.setButtonHeight(24);
        button.setFontName("Arial");
        button.setText(userType);
        button.setBackgroundColour("#fbb12eff");
        button.setClickcolour(Color.WHITE);
        button.setHoverColour("#ff8c00ff");
        button.setFontColour(Color.WHITE);
        button.setFontSize(12);
        button.removeBorder();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                filterUsersByRole(userType);
            }
        });
        return button;
    }

    private List<User> generateUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("John Doe", "john@example.com", "Admin"));
        userList.add(new User("Jane Smith", "jane@example.com", "Operator"));
        userList.add(new User("Bob Johnson", "bob@example.com", "Engineer"));
        userList.add(new User("Emily Brown", "emily@example.com", "Admin"));
        userList.add(new User("Michael Clark", "michael@example.com", "Operator"));
        userList.add(new User("Alice Green", "alice@example.com", "Engineer"));

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
