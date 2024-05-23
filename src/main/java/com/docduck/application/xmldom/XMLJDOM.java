package com.docduck.application.xmldom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.jdom2.util.IteratorIterable;
import org.xml.sax.SAXException;

public class XMLJDOM {

    private final String xmlFilename;
    private final String schemaFilename;
    private final boolean xsdValidate;
    private final boolean namespaceAware;
    static final String outputEncoding = "UTF-8";
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    private final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    private DocumentBuilder documentBuilder;

    protected Document document;

    public XMLJDOM(String xmlFilename, String schemaFilename, boolean validate, boolean setNamespaceAware) {

        this.xmlFilename = xmlFilename;
        this.schemaFilename = schemaFilename;
        this.xsdValidate = validate;
        this.namespaceAware = setNamespaceAware;

    }

    /**
     * Sets up the JDOM parser, and parses the given xml file into the Document
     * Object Model
     * 
     * @author William-A-B
     */
    public void setupJDOM() {
        this.document = null;

        InputStream xmlStream = classloader.getResourceAsStream(xmlFilename);
        InputStream schemaStream = classloader.getResourceAsStream(schemaFilename);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(namespaceAware);
            factory.setValidating(xsdValidate);

            if (xsdValidate) {

                try {
                    factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
                }
                catch (IllegalArgumentException x) {
                    System.err.println("Error: JAXP DocumentBuilderFactory attribute " + "not recognised: "
                            + JAXP_SCHEMA_LANGUAGE);
                    System.err.println("Check to see if parser conforms to JAXP spec");
                }
            }

            if (schemaFilename != null) {
                factory.setAttribute(JAXP_SCHEMA_SOURCE, schemaStream);
            }

            documentBuilder = factory.newDocumentBuilder();

            OutputStreamWriter errorWriter = new OutputStreamWriter(System.err, outputEncoding);
            documentBuilder.setErrorHandler(new DOMErrorHandler(new PrintWriter(errorWriter, true)));

            org.w3c.dom.Document w3cDocument = documentBuilder.parse(xmlStream);
            this.document = new DOMBuilder().build(w3cDocument);
        }
        catch (IOException | SAXException | ParserConfigurationException e) {
            System.out.println(e.getClass());
            e.printStackTrace();
        }
    }

    /**
     * Using the same settings as when setting up the JDOM, this method parses a new
     * xml document and overwrites the original document
     * 
     * @param xmlFilename - The filename of the xml file to parse
     * @author William-A-B
     */
    public void parseNewXMLFile(String xmlFilename) {

        InputStream xmlStream = classloader.getResourceAsStream(xmlFilename);

        try {
            org.w3c.dom.Document w3cDocument = documentBuilder.parse(xmlStream);
            this.document = new DOMBuilder().build(w3cDocument);
        }
        catch (SAXException | IOException e) {
            System.out.println(e.getClass());
            e.printStackTrace();
        }
    }

    /**
     * Uses an iterative depth first search to get an element in the xml specified
     * by the element name, and the id of the element. Uses a stack to make sure the
     * full depth of the xml document is searched.
     * 
     * @param desiredElementName - the type of element to get
     * @param desiredID          - the id of the element to get
     * @param documentRoot       - whether to start from the root of the xml (the
     *                           start of the xml)
     * @param rootElement        - if not starting at the root of the document,
     *                           provide the starting element to search from
     * @return The element found
     */
    public Element getElement(String desiredElementName, int desiredID, boolean documentRoot, Element rootElement) {

        if (documentRoot) {
            rootElement = document.getRootElement();
        }

        Element currentElement = rootElement;

        Element desiredElement;

        Stack<Element> elementStack = new Stack<>();

        elementStack.push(currentElement);

        while (!elementStack.isEmpty()) {
            currentElement = elementStack.pop();

            if (currentElement.getName().equals(desiredElementName)) {

                if (desiredID == -1) {
                    desiredElement = currentElement;
                    return desiredElement;
                }
                else {
                    int elementID = -1;

                    try {
                        elementID = currentElement.getAttribute("id").getIntValue();
                    }
                    catch (DataConversionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (elementID == desiredID) {
                        desiredElement = currentElement;
                        return desiredElement;
                    }
                }

            }

            if (!currentElement.getChildren().isEmpty()) {
                elementStack.addAll(currentElement.getChildren());
            }
        }

        return null;
    }

    public void addElement(Element parentElement, Element desiredELement, String value) {

    }

    /**
     * Gets the attribute value for a given element and attribute name
     * 
     * @param element       - the element to get the attribute for
     * @param attributeName - the attribute to get
     * @return A string containing the attribute value
     * @author William-A-B
     */
    protected String getAttributeValue(Element element, String attributeName) {

        Attribute att = element.getAttribute(attributeName);

        return att.getValue();
    }

    /**
     * Prints out the whole document to the console
     * 
     * @author William-A-B
     */
    public void printDoc() {
        IteratorIterable<Content> descendants = document.getDescendants();

        for (Content content : descendants)
            System.out.println(content);

    }

}
