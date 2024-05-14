package com.docduck.application.gui.pages;

import java.util.List;

import com.docduck.application.gui.EventManager;

public class StatusPage extends Page {

    private static EventManager eventHandler = null;
    
    // This contains a list of nodes for the status page
    private List nodeList = null;
    
    public StatusPage() {
        super();
        this.eventHandler = EventManager.getInstance();
    }
    
    // Build the status page here.
    // get the menu and container from Page class (this is the daughter class of page)
    // getDefaultPage();
    
    // return listof nodes
    @Override
    public Object buildPage() {
        
        buildStatusPage();
        addStatusPageToDefaultPage();
        
        return null;
    }
    
    private Object buildStatusPage() {
        return null; 
    }
    
    private Object addStatusPageToDefaultPage() {
        return null; 
    }
    
}
