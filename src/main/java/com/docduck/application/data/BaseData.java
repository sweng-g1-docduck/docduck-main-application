package com.docduck.application.data;


import com.docduck.application.xmldom.InvalidID;
import com.docduck.application.xmldom.JDOMDataHandlerNotInitialised;
import com.docduck.application.xmldom.XMLJDOMDataHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BaseData {

    public XMLJDOMDataHandler domDataHandler;
    private String docduckDataFileName;
    private File docduckDataInputFile;
    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public BaseData() {

        try {
            domDataHandler = XMLJDOMDataHandler.getInstance();

        }
        catch (JDOMDataHandlerNotInitialised e) {
            e.printError();
            System.out.println("Creating a new XMLJDOMDataHandler instance");

            try {
                loadDataFiles();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            domDataHandler = XMLJDOMDataHandler.createNewInstance(docduckDataInputFile.getPath(), "DocDuckSchema.xsd", true, true);
            domDataHandler.setupJDOM();
        }
    }

    private void loadDataFiles() throws IOException {

        String workingDirectory;
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            workingDirectory = System.getenv("AppData");
        }
        else {
            workingDirectory = null;
        }

        String docduckWorkingDirectory = workingDirectory + "/com.docduck/resources/";
        Path docduckPath = Paths.get(docduckWorkingDirectory);
        docduckDataFileName = docduckPath + "/DocDuckData.xml";

        if (!Files.exists(docduckPath)) {
            // Directory doesn't exist, create it.
            if (new File(docduckWorkingDirectory).mkdirs()) {
                InputStream defaultInput = classLoader.getResourceAsStream("DocDuckDefaults.xml");;

                if (defaultInput == null) {
                    System.err.println("DocDuckDefaults.xml null and not found!");
                }
                else {
                    Files.copy(defaultInput, Paths.get(docduckDataFileName));
                    docduckDataInputFile = new File(docduckDataFileName);
                }
            }
        }
        else {
            docduckDataInputFile = new File(docduckDataFileName);
            if (docduckDataInputFile.length() == 0) {
                System.err.println("Data File is empty, cannot parse empty file");
                docduckDataInputFile.delete();
                InputStream defaultInput = classLoader.getResourceAsStream("DocDuckDefaults.xml");
                if (defaultInput == null) {
                    System.err.println("DocDuckDefaults.xml null and not found!");
                }
                else {
                    Files.copy(defaultInput, Paths.get(docduckDataFileName));
                    docduckDataInputFile = new File(docduckDataFileName);
                }
            }
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

        ArrayList<Integer> reportIDs = domDataHandler.getListOfReportIDs();
        ArrayList<Report> reportList = new ArrayList<>();

        for (int currentID : reportIDs) {
            try {
                reportList.add(new Report(currentID));
            } catch (InvalidID e) {
                e.printErrorMessage();
            }
        }
        return reportList;
    }
    public ArrayList<User> setupUserDataFromXML() {

        ArrayList<Integer> userIDs = domDataHandler.getListOfUserIDs();
        ArrayList<User> userList = new ArrayList<>();

        for (int currentID : userIDs) {
            try {
                userList.add(new User(currentID));
            } catch (InvalidID e) {
                e.printErrorMessage();
            }
        }
        return userList;
    }
    

    
    protected int addPrefix(int id, int prefix) {

        String idString = Integer.toString(id);
        String prefixString = Integer.toString(prefix);


        int prefixedID = -1;
        try {
            prefixedID = Integer.parseInt(prefixString + idString);
        } catch (NumberFormatException e) {
            System.err.println("Error caused by adding prefix to non-prefixed ID");
            e.printStackTrace();
        }

        return prefixedID;
    }
}
