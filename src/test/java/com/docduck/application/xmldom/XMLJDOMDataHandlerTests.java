package com.docduck.application.xmldom;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.jdom2.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Stack;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;

import com.docduck.application.data.*;

public class XMLJDOMDataHandlerTests {

	public static XMLJDOMDataHandler data;
	
	@BeforeAll
	public static void createJDOMInstance() {
        try {
        	data = XMLJDOMDataHandler.getInstance();
        } catch (JDOMDataHandlerNotInitialised e) {
            e.printError();
            System.out.println("Creating New Instance");
            data = XMLJDOMDataHandler.createNewInstance("DocDuckData.xml", "DocDuckSchema.xsd", true, true);
            data.setupJDOM();
        }
        
//        
        
	}
	
	@Test
	public void testJDOMInstance() {
		assertNotNull(data.getClass().toString(), "Data Instance not initialised correctly");
	}
	
	
	@Test
	public void getMachineDataWorks() {
        
		List<Element> machineData = data.getMachineData(1001); 
        assertEquals(machineData.isEmpty(), false);
        
        for (Element target : machineData) {
        	if (target.getName().equals("status"))
        		assertEquals(target.getValue(), "OFFLINE");
        }
        		
	}
	
	
	
}


