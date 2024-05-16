package com.docduck.application.data;

import java.util.ArrayList;

public class Machine {

    private String name;
    private String room;
    private String status;
    private String serialNumber;
    private String dataSheet;
    private String purchaseLocation;
    private Report currentReport;
    private ArrayList<Report> oldReports;

    public Machine(String name, String room, String status, String serialNumber, String dataSheet, String purchaseLocation) {
        this.name = name;
        this.room = room;
        this.serialNumber = serialNumber;
        this.dataSheet = dataSheet;
        this.purchaseLocation  = purchaseLocation;
        if (status.equals("ONLINE") || status.equals("MAINTENANCE") || status.equals("OFFLINE")) {
            this.status = status;
        }

    }

    public String getHyperlink() {
        return this.dataSheet;
    }

    public String getPurchaseLocation() {
        return this.purchaseLocation;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return ("Machine name: " + this.name + ", Room: " + this.room + ", Status: " + this.status + ", Serial Number: "
                + this.serialNumber + ", Datasheet Ref: " + this.dataSheet);

    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getDatasheet() {
        return this.dataSheet;
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
