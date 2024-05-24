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


    public User getUser() {
        return this.user;
    }

    public String getDescription() {
        return this.description;
    }

}
