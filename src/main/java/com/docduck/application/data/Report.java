package com.docduck.application.data;

import com.docduck.application.xmldom.InvalidID;
import org.jdom2.Element;

import java.util.List;

/**
 * A Class for storing all data for a report - The User which has submitted the
 * report - The description of the fault - The path of the uploaded media
 * 
 * @author jrb617
 */
public class Report extends BaseData {

    private String title;
    private User user;
    private String description;
    private String pathToFile;

    public Report(User user, String description) {
        super();
        this.user = user;
        this.description = description;
    }

    /**
     * Constructor to create a new Report and populate the data from the xml files
     *
     * @param id - The ID of the report to get out of the xml database
     * @throws InvalidID The provided id of the report doesn't exist in the data
     * @author William-A-B
     */
    public Report(int id) throws InvalidID {

        if (!domDataHandler.checkIfIDExists(id)) {
            throw new InvalidID(
                    "ID does not exist in database, please provide an existing ID, or create a new Report.");
        }

        List<Element> reportData = domDataHandler.getReportData(id);

        for (Element target : reportData) {

            if (target.getName().equals("title")) {
                this.title = target.getValue();
            }

            // TODO Complete data population
        }
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
