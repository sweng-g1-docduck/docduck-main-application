package com.docduck.application.gui.pages;

import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminPage extends Page {

    private VBox userListVBox;
    private List<User> allUsersList;
    private List<User> filteredList;
    private String currentName = "";
    private String currentRole = "All Users";
    private ButtonWrapper lastPressedUserTypeButton;
    private ButtonWrapper lastPressedManagerButton;
    private ButtonWrapper lastPressedUserButton;


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
        leftSection.setBackground(new Background(new BackgroundFill(Color.web("#1f5398"), new CornerRadii(5), new Insets(5))));
        leftSection.setPadding(new Insets(5));

        leftSection.getChildren().addAll(
                createManagerBox("User Manager", "Edit User", "Add User", "Remove User"),
                createManagerBox("Machine Manager", "Edit Machine", "Add Machine", "Remove Machine"),
                createManagerBox("Components Manager", "Edit Component", "Add Component", "Remove Component"),
                createManagerBox("Parts Manager", "Edit Part", "Add Part", "Remove Part")
        );

        return leftSection;
    }

    private VBox createManagerBox(String header, String... buttons) {
        VBox managerBox = new VBox(10);
        managerBox.setAlignment(Pos.CENTER);
        managerBox.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5))));
        managerBox.setPadding(new Insets(10));
        managerBox.getChildren().add(createManagerHeader(header));
        for (String buttonText : buttons) {
            ButtonWrapper button = createButton(buttonText, header, ButtonType.MANAGER, null);
            if (buttonText.equals("Edit User")) {
                setLastPressedButton(button, lastPressedManagerButton);
                lastPressedManagerButton = button;
            }
            managerBox.getChildren().add(button);
        }
        return managerBox;
    }


    private Label createManagerHeader(String headerText) {
        Label managerHeader = new Label(headerText);
        managerHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        return managerHeader;
    }

    private ButtonWrapper createButton(String text, String managerType, ButtonType type, User user) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(250);
        button.setButtonHeight(18);
        button.setFontName("Arial");
        button.setText(text);
        button.setBackgroundColour("#fbb12eff");
        button.setClickcolour(Color.WHITE);
        button.setHoverColour("#ff8c00ff");
        button.setFontColour(Color.WHITE);
        button.setFontSize(10);
        button.removeBorder();

        button.setOnAction(event -> {
            if (text.equals("Edit User")) {
                openWindow(managerType, text, user);
            } else {
                openWindow(managerType, text, null);
            }
            if (type == ButtonType.MANAGER) {
                setLastPressedButton(button, lastPressedManagerButton);
                lastPressedManagerButton = button;
            } else {
                setLastPressedButton(button, lastPressedUserTypeButton);
                lastPressedUserTypeButton = button;
            }
        });

        return button;
    }

    private void openWindow(String managerType, String actionType, User user) {
        Stage newStage = new Stage();
        VBox formLayout = createManagerForm(managerType, actionType, user);

        Scene scene = new Scene(formLayout, 500, 400); // Extend window size
        newStage.setTitle(managerType + " - " + actionType);
        newStage.setScene(scene);
        newStage.show();
    }

    private VBox createManagerForm(String managerType, String actionType, User user) {
        VBox formLayout = new VBox(20);
        formLayout.setPadding(new Insets(20));
        formLayout.setAlignment(Pos.TOP_CENTER);

        Label headerLabel = new Label(managerType + " Form - " + actionType);
        headerLabel.setFont(new Font("Arial", 20));
        headerLabel.setStyle("-fx-font-weight: bold;");

        formLayout.getChildren().add(headerLabel);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        int row = 0;
        switch (managerType) {
            case "User Manager":
                TextField usernameField = createFormField("Username", user != null ? user.getName() : "");
                gridPane.add(usernameField, 0, row++);
                gridPane.add(createFormField("Email", user != null ? user.getEmail() : ""), 0, row++);
                gridPane.add(createComboBox("Role", "Admin", "Operator", "Engineer"), 0, row++);
                break;
            case "Machine Manager":
                gridPane.add(createFormField("Machine Name", ""), 0, row++);
                gridPane.add(createFormField("Machine Type", ""), 0, row++);
                gridPane.add(createComboBox("Status", "Active", "Inactive"), 0, row++);
                break;
            case "Components Manager":
                gridPane.add(createFormField("Component Name", ""), 0, row++);
                gridPane.add(createFormField("Component Type", ""), 0, row++);
                gridPane.add(createFormField("Quantity", ""), 0, row++);
                break;
            case "Parts Manager":
                gridPane.add(createFormField("Part Name", ""), 0, row++);
                gridPane.add(createFormField("Part Number", ""), 0, row++);
                gridPane.add(createFormField("Supplier", ""), 0, row++);
                break;
            default:
                break;
        }

        formLayout.getChildren().add(gridPane);
        formLayout.getChildren().add(createFormButtons());

        return formLayout;
    }


    private TextField createFormField(String label, String value) {
        TextField textField = new TextField();
        textField.setPromptText(label);
        textField.setText(value);
        textField.setPrefWidth(200);
        return textField;
    }


    private ComboBox<String> createComboBox(String label, String... items) {
        Label fieldLabel = new Label(label + ":");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.getSelectionModel().selectFirst();

        fieldLabel.setPrefWidth(100); // Ensure labels and combo boxes are aligned
        fieldLabel.setFont(new Font("Arial", 14));

        return comboBox;
    }

    private HBox createFormButtons() {
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        ButtonWrapper saveButton = new ButtonWrapper();
        saveButton.setText("Save");
        saveButton.setButtonWidth(100);
        saveButton.setButtonHeight(30);
        saveButton.setFontSize(14);
        saveButton.setOnAction(event -> {
            // Handle save action
            System.out.println("Save button pressed");
        });

        ButtonWrapper cancelButton = new ButtonWrapper();
        cancelButton.setText("Cancel");
        cancelButton.setButtonWidth(100);
        cancelButton.setButtonHeight(30);
        cancelButton.setFontSize(14);
        cancelButton.setOnAction(event -> {
            // Handle cancel action
            ((Stage) cancelButton.getScene().getWindow()).close();
        });

        buttonBox.getChildren().addAll(saveButton, cancelButton);
        return buttonBox;
    }

    private void setLastPressedButton(ButtonWrapper currentButton, ButtonWrapper lastPressedButton) {
        if (lastPressedButton != null) {
            lastPressedButton.removeBorder();
        }
        currentButton.setBorderColour(Color.BLACK);
    }

    private VBox createRightSection() {
        // Header section
        HBox headerBox = new HBox(10);
        headerBox.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5))));
        headerBox.setPadding(new Insets(10));
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label headerLabel = new Label("Users");
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");

        ButtonWrapper allUsersButton = createUserTypeButton("All Users");
        allUsersButton.setBorderColour(Color.BLACK); // Highlight the All Users button

        ButtonWrapper adminsButton = createUserTypeButton("Admins");
        ButtonWrapper engineersButton = createUserTypeButton("Engineers");
        ButtonWrapper operatorsButton = createUserTypeButton("Operators");

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Users");
        searchBar.setMaxWidth(400);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filterUsersByName(newValue));

        headerBox.getChildren().addAll(headerLabel, allUsersButton, adminsButton, engineersButton, operatorsButton, searchBar);


        // User list section
        userListVBox = new VBox(10);
        userListVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(10))));
        userListVBox.setPadding(new Insets(5));

        allUsersList = generateUserList();
        filteredList = new ArrayList<>(allUsersList);

        updateDisplayedUserList(filteredList);

        ScrollPane userListScrollPane = new ScrollPane(userListVBox);
        userListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        userListScrollPane.setFitToWidth(true);

        // Set a maximum height for the scroll pane (adjust as needed)
        userListScrollPane.setMaxHeight(540);

        // StackPane for rounded edges
        StackPane stackPane = new StackPane(userListScrollPane);
        stackPane.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(0))));
        stackPane.setMaxWidth(990);

        // Attach event handler to each user button
        for (User user : allUsersList) {
            ButtonWrapper userButton = createUserButton(user);
            userButton.setOnAction(event -> openEditWindow(user));
        }

        // VBox for the entire right section
        VBox rightSection = new VBox(5);
        rightSection.getChildren().addAll(headerBox, stackPane);
        VBox.setVgrow(stackPane, Priority.ALWAYS);

        return rightSection;
    }

    // Method to open the edit window with user's information filled out
    private void openEditWindow(User user) {
        openWindow("User Manager", "Edit User", user);
    }


    private void filterUsersByName(String name) {
        currentName = name;
        applyFilters();
    }

    private void filterUsersByRole(String role) {
        currentRole = role;
        applyFilters();
    }

    private void applyFilters() {
        filteredList = allUsersList.stream()
                .filter(user -> user.getName().toLowerCase().contains(currentName.toLowerCase()))
                .filter(user -> user.getRole().equalsIgnoreCase(currentRole) || currentRole.equalsIgnoreCase("All Users"))
                .collect(Collectors.toList());
        updateDisplayedUserList(filteredList);
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
        // Calculate the height dynamically based on the number of users
        double height = Math.max(50, Math.min(70, 500 / allUsersList.size()));
        userButton.setButtonHeight(height);
        userButton.setFont(Font.font("System", 80));
        userButton.setText(user.getName() + " - " + user.getUsername() + " - " + user.getEmail() + " (" + user.getRole() + ")");
        userButton.setBackgroundColour("#fbb12eff");
        userButton.setClickcolour(Color.WHITE);
        userButton.setHoverColour("#ff8c00ff");
        userButton.setFontColour(Color.WHITE);
        userButton.setFontSize(18);
        userButton.removeBorder();

        // Adjust event handling to open the edit window with user's information
        userButton.setOnAction(event -> {
            openEditWindow(user);
            setLastPressedButton(userButton, lastPressedUserButton);
            lastPressedUserButton = userButton;
        });

        return userButton;
    }




    private ButtonWrapper createUserTypeButton(String userType) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(100);
        button.setButtonHeight(24);
        button.setFontName("Arial");
        button.setText(userType);
        button.setBackgroundColour("#fbb12eff");
        button.setClickcolour(Color.WHITE);
        button.setHoverColour("#ff8c00ff");
        button.setFontColour(Color.WHITE);
        button.setFontSize(12);
        button.removeBorder();

        String role = userType;
        if (!role.equals("All Users")) {
            role = role.substring(0, role.length() - 1);
        }

        String finalRole = role;
        button.setOnAction(event -> {
            filterUsersByRole(finalRole);
            setLastPressedButton(button, lastPressedUserTypeButton);
            lastPressedUserTypeButton = button;
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
        userList.add(new User("Kelvin Zacharias", "KZ@example.com", "Engineer"));

        for (int i = 0; i < 20; i++) {
            String[] names = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack"};
            String[] roles = {"Admin", "Operator", "Engineer"};
            String name = names[(int) (Math.random() * names.length)];
            String role = roles[(int) (Math.random() * roles.length)];
            userList.add(new User(name + " " + (i + 1), name.toLowerCase() + (i + 1) + "@example.com", role));
        }

        return userList;
    }

    private enum ButtonType {
        USER_TYPE,
        MANAGER
    }
}
