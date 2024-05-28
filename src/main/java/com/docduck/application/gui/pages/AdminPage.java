package com.docduck.application.gui.pages;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.docduck.application.data.Machine;
import com.docduck.application.data.User;
import com.docduck.buttonlibrary.ButtonWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private String serialNumberValue;
    private boolean nullUserField = true;
    private boolean nullUsernameField = true;
    private boolean nullEmailField = true;
    private boolean nullRoleField = false;
    private boolean nullPasswordField= true;
    private boolean nullMachineField= true;
    private boolean nullLocationField= true;
    private boolean nullStatusField= false;
    private boolean nullDatasheetField= true;
    private boolean nullPurchaseLocationField= true;
    private boolean nullSerialNumberField= true;
    private Image tempMachineImage;
    private ImageView machineImageView;




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


    /**

     {@inheritDoc}
     This method builds the page layout by creating and setting up the left and right sections within a VBox layout.
     The left section is constructed using the method createLeftSection(), while the right section is constructed
     using the method createRightSection(). These sections are then assigned to the left and center of the layout
     respectively using the setLeft() and setCenter() methods inherited from the superclass. Finally, the superclass
     implementation of buildPage() is invoked to complete the page building process.
     * @author Lw2380
     */
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
     *
     * @author Lw2380
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
     *
     * @author Lw2380
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
     *
     * @author Lw2380
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
     * @author Lw2380
     */
    private ButtonWrapper createManagerButton(String text, String managerType) {
        ButtonWrapper button = new ButtonWrapper();
        button.setCornerRadius(5);
        button.setButtonWidth(250);
        button.setButtonHeight(18);
        button.setFontName(fontName);
        button.setText(text);
        button.setBackgroundColour(btnColour);
        button.setClickcolour(btnClickColour);
        button.setHoverColour(btnHoverColour);
        button.setFontColour(lightTextColour);
        button.setFontSize(10);
        button.removeBorder();

        // Disable the buttons for deletion till implemented
        if ("Remove User".equals(text) || "Remove Machine".equals(text)) {
            button.setDisable(true);
            button.setStyle("-fx-opacity: 0.5;");
        }

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
     * @author Lw2380
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


    /**

     Creates a VBox layout containing a manager form based on the provided manager type and action type,
     along with optional user and machine objects.

     @param managerType The type of manager for which the form is being created.
     @param actionType The type of action (e.g., "Edit", "Create") being performed on the form.
     @param user The user object (optional) to be included in the form.
     @param machine The machine object (optional) to be included in the form.

     @return The VBox layout containing the manager form.
      * @author Lw2380
     */
    private VBox createManagerForm(String managerType, String actionType, User user, Machine machine) {
        VBox formLayout = new VBox(20);
        formLayout.setPadding(new Insets(20));
        formLayout.setAlignment(Pos.CENTER);

        Label headerLabel = new Label(managerType + " Form - " + actionType);
        headerLabel.setFont(Font.font(fontName, FontWeight.BOLD, 20));

        formLayout.getChildren().add(headerLabel);

        if (editingUsers) {
            formLayout.getChildren().add(createUserManagerForm(managerType, actionType, user));
        }
        else if (editingMachines) {
            formLayout.getChildren().add(createMachineManagerForm(managerType, actionType, machine));
        }

        return formLayout;
    }

    /**

     Creates a VBox layout for the user manager form, incorporating the provided manager type, action type,
     and user details.
     @param managerType The type of manager for which the form is being created.
     @param actionType The type of action (e.g., "Edit", "Create") being performed on the form.
     @param user The user object for which the form is being created.

     @return The VBox layout containing the user manager form.
      * @author Lw2380
     */
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
            nameFieldValue = user.getName();
            if (nameFieldValue.isEmpty()) {
                nullTextMessage.setText("Please Fill in Fields");
                nullUserField = true;
            }
            else {nullUserField = false;}

            gridPane.add(createLabel("Username"), 0, row);
            TextField usernameField = createFormField("Username", user != null ? user.getUsername() : "");
            gridPane.add(usernameField, 1, row++);
            usernameFieldValue = user.getUsername();
            if (usernameFieldValue.isEmpty()) {
                nullTextMessage.setText("Please fill in Username");
                nullUsernameField = true;
            }
            else {nullUsernameField = false;}

            gridPane.add(createLabel("Email"), 0, row);
            TextField emailField = createFormField("Email", user != null ? user.getEmail() : "");
            gridPane.add(emailField, 1, row++);
            emailFieldValue = user.getEmail();
            if (emailFieldValue.isEmpty()) {
                nullTextMessage.setText("Please fill in Email");
                nullEmailField = true;
            }
            else {nullEmailField = false;}

            ComboBox<String> roleComboBox;
            if (user != null) {
                gridPane.add(createLabel("Role"), 0, row);
                roleComboBox = createUserComboBox(user.getRole());
                gridPane.add(roleComboBox, 1, row++);
                roleFieldValue = user.getRole();
                if (roleFieldValue.isEmpty()) {
                    nullTextMessage.setText("Please select Role");
                    roleFieldValue = "OPERATOR";
                }
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
                if (nameFieldValue.isEmpty()) {
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

    /**

     Creates a Label with the specified text and applies a bold Arial font with size 14.
     @param text The text to be displayed on the label.
     @return The created Label with the specified text and font.
      * @author Lw2380
     */
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font(fontName, FontWeight.BOLD, 14));
        return label;
    }

    /**
     * Creates a VBox layout for the machine manager form, incorporating the provided manager type, action type,
     * and machine details.
     *
     * @param managerType The type of manager for which the form is being created.
     * @param actionType The type of action (e.g., "Edit", "Create") being performed on the form.
     * @param machine The machine object for which the form is being created.
     * @return The VBox layout containing the machine manager form.
     *
     * @author Lw2380
     */
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

        ImageView machineImageView = new ImageView();
        machineImageView.setFitWidth(100);
        machineImageView.setPreserveRatio(true);
        if (tempMachineImage != null) {
            machineImageView.setImage(tempMachineImage);
        }

        formLayout.getChildren().add(machineImageView);

        if (managerType.equals("Machine Manager")) {
            gridPane.add(createLabel("Machine Name"), 0, row);
            TextField machineNameField = createFormField("Machine Name", machine != null ? machine.getName() : "");
            gridPane.add(machineNameField, 1, row++);
            machineFieldValue = machine != null ? machine.getName() : "";

            if (machineFieldValue.isEmpty()) {
                nullTextMessage.setText("Please fill in Fields");
                nullMachineField = true;
            }
            else{nullMachineField = false;}

            gridPane.add(createLabel("Location"), 0, row);
            TextField locationField = createFormField("Location", machine != null ? machine.getLocation() : "");
            gridPane.add(locationField, 1, row++);
            locationFieldValue = machine != null ? machine.getLocation() : "";

            if (locationFieldValue.isEmpty()) {
                nullTextMessage.setText("Please fill in Fields");
                nullLocationField = true;
                System.out.println(nullMachineField);
            }
            else{nullLocationField = false;}

            ComboBox<String> statusComboBox;
            if (machine != null) {
                gridPane.add(createLabel("Status"), 0, row);
                statusComboBox = createStatusComboBox(machine.getStatus());
                gridPane.add(statusComboBox, 1, row++);
                statusValue = machine.getStatus();

                if (statusValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in Fields");
                    nullStatusField = true;
                }
                else{nullStatusField = false;}
            } 
            else {
                gridPane.add(createLabel("Status"), 0, row);
                statusComboBox = createStatusComboBox("");
                gridPane.add(statusComboBox, 1, row++);
            }
            gridPane.add(createLabel("Datasheet Hyperlink"), 0, row);
            TextField datasheetField = createFormField("Datasheet Hyperlink", machine != null ? machine.getDatasheetRef() : "");
            gridPane.add(datasheetField, 1, row++);
            datasheetValue = machine != null ? machine.getDatasheetRef() : "";

            if (datasheetValue.isEmpty()) {
                nullTextMessage.setText("Please fill in Fields");
                nullDatasheetField = true;
            }
            else{nullDatasheetField = false;}

            gridPane.add(createLabel("Serial Number"), 0, row);
            TextField serialNumberField = createFormField("Serial Number", machine != null ? machine.getSerialNumber() : "");
            gridPane.add(serialNumberField, 1, row++);
            serialNumberValue = machine != null ? machine.getSerialNumber() : "";

            if (serialNumberValue.isEmpty()) {
                nullTextMessage.setText("Please fill in Fields");
                nullSerialNumberField = true;
            }
            else{nullSerialNumberField = false;}

            gridPane.add(createLabel("Purchase Location Hyperlink"), 0, row);
            TextField purchaseLocationField = createFormField("Purchase Location Hyperlink",
                    machine != null ? machine.getPurchaseLocationRef() : "");
            gridPane.add(purchaseLocationField, 1, row++);
            purchaseLocationValue = machine != null ? machine.getPurchaseLocationRef() : "";
            if (purchaseLocationValue.isEmpty()) {
                nullTextMessage.setText("Please fill in Fields");
                nullPurchaseLocationField = true;
            }
            else{nullPurchaseLocationField = false;}

            // Set the instance variables when the form fields are filled out
            machineNameField.textProperty().addListener((observable, oldValue, newValue) -> {
                machineFieldValue = newValue.strip();
                if (machineFieldValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in Machine Name");
                    nullMachineField = true;
                    System.out.println(nullMachineField);
                } else {
                    nullTextMessage.setText("");
                    nullMachineField = false;
                    System.out.println(nullMachineField);
                }
            });

            locationField.textProperty().addListener((observable, oldValue, newValue) -> {
                locationFieldValue = newValue.strip();
                if (locationFieldValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in Machine Name");
                    nullLocationField= true;
                } else {
                    nullTextMessage.setText("");
                    nullLocationField = false;
                }
            });

            statusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                statusValue = newValue.strip();
                if (statusValue.isEmpty()) {
                    nullTextMessage.setText("Please select Status");
                    //nullStatusField = true;

                } else {
                    nullTextMessage.setText("");
                    nullStatusField = false;
                }
            });

            serialNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
                serialNumberValue = newValue.strip();
                if (serialNumberValue.isEmpty()) {
                    nullTextMessage.setText("Please fill in serial Number Value");
                    nullSerialNumberField = true;
                } else {
                    nullTextMessage.setText("");
                    nullSerialNumberField = false;
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

    /**
     Creates a TextField form field with the specified label and default value.
     @param label The prompt text for the TextField.
     @param value The default value to be displayed in the TextField.
     @return The created TextField form field.
      * @author Lw2380
     */
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

    /**
     Creates a ComboBox for selecting user roles, with the specified selected item.
     @param selectedItem The role item to be selected by default in the ComboBox.
     @return The created ComboBox for user roles.
      * @author Lw2380
     */
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
    /**
     * Creates an HBox containing buttons for the form, such as adding/editing a machines and users, saving, and canceling.
     *
     * @return The HBox layout containing the form buttons.
     *
     * @author Lw2380
     */
    private HBox createFormButtons() {
    HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

    // Add Machine Picture button
    ButtonWrapper addMachinePictureButton = new ButtonWrapper();
        addMachinePictureButton.setText("Add Machine Picture");
        addMachinePictureButton.setCornerRadius(5);
        addMachinePictureButton.setButtonWidth(150);
        addMachinePictureButton.setButtonHeight(24);
        addMachinePictureButton.setFontName(fontName);
        addMachinePictureButton.setBackgroundColour(btnColour);
        addMachinePictureButton.setClickcolour(btnClickColour);
        addMachinePictureButton.setHoverColour(btnHoverColour);
        addMachinePictureButton.setFontColour(lightTextColour);
        addMachinePictureButton.setFontSize(12);
        addMachinePictureButton.removeBorder();

        addMachinePictureButton.setOnAction(event -> {
        // Handle add machine picture action
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(managerPopOutStage);
        if (file != null) {
            System.out.println(file.getPath());
        }
    });

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

            boolean userExists = false;

            for (User user : allUsersList) {
                if (user.getEmail().equals(emailFieldValue) || user.getUsername().equals(usernameFieldValue)) {
                    userExists = true;
                    // Update user details if fields are not null
                    if (nameFieldValue != null) {
                        user.setName(nameFieldValue);
                    }
                    if (usernameFieldValue != null) {
                        user.setUsername(usernameFieldValue);
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
                managerPopOutStage.close();
            }

            else if (userExists) {
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
                    if (serialNumberValue != null) {
                        machine.setSerialNumber(serialNumberValue);
                    }
                    if (datasheetValue != null) {
                        machine.setDatasheet(datasheetValue);
                    }
                    if (purchaseLocationValue != null) {
                        machine.setPurchaseLocation(purchaseLocationValue);
                    }
                }
            }

            if (!machineExists && !nullMachineField && !nullStatusField && !nullDatasheetField && !nullPurchaseLocationField) {
                Machine machine = new Machine(machineFieldValue, locationFieldValue, statusValue, serialNumberValue, datasheetValue, purchaseLocationValue);
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

        buttonBox.getChildren().addAll(addMachinePictureButton, saveButton, cancelButton);
        return buttonBox;
}

    /**
     * Updates the machine image if a temporary image is available and the machine image view exists.
     *
     * @author Lw2380
     */
    private void updateMachineImage() {
        if (tempMachineImage != null && machineImageView != null) {
            machineImageView.setImage(tempMachineImage);
        }
    }

    /**
     * Sets the border colour of the current button and removes the border colour of the last pressed button.
     *
     * @param currentButton The button currently pressed.
     * @param lastPressedButton The button previously pressed.
     *
     * @author Lw2380
     */
    private void setLastPressedButton(ButtonWrapper currentButton, ButtonWrapper lastPressedButton) {

        if (lastPressedButton != null) {
            lastPressedButton.removeBorder();
        }
        currentButton.setBorderColour(Color.BLACK);
    }

    /**
     * Creates the right section of the layout, including the header and user or machine list.
     *
     * @return The VBox layout containing the right section.
     *
     * @author Lw2380
     */
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

    /**
     * Opens the edit window for the specified user.
     *
     * @param user The user to be edited.
     *
     * @author Lw2380
     */
    private void openEditWindow(User user) {
        openWindow("User Manager", "Edit User", user, null);
    }

    /**
     * Opens the edit window with machine's information filled out.
     *
     * @param machine The machine to be edited.
     *
     * @author Lw2380
     */
    private void openEditWindow(Machine machine) {
        openWindow("Machine Manager", "Edit Machine", null, machine);
    }

    /**
     * Filters users by name based on the provided name string.
     *
     * @param name The name string used for filtering users.
     *
     * @author Lw2380
     */
    private void filterUsersByName(String name) {
        currentName = name;
        applyUserFilters();
    }

    /**
     * Filters users by role based on the provided role string.
     *
     * @param role The role string used for filtering users.
     *
     * @author Lw2380
     */
    private void filterUsersByRole(String role) {
        currentRole = role;
        applyUserFilters();
    }

    /**
     * Filters machines by name based on the provided name string.
     *
     * @param name The name string used for filtering machines.
     *
     * @author Lw2380
     */
    private void filterMachinesByName(String name) {
        currentName = name;
        applyMachineFilters();
    }

    /**
     * Filters machines by status based on the provided status string.
     *
     * @param status The status string used for filtering machines.
     *
     * @author Lw2380
     */
    private void filterMachinesByStatus(String status) {
        currentMachineStatus = status;
        applyMachineFilters();
    }

    /**
     * Applies filters to the list of users based on the current name and role criteria.
     *
     * @author Lw2380
     */
    private void applyUserFilters() {
        filteredUserList = allUsersList.stream()
                .filter(user -> user.getName().toLowerCase().contains(currentName.toLowerCase()))
                .filter(user -> user.getRole().equalsIgnoreCase(currentRole)
                        || currentRole.equalsIgnoreCase("All Users"))
                .collect(Collectors.toList());
        updateDisplayedUserList(filteredUserList);
    }

    /**
     * Applies filters to the list of machines based on the current name and status criteria.

     *  @author Lw2380
     */
    private void applyMachineFilters() {
        filteredMachineList = machines.stream()
                .filter(machine -> machine.getName().toLowerCase().contains(currentName.toLowerCase()))
                .filter(machine -> currentMachineStatus.equalsIgnoreCase("All Machines")
                        || machine.getStatus().equalsIgnoreCase(currentMachineStatus))
                .collect(Collectors.toList());
        updateDisplayedMachineList(filteredMachineList);
        System.out.println(filteredMachineList);
    }

    /**
     * Updates the displayed user list in the user list VBox.
     *
     * @param users The list of users to be displayed.
     *
     * @author Lw2380
     */
    private void updateDisplayedUserList(List<User> users) {
        userListVBox.getChildren().clear();

        for (User user : users) {
            userListVBox.getChildren().add(createUserButton(user));
        }
    }

    /**
     * Updates the displayed machine list in the machine list VBox.
     *
     * @param machines The list of machines to be displayed.
     *
     * @author Lw2380
     */
    private void updateDisplayedMachineList(List<Machine> machines) {
        machineListVBox.getChildren().clear();

        for (Machine machine : machines) {
            machineListVBox.getChildren().add(createMachineButton(machine));
        }
    }

    /**
     * Creates a button representing a user with the provided user's information.
     *
     * @param user The user for which the button is created.
     * @return The ButtonWrapper representing the user.
     *
     * * @author Lw2380
     */
    private ButtonWrapper createUserButton(User user) {
        ButtonWrapper userButton = new ButtonWrapper();
        userButton.setCornerRadius(5);
        userButton.setButtonWidth(960);
        // Calculate the height dynamically based on the number of users
        double height = Math.max(50, Math.min(70, 500 / allUsersList.size()));
        userButton.setButtonHeight(height);
        userButton.setFont(Font.font("System", 80));
        userButton.setBackgroundColour(btnColour);
        userButton.setClickcolour(btnClickColour);
        userButton.setHoverColour(btnHoverColour);
        userButton.setFontColour(lightTextColour);
        userButton.setFontSize(18);
        userButton.removeBorder();

        // Create a VBox for the user information
        VBox userInfo = new VBox();

        // Create a label for the user's name in bold and big text
        Label userName = new Label(user.getName());
        userName.setFont(Font.font("System", FontWeight.BOLD, 24));
        userInfo.getChildren().add(userName);

        // Create an HBox for the rest of the user information
        HBox userDetails = new HBox();

        // Add the user's username, email, and role to the HBox
        Label userUsername = new Label("Username: " + user.getUsername());
        Label userEmail = new Label("Email: " + user.getEmail());
        Label userRole = new Label("Role: " + user.getRole());

        userDetails.getChildren().addAll(userUsername, userEmail, userRole);
        userDetails.setSpacing(10); // Set spacing between the details

        // Add the HBox to the VBox
        userInfo.getChildren().add(userDetails);

        // Set the VBox as the text for the button
        userButton.setGraphic(userInfo);

        // Adjust event handling to open the edit window with user's information
        userButton.setOnAction(event -> {
            openEditWindow(user);
            setLastPressedButton(userButton, lastPressedUserButton);
            lastPressedUserButton = userButton;
        });

        return userButton;
    }

    /**
     * Creates a button representing a machine with the provided machine's information.
     *
     * @param machine The machine for which the button is created.
     * @return The ButtonWrapper representing the machine.
     *
     * * @author Lw2380
     */
    private ButtonWrapper createMachineButton(Machine machine) {
        ButtonWrapper machineButton = new ButtonWrapper();
        machineButton.setCornerRadius(5);
        machineButton.setButtonWidth(960);
        double height = Math.max(50, Math.min(70, 500 / machines.size()));
        machineButton.setButtonHeight(height);
        machineButton.setFont(Font.font("System", 80));
        machineButton.setBackgroundColour(btnColour);
        machineButton.setClickcolour(btnClickColour);
        machineButton.setHoverColour(btnHoverColour);
        machineButton.setFontColour(lightTextColour);
        machineButton.setFontSize(18);
        machineButton.removeBorder();

        VBox machineInfo = new VBox();

        Label machineName = new Label(machine.getName());
        machineName.setFont(Font.font("System", FontWeight.BOLD, 24));
        machineInfo.getChildren().add(machineName);

        HBox machineDetails = new HBox();

        Label machineLocation = new Label("Location: " + machine.getLocation());
        Label machineStatus = new Label("Status: " + machine.getStatus());

        machineDetails.getChildren().addAll(machineLocation, machineStatus);
        machineDetails.setSpacing(10);

        machineInfo.getChildren().add(machineDetails);

        machineButton.setGraphic(machineInfo);

        machineButton.setOnAction(event -> {
            openEditWindow(machine);
            setLastPressedButton(machineButton, lastPressedMachineButton);
            lastPressedMachineButton = machineButton;
        });

        return machineButton;
    }
    /**
     * Creates a button representing a user type with the provided user type.
     *
     * @param userType The type of user for which the button is created.
     * @return The ButtonWrapper representing the user type.
     *
     * * @author Lw2380
     */
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
                lastPressedUserButton.setBackgroundColour(btnColour); // Default colour
            }

            // Set highlight to the current button
            button.setBackgroundColour(btnClickColour); // Highlight colour

            filterUsersByRole(finalRole);
            lastPressedUserButton = button;
        });

        // Highlight the "All Users" button by default
        if (userType.equals("All Users")) {
            button.setBackgroundColour(btnClickColour); // Highlight colour
            lastPressedUserButton = button;
        }

        return button;
    }
    /**
     * Creates a button representing a machine status with the provided status.
     *
     * @param machineStatus The status of the machine for which the button is created.
     * @return The ButtonWrapper representing the machine status.
     *
     * * @author Lw2380
     */
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
                lastPressedMachineButton.setBackgroundColour(btnColour); // Default colour
            }

            // Set highlight to the current button
            button.setBackgroundColour(btnClickColour); // Highlight colour

            filterMachinesByStatus(machineStatus);
            lastPressedMachineButton = button;
        });

        // Highlight the "All Machines" button by default
        if (machineStatus.equals("All Machines")) {
            button.setBackgroundColour(btnClickColour); // Highlight colour
            lastPressedMachineButton = button;
        }

        return button;
    }
    /**
     * Updates the header label with the provided text.
     *
     * @param text The text to be set as the header label.
     * @author Lw2380
     */
    private void updateHeader(String text) {
        headerLabel.setText(text);
    }

}
