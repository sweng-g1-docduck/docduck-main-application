package com.docduck.application.gui.pages;

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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AdminPage extends Page {

    private VBox rightSection;
    private VBox userListVBox;
    private VBox machineListVBox;
    private List<User> allUsersList;
    private List<User> filteredUserList;
    private List<Machine> machines;
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

    public AdminPage() {
        super();
        user = new User("Bob", "bob@york.ac.uk", "ADMIN","password");
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
            ButtonWrapper button = createManagerButton(buttonText, header, ButtonType.MANAGER);

            managerBox.getChildren().add(button);

            // Disable the buttons for Components Manager and Parts Manager
            if ("Components Manager".equals(header) || "Parts Manager".equals(header)) {
                button.setDisable(true);
                button.setStyle("-fx-opacity: 0.5;");
            }
        }

        return managerBox;
    }


    private Label createManagerHeader(String headerText) {
        Label managerHeader = new Label(headerText);
        managerHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        return managerHeader;
    }

    private ButtonWrapper createManagerButton(String text, String managerType, ButtonType type) {
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
            switch (text) {
                case "Edit User":
                    editingUsers = true;
                    editingMachines = false;
                    createRightSection(); // Clear and refresh right section
                    break;
                case "Edit Machine":
                    editingMachines = true;
                    editingUsers = false;
                    createRightSection(); // Clear and refresh right section
                    break;
                case "Remove User":
                    editingUsers = true;
                    editingMachines = false;
                    createRightSection(); // Clear and refresh right section
                    break;
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

            if (type == ButtonType.MANAGER) {
                setLastPressedButton(button, lastPressedUserTypeButton);
                lastPressedUserTypeButton = button;
            }
        });

        return button;
    }




    private void openWindow(String managerType, String actionType, User user, Machine machine) {
        Stage newStage = new Stage();
        VBox formLayout = createManagerForm(managerType, actionType, user, machine);
        formLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(50))));

        Scene scene = new Scene(formLayout, 700, 600);
        scene.setFill(Color.web("#1f5398"));
        newStage.setTitle(managerType + " - " + actionType);
        newStage.setScene(scene);
        newStage.show();
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
        } else if (editingMachines) {
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

        int row = 0;
        switch (managerType) {
            case "User Manager":
                gridPane.add(createLabel("Username"), 0, row);
                gridPane.add(createFormField("Username", user != null ? user.getName() : ""), 1, row++);

                gridPane.add(createLabel("Email"), 0, row);
                gridPane.add(createFormField("Email", user != null ? user.getEmail() : ""), 1, row++);

                if (user != null) {
                    gridPane.add(createLabel("Role"), 0, row);
                    gridPane.add(createUserComboBox(user.getRole()), 1, row++);
                } else {
                    gridPane.add(createLabel("Role"), 0, row);
                    gridPane.add(createUserComboBox(""), 1, row++);
                }

                PasswordField passwordField = new PasswordField();
                passwordField.setPromptText("Enter password");
                gridPane.add(createLabel("Password"), 0, row);
                gridPane.add(passwordField, 1, row++);
                break;
            default:
                break;
        }

        formLayout.getChildren().addAll(gridPane, createFormButtons());

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

        int row = 0;
        switch (managerType) {
            case "Machine Manager":
                gridPane.add(createLabel("Machine Name"), 0, row);
                gridPane.add(createFormField("Machine Name", machine != null ? machine.getName() : ""), 1, row++);

                gridPane.add(createLabel("Location"), 0, row);
                gridPane.add(createFormField("Location", machine != null ? machine.getRoom() : ""), 1, row++);

                gridPane.add(createLabel("Status"), 0, row);
                gridPane.add(createStatusComboBox(machine != null ? machine.getStatus() : ""), 1, row++);

                gridPane.add(createLabel("Id"), 0, row);
                gridPane.add(createFormField("Id", machine != null ? machine.getSerialNumber() : ""), 1, row++);

                gridPane.add(createLabel("Datasheet Hyperlink"), 0, row);
                gridPane.add(createFormField("Datasheet Hyperlink", machine != null ? machine.getDatasheet() : ""), 1, row++);
                break;
            default:
                break;
        }

        formLayout.getChildren().addAll(gridPane, createFormButtons());

        return formLayout;
    }


    private TextField createFormField(String label, String value) {
        TextField textField = new TextField();
        textField.setPromptText(label);
        textField.setText(value);
        textField.setPrefWidth(200);
        //textField.setStyle("-fx-background-color: #2E5D68; -fx-text-fill: white;");
        return textField;
    }


    private ComboBox<String> createUserComboBox(String selectedItem) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Operator", "Engineer", "Admin");
        
        switch(selectedItem) {
        case "Admin":
            comboBox.setValue("Admin");
            break;
        case "Engineer":
            comboBox.setValue("Engineer");
            break;
        default:
            comboBox.setValue("Operator");
            break;
        }
        return comboBox;
    }
    
    private ComboBox<String> createStatusComboBox(String selectedItem) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Online", "Maintenance", "Offline");
        
        switch(selectedItem) {
        case "MAINTENANCE":
            comboBox.setValue("Maintenance");
            break;
        case "OFFLINE":
            comboBox.setValue("Offline");
            break;
        default:
            comboBox.setValue("Online");
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
        saveButton.setFontName("Arial");
        saveButton.setBackgroundColour("#fbb12eff");
        saveButton.setClickcolour(Color.WHITE);
        saveButton.setHoverColour("#ff8c00ff");
        saveButton.setFontColour(Color.WHITE);
        saveButton.setFontSize(12);
        saveButton.removeBorder();

        ButtonWrapper cancelButton = new ButtonWrapper();
        cancelButton.setText("Cancel");
        cancelButton.setCornerRadius(5);
        cancelButton.setButtonWidth(100);
        cancelButton.setButtonHeight(24);
        cancelButton.setFontName("Arial");
        cancelButton.setBackgroundColour("#fbb12eff");
        cancelButton.setClickcolour(Color.WHITE);
        cancelButton.setHoverColour("#ff8c00ff");
        cancelButton.setFontColour(Color.WHITE);
        cancelButton.setFontSize(12);
        cancelButton.removeBorder();

        saveButton.setOnAction(event -> {
            // Handle save action
            System.out.println("Save button pressed");
        });

        cancelButton.setOnAction(event -> {
            // Handle cancel action
            System.out.println("Cancel button pressed");
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
        headerBox.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5))));
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
            headerBox.getChildren().addAll(headerLabel, allUsersButton, adminsButton, engineersButton, operatorsButton, searchBar);
            userListVBox = new VBox(10);
            userListVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(10))));
            userListVBox.setPadding(new Insets(5));


            allUsersList = generateUserList();
            filteredUserList = new ArrayList<>(allUsersList);

            updateDisplayedUserList(filteredUserList);

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
            rightSection = new VBox(5);
            rightSection.getChildren().addAll(headerBox, stackPane);
            VBox.setVgrow(stackPane, Priority.ALWAYS);

            // Add the new right section to the layout
            setCenter(rightSection);

            return rightSection;
        } else if (editingMachines) {
            searchBar.setPromptText("Search Machines");
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> filterMachinesByName(newValue));
            updateHeader("Machines");
            //headerBox.getChildren().removeAll(allUsersButton, adminsButton, engineersButton, operatorsButton);
            headerBox.getChildren().addAll(headerLabel,allMachinesButton,onlineButton,offlineButton,maintenanceButton,searchBar);

            machineListVBox = new VBox(10);
            machineListVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(10))));
            machineListVBox.setPadding(new Insets(5));

            machines = generateMachineList();
            filteredMachineList = new ArrayList<>(machines);

            updateDisplayedMachineList(filteredMachineList);

            ScrollPane machineListScrollPane = new ScrollPane(machineListVBox);
            machineListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            machineListScrollPane.setFitToWidth(true);

            // Set a maximum height for the scroll pane (adjust as needed)
            machineListScrollPane.setMaxHeight(540);

            // StackPane for rounded edges
            StackPane stackPane = new StackPane(machineListScrollPane);
            stackPane.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(0))));
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
                .filter(user -> user.getRole().equalsIgnoreCase(currentRole) || currentRole.equalsIgnoreCase("All Users"))
                .collect(Collectors.toList());
        updateDisplayedUserList(filteredUserList);
    }

    private void applyMachineFilters() {
        filteredMachineList = machines.stream()
                .filter(machine -> machine.getName().toLowerCase().contains(currentName.toLowerCase()))
                .filter(machine -> currentMachineStatus.equalsIgnoreCase("All Machines") || machine.getStatus().equalsIgnoreCase(currentMachineStatus))
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

    private ButtonWrapper createMachineButton(Machine machine) {
        ButtonWrapper machineButton = new ButtonWrapper();
        machineButton.setCornerRadius(5);
        machineButton.setButtonWidth(900);
        // Calculate the height dynamically based on the number of machines
        double height = Math.max(50, Math.min(70, 500 / machines.size()));
        machineButton.setButtonHeight(height);
        machineButton.setFont(Font.font("System", 80));
        machineButton.setText(machine.getName() + " - " + machine.getRoom() + " - " + machine.getStatus());
        machineButton.setBackgroundColour("#fbb12eff");
        machineButton.setClickcolour(Color.WHITE);
        machineButton.setHoverColour("#ff8c00ff");
        machineButton.setFontColour(Color.WHITE);
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
        button.setFontName("Arial");
        button.setText(machineStatus);
        button.setBackgroundColour("#fbb12eff");
        button.setClickcolour(Color.WHITE);
        button.setHoverColour("#ff8c00ff");
        button.setFontColour(Color.WHITE);
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


    private List<User> generateUserList() {
        userList.add(new User("John Doe", "john@docduck.com", "Admin", "password123"));
        userList.add(new User("Jane Smith", "jane@docduck.com", "Operator", "password123"));
        userList.add(new User("Bob Johnson", "bob@docduck.com", "Engineer", "password123"));
        userList.add(new User("Emily Brown", "emily@docduck.com", "Admin", "password123"));
        userList.add(new User("Michael Clark", "michael@docduck.com", "Operator", "password123"));
        userList.add(new User("Alice Green", "alice@docduck.com", "Engineer", "password123"));
        userList.add(new User("Kelvin Zacharias", "KZ@docduck.com", "Engineer", "password123"));

        for (int i = 0; i < 20; i++) {
            String[] names = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack"};
            String[] roles = {"Admin", "Operator", "Engineer"};
            String name = names[(int) (Math.random() * names.length)];
            String role = roles[(int) (Math.random() * roles.length)];
            userList.add(new User(name + " " + (i + 1), name.toLowerCase() + (i + 1) + "@docduck.com", role, "password123"));
        }

        return userList;
    }


    private List<Machine> generateMachineList() {
        machineList.add(new Machine("Machine One", "Room 1", "ONLINE", "1", "1"));
        machineList.add(new Machine("Machine Two", "Room 2", "ONLINE", "2", "2"));
        machineList.add(new Machine("Machine Three", "Room 1", "MAINTENANCE", "3", "2"));
        machineList.add(new Machine("Machine Four", "Room 2", "ONLINE", "4", "2"));
        machineList.add(new Machine("Machine Five", "Room 1", "OFFLINE", "5", "2"));
        machineList.add(new Machine("Machine Six", "Room 2", "ONLINE", "6", "2"));
        machineList.add(new Machine("Machine Seven", "Room 1", "ONLINE", "7", "2"));
        machineList.add(new Machine("Machine Eight", "Room 2", "ONLINE", "8", "2"));
        machineList.add(new Machine("Machine Nine", "Room 1", "ONLINE", "9", "2"));

        for (int i = 0; i < 20; i++) {
            String[] names = {"Machine", "Generator", "Compressor", "Pump", "Boiler", "Engine"};
            String[] statuses = {"ONLINE", "OFFLINE", "MAINTENANCE"};
            String name = names[(int) (Math.random() * names.length)];
            String status = statuses[(int) (Math.random() * statuses.length)];
            machineList.add(new Machine(name + " " + (i + 1), "Room " + ((i % 3) + 1), status, String.valueOf(i + 10), "2"));
        }

        return machineList;
    }

    private void updateHeader(String text) {
        headerLabel.setText(text);
    }

    private enum ButtonType {
        MANAGER
    }
}
