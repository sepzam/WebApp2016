package com.example.ScheduleMe;


import java.io.File;
import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
final class Database2 extends Window {

	public static ArrayList<Course> courses = new ArrayList<Course>();
	//static CourseGrid grid = new CourseGrid();
	
	public Database2() {	
		System.out.println("Degree:   "+ MyInit.selectedDegree);
		System.out.println("Period: "+ MyInit.selectedPeriod);
		
	  	//MyInit.grid.getContainerDataSource().removeAllItems();
	  	
		try {
			courses.clear();
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File("final.xml"));
					    
			NodeList listOfCourses = doc.getElementsByTagName("record");
			int totalCourse = listOfCourses.getLength(); 
			System.out.println("Total no of courses : " + totalCourse); //silebilirsin
	
		    for(int s=0; s<totalCourse ; s++) {
		    	Node CourseNode = listOfCourses.item(s);
				
				if(CourseNode.getNodeType() == Node.ELEMENT_NODE){
					ArrayList<String> lectureDays = new ArrayList<String>();
					ArrayList<String> lectureHours = new ArrayList<String>();	
					
					Element CourseElement = (Element)CourseNode; 
			    		
					NodeList tucsList = CourseElement.getElementsByTagName("TUCS");
				    Element tucsElement = (Element)tucsList.item(0);
				    NodeList textORGList = tucsElement.getChildNodes();
				    //System.out.println("Organization : " + ((Node)textORGList.item(0)).getNodeValue().trim());
				      
				    
			        NodeList departmentList = CourseElement.getElementsByTagName("Department");
			        Element departmentElement = (Element)departmentList.item(0);
			        NodeList textDEPList = departmentElement.getChildNodes();
			        //System.out.println("Department : " + ((Node)textDEPList.item(0)).getNodeValue().trim());
			        
			        NodeList courseCodeList = CourseElement.getElementsByTagName("CourseCode");
			        Element courseCodeElement = (Element)courseCodeList.item(0);
			        NodeList textCCList = courseCodeElement.getChildNodes();
			        //System.out.println("Course Code : " + ((Node)textCCList.item(0)).getNodeValue().trim());
			         
			        NodeList courseNameList = CourseElement.getElementsByTagName("CourseName");
			        Element courseNameElement = (Element)courseNameList.item(0);
			        NodeList textCNList = courseNameElement.getChildNodes();
			        //System.out.println("Course Name : " + ((Node)textCNList.item(0)).getNodeValue().trim());
			        
			        NodeList lecturerList = CourseElement.getElementsByTagName("Lecturer");
			        Element lecturerElement = (Element)lecturerList.item(0);
			        NodeList textLECList = lecturerElement.getChildNodes();
			        //System.out.println("Lecturer : " + ((Node)textLECList.item(0)).getNodeValue().trim());
			
			        NodeList creditList = CourseElement.getElementsByTagName("Credit");
			        Element creditElement = (Element)creditList.item(0);
			        NodeList textCREList = creditElement.getChildNodes();
			        //System.out.println("Credit : " + ((Node)textCREList.item(0)).getNodeValue().trim());
			        
			        NodeList periodList = CourseElement.getElementsByTagName("Period");
			        Element periodElement = (Element)periodList.item(0);
			        NodeList textPERList = periodElement.getChildNodes();
			        //System.out.println("Period : " + ((Node)textPERList.item(0)).getNodeValue().trim());
			         
			   
			        NodeList daySlot1List = CourseElement.getElementsByTagName("DaySlot1");
			        Element daySlot1Element = (Element)daySlot1List.item(0);
			        NodeList textSD1List = daySlot1Element.getChildNodes();
			        //System.out.println("Slot1-Day : " +	((Node)textSD1List.item(0)).getNodeValue().trim());
	
			        
			        NodeList timeSlot1List = CourseElement.getElementsByTagName("TimeSlot1");
			        Element timeSlot1Element = (Element)timeSlot1List.item(0);
			        NodeList textST1List = timeSlot1Element.getChildNodes();
			       // System.out.println("Slot1-Time : " + ((Node)textST1List.item(0)).getNodeValue().trim());
				        
			        NodeList daySlot2List = CourseElement.getElementsByTagName("DaySlot2");
			        Element daySlot2Element = (Element)daySlot2List.item(0);
			        NodeList textSD2List = daySlot2Element.getChildNodes();
			        //System.out.println("Slot2-Day : " + ((Node)textSD2List.item(0)).getNodeValue().trim());
			         
			        NodeList timeSlot2List = CourseElement.getElementsByTagName("TimeSlot2");
			        Element timeSlot2Element = (Element)timeSlot2List.item(0);
			        NodeList textST2List = timeSlot2Element.getChildNodes();
			        //System.out.println("Slot2-Time : " + ((Node)textST2List.item(0)).getNodeValue().trim());
						        
			        NodeList daySlot3List = CourseElement.getElementsByTagName("DaySlot3");
			        Element daySlot3Element = (Element)daySlot3List.item(0);
			        NodeList textSD3List = daySlot3Element.getChildNodes();
			        //System.out.println("Slot3-Day : " + ((Node)textSD3List.item(0)).getNodeValue().trim());
			         
			        NodeList timeSlot3List = CourseElement.getElementsByTagName("TimeSlot3");
			        Element timeSlot3Element = (Element)timeSlot3List.item(0);
			        NodeList textST3List = timeSlot3Element.getChildNodes();
			        //System.out.println("Slot3-Time : " + ((Node)textST3List.item(0)).getNodeValue().trim());
		 
			        
			        int databasePeriod = Integer.parseInt(((Node)textPERList.item(0)).getNodeValue().trim());
			        databasePeriod--;
			        String databaseDegree = ((Node)textDEPList.item(0)).getNodeValue().trim();

					if (databasePeriod == MyInit.selectedPeriod) {
						if(MyInit.selectedDegree == 4){
							// show all courses
							MyInit.grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
							
							lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
					    	lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim().toString());
					    	lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim().toString());
					    	   
					    	// add the hours to lectureHours arraylist
							lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());    
						   	
					         courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours,((Node)textLECList.item(0)).getNodeValue().trim()));
	
						} else if(MyInit.selectedDegree == 0) {
							if(databaseDegree.equals("BIO") || databaseDegree.equals("L")) {
								
								MyInit.grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
								lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
						    	lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim().toString());
						    	lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim().toString());
						    	   
						    	// add the hours to lectureHours arraylist
								lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim().toString());
								lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim().toString());
								lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());    
							   	
						         courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours,((Node)textLECList.item(0)).getNodeValue().trim()));
							}
						} else if(MyInit.selectedDegree == 1){
							if(databaseDegree.equals("NSS") || databaseDegree.equals("CDS") || 
									databaseDegree.equals("ISS") || databaseDegree.equals("L") || databaseDegree.equals("GD")) {
								MyInit.grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
								lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
						    	lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim());
						    	lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim());
						    	   
						    	// add the hours to lectureHours arraylist
								lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim());
								lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim());
								lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());    
							   	
						         courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours,((Node)textLECList.item(0)).getNodeValue().trim()));
							}
						} else if (MyInit.selectedDegree == 2){
							if(databaseDegree.equals("EC") || databaseDegree.equals("L")) {
								MyInit.grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
								lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
						    	lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim().toString());
						    	lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim().toString());
						    	   
						    	// add the hours to lectureHours arraylist
								lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim().toString());
								lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim().toString());
								lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());    
						   	
					         courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours,((Node)textLECList.item(0)).getNodeValue().trim()));
							}
						} else if(MyInit.selectedDegree == 3){
							if(((Node)textORGList.item(0)).getNodeValue().trim().equals("YES")) {
								MyInit.grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
								
								lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
						    	lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim().toString());
						    	lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim().toString());
						    	   
						    	// add the hours to lectureHours arraylist
								lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim().toString());
								lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim().toString());
								lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());    
							   	
						         courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours,((Node)textLECList.item(0)).getNodeValue().trim()));
							}
						} 
					}
				}
		    }
		} catch (SAXParseException err) {
			System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
			System.out.println(" " + err.getMessage ());
	
		} catch (SAXException e) {
			Exception x = e.getException ();
			((x == null) ? e : x).printStackTrace ();
	
		} catch (Throwable t) {
			t.printStackTrace ();
		}
	}
		
}
