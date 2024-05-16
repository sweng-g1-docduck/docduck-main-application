package com.docduck.application.data;

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
