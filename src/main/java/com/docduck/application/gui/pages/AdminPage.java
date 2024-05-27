package com.docduck.application.gui.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Represents the admin page of the application, where administrators can manage
 * users, machines, components, and parts. Inherits from the Page class.
 *
 * @author lw2380
 */
public class AdminPage extends Page {

    private VBox rightSection;
    private VBox userListVBox;
    private VBox machineListVBox;
    private List<User> allUsersList;
    private List<User> filteredUserList;
    private List<Machine> filteredMachineList;
    private String currentName = "";
    private String currentRole = "All Users";
    private String currentMachineStatus = "All Machines";
    private ButtonWrapper lastPressedUserTypeButton;
    private ButtonWrapper lastPressedUserButton;
    private ButtonWrapper lastPressedMachineButton;
    private boolean editingUsers = false;
    private boolean editingMachines = false;
    private Label headerLabel;
    private Stage managerPopOutStage;

    private String usernameFieldValue;
    private String emailFieldValue;
    private String roleFieldValue;
    private String passwordFieldValue;
    private String machineFieldValue;
    private String locationFieldValue;
    private String statusValue;
    private int userIdValue;
    private int machineIdValue;
    private String datasheetValue;
    private String purchaseLocationValue;
    private String nameFieldValue;
    private boolean nullUserField = true;
    private boolean nullUsernameField = true;
    private boolean nullEmailField = true;
    private boolean nullRoleField = true;
    private boolean nullPasswordField = true;
    private boolean nullMachineField;
    private boolean nullLocationField;
    private boolean nullStatusField;
    private boolean nullDatasheetField;
    private boolean nullPurchaseLocationField;


    /**
     * Constructor for AdminPage.
     *
     * @param machines The list of machines in the application.
     * @param user     The current user logged into the application.
     * @param allUsers The list of all users in the application.
     *
     * @author lw2380
     */
    public AdminPage(ArrayList<Machine> machines, User user, ArrayList<User> allUsers) {
        super(machines, user);
        setTop(drawMenuBar());
        buildPage();
        allUsersList = allUsers;
    }

    @Override
    public void buildPage() {
        VBox leftSection = createLeftSection();
        VBox rightSection = createRightSection();

        setLeft(leftSection);
        setCenter(rightSection);

        super.buildPage();
    }

    /**
     * Creates the left section of the admin page, containing various manager boxes
     * for managing users, machines, components, and parts.
     *
     * @return The VBox representing the left section of the admin page.
     */
    private VBox createLeftSection() {
        VBox leftSection = new VBox(25);
        leftSection.setBackground(new Background(new BackgroundFill(barColour, new CornerRadii(5), new Insets(5))));
        leftSection.setPadding(new Insets(5));

        leftSection.getChildren().addAll(createManagerBox("User Manager", "Edit User", "Add User", "Remove User"),
                createManagerBox("Machine Manager", "Edit Machine", "Add Machine", "Remove Machine"),
                createManagerBox("Components Manager", "Edit Component", "Add Component", "Remove Component"),
                createManagerBox("Parts Manager", "Edit Part", "Add Part", "Remove Part"));

        return leftSection;
    }

