package com.docduck.application.data;

/**
 * A Class for storing all data for a report - The User which has submitted the
 * report - The description of the fault - The path of the uploaded media
 * 
 * @author jrb617
 */
public class Report extends BaseData {


    private final int id;
    private final User user;
    private final String title;
    private final String description;
    private final String pathToFile;

    public Report(int id, User user, String title, String description, String pathToFile) {
        super();
        this.id = id;        
        this.user = user;
        this.title = title;
        this.description = description;
        this.pathToFile = pathToFile;
    }

    public String getPathToFile() {
        return pathToFile;
    }


    public User getUser() {
        return this.user;
    }

    public String getDescription() {
        return this.description;
    }

}
