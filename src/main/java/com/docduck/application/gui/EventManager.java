package com.docduck.application.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EventManager {

	private GUIBuilder builder;
	private static EventManager instance;
	
	private EventManager(GUIBuilder builder) {
		this.builder = builder;
	}
	
	public static EventManager createInstance(GUIBuilder builder) {
    	if (instance == null) {
    	instance = new EventManager(builder);
    	}
    	return instance;
    }
	
	public EventHandler<ActionEvent> getActionEvent(String eventID) {
		switch (eventID) {
			case "Test":
				EventHandler<ActionEvent> Test = new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						System.out.println("Test");
					}
				};
				return Test;

				
			default:
				EventHandler<ActionEvent> fault = new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						System.out.println("ERROR: Event not attached, check eventID");
					}
				};
				return fault;
		}
	}
}