    /**
     * Creates a manager box with the given header and buttons.
     *
     * @param header  The header text for the manager box.
     * @param buttons The buttons to be included in the manager box.
     * @return The VBox representing the manager box.
     */
    private VBox createManagerBox(String header, String... buttons) {
        VBox managerBox = new VBox(10);
        managerBox.setAlignment(Pos.CENTER);
        managerBox.setBackground(
                new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5))));
        managerBox.setPadding(new Insets(10));
        managerBox.getChildren().add(createManagerHeader(header));

        for (String buttonText : buttons) {
            ButtonWrapper button = createManagerButton(buttonText, header);

            managerBox.getChildren().add(button);

            // Disable the buttons for Components Manager and Parts Manager
            if ("Components Manager".equals(header) || "Parts Manager".equals(header)) {
                button.setDisable(true);
                button.setStyle("-fx-opacity: 0.5;");
            }
        }

        return managerBox;
    }

    /**
     * Creates a label for the manager header.
     *
     * @param headerText The text for the manager header.
     * @return The Label representing the manager header.
     */
    private Label createManagerHeader(String headerText) {
        Label managerHeader = new Label(headerText);
        managerHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        return managerHeader;
    }

    /**
     * Creates a manager button with the given text and manager type.
     *
     * @param text        The text for the button.
     * @param managerType The type of manager associated with the button.
     * @return The ButtonWrapper representing the manager button.
     */
    private ButtonWrapper createManagerButton(String text, String managerType) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(250);
        button.setButtonHeight(18);
        button.setFontName("Arial");
        button.setText(text);
        button.setBackgroundColour(btnColour);
        button.setClickcolour(btnClickColour);
        button.setHoverColour(btnHoverColour);
        button.setFontColour(lightTextColour);
        button.setFontSize(10);
        button.removeBorder();

        button.setOnAction(event -> {

            switch (text) {
            case "Edit User":
            case "Remove User":
                editingUsers = true;
                editingMachines = false;
                createRightSection(); // Clear and refresh right section
                break;
            case "Edit Machine":
            case "Remove Machine":
                editingMachines = true;
                editingUsers = false;
                createRightSection(); // Clear and refresh right section
                break;
            case "Add User":
                editingUsers = true;
                editingMachines = false;
                createRightSection(); // Clear and refresh right section
                openWindow(managerType, text, user, null);
                break;
            case "Add Machine":
                editingUsers = false;
                editingMachines = true;
                createRightSection(); // Clear and refresh right section
                openWindow(managerType, text, null, machine);
                break;
            }

            setLastPressedButton(button, lastPressedUserTypeButton);
            lastPressedUserTypeButton = button;
        });

        return button;
    }

    /**
     * Opens a new window for managing users or machines, depending on the specified
     * manager type and action type.
     *
     * @param managerType The type of manager (e.g., "User Manager", "Machine
     *                    Manager").
     * @param actionType  The action type (e.g., "Edit User", "Edit Machine").
     * @param user        The user object (can be null if managing machines).
     * @param machine     The machine object (can be null if managing users).
     */
    private void openWindow(String managerType, String actionType, User user, Machine machine) {
        if (managerPopOutStage != null) {
            managerPopOutStage.close();
        }
        managerPopOutStage = new Stage();
        VBox formLayout = createManagerForm(managerType, actionType, user, machine);
        formLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(50))));

        Scene scene = new Scene(formLayout, 700, 600);
        scene.setFill(Color.web("#1f5398"));
        managerPopOutStage.setTitle(managerType + " - " + actionType);
        managerPopOutStage.setScene(scene);
        managerPopOutStage.show();
    }

    private VBox createManagerForm(String managerType, String actionType, User user, Machine machine) {
        VBox formLayout = new VBox(20);
        formLayout.setPadding(new Insets(20));
        formLayout.setAlignment(Pos.CENTER);

        Label headerLabel = new Label(managerType + " Form - " + actionType);
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        formLayout.getChildren().add(headerLabel);

        if (editingUsers) {
            formLayout.getChildren().add(createUserManagerForm(managerType, actionType, user));
        }
        else if (editingMachines) {
            formLayout.getChildren().add(createMachineManagerForm(managerType, actionType, machine));
        }

        return formLayout;
    }

    private VBox createUserManagerForm(String managerType, String actionType, User user) {
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(20));
        formLayout.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        
        Label nullTextMessage = new Label();
        nullTextMessage.setTextFill(Color.RED);

        int row = 0;
        if (managerType.equals("User Manager")) {

            gridPane.add(createLabel("Full Name"), 0, row);
            TextField nameField = createFormField("Full Name", user != null ? user.getName() : "");
            gridPane.add(nameField, 1, row++);
            usernameFieldValue = user.getName();

            gridPane.add(createLabel("Username"), 0, row);
            TextField usernameField = createFormField("Username", user != null ? user.getUsername() : "");
            gridPane.add(usernameField, 1, row++);
            usernameFieldValue = user.getUsername();

            gridPane.add(createLabel("Email"), 0, row);
            TextField emailField = createFormField("Email", user != null ? user.getEmail() : "");
            gridPane.add(emailField, 1, row++);
            emailFieldValue = user.getEmail();

            ComboBox<String> roleComboBox;
            if (user != null) {
                gridPane.add(createLabel("Role"), 0, row);
                roleComboBox = createUserComboBox(user.getRole());
                gridPane.add(roleComboBox, 1, row++);
                roleFieldValue = user.getRole();
            }
            else {
                gridPane.add(createLabel("Role"), 0, row);
                roleComboBox = createUserComboBox("");
                gridPane.add(roleComboBox, 1, row++);
            }

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter password");
            gridPane.add(createLabel("Password"), 0, row);
            gridPane.add(passwordField, 1, row++);



            // Set the instance variables when the form fields are filled out
            nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                nameFieldValue = newValue.strip();
                if (nameFieldValue.equals("")) {
                    nullTextMessage.setText("Please fill in First and Second Name");
                    nullUserField = true;
                }
                else {
                    nullTextMessage.setText("");
                    nullUserField = false;
                    System.out.println(nameFieldValue);
                }
            });
            usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
                usernameFieldValue = newValue.strip();
                if (usernameFieldValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in Username");
                    nullUsernameField = true;
                }
                else {
                    nullTextMessage.setText("");
                    nullUsernameField = false;
                }
            });
            emailField.textProperty().addListener((observable, oldValue, newValue) -> {
                emailFieldValue = newValue.strip();
                if (emailFieldValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in Email");
                    nullEmailField = true;
                }
                else {
                    nullTextMessage.setText("");
                    nullEmailField = false;
                }
            });
            roleComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                roleFieldValue = newValue.strip();
                if (roleFieldValue.isEmpty()) {
                    nullTextMessage.setText("Please select Role");
                    nullRoleField = true;
                }
                else {
                    nullTextMessage.setText("");
                    nullRoleField = false;
                }
            });
            passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
                passwordFieldValue = newValue.strip();
                if (passwordFieldValue.isEmpty()) {
                    nullTextMessage.setText("Please set Password");
                    nullPasswordField = true;
                }
                else {
                    nullTextMessage.setText("");
                    nullPasswordField = false;
                }
            });
        }

        formLayout.getChildren().addAll(gridPane, nullTextMessage, createFormButtons());

        return formLayout;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return label;
    }

    private VBox createMachineManagerForm(String managerType, String actionType, Machine machine) {
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(20));
        formLayout.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Label nullTextMessage = new Label();
        nullTextMessage.setTextFill(Color.RED);

        int row = 0;

        if (managerType.equals("Machine Manager")) {
            gridPane.add(createLabel("Machine Name"), 0, row);
            TextField machineNameField = createFormField("Machine Name", machine != null ? machine.getName() : "");
            gridPane.add(machineNameField, 1, row++);
            machineFieldValue = machine != null ? machine.getName() : "";

            gridPane.add(createLabel("Location"), 0, row);
            TextField locationField = createFormField("Location", machine != null ? machine.getLocation() : "");
            gridPane.add(locationField, 1, row++);
            locationFieldValue = machine != null ? machine.getLocation() : "";

            ComboBox<String> statusComboBox;
            if (machine != null) {
                gridPane.add(createLabel("Status"), 0, row);
                statusComboBox = createStatusComboBox(machine.getStatus());
                gridPane.add(statusComboBox, 1, row++
                statusValue = machine.getStatus();
            } 
            else {
                gridPane.add(createLabel("Status"), 0, row);
                statusComboBox = createStatusComboBox("");
                gridPane.add(statusComboBox, 1, row++);
            }

            gridPane.add(createLabel("Datasheet Hyperlink"), 0, row);
            gridPane.add(createFormField("Datasheet Hyperlink", machine != null ? machine.getDatasheetRef() : ""), 1,
                    row++);
            TextField datasheetField = createFormField("Datasheet Hyperlink",
                    machine != null ? machine.getDatasheetRef() : "");
            gridPane.add(datasheetField, 1, row++);
            datasheetValue = machine != null ? machine.getDatasheetRef() : "";

            gridPane.add(createLabel("Purchase Location Hyperlink"), 0, row);
            TextField purchaseLocationField = createFormField("Purchase Location Hyperlink",
                    machine != null ? machine.getPurchaseLocationRef() : "");
            gridPane.add(purchaseLocationField, 1, row++);
            purchaseLocationValue = machine != null ? machine.getPurchaseLocationRef() : "";

            // Set the instance variables when the form fields are filled out
            machineNameField.textProperty().addListener((observable, oldValue, newValue) -> {
                machineFieldValue = newValue.strip();
                if (machineFieldValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in Machine Name");
                    nullMachineField = true;
                } else {
                    nullTextMessage.setText("");
                    nullMachineField = false;
                }
            });
            locationField.textProperty().addListener((observable, oldValue, newValue) -> {
                locationFieldValue = newValue.strip();
                if (locationFieldValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in Location");
                    nullLocationField = true;
                } else {
                    nullTextMessage.setText("");
                    nullLocationField = false;
                }
            });
            statusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                statusValue = newValue.strip();
                if (statusValue.isEmpty()) {
                    nullTextMessage.setText("Please select Status");
                    nullStatusField = true;
                } else {
                    nullTextMessage.setText("");
                    nullStatusField = false;
                }
            });

            datasheetField.textProperty().addListener((observable, oldValue, newValue) -> {
                datasheetValue = newValue.strip();
                if (datasheetValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in Datasheet Hyperlink");
                    nullDatasheetField = true;
                } else {
                    nullTextMessage.setText("");
                    nullDatasheetField = false;
                }
            });
            purchaseLocationField.textProperty().addListener((observable, oldValue, newValue) -> {
                purchaseLocationValue = newValue.strip();
                if (purchaseLocationValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in Purchase Location Hyperlink");
                    nullPurchaseLocationField = true;
                } else {
                    nullTextMessage.setText("");
                    nullPurchaseLocationField = false;
                }

            });
        }

        formLayout.getChildren().addAll(gridPane, nullTextMessage, createFormButtons());

        return formLayout;
    }

    private TextField createFormField(String label, String value) {
        TextField textField = new TextField();
        textField.setPromptText(label);
        textField.setText(value);
        textField.setPrefWidth(200);
        // textField.setStyle("-fx-background-color: #2E5D68; -fx-text-fill: white;");
        return textField;
    }

    private ComboBox<String> createUserComboBox(String selectedItem) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("OPERATOR", "ENGINEER", "ADMIN");

        switch (selectedItem) {
        case "ADMIN":
            comboBox.setValue("ADMIN");
            break;
        case "ENGINEER":
            comboBox.setValue("ENGINEER");
            break;
        default:
            comboBox.setValue("OPERATOR");
            break;
        }
        return comboBox;
    }

    private ComboBox<String> createStatusComboBox(String selectedItem) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("ONLINE", "MAINTENANCE", "OFFLINE");

        switch (selectedItem) {
        case "MAINTENANCE":
            comboBox.setValue("MAINTENANCE");
            break;
        case "OFFLINE":
            comboBox.setValue("OFFLINE");
            break;
        default:
            comboBox.setValue("ONLINE");
            break;
        }
        return comboBox;
    }

    private HBox createFormButtons() {
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        ButtonWrapper saveButton = new ButtonWrapper();
        saveButton.setText("Save");
        saveButton.setCornerRadius(5);
        saveButton.setButtonWidth(100);
        saveButton.setButtonHeight(24);
        saveButton.setFontName(fontName);
        saveButton.setBackgroundColour(btnColour);
        saveButton.setClickcolour(btnClickColour);
        saveButton.setHoverColour(btnHoverColour);
        saveButton.setFontColour(lightTextColour);
        saveButton.setFontSize(12);
        saveButton.removeBorder();

        ButtonWrapper cancelButton = new ButtonWrapper();
        cancelButton.setText("Cancel");
        cancelButton.setCornerRadius(5);
        cancelButton.setButtonWidth(100);
        cancelButton.setButtonHeight(24);
        cancelButton.setFontName(fontName);
        cancelButton.setBackgroundColour(btnColour);
        cancelButton.setClickcolour(btnClickColour);
        cancelButton.setHoverColour(btnHoverColour);
        cancelButton.setFontColour(lightTextColour);
        cancelButton.setFontSize(12);
        cancelButton.removeBorder();

        saveButton.setOnAction(event -> {
            // Handle save action
            if (editingUsers) {
                System.out.println("User Save button pressed");
                // if user does not exist from user list, then add user to list.
                // if we are adding a user with real details (e.g. username, email) then deny it
                // if we are editing a user then the above is okay, but it should change the
                // existing user in array (and save to xml)
                User user = new User(usernameFieldValue, usernameFieldValue, passwordFieldValue, emailFieldValue,
                        roleFieldValue); // placeholder
                allUsersList.add(user);
                createRightSection();
            }
            else if (editingMachines) {
                System.out.println("Machine Save button pressed");

                Machine machine = new Machine(machineFieldValue, locationFieldValue, statusValue, "g", datasheetValue,
                        locationFieldValue);
                System.out.println(statusValue);
                machineList.add(machine);
                createRightSection();
                boolean userExists = false;

                for (User user : allUsersList) {
                    if (user.getEmail().equals(emailFieldValue) || user.getUsername().equals(usernameFieldValue)) {
                        userExists = true;
                        // Update user details if fields are not null
                        if (nameFieldValue != null) {
                            user.setName(nameFieldValue);
                        }
                        if (passwordFieldValue != null) {
                            user.setPassword(passwordFieldValue);
                        }
                        if (roleFieldValue != null) {
                            user.setRole(roleFieldValue);
                        }
                        break;
                    }
                }

                if (!userExists && !nullUserField && !nullUsernameField && !nullRoleField && !nullEmailField && !nullPasswordField) {
                    User user = new User(nameFieldValue, usernameFieldValue, passwordFieldValue, emailFieldValue, roleFieldValue);
                    allUsersList.add(user);
                    createRightSection();
                } else if (userExists) {
                    createRightSection();
                    System.out.println("User updated successfully");
                } else {
                    System.out.println("Please fill in all required fields.");
                }
            } else if (editingMachines) {
                System.out.println("Machine Save button pressed");

                boolean machineExists = false;

                for (Machine machine : machines) {
                    if (machine.getName().equals(machineFieldValue)) {
                        machineExists = true;
                        // Update machine details if fields are not null
                        if (locationFieldValue != null) {
                            machine.setLocation(locationFieldValue);
                        }
                        if (statusValue != null) {
                            machine.setStatus(statusValue);
                        }
                        if (datasheetValue != null) {
                            machine.setDatasheet(datasheetValue);
                        }
                        if (purchaseLocationValue != null) {
                            machine.setPurchaseLocation(purchaseLocationValue);
                        }
                        break;
                    }
                }

                if (!machineExists && !nullMachineField && !nullStatusField && !nullDatasheetField && !nullPurchaseLocationField) {
                    Machine machine = new Machine(machineFieldValue, locationFieldValue, statusValue, "g", datasheetValue, purchaseLocationValue);
                    machines.add(machine);
                    createRightSection();
                    managerPopOutStage.close();
                } else if (machineExists) {
                    createRightSection();
                    System.out.println("Machine updated successfully");
                    managerPopOutStage.close();
                } else {
                    System.out.println("Please fill in all required fields.");
                }
            }
        });

        cancelButton.setOnAction(event -> {
            // Handle cancel action
            System.out.println("Cancel button pressed");
            managerPopOutStage.close();
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

        // Remove the existing right section if it exists
        if (rightSection != null) {
            getChildren().remove(rightSection);
        }

        // Header section
        HBox headerBox = new HBox(10);
        headerBox.setBackground(
                new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5))));
        headerBox.setPadding(new Insets(10));
        headerBox.setAlignment(Pos.CENTER_LEFT);

        headerLabel = new Label("Users");
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");

        ButtonWrapper allUsersButton = createUserTypeButton("All Users");
        ButtonWrapper adminsButton = createUserTypeButton("Admins");
        ButtonWrapper engineersButton = createUserTypeButton("Engineers");
        ButtonWrapper operatorsButton = createUserTypeButton("Operators");
        ButtonWrapper allMachinesButton = createMachineTypeButton("All Machines");
        ButtonWrapper onlineButton = createMachineTypeButton("Online");
        ButtonWrapper offlineButton = createMachineTypeButton("Offline");
        ButtonWrapper maintenanceButton = createMachineTypeButton("Maintenance");

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Users");
        searchBar.setMaxWidth(400);

        // User or Machine list section
        if (editingUsers) {
            updateHeader("Users");
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> filterUsersByName(newValue));
            headerBox.getChildren().addAll(headerLabel, allUsersButton, adminsButton, engineersButton, operatorsButton,
                    searchBar);
            userListVBox = new VBox(10);
            userListVBox.setBackground(
                    new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(10))));
            userListVBox.setPadding(new Insets(5));

