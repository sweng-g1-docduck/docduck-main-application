package com.docduck.application.data;

import com.docduck.application.xmldom.JDOMDataHandlerNotInitialised;
import com.docduck.application.xmldom.XMLJDOMDataHandler;

public class BaseData {

    protected XMLJDOMDataHandler domDataHandler;

    public BaseData() {

        try {
            domDataHandler = XMLJDOMDataHandler.getInstance();

        }
        catch (JDOMDataHandlerNotInitialised e) {
            e.printError();
            domDataHandler = XMLJDOMDataHandler.createNewInstance("/DocDuckData.xml", "/DocDuckSchema.xsd", true, true);
            domDataHandler.setupJDOM();
        }
    }
}
