package com.docduck.application.data;

/**
 * A Class for storing all data for a report
 * - The User which has submitted the report
 * - The description of the fault
 * - The path of the uploaded media 
 * 
 * @author jrb617
 */
public class Report {

    private User user;
    private String description;
    private String pathToFile;
    
    public Report(User user, String description){
        this.user = user;
        this.description = description;
    }
    
    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public User getUser() {
        return this.user;
    }

    public String getDescription() {
        return this.description;
    }


    
    
}
