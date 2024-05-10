package com.docduck.application.gui.pages;

import java.util.List;

public class Page {
    
    
    /**
     * This class will contain the basic page structure for the main application
     * This will include the main menu and a container to contain each specific pages content
     */
    
    // This contains a list of nodes for the default page
    private List nodeList = null;
    
    public Page() {
        
    }
    
    // return list of nodes
    public Object buildPage() {
        buildMainMenu();
        return null;
    }
    
    private Object buildMainMenu() {
        return null; 
    }
    
    protected Object getDefaultPage() {
        return null;
    }
}
