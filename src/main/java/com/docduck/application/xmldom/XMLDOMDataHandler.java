package com.docduck.application.xmldom;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.docduck.application.utils.InvalidID;

public class XMLDOMDataHandler extends XMLDOM {

    XMLDOM dom = null;

    public XMLDOMDataHandler(String xmlFilename, String schemaFilename, boolean validate) {
        super(xmlFilename, schemaFilename, validate);
    }

    public XMLDOMDataHandler(XMLDOM dom) {
        super(null, null, false);
        this.dom = dom;
    }

    public NodeList getMachineData(int machineID) {

        NodeList machineNodeList = null;
        Node machineNode = null;

        try {
            machineNode = getNode("machine", machineID);
        }
        catch (InvalidID e) {

            if (e.isIDInvalid() == true) {
                System.out.println("The provided ID is not valid");
            }
        }

        if (machineNode.hasChildNodes() == false) {
            return null;
        }

        machineNodeList = machineNode.getChildNodes();

        return machineNodeList;

    }

    public NodeList getMachineComponentData(NodeList machineNodeList, int componentID) {

        NodeList componentNodeList = null;

        for (int i = 0; i < machineNodeList.getLength(); i++) {

            Node subnode = machineNodeList.item(i);

            if (subnode.getNodeName().equals("components")) {
                NodeList nodeList = subnode.getChildNodes();

                for (int j = 0; j < nodeList.getLength(); j++) {
                    Node componentSubnode = nodeList.item(j);

                    String componentAttribute = getAttribute(componentSubnode, "id");

                    int componentSubnodeID = -1;

                    if (checkValidInteger(componentAttribute) == true) {
                        componentSubnodeID = Integer.parseInt(componentAttribute);
                    }

                    if (componentSubnodeID == componentID) {
                        componentNodeList = componentSubnode.getChildNodes();
                        return componentNodeList;
                    }
                }
            }
        }

        return componentNodeList;
    }

    public NodeList getComponentPartData(NodeList componentNodeList, int partID) {

        NodeList partNodeList = null;

        for (int i = 0; i < componentNodeList.getLength(); i++) {

            Node subnode = componentNodeList.item(i);

            if (subnode.getNodeName().equals("parts")) {
                NodeList nodeList = subnode.getChildNodes();

                for (int j = 0; j < nodeList.getLength(); j++) {
                    Node partSubnode = nodeList.item(j);

                    String partAttribute = getAttribute(partSubnode, "id");

                    int partSubnodeID = -1;

                    if (checkValidInteger(partAttribute) == true) {
                        partSubnodeID = Integer.parseInt(partAttribute);
                    }

                    if (partSubnodeID == partID) {
                        partNodeList = partSubnode.getChildNodes();
                        return partNodeList;
                    }
                }
            }
        }

        return partNodeList;
    }

    public NodeList getComponentPartData(int machineID, int componentID, int partID) {
        return getComponentPartData(getMachineComponentData(getMachineData(machineID), componentID), partID);
    }

    public NodeList getMachineComponentData(int machineID, int componentID) {
        return getMachineComponentData(getMachineData(machineID), componentID);
    }

    public NodeList getComponentData(int componentID) {

        NodeList componentNodeList = null;
        Node componentNode = null;

        try {
            componentNode = getNode("component", componentID);
        }
        catch (InvalidID e) {

            if (e.isIDInvalid() == true) {
                System.out.println("The provided ID is not valid");
            }
        }

        if (componentNode.hasChildNodes() == false) {
            return null;
        }

        componentNodeList = componentNode.getChildNodes();

        return componentNodeList;

    }

    public NodeList getPartData(int partID) {

        NodeList partNodeList = null;
        Node partNode = null;

        try {
            partNode = getNode("part", partID);
        }
        catch (InvalidID e) {

            if (e.isIDInvalid() == true) {
                System.err.println("Error: The provided ID is not valid");
            }
        }

        if (partNode.hasChildNodes() == false) {
            return null;
        }

        partNodeList = partNode.getChildNodes();

        return partNodeList;

    }

    public NodeList getMachinesByRoom(String roomName) {

        NodeList locationNodeList = doc.getElementsByTagName("location");

        for (int i = 0; i < locationNodeList.getLength(); i++) {

        }

        // machine location == roomName
        // return list of machine Names or IDs
        // Another method the same to return a list of NodeLists

        return null;
    }

    // getNameFromID
}
