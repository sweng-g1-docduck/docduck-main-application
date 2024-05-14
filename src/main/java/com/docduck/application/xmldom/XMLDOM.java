package com.docduck.application.xmldom;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.docduck.application.utils.InvalidID;

public class XMLDOM {

    private String xmlFilename;
    private String schemaFilename;
    private boolean xsdValidate;
    static final String outputEncoding = "UTF-8";
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    private PrintWriter out;
    private int indent = 0;
    private final String basicIndent = " ";

    // The document that stores the whole xml file
    private Document doc;

    public enum DocDuckData {
        USER,
        MACHINE_REPORT,
        MACHINE,
        MACHINE_COMPONENT,
        COMPONENT_PART,
        MACHINE_PART,
        MACHINE_STATUS
    }

    public XMLDOM(String xmlFilename, String schemaFilename, boolean validate) {
        this.xmlFilename = xmlFilename;
        this.schemaFilename = schemaFilename;
        this.xsdValidate = validate;

        try {
            this.out = new PrintWriter(new OutputStreamWriter(System.out, outputEncoding), true);
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setupDOM() throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setNamespaceAware(true);
        dbf.setValidating(xsdValidate);

        if (xsdValidate) {

            try {
                dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
            }
            catch (IllegalArgumentException x) {
                System.err.println(
                        "Error: JAXP DocumentBuilderFacotry attribute " + "not recognised: " + JAXP_SCHEMA_LANGUAGE);
                System.err.println("Check to see if parser conforms to JAXP spec");
            }
        }

        if (schemaFilename != null) {
            dbf.setAttribute(JAXP_SCHEMA_SOURCE, new File(schemaFilename));
        }

        DocumentBuilder db = dbf.newDocumentBuilder();

        OutputStreamWriter errorWriter = new OutputStreamWriter(System.err, outputEncoding);

        db.setErrorHandler(new DOMErrorHandler(new PrintWriter(errorWriter, true)));

        this.doc = db.parse(new File(xmlFilename));

    }

    /**
     * 
     * @param name
     * @return
     */
    public String getChildNodeValue(String nodeName, Node parentNode) {

        String nodeValue = null;

        if (parentNode.getNodeType() != Node.ELEMENT_NODE) {
            System.err.println("Error: Search node not of element type");
        }

        if (parentNode.hasChildNodes() == false) {
            System.out.println("Warning: Search does not have any child nodes");
            return null;
        }

        NodeList nodeList = parentNode.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node subnode = nodeList.item(i);

            if (subnode.getNodeType() == Node.ELEMENT_NODE) {

                if (subnode.getNodeName() == nodeName) {
                    return getText(subnode);
                }
            }
        }
        return nodeValue;
    }

    public Node getNode(String name, int id) throws InvalidID {

        Node desiredNode = null;
        int nodeID = 0;

        NodeList nodeList = doc.getElementsByTagName(name);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                if (node.hasAttributes() == true) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    Node attributeNode = nodeMap.getNamedItem("id");
                    String attributeValue = attributeNode.getNodeValue();

                    if (checkValidInteger(attributeValue) == true) {

                        if (Integer.parseInt(attributeValue) == id) {
                            desiredNode = node;
                            return desiredNode;
                        }
                    }
                }
                else {
                    continue;
                }
            }
        }

        if (desiredNode == null) {
            throw new InvalidID(true);
        }

        return desiredNode;
    }

    public String getAttribute(Node node, String attributeName) {

        String attributeValue = null;

        if (node.hasAttributes() == true) {
            NamedNodeMap nodeMap = node.getAttributes();
            Node attributeNode = nodeMap.getNamedItem(attributeName);
            attributeValue = attributeNode.getNodeValue();
        }
        return attributeValue;
    }

    /**
     * @param name the tag name for the element to find
     * @param node the element node to start searching from
     * @return the Node found
     */
    public Node findSubNode(String name, Node node) {

        if (node.getNodeType() != Node.ELEMENT_NODE) {
            System.err.println("Error: Search node not of element type");
        }

        if (node.hasChildNodes() == false) {
            System.out.println("Warning: Search does not have any child nodes");
            return null;
        }

        NodeList list = node.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node subnode = list.item(i);

            if (subnode.getNodeType() == Node.ELEMENT_NODE) {

                if (subnode.getNodeName().equals(name)) {
                    return subnode;
                }
            }
        }
        return node;
    }

