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
	
	@Test
	public void getComponentDataWorks() {
		
		List<Element> componentData = data.getComponentData(1101);
		assertEquals(componentData.isEmpty(), false);
		
		for (Element target : componentData) {
			if (target.getName().equals("stockNumber"))
				assertEquals(target.getValue(), "2");
		}
	}
	
	@Test
	public void getPartDataWorks() {
		
		List<Element> partData = data.getPartData(1111);
		assertEquals(partData.isEmpty(), false);
		
		for (Element target : partData) {
			if (target.getName().equals("name"))
				assertEquals(target.getValue(), "LCD Screen");
		}
				
	}
		
	@Test
	public void getComponentDataFromMachineWorks() {
		
		List<Element> componentDataFromMachine = data.getComponentDataFromMachine(1001, 1101);
		assertEquals(componentDataFromMachine.isEmpty(), false);
		
		for (Element target : componentDataFromMachine) {
			if (target.getName().equals("stockNumber"))
				assertEquals(target.getValue(), "2");
		}
		
	}
	
	@Test
	public void getPartDataFromComponentFromMachineWorks() {
		
		List<Element> partDataFromCompMach = data.getPartDataFromComponentFromMachine(1001, 1101, 1111);
		assertEquals(partDataFromCompMach.isEmpty(), false);
		
		for (Element target : partDataFromCompMach) {
			if (target.getName().equals("name"))
				assertEquals(target.getValue(), "LCD Screen");
			else if (target.getName().equals("stockNumber"))
				assertEquals(target.getValue(), "2");
		}
	}
	
	@Test
	public void getNameFromIDWorks() {
		
		String nameFromID = data.getNameFromID("machine", 1001);

        assertNotNull(nameFromID);
        assertEquals("3D Printer", nameFromID);
	}
	
	@Test
	public void getMachinesAtLocationWorks() {
		
		List<Element> machinesAtLocation = data.getMachinesAtLocation("Labs_4th_Floor_Office");
		
		for (Element target : machinesAtLocation) {
			if (target.getName().equals("location"))
				assertEquals(target.getValue(), "Labs_4th_Floor_Office");
		}
	}
	
//	@Test
//	public void getMachineNamesAtLocationWorks() {
//		
//		List<String> machineNamesAtLocation = data.getMachineNamesAtLocation("Labs_4th_Floor_Office");
//		
//		for (String target : machineNamesAtLocation) {
//			if (target.getName().equals("location"))
//				assertEquals(target.getValue(), "Labs_4th_Floor_Office");
//		}
//	}
	
	@Test
	public void getMachineNamesAtLocationWorks() {
		
		List<String> machineName = data.getMachineNamesAtLocation("Labs_4th_Floor_Office");
		
        for (String target : machineName) {
			if (target.equals("location"))
				assertEquals(target, "Labs_4th_Floor_Office");
		}
        
	}
	
	@Test
	public void getUserDataWorks() {
        
		List<Element> userData = data.getUserData(2001); 
        assertEquals(userData.isEmpty(), false);
        
        for (Element target : userData) {
        	if (target.getName().equals("role"))
        		assertEquals(target.getValue(), "OPERATOR");
        }
        		
	}
	
	@Test
	public void getMachineReportDataWorks() {
        
		List<Element> machineReportData = data.getMachineReportData(3001); 
        assertEquals(machineReportData.isEmpty(), false);
        
        for (Element target : machineReportData) {
        	if (target.getName().equals("description"))
        		assertEquals(target.getValue(), "The touchscreen on the 3D printer doesn't respond");
        }
        		
	}
	
	
	@Test
    public void getMachineReportWorks() {

        Element machineReport = data.getMachineReport(3001);

        assertNotNull(machineReport);

        assertEquals("3D Printer Not Responding", machineReport.getChildText("title"));
        assertEquals("The touchscreen on the 3D printer doesn't respond", machineReport.getChildText("description"));
    }
	
	 @Test
	    public void getListOfMachineIDsWorks() {

	        List<String> listOfMachineIDs = List.of("1001", "1002");

	        List<Integer> actualMachineIDs = data.getListOfMachineIDs();

	        assertEquals(listOfMachineIDs.size(), actualMachineIDs.size(), "The size of the machine ID list does not match.");
	    }
	 
	 @Test
	    public void getListOfReportIDsWorks() {

	        List<String> listOfReportIDs = List.of("3001");

	        List<Integer> actualReportIDs = data.getListOfReportIDs();

	        assertEquals(listOfReportIDs.size(), actualReportIDs.size(), "The size of the report ID list does not match.");
	    }
	 
	 @Test
	 public void getListOfUserIDsWorks() {
		 
		 List<String> listOfUserIDs = List.of("2001", "2002");
		 
		 List<Integer> actualUserIDs = data.getListOfMachineIDs();
		 
		 assertEquals(listOfUserIDs.size(), actualUserIDs.size(), "The size of the user ID list does not match.");
	 }
	 
}