//            allUsersList = getAllUsers();
            filteredUserList = new ArrayList<>(allUsersList);

            updateDisplayedUserList(filteredUserList);

            ScrollPane userListScrollPane = new ScrollPane(userListVBox);
            userListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            userListScrollPane.setFitToWidth(true);

            // Set a maximum height for the scroll pane (adjust as needed)
            userListScrollPane.setMaxHeight(540);

            // StackPane for rounded edges
            StackPane stackPane = new StackPane(userListScrollPane);
            stackPane.setBackground(
                    new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(0))));
            stackPane.setMaxWidth(990);

            // Attach event handler to each user button
            for (User user : allUsersList) {
                ButtonWrapper userButton = createUserButton(user);
                userButton.setOnAction(event -> openEditWindow(user));
            }

            // VBox for the entire right section
            rightSection = new VBox(5);
            rightSection.getChildren().addAll(headerBox, stackPane);
            VBox.setVgrow(stackPane, Priority.ALWAYS);

            // Add the new right section to the layout
            setCenter(rightSection);

            return rightSection;
        }
        else if (editingMachines) {
            searchBar.setPromptText("Search Machines");
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> filterMachinesByName(newValue));
            updateHeader("Machines");
            headerBox.getChildren().addAll(headerLabel, allMachinesButton, onlineButton, offlineButton,
                    maintenanceButton, searchBar);

            machineListVBox = new VBox(10);
            machineListVBox.setBackground(
                    new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(10))));
            machineListVBox.setPadding(new Insets(5));

            filteredMachineList = new ArrayList<>(machines);

            updateDisplayedMachineList(filteredMachineList);

            ScrollPane machineListScrollPane = new ScrollPane(machineListVBox);
            machineListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            machineListScrollPane.setFitToWidth(true);

            // Set a maximum height for the scroll pane (adjust as needed)
            machineListScrollPane.setMaxHeight(540);

            // StackPane for rounded edges
            StackPane stackPane = new StackPane(machineListScrollPane);
            stackPane.setBackground(
                    new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(0))));
            stackPane.setMaxWidth(990);

            // Attach event handler to each machine button
            for (Machine machine : machines) {
                ButtonWrapper machineButton = createMachineButton(machine);
                machineButton.setOnAction(event -> openEditWindow(machine));
            }

            // VBox for the entire right section
            rightSection = new VBox(5);
            rightSection.getChildren().addAll(headerBox, stackPane);
            VBox.setVgrow(stackPane, Priority.ALWAYS);

            // Add the new right section to the layout
            setCenter(rightSection);

            return rightSection;
        }

        return null; // Handle other cases as needed
    }

    // Method to open the edit window with user's information filled out
    private void openEditWindow(User user) {
        openWindow("User Manager", "Edit User", user, null);
    }

    // Method to open the edit window with machine's information filled out
    private void openEditWindow(Machine machine) {
        openWindow("Machine Manager", "Edit Machine", null, machine);
    }

    private void filterUsersByName(String name) {
        currentName = name;
        applyUserFilters();
    }

    private void filterUsersByRole(String role) {
        currentRole = role;
        applyUserFilters();
    }

    private void filterMachinesByName(String name) {
        currentName = name;
        applyMachineFilters();
    }

    private void filterMachinesByStatus(String status) {
        currentMachineStatus = status;
        applyMachineFilters();
    }

    private void applyUserFilters() {
        filteredUserList = allUsersList.stream()
                .filter(user -> user.getName().toLowerCase().contains(currentName.toLowerCase()))
                .filter(user -> user.getRole().equalsIgnoreCase(currentRole)
                        || currentRole.equalsIgnoreCase("All Users"))
                .collect(Collectors.toList());
        updateDisplayedUserList(filteredUserList);
    }

    private void applyMachineFilters() {
        filteredMachineList = machines.stream()
                .filter(machine -> machine.getName().toLowerCase().contains(currentName.toLowerCase()))
                .filter(machine -> currentMachineStatus.equalsIgnoreCase("All Machines")
                        || machine.getStatus().equalsIgnoreCase(currentMachineStatus))
                .collect(Collectors.toList());
        updateDisplayedMachineList(filteredMachineList);
        System.out.println(filteredMachineList);
    }

    private void updateDisplayedUserList(List<User> users) {
        userListVBox.getChildren().clear();

        for (User user : users) {
            userListVBox.getChildren().add(createUserButton(user));
        }
    }

    private void updateDisplayedMachineList(List<Machine> machines) {
        machineListVBox.getChildren().clear();

        for (Machine machine : machines) {
            machineListVBox.getChildren().add(createMachineButton(machine));
        }
    }

    private ButtonWrapper createUserButton(User user) {
        ButtonWrapper userButton = new ButtonWrapper();
        userButton.setCornerRadius(5);
        userButton.setButtonWidth(960);
        // Calculate the height dynamically based on the number of users
        double height = Math.max(50, Math.min(70, 500 / allUsersList.size()));
        userButton.setButtonHeight(height);
        userButton.setFont(Font.font("System", 80));

        userButton.setText(
                user.getName() + " - " + user.getUsername() + " - " + user.getEmail() + " (" + user.getRole() + ")");
        userButton.setBackgroundColour(btnColour);
        userButton.setClickcolour(btnClickColour);
        userButton.setHoverColour(btnHoverColour);
        userButton.setFontColour(lightTextColour);
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

    private ButtonWrapper createMachineButton(Machine machine) {
        ButtonWrapper machineButton = new ButtonWrapper();
        machineButton.setCornerRadius(5);
        machineButton.setButtonWidth(960);
        // Calculate the height dynamically based on the number of machines
        double height = Math.max(50, Math.min(70, 500 / machines.size()));
        machineButton.setButtonHeight(height);
        machineButton.setFont(Font.font("System", 80));

        machineButton.setText(machine.getName() + " - " + machine.getLocation() + " - " + machine.getStatus());
        machineButton.setBackgroundColour(btnColour);
        machineButton.setClickcolour(btnClickColour);
        machineButton.setHoverColour(btnHoverColour);
        machineButton.setFontColour(lightTextColour);
        machineButton.setFontSize(18);
        machineButton.removeBorder();

        // Adjust event handling to open the edit window with machine's information
        machineButton.setOnAction(event -> {
            openEditWindow(machine);
            setLastPressedButton(machineButton, lastPressedMachineButton);
            lastPressedMachineButton = machineButton;
        });

        return machineButton;
    }

    private ButtonWrapper createUserTypeButton(String userType) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(100);
        button.setButtonHeight(24);
        button.setFontName(fontName);
        button.setText(userType);
        button.setBackgroundColour(btnColour);
        button.setClickcolour(btnClickColour);
        button.setHoverColour(btnHoverColour);
        button.setFontColour(lightTextColour);
        button.setFontSize(12);
        button.removeBorder();

        String role = userType;

        if (!role.equals("All Users")) {
            role = role.substring(0, role.length() - 1);
        }

        String finalRole = role;
        button.setOnAction(event -> {

            // Remove highlight from the last pressed button before applying to the new one
            if (lastPressedUserButton != null) {
                lastPressedUserButton.setBackgroundColour("#fbb12eff"); // Default colour
            }

            // Set highlight to the current button
            button.setBackgroundColour("#2e78fb"); // Highlight colour

            filterUsersByRole(finalRole);
            lastPressedUserButton = button;
        });

        // Highlight the "All Users" button by default
        if (userType.equals("All Users")) {
            button.setBackgroundColour("#2e78fb"); // Highlight colour
            lastPressedUserButton = button;
        }

        return button;
    }

    private ButtonWrapper createMachineTypeButton(String machineStatus) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(100);
        button.setButtonHeight(24);
        button.setFontName(fontName);
        button.setText(machineStatus);
        button.setBackgroundColour(btnColour);
        button.setClickcolour(btnClickColour);
        button.setHoverColour(btnHoverColour);
        button.setFontColour(lightTextColour);
        button.setFontSize(12);
        button.removeBorder();

        button.setOnAction(event -> {

            // Remove highlight from the last pressed button before applying to the new one
            if (lastPressedMachineButton != null) {
                lastPressedMachineButton.setBackgroundColour("#fbb12eff"); // Default colour
            }

            // Set highlight to the current button
            button.setBackgroundColour("#2e78fb"); // Highlight colour

            filterMachinesByStatus(machineStatus);
            lastPressedMachineButton = button;
        });

        // Highlight the "All Machines" button by default
        if (machineStatus.equals("All Machines")) {
            button.setBackgroundColour("#2e78fb"); // Highlight colour
            lastPressedMachineButton = button;
        }

        return button;
    }

    private void updateHeader(String text) {
        headerLabel.setText(text);
    }

}
