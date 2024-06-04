package com.docduck.application.data;

import com.docduck.application.xmldom.ElementDataNotRemoved;
import com.docduck.application.xmldom.InvalidID;
import org.jdom2.Element;
import java.util.List;
import org.jdom2.Element;
import java.util.ArrayList;
import java.util.List;
import com.docduck.application.xmldom.InvalidID;

/**
 * A Class for storing all data for a report - The User which has submitted the
 * report - The description of the fault - The path of the uploaded media
 * 
 * @author jrb617 wab513 lw2380
 */
public class Report extends BaseData {

    private final int REPORT_ID_PREFIX = 300;
    private final int id;
    private User user;
    private String title;
    private String description;
    private String pathToFile;
    private int machineID;
    private int userID;

    public Report(User user, String title, String description, String pathToFile, int machineID) {
        super();
        
        ArrayList<Integer> reportIDs = domDataHandler.getListOfReportIDs();
        int counter = 1;

        while (reportIDs.contains(addPrefix(counter, REPORT_ID_PREFIX))) {
            counter++;
        }

        this.id = addPrefix(counter, REPORT_ID_PREFIX);       
        this.user = user;
        this.title = title;
        this.description = description;
        this.pathToFile = pathToFile;
        this.userID = user.getId();
        this.machineID = machineID;

        domDataHandler.addNewReport(this);
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


        List<Element> reportData = domDataHandler.getMachineReportData(id);

        this.id = id;
        for (Element target : reportData) {

            if (target.getName().equals("title")) {
                this.title = target.getValue();
            }
            if (target.getName().equals("description")) {
                this.description = target.getValue();
            }
            if (target.getName().equals("media")) {
                this.pathToFile = target.getValue();
            }
            if (target.getName().equals("machineID")) {
                this.machineID = Integer.parseInt(target.getValue());
            }
            if (target.getName().equals("userID")) {
                this.userID = Integer.parseInt(target.getValue());
            }

        }
        this.user = null;
    }

    public int getUserID() {
        return userID;
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

    public void setUser(User user) {
        this.user = user;
    }

    public int getMachineID() {
        return machineID;
    }

    public String getTitle() {
        return title;
    }


    public int getId() {
        return this.id;
    }
    public void deleteReport() throws ElementDataNotRemoved {
        domDataHandler.deleteReport(id);
    }

    public void editReport(String valueToEdit, String newValue) {
        domDataHandler.editReport(id, valueToEdit, newValue);
    }

}
