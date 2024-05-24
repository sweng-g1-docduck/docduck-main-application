package com.docduck.application.data;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import com.docduck.application.xmldom.InvalidID;

/**
 * A Class for storing all data related to a machine - The Machine's Name - The
 * Room it is in - The status of the machine - The Serial number of the machine
 * - The datasheet Url - The Purchase location Url - The Current Report
 * associated with the machine - All previous reports of the machine
 * 
 * @author jrb617
 */
public class Machine extends BaseData {

    private final int id;
    private String name;
    private String location;
    private String status;
    private String serialNumber;
    private String datasheetRef;
    private String purchaseLocationRef;
    private Report currentReport;
    private final ArrayList<Report> oldReports = new ArrayList<>();

    public Machine(int id, String name, String room, String status, String serialNumber, String dataSheet,
            String purchaseLocation) throws InvalidID{
        super();
        if (!domDataHandler.checkIfIDExists(id)) {
            throw new InvalidID(
                    "ID does not exist in database, please provide an existing ID, or create a new machine.");
        }
        this.id = id;
        this.name = name;
        this.location = room;
        this.serialNumber = serialNumber;
        this.datasheetRef = dataSheet;
        this.purchaseLocationRef = purchaseLocation;

        if (status.equals("ONLINE") || status.equals("MAINTENANCE") || status.equals("OFFLINE")) {
            this.status = status;
        }
        addNewDataToXML();
    }

    /**
     * Constructor to create a new Machine and populate the data from the xml files
     * 
     * @param id - The ID of the machine to get out of the xml database
     * @throws InvalidID The provided id of the machine doesn't exist in the data
     * @author William-A-B
     */
    public Machine(int id) throws InvalidID {

        if (!domDataHandler.checkIfIDExists(id)) {
            throw new InvalidID(
                    "ID does not exist in database, please provide an existing ID, or create a new machine.");
        }

        List<Element> machineData = domDataHandler.getReportData(id);

        this.id = id;
        
        for (Element target : machineData) {

            if (target.getName().equals("name")) {
                this.name = target.getValue();
            }

            if (target.getName().equals("location")) {
                this.location = target.getValue();
            }

            if (target.getName().equals("status")) {
                this.status = target.getValue();
            }
            
            if (target.getName().equals("serialNumber")) {
                this.serialNumber = target.getValue();
            }
            
            if (target.getName().equals("datasheetRef")) {
                this.datasheetRef = target.getValue();
            }
            
            if (target.getName().equals("purchaseLocationRef")) {
                this.purchaseLocationRef = target.getValue();
            }

        }
    }

    private void addNewDataToXML() {
        domDataHandler.addNewMachine(this);
    }


    public int getId() {
        return id;
    }

    public String getHyperlink() {
        return this.datasheetRef;
    }

    public String getPurchaseLocation() {
        return this.purchaseLocationRef;
    }

    public String getStatus() {
        return this.status;
    }

    public ArrayList<Report> getOldReports() {
        return oldReports;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return ("Machine name: " + this.name + ", Room: " + this.location + ", Status: " + this.status
                + ", Serial Number: " + this.serialNumber + ", Datasheet Ref: " + this.datasheetRef);

    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getDatasheet() {
        return this.datasheetRef;
    }

    public void addReport(Report report) {
        this.currentReport = report;
    }

    public Report getReport() {
        return this.currentReport;
    }

    public void archiveReport() {
        this.oldReports.add(currentReport);
        this.currentReport = null;
    }

}
