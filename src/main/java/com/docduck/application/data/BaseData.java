package com.docduck.application.data;

import java.util.ArrayList;
import java.util.List;

import com.docduck.application.xmldom.JDOMDataHandlerNotInitialised;
import com.docduck.application.xmldom.XMLJDOMDataHandler;

public class BaseData {

    protected XMLJDOMDataHandler domDataHandler;
    
    private List<Machine> machineList = new ArrayList<>(); 
    private List<Report> reportList = new ArrayList<>(); 
    private List<User> userList = new ArrayList<>(); 

    public BaseData() {

        try {
            domDataHandler = XMLJDOMDataHandler.getInstance();

        }
        catch (JDOMDataHandlerNotInitialised e) {
            e.printError();
            System.out.println("Creating a new instance");
            domDataHandler = XMLJDOMDataHandler.createNewInstance("DocDuckData.xml", "DocDuckSchema.xsd", true, true);
            domDataHandler.setupJDOM();
        }
    }
    
    public ArrayList<Machine> setupMachineDataFromXML() {
        
        ArrayList<int> machineIDs = domDataHandler.getListOfMachineIDs();
        ArrayList<Machine> machineList = new ArrayList<>();
               
        for (int currentID : machineIDs) {
            machineList.add(new Machine(currentID));
        }
 
        return machineList;
    }
    
    
    
}
