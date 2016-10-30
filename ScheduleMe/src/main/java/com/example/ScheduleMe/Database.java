package com.example.ScheduleMe;


import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;

public class Database extends Window {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static HorizontalLayout courseTable = new HorizontalLayout();
	final static Grid grid = new Grid();
	public static ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<String> lectureDays = new ArrayList<String>();
	private ArrayList<String> lectureHours = new ArrayList<String>();
	
	public Database() {

		CheckboxListener checkListener = new CheckboxListener();

		try {
	 
	

    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(new File("final.xml"));
    
    NodeList listOfCourses = doc.getElementsByTagName("record");
    int totalCourse = listOfCourses.getLength(); 
    System.out.println("Total no of courses : " + totalCourse); //silebilirsin

  //  System.out.println();
    
    
    grid.setSizeFull();
    grid.addSelectionListener(checkListener);
    //grid.removeColumn("changed");   // Hide the "changed" property
    grid.setSelectionMode(SelectionMode.MULTI);    // Activate single selection mode

//      grid.getColumn("name").setHeaderCaption("Bean name");
//      grid.removeColumn("count");
//     grid.setColumnOrder("name", "amount");
     grid.addColumn("ID").setSortable(true);
     grid.addColumn("Course Name").setSortable(true);
     grid.addColumn("Teacher");
     grid.addColumn("Location");
 

        for(int s=0; s<totalCourse ; s++){
        
	        Node CourseNode = listOfCourses.item(s);
	        if(CourseNode.getNodeType() == Node.ELEMENT_NODE){
	
		        Element CourseElement = (Element)CourseNode; 
		
		        NodeList organizationList = CourseElement.getElementsByTagName("Organization");
		        Element organizationElement = (Element)organizationList.item(0);
		        NodeList textORGList = organizationElement.getChildNodes();
		        System.out.println("Organization : " + ((Node)textORGList.item(0)).getNodeValue().trim());
		        
		        
		        NodeList departmentList = CourseElement.getElementsByTagName("Department");
		        Element departmentElement = (Element)departmentList.item(0);
		        NodeList textDEPList = departmentElement.getChildNodes();
		        System.out.println("Department : " + ((Node)textDEPList.item(0)).getNodeValue().trim());
		        
		        NodeList courseCodeList = CourseElement.getElementsByTagName("CourseCode");
		        Element courseCodeElement = (Element)courseCodeList.item(0);
		        NodeList textCCList = courseCodeElement.getChildNodes();
		        System.out.println("Course Code : " + ((Node)textCCList.item(0)).getNodeValue().trim());
		         
		        NodeList courseNameList = CourseElement.getElementsByTagName("CourseName");
		        Element courseNameElement = (Element)courseNameList.item(0);
		        NodeList textCNList = courseNameElement.getChildNodes();
		        System.out.println("Course Name : " + ((Node)textCNList.item(0)).getNodeValue().trim());
		        
		        NodeList lecturerList = CourseElement.getElementsByTagName("Lecturer");
		        Element lecturerElement = (Element)lecturerList.item(0);
		        NodeList textLECList = lecturerElement.getChildNodes();
		        System.out.println("Lecturer : " + ((Node)textLECList.item(0)).getNodeValue().trim());
		
		        NodeList creditList = CourseElement.getElementsByTagName("Credit");
		        Element creditElement = (Element)creditList.item(0);
		        NodeList textCREList = creditElement.getChildNodes();
		        System.out.println("Credit : " + ((Node)textCREList.item(0)).getNodeValue().trim());
		        
		        NodeList periodList = CourseElement.getElementsByTagName("Period");
		        Element periodElement = (Element)periodList.item(0);
		        NodeList textPERList = periodElement.getChildNodes();
		        System.out.println("Period : " + ((Node)textPERList.item(0)).getNodeValue().trim());
		         
		   
		        NodeList daySlot1List = CourseElement.getElementsByTagName("DaySlot1");
		        Element daySlot1Element = (Element)daySlot1List.item(0);
		        NodeList textSD1List = daySlot1Element.getChildNodes();
		        System.out.println("Slot1-Day : " + ((Node)textSD1List.item(0)).getNodeValue().trim());
		         
		        NodeList timeSlot1List = CourseElement.getElementsByTagName("TimeSlot1");
		        Element timeSlot1Element = (Element)timeSlot1List.item(0);
		        NodeList textST1List = timeSlot1Element.getChildNodes();
		        System.out.println("Slot1-Time : " + ((Node)textST1List.item(0)).getNodeValue().trim());
		
		        NodeList classroomSlot1List = CourseElement.getElementsByTagName("ClassroomSlot1");
		        Element classroomSlot1Element = (Element)classroomSlot1List.item(0);
		        NodeList textSC1List = classroomSlot1Element.getChildNodes();
		        System.out.println("Slot1-Class : " + ((Node)textSC1List.item(0)).getNodeValue().trim());
		        
		        NodeList daySlot2List = CourseElement.getElementsByTagName("DaySlot2");
		        Element daySlot2Element = (Element)daySlot2List.item(0);
		        NodeList textSD2List = daySlot2Element.getChildNodes();
		        System.out.println("Slot2-Day : " + ((Node)textSD2List.item(0)).getNodeValue().trim());
		         
		        NodeList timeSlot2List = CourseElement.getElementsByTagName("TimeSlot2");
		        Element timeSlot2Element = (Element)timeSlot2List.item(0);
		        NodeList textST2List = timeSlot2Element.getChildNodes();
		        System.out.println("Slot2-Time : " + ((Node)textST2List.item(0)).getNodeValue().trim());
		
		        NodeList classroomSlot2List = CourseElement.getElementsByTagName("ClassroomSlot2");
		        Element classroomSlot2Element = (Element)classroomSlot2List.item(0);
		        NodeList textSC2List = classroomSlot2Element.getChildNodes();
		        System.out.println("Slot2-Class : " + ((Node)textSC2List.item(0)).getNodeValue().trim());
		        
		        NodeList daySlot3List = CourseElement.getElementsByTagName("DaySlot3");
		        Element daySlot3Element = (Element)daySlot3List.item(0);
		        NodeList textSD3List = daySlot3Element.getChildNodes();
		        System.out.println("Slot3-Day : " + ((Node)textSD3List.item(0)).getNodeValue().trim());
		         
		        NodeList timeSlot3List = CourseElement.getElementsByTagName("TimeSlot3");
		        Element timeSlot3Element = (Element)timeSlot3List.item(0);
		        NodeList textST3List = timeSlot3Element.getChildNodes();
		        System.out.println("Slot3-Time : " + ((Node)textST3List.item(0)).getNodeValue().trim());
		
		        NodeList classroomSlot3List = CourseElement.getElementsByTagName("ClassroomSlot3");
		        Element classroomSlot3Element = (Element)classroomSlot3List.item(0);
		        NodeList textSC3List = classroomSlot3Element.getChildNodes();
		        System.out.println("Slot3-Class : " + ((Node)textSC3List.item(0)).getNodeValue().trim());
		                
		        NodeList websiteList = CourseElement.getElementsByTagName("Website");
		        Element websiteElement = (Element)websiteList.item(0);
		        NodeList textWSList = websiteElement.getChildNodes();
		        System.out.println("Website : " + ((Node)textWSList.item(0)).getNodeValue().trim());
		       
		       grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textDEPList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
		       
		       lectureDays.removeAll(lectureDays);
		       //if (((Node)textSD1List.item(0)).getNodeValue().trim().toString() != "Empty") 
		    	   lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
		       //if (((Node)textSD2List.item(0)).getNodeValue().trim().toString() != "Empty") 
		    	   lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim().toString());
		       //if (((Node)textSD3List.item(0)).getNodeValue().trim().toString() != "Empty") 
		    	   lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim().toString());
		       
		    for (int i = 0; i < lectureDays.size(); i++) {
		    	if (lectureDays.get(i).equals("Empty"))
			    	lectureDays.remove(i);
		    }
		    	   
		       lectureHours.removeAll(lectureHours);
		       lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim().toString());
		       lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim().toString());
		       lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());
		       
		       courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours));
		       
	        }
	   }
       
       
   //     courseTable.addComponent(grid);
        

}catch (SAXParseException err) {
System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
System.out.println(" " + err.getMessage ());

}catch (SAXException e) {
Exception x = e.getException ();
((x == null) ? e : x).printStackTrace ();

}catch (Throwable t) {
t.printStackTrace ();
}	
		for (int i = 0; i < courses.size(); i++) {
			System.out.println(courses.get(i).getCourseName() + courses.get(i).getLecturingDays().toString() + courses.get(i).getLecturingHours().toString());
			
		}
	}  
    
    class CheckboxListener implements SelectionListener {

		@Override
		public void select(SelectionEvent event) {
			boolean empty = event.getSelected().isEmpty();
			if (!empty) {
				
			}
			
		}
    	
    }
}

