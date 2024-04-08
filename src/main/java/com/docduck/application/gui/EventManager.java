package com.docduck.application.gui;

import com.docduck.textlibrary.TextBoxField;
import com.docduck.textlibrary.TextBoxPassword;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

public class EventManager {

    private GUIBuilder builder;
    private HostServices hostServices;
    private Pane root;
    private static EventManager instance;

    private EventManager(GUIBuilder builder, HostServices hostServices, Pane root) {
        this.builder = builder;
        this.hostServices = hostServices;
        this.root = root;
    }

    public static EventManager createInstance(GUIBuilder builder, HostServices hostServices, Pane root) {

        if (instance == null) {
            instance = new EventManager(builder, hostServices, root);
        }
        return instance;
    }

    public EventHandler<ActionEvent> getActionEvent(String eventID) {

        switch (eventID) {
        case "signup":
            EventHandler<ActionEvent> signup = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    hostServices.showDocument("https://docduck.000webhostapp.com");
                }
            };
            return signup;

        case "signin":
            EventHandler<ActionEvent> signin = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    TextBoxField username = (TextBoxField) root.getChildren().get(3);
                    TextBoxPassword password = (TextBoxPassword) root.getChildren().get(5);

                    if (username.getText().equals("admin") && password.getText().equals("password")) {
                        password.setBorderWidth(0);
                        System.out.println("Logged in!");
                        builder.buildSlide(2);
                    }
                    else {
                        password.setBorderWidth(2);
                        password.setBorderColour("#FF0000");
                    }
                }
            };
            return signin;

        default:
            EventHandler<ActionEvent> fault = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    System.out.println("ERROR: Event not attached, check eventID");
                }
            };
            return fault;
        }
    }
}