//    public String getAttribute(String attributeName, Node node) {
//
//        StringBuffer result = new StringBuffer();
//
//        if (attributeName.equals("id")) {
//            NodeList list = node.getChildNodes();
//
//            for (int i = 0; i < list.getLength(); i++) {
//                Node subnode = list.item(i);
//
//                if (subnode.getNodeType() == Node.ATTRIBUTE_NODE) {
//                    return subnode.getNodeValue();
//                }
//                else if (subnode.hasChildNodes()) {
//                    NodeList sublist = subnode.getChildNodes();
//
//                    for (int j = 0; j < list.getLength(); j++) {
//                        Node subSubnode = sublist.item(j);
//
//                        if (subSubnode.getNodeType() == Node.ATTRIBUTE_NODE) {
//                            return subSubnode.getNodeValue();
//                        }
//                    }
//                }
//            }
//        }
//        else {
//            return "0";
//        }
//
//        if (node.hasChildNodes() == false) {
//            return "";
//        }
//
//        NodeList list = node.getChildNodes();
//
//        for (int i = 0; i < list.getLength(); i++) {
//            Node subnode = list.item(i);
//
//            if (subnode.getNodeType() == Node.ATTRIBUTE_NODE) {
//                result.append(subnode.getNodeValue());
//            }
//        }
//        return result.toString();
//    }

    public String getNodeListNodeValue(NodeList nodeList, String nodeName) {

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node desiredNode = nodeList.item(i);

            if (desiredNode.getNodeName().equals(nodeName)) {
                return getText(desiredNode);
            }
        }
        return null;
    }

    public void printNodeListValues(NodeList nodeList) {

        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(nodeList.item(i).getNodeName());
            System.out.println(getText(nodeList.item(i)));
        }
    }

    public String getText(Node node) {

        StringBuffer result = new StringBuffer();

        if (node.hasChildNodes() == false) {
            return "";
        }

        NodeList list = node.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node subnode = list.item(i);

            if (subnode.getNodeType() == Node.TEXT_NODE) {
                result.append(subnode.getNodeValue());
            }
            else if (subnode.getNodeType() == Node.CDATA_SECTION_NODE) {
                result.append(subnode.getNodeValue());
            }
            else if (subnode.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
                // Recurse into the subtree for text
                result.append(getText(subnode));
            }
        }
        return result.toString();
    }

    protected boolean checkValidInteger(String stringToCheck) {

        boolean isValidInteger = false;

        try {
            Integer.parseInt(stringToCheck);
            isValidInteger = true;
        }
        catch (NumberFormatException e) {
            System.out.println("Not a valid integer");
        }
        return isValidInteger;
    }

    /**
     * Prints the whole DOM Tree in full
     */
    public void printDOMTree() {
        echo(doc.getFirstChild());
    }

    /**
     * Provides the node type information
     * 
     * @param n The current node
     */
    private void printlnCommon(Node n) {
        out.print(" nodeName=\"" + n.getNodeName() + "\"");

        String val = n.getNamespaceURI();

        if (val != null) {
            out.print(" uri=\"" + val + "\"");
        }

        val = n.getPrefix();

        if (val != null) {
            out.print(" pre=\"" + val + "\"");
        }

        val = n.getLocalName();

        if (val != null) {
            out.print(" local=\"" + val + "\"");
        }

        val = n.getNodeValue();

        if (val != null) {
            out.print(" nodeValue=");

            if (val.trim().equals("")) {
                // Whitespace
                out.print("[WS]");
            }
            else {
                out.print("\"" + n.getNodeValue() + "\"");
            }
        }
        out.println();
    }

    /**
     * Prints the indentation required when printing the whole DOM tree
     */
    private void outputIndentation() {

        for (int i = 0; i < indent; i++) {
            out.print(basicIndent);
        }
    }

    /**
     * Prints out the DOM tree nodes with the appropriate indentation.
     * 
     * @param n The node to start printing from. Give it the start node to print the
     *          whole tree
     */
    private void echo(Node n) {
        outputIndentation();
        int type = n.getNodeType();

        switch (type) {
        case Node.ATTRIBUTE_NODE:
            out.print("ATTR:");
            printlnCommon(n);
            break;

        case Node.CDATA_SECTION_NODE:
            out.print("CDATA:");
            printlnCommon(n);
            break;

        case Node.COMMENT_NODE:
            out.print("COMM:");
            printlnCommon(n);
            break;

        case Node.DOCUMENT_FRAGMENT_NODE:
            out.print("DOC_FRAG:");
            printlnCommon(n);
            break;

        case Node.DOCUMENT_NODE:
            out.print("DOC:");
            printlnCommon(n);
            break;

        case Node.DOCUMENT_TYPE_NODE:
            out.print("DOC_TYPE:");
            printlnCommon(n);
            NamedNodeMap nodeMap = ((DocumentType) n).getEntities();
            indent += 2;
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Entity entity = (Entity) nodeMap.item(i);
                echo(entity);
            }
            indent -= 2;
            break;

        case Node.ELEMENT_NODE:
            out.print("ELEM:");
            printlnCommon(n);

            NamedNodeMap atts = n.getAttributes();
            indent += 2;
            for (int i = 0; i < atts.getLength(); i++) {
                Node att = atts.item(i);
                echo(att);
            }
            indent -= 2;
            break;

        case Node.ENTITY_NODE:
            out.print("ENT:");
            printlnCommon(n);
            break;

        case Node.ENTITY_REFERENCE_NODE:
            out.print("ENT_REF:");
            printlnCommon(n);
            break;

        case Node.NOTATION_NODE:
            out.print("NOTATION:");
            printlnCommon(n);
            break;

        case Node.PROCESSING_INSTRUCTION_NODE:
            out.print("PROC_INST:");
            printlnCommon(n);
            break;

        case Node.TEXT_NODE:
            out.print("TEXT:");
            printlnCommon(n);
            break;

        default:
            out.print("UNSUPPORTED NODE: " + type);
            printlnCommon(n);
            break;
        }

        indent++;

        for (Node child = n.getFirstChild(); child != null; child = child.getNextSibling()) {
            echo(child);
        }
        indent--;
    }
}
