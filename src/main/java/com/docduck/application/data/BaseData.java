package com.docduck.application.data;


import com.docduck.application.xmldom.InvalidID;
import com.docduck.application.xmldom.JDOMDataHandlerNotInitialised;
import com.docduck.application.xmldom.XMLJDOMDataHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BaseData {

    public XMLJDOMDataHandler domDataHandler;
    private String docduckDataFileName;
    private FileWriter docduckDataOutputWriter;


    public BaseData() {

        try {
            domDataHandler = XMLJDOMDataHandler.getInstance();

        }
        catch (JDOMDataHandlerNotInitialised e) {
            e.printError();
            System.out.println("Creating a new instance");

            loadDataFiles();

            domDataHandler = XMLJDOMDataHandler.createNewInstance("DocDuckData.xml", "DocDuckSchema.xsd", true, true, docduckDataOutputWriter);
            domDataHandler.setupJDOM();
        }
    }

    private void loadDataFiles() {

        String workingDirectory;
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            workingDirectory = System.getenv("AppData");
        }
        else {
            workingDirectory = null;
        }

        String docduckWorkingDirectory = workingDirectory + "/docduck/resources/";
        Path docduckPath = Paths.get(docduckWorkingDirectory);
        System.out.println("DocDuck Path = " + docduckPath);

        if (!Files.exists(docduckPath)) {
            // Directory doesn't exist, create it.
            if (new File(docduckWorkingDirectory).mkdirs()) {
                // Directory created
                System.out.println("Created " + docduckWorkingDirectory);
                docduckDataFileName = docduckPath.getFileName().toString() + "/DocDuckData.xml";
                System.out.println("DocDuckFileName = " + docduckDataFileName);
                try {
                    Files.createFile(Paths.get(docduckDataFileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            docduckDataFileName = docduckPath.getFileName().toString() + "/DocDuckData.xml";
        }
        System.out.println("DocDuckFileName = " + docduckDataFileName);

        try {
            docduckDataOutputWriter = new FileWriter(docduckDataFileName);
            System.out.println("Output Writer = " + docduckDataOutputWriter);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
        }
        catch (NumberFormatException e) {
            System.err.println("Error caused by adding prefix to non-prefixed ID");
            e.printStackTrace();
        }
        
        return prefixedID;
    }
}
