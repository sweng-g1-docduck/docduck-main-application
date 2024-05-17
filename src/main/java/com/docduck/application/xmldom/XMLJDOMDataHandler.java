package com.docduck.application.xmldom;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

public class XMLJDOMDataHandler extends XMLJDOM {

    private XMLJDOM JDOM = null;

    /**
     * Constructor for specific DocDuck data handling for docduck xml files, the
     * super method is called to parse the xml files given
     * 
     * @param xmlFilename       - The filename and path for the xml file
     * @param schemaFilename    - The filename and path for the xsd schema file
     * @param validate          - Whether to validate the xml against the schema or
     *                          not
     * @param setNamespaceAware - Whether to set the namespace awareness
     * @author William-A-B
     */
    public XMLJDOMDataHandler(String xmlFilename, String schemaFilename, boolean validate, boolean setNamespaceAware) {
        super(xmlFilename, schemaFilename, validate, setNamespaceAware);
    }

    public XMLJDOMDataHandler(XMLJDOM JDOM) {
        super(null, null, false, false);
        this.JDOM = JDOM;
    }

    /**
     * Gets the machines data for a given machine ID
     * 
     * @param machineID - The ID of the machine desired
     * @return A list of the elements contained within the machine parent element
     * @author William-A-B
     */
    public List<Element> getMachineData(int machineID) {

        return getElement("machine", machineID, true, null).getChildren();
    }

    /**
     * Gets the component data for a given component ID
     * 
     * @param componentID - The ID of the component desired
     * @return A list of the elements contained within the component parent element
     * @author William-A-B
     */
    public List<Element> getComponentData(int componentID) {

        return getElement("component", componentID, true, null).getChildren();
    }

    /**
     * Gets the part data for a given part ID
     * 
     * @param partID - The ID of the part desired
     * @return A list of the elements contained within the part parent element
     * @author William-A-B
     */
    public List<Element> getPartData(int partID) {

        return getElement("part", partID, true, null).getChildren();
    }

    /**
     * Gets the component data that is part of a specified machine
     * 
     * @param machineID   - The ID of the machine to search within
     * @param componentID - The ID of the component to retrieve
     * @return A list of the elements contained within the component parent element
     * @author William-A-B
     */
    public List<Element> getComponentDataFromMachine(int machineID, int componentID) {

        Element desiredMachineElement = getElement("machine", machineID, true, null);

        return getElement("component", componentID, false, desiredMachineElement).getChildren();
    }

    /**
     * Gets the part data that is part of a specified component within a machine
     * 
     * @param machineID   - The ID of the machine to search within
     * @param componentID - The ID of the component to search within
     * @param partID      - The ID of the part to retrieve
     * @return A list of the elements contained within the component parent element
     * @author William-A-B
     */
    public List<Element> getPartDataFromComponentFromMachine(int machineID, int componentID, int partID) {

        Element desiredMachineElement = getElement("machine", machineID, true, null);

        Element desiredComponentElement = getElement("component", componentID, false, desiredMachineElement);

        return getElement("part", componentID, false, desiredComponentElement).getChildren();
    }

    /**
     * Gets the name of a specified data type for a given ID
     * 
     * @param dataType - The type of data to search for e.g. machine, component,
     *                 user etc.
     * @param id       - The ID of the data you are searching for
     * @return A string containing the name of the of the data for the ID given
     * @author William-A-B
     */
    public String getNameFromID(String dataType, int id) {

        return getElement(dataType, id, true, null).getChildText("name");
    }

    /**
     * Gets a list of all the machines for a given location e.g. for a specific
     * room, get all the machines within that room
     * 
     * @param locationCode - The location to search in
     * @return A list of elements of all the machines
     * @author William-A-B
     */
    public List<Element> getMachinesAtLocation(String locationCode) {

        List<Element> machines = new ArrayList<>();

        List<Element> machineDataElements = getElement("machineData", -1, true, null).getChildren();

        for (Element target : machineDataElements) {

            if (target.getChild("location").getText().equals(locationCode)) {
                machines.add(target);
            }
        }

        return machines;
    }

    /**
     * Gets a list of all the machine names for a given location e.g. for a
     * specifidc room, get a list of strings for all the machine names within that
     * room
     * 
     * @param locationCode - The location to search in
     * @return A list of strings containing all the names of the machines found at
     *         the specified location
     * @author William-A-B
     */
    public List<String> getMachineNamesAtLocation(String locationCode) {

        List<String> machineNames = new ArrayList<>();

        List<Element> machines = getMachinesAtLocation(locationCode);

        for (Element target : machines) {
            machineNames.add(target.getChild("name").getText());
        }

        return machineNames;
    }

    /**
     * Gets a users data for a given user ID
     * 
     * @param userID - The ID of the user desired
     * @return A list of the elements contained within the user parent element
     * @author William-A-B
     */
    public List<Element> getUserData(int userID) {

        return getElement("user", userID, true, null).getChildren();
    }

    /**
     * Gets a machines report data for a given report ID
     * 
     * @param reportID - The ID of the report desired
     * @return A list of the elements contained within the report parent element
     * @author William-A-B
     */
    public List<Element> getMachineReportData(int reportID) {

        return getElement("report", reportID, true, null).getChildren();
    }

    /**
     * Gets a machines report for a given report ID
     * 
     * @param reportID - The ID of the report desired
     * @return An element of the machine report, containing all the report data
     * @author William-A-B
     */
    public Element getMachineReport(int reportID) {

        return getElement("report", reportID, true, null);
    }

    public void addNewMachine() {

    }

}
