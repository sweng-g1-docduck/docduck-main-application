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
import javafx.scene.control.TextField;
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


        super.buildPage();
    }

    private VBox createLeftSection() {
        VBox leftSection = new VBox(25);
        leftSection.setBackground(new Background(new BackgroundFill(Color.web("#1f5398"), new CornerRadii(5), new Insets(0))));
        //leftSection.setPrefWidth(200); // Increased width

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
        managerHeader.setStyle("-fx-font-size: 15px; -fx-text-fill: #333333;");
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
        Stage addStage = new Stage();
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.setTitle("Add " + managerType);
        addStage.setMinWidth(500);
        addStage.setMinHeight(700);

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
        // Create VBox for the header and search bar
        VBox headerBox = new VBox(10);
        headerBox.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), new CornerRadii(5), new Insets(5)))); // Set background color
        headerBox.setPadding(new Insets(10));
        headerBox.setAlignment(Pos.TOP_LEFT);

        // Create header label
        Label headerLabel = new Label("Users");
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;"); // Increased font size

        // Create search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Users");
        searchBar.setMaxWidth(200); // Set maximum width for the search bar

        // Add header label and search bar to headerBox
        headerBox.getChildren().addAll(headerLabel, searchBar);

        // Create VBox for the user list
        VBox userListVBox = new VBox(10);
        userListVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(5)))); // Change background color
        userListVBox.setPadding(new Insets(10));


        // Add users to the user list VBox
        List<User> userList = generateUserList();
        for (User user : userList) {
            userListVBox.getChildren().add(createUserButton(user));
        }

        // Create ScrollPane for the user list
        ScrollPane userListScrollPane = new ScrollPane(userListVBox);
        //userListScrollPane.setFitToWidth(true);
        userListScrollPane.setMaxWidth(1000); // Fixed width
        userListScrollPane.setMaxHeight(500); // Fixed height
        userListScrollPane.setPadding(new Insets(20));
        userListScrollPane.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(5), new Insets(5))));

        // Set the scrollbar policy to fit the content inside the scroll pane
        userListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Create VBox to contain headerBox and userListScrollPane
        VBox rightSection = new VBox(10);
        rightSection.getChildren().addAll(headerBox, userListScrollPane);

        return rightSection;
    }








    private ButtonWrapper createUserButton(User user) {
        ButtonWrapper userButton = new ButtonWrapper();
        userButton.setCornerRadius(5);
        userButton.setButtonWidth(400);
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
