package com.docduck.application.data;

import com.docduck.application.xmldom.InvalidID;
import com.docduck.application.xmldom.JDOMDataHandlerNotInitialised;
import com.docduck.application.xmldom.XMLJDOMDataHandler;

import java.util.ArrayList;

public class BaseData {

    protected XMLJDOMDataHandler domDataHandler;

    public BaseData() {

        try {
            domDataHandler = XMLJDOMDataHandler.getInstance();

        }
        catch (JDOMDataHandlerNotInitialised e) {
            e.printError();
            domDataHandler = XMLJDOMDataHandler.createNewInstance("DocDuckData.xml", "DocDuckSchema.xsd", true, true);
            domDataHandler.setupJDOM();
        }
    }

    public ArrayList<Machine> setupMachineDataFromXML() {

        ArrayList<Integer> machineIDs = domDataHandler.getListOfMachineIDs();
        ArrayList<Machine> machineList = new ArrayList<>();

        for (int currentID : machineIDs) {
            try {
                machineList.add(new Machine(currentID));
            } catch (InvalidID e) {
                e.printErrorMessage();
            }
        }
        return machineList;
    }
    public ArrayList<Report> setupReportDataFromXML() {

        ArrayList<Integer> machineIDs = domDataHandler.getListOfReportIDs();
        ArrayList<Report> reportList = new ArrayList<>();

        for (int currentID : machineIDs) {
            try {
                reportList.add(new Report(currentID));
            } catch (InvalidID e) {
                e.printErrorMessage();
            }
        }
        return reportList;
    }
    public ArrayList<User> setupUserDataFromXML() {

        ArrayList<Integer> machineIDs = domDataHandler.getListOfUserIDs();
        ArrayList<User> userList = new ArrayList<>();

        for (int currentID : machineIDs) {
            try {
                userList.add(new User(currentID));
            } catch (InvalidID e) {
                e.printErrorMessage();
            }
        }
        return userList;
    }
}
