package com.docduck.application.data;

import com.docduck.application.xmldom.ElementDataNotRemoved;
import com.docduck.application.xmldom.InvalidID;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * A Class for storing all data related to a machine - The Machine's Name - The
 * Room it is in - The status of the machine - The Serial number of the machine
 * - The datasheet Url - The Purchase location Url - The Current Report
 * associated with the machine - All previous reports of the machine
 * 
 * @author jrb617 wab513 lw2380
 */
public class Machine extends BaseData {

    private static final int MACHINE_ID_PREFIX = 100;
    private final int id;
    private String name;
    private String status;
    private String serialNumber;
    private String location;
    private String imageRef;
    private String datasheetRef;
    private String purchaseLocationRef;
    private String machineImageLocation;
    private Report currentReport;
    private final ArrayList<Report> oldReports = new ArrayList<>();

    /**
     * Creates a new machine and auto-increments to the next available id
     * 
     * @param name
     * @param room
     * @param status
     * @param serialNumber
     * @param dataSheet
     * @param purchaseLocation
     * 
     * @author jrb617 wab513
     */
    public Machine(String name, String room, String status, String serialNumber, String dataSheet,
            String purchaseLocation, String machineImageLocation) {
        super();

        ArrayList<Integer> machineIDs = domDataHandler.getListOfMachineIDs();
        int counter = 1;

        while (machineIDs.contains(addPrefix(counter, MACHINE_ID_PREFIX))) {
            counter++;
        }

        this.id = addPrefix(counter, MACHINE_ID_PREFIX);
        this.name = name;
        this.location = room;
        this.serialNumber = serialNumber;
        this.datasheetRef = dataSheet;
        this.purchaseLocationRef = purchaseLocation;
        this.status = status;
        this.machineImageLocation = machineImageLocation;

        domDataHandler.addNewMachine(this);
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

        List<Element> machineData = domDataHandler.getMachineData(id);

        this.id = id;

        for (Element target : machineData) {

            if (target.getName().equals("name")) {
                this.name = target.getValue();
            }

            if (target.getName().equals("status")) {
                this.status = target.getValue();
            }

            if (target.getName().equals("serialNumber")) {
                this.serialNumber = target.getValue();
            }

            if (target.getName().equals("location")) {
                this.location = target.getValue();
            }

            if (target.getName().equals("imageRef")) {
                this.imageRef = target.getValue();
            }

            if (target.getName().equals("datasheetRef")) {
                this.datasheetRef = target.getValue();
            }

            if (target.getName().equals("purchaseLocationRef")) {
                this.purchaseLocationRef = target.getValue();
            }

        }
    }

    public int getId() {
        return id;
    }

    public ArrayList<Report> getOldReports() {
        return oldReports;
    }

    public void setStatus(String status) {
        this.status = status;
        editMachine("status",this.status);
        if (this.status.equals("ONLINE") && this.currentReport != null) {
            System.out.println("Archived report");
            oldReports.add(currentReport);
            currentReport = null;
        }
    }

    @Override
    public String toString() {
        return ("Machine name: " + this.name + ", Room: " + this.location + ", Status: " + this.status
                + ", Serial Number: " + this.serialNumber + ", Datasheet Ref: " + this.datasheetRef);

    }

    public void addReport(Report report) {
        this.currentReport = report;
    }

    public void archiveReport() {
        this.oldReports.add(currentReport);
        this.currentReport = null;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getImageRef() {
        return imageRef;
    }

    public String getDatasheetRef() {
        return datasheetRef;
    }

    public String getPurchaseLocationRef() {
        return purchaseLocationRef;
    }

    public Report getCurrentReport() {
        return currentReport;
    }

    public void setName(String name) {
        this.name = name;
        editMachine("name", name);
    }

    public void setLocation(String location) {
        this.location = location;
        editMachine("location", location);
    }


    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        editMachine("serialNumber", serialNumber);
    }

    public void setDatasheet(String datasheet) {
        this.datasheetRef = datasheet;
        editMachine("datasheetRef", datasheet);
    }

    public void setPurchaseLocation(String purchaseLocation) {
        this.purchaseLocationRef = purchaseLocation;
        editMachine("purchaseLocationRef", purchaseLocation);
    }

    public void setMachineImage(String machineImageLocation) {
        this.machineImageLocation = this.machineImageLocation;
        editMachine("imageRef", machineImageLocation);
    }


    public void deleteMachine() throws ElementDataNotRemoved {
        domDataHandler.deleteMachine(id);
    }

    public void editMachine(String valueToEdit, String newValue) {
        domDataHandler.editMachine(id, valueToEdit, newValue);
    }
}
