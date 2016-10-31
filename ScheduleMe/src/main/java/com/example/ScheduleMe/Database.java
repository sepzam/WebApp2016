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
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;

final class Database extends Window {


//	final static HorizontalLayout courseTable = new HorizontalLayout();
	final static Grid grid = new Grid();
	public static ArrayList<Course> courses = new ArrayList<Course>();
	
	public Database() {

		CheckboxListener checkListener = new CheckboxListener();
		System.out.println("Degree:   "+ MyUI.degree);
		System.out.println("Period: "+ MyUI.per);
		
		
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
			grid.setSelectionMode(SelectionMode.SINGLE);    // Activate single selection mode

			//grid.getColumn("name").setHeaderCaption("Bean name");
			//grid.removeColumn("count");
			//grid.setColumnOrder("name", "amount");

			grid.removeAllColumns();
             
			grid.addColumn("ID").setSortable(true);
		     grid.addColumn("Course Name").setSortable(true);
		     grid.addColumn("Teacher");
		     grid.addColumn("Credits");
 
		     
		     for(int s=0; s<totalCourse ; s++) {
        
				Node CourseNode = listOfCourses.item(s);
				
				if(CourseNode.getNodeType() == Node.ELEMENT_NODE){
					//lectureDays.removeAll(lectureDays);
					//lectureHours.removeAll(lectureHours);
					ArrayList<String> lectureDays = new ArrayList<String>();
					ArrayList<String> lectureHours = new ArrayList<String>();

					
					Element CourseElement = (Element)CourseNode; 
			        /*
			        NodeList organizationList = CourseElement.getElementsByTagName("Organization");
			        Element organizationElement = (Element)organizationList.item(0);
			        NodeList textORGList = organizationElement.getChildNodes();
			        System.out.println("Organization : " + ((Node)textORGList.item(0)).getNodeValue().trim());
			        */
					
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
			
			        NodeList classroomSlot1List = CourseElement.getElementsByTagName("ClassroomSlot1");
			        Element classroomSlot1Element = (Element)classroomSlot1List.item(0);
			        NodeList textSC1List = classroomSlot1Element.getChildNodes();
			        //System.out.println("Slot1-Class : " + ((Node)textSC1List.item(0)).getNodeValue().trim());
			        
			        NodeList daySlot2List = CourseElement.getElementsByTagName("DaySlot2");
			        Element daySlot2Element = (Element)daySlot2List.item(0);
			        NodeList textSD2List = daySlot2Element.getChildNodes();
			        //System.out.println("Slot2-Day : " + ((Node)textSD2List.item(0)).getNodeValue().trim());
			         
			        NodeList timeSlot2List = CourseElement.getElementsByTagName("TimeSlot2");
			        Element timeSlot2Element = (Element)timeSlot2List.item(0);
			        NodeList textST2List = timeSlot2Element.getChildNodes();
			        //System.out.println("Slot2-Time : " + ((Node)textST2List.item(0)).getNodeValue().trim());
			
			        NodeList classroomSlot2List = CourseElement.getElementsByTagName("ClassroomSlot2");
			        Element classroomSlot2Element = (Element)classroomSlot2List.item(0);
			        NodeList textSC2List = classroomSlot2Element.getChildNodes();
			       // System.out.println("Slot2-Class : " + ((Node)textSC2List.item(0)).getNodeValue().trim());
			        
			        NodeList daySlot3List = CourseElement.getElementsByTagName("DaySlot3");
			        Element daySlot3Element = (Element)daySlot3List.item(0);
			        NodeList textSD3List = daySlot3Element.getChildNodes();
			        //System.out.println("Slot3-Day : " + ((Node)textSD3List.item(0)).getNodeValue().trim());
			         
			        NodeList timeSlot3List = CourseElement.getElementsByTagName("TimeSlot3");
			        Element timeSlot3Element = (Element)timeSlot3List.item(0);
			        NodeList textST3List = timeSlot3Element.getChildNodes();
			        //System.out.println("Slot3-Time : " + ((Node)textST3List.item(0)).getNodeValue().trim());
			
			        NodeList classroomSlot3List = CourseElement.getElementsByTagName("ClassroomSlot3");
			        Element classroomSlot3Element = (Element)classroomSlot3List.item(0);
			        NodeList textSC3List = classroomSlot3Element.getChildNodes();
			        //System.out.println("Slot3-Class : " + ((Node)textSC3List.item(0)).getNodeValue().trim());
			         
			        /*
			        NodeList websiteList = CourseElement.getElementsByTagName("Website");
			        Element websiteElement = (Element)websiteList.item(0);
			        NodeList textWSList = websiteElement.getChildNodes();
			        System.out.println("Website : " + ((Node)textWSList.item(0)).getNodeValue().trim());
			        */
			        int intPer=0;
					int P=Integer.parseInt(((Node)textPERList.item(0)).getNodeValue().trim());

					String d= ((Node)textDEPList.item(0)).getNodeValue().trim();
					if(P==2){ intPer=1;} else if (P==1){ intPer=0;}
					
					if (intPer==MyUI.per ){
						if(MyUI.degree==4){
							
							grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
							lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
					    	lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim().toString());
					    	lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim().toString());
					    	   
					    	// add the hours to lectureHours arraylist
							lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());    
						   	
					         courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours,((Node)textLECList.item(0)).getNodeValue().trim()));

						} else if(MyUI.degree==0){

							if(d.equals("BIO") || d.equals("L")){
								
							grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
							lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
					    	lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim().toString());
					    	lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim().toString());
					    	   
					    	// add the hours to lectureHours arraylist
							lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());    
						   	
					         courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours,((Node)textLECList.item(0)).getNodeValue().trim()));
							}
						} else if(MyUI.degree==1){
							if(d.equals("NSS") || d.equals("CDS") || d.equals("ISS") || d.equals("L") || d.equals("GD"))
							{
							grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
							lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
					    	lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim().toString());
					    	lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim().toString());
					    	   
					    	// add the hours to lectureHours arraylist
							lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());    
						   	
					         courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours,((Node)textLECList.item(0)).getNodeValue().trim()));
							}
						} else if(MyUI.degree==2){
							if(d.equals("EC") || d.equals("L"))
							{
							grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
							lectureDays.add(((Node)textSD1List.item(0)).getNodeValue().trim());
					    	lectureDays.add(((Node)textSD2List.item(0)).getNodeValue().trim().toString());
					    	lectureDays.add(((Node)textSD3List.item(0)).getNodeValue().trim().toString());
					    	   
					    	// add the hours to lectureHours arraylist
							lectureHours.add(((Node)textST1List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST2List.item(0)).getNodeValue().trim().toString());
							lectureHours.add(((Node)textST3List.item(0)).getNodeValue().trim().toString());    
						   	
					         courses.add(new Course(((Node)textCNList.item(0)).getNodeValue().trim(), lectureDays, lectureHours,((Node)textLECList.item(0)).getNodeValue().trim()));
							}
						} else if(MyUI.degree==3){
							if(((Node)textORGList.item(0)).getNodeValue().trim().equals("YES"))
							{
							grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
							
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
						
						  
					
					// add the days to lectureDays arraylist
										} 
					
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

	}  
    
    class CheckboxListener implements SelectionListener {

		@Override
		public void select(SelectionEvent event) {
			AddWindow notifWindow = new AddWindow();
			
			boolean empty = event.getSelected().isEmpty();
			if (!empty) {
				int courseIndex = (int) grid.getSelectedRow() - 1;
				
				String name = courses.get(courseIndex).getCourseName();
				String teacher= courses.get(courseIndex).getTeacher();
				ArrayList<String> lecDays = courses.get(courseIndex).getLecturingDays();
				ArrayList<String> lecHours = courses.get(courseIndex).getLecturingHours();
				
				System.out.println(name + lecDays + lecHours);
				
				for (int i = 0; i < lecHours.size(); i++){
					for (int j = 0; j < 6; j++) {
						if (!lecHours.get(i).equals("Empty")) {
							if (lecHours.get(i).equals(MyUI.hours[j])) {
								System.out.println("debug: " + lecDays.get(i) + ": " + MyUI.scheduleTable.getItem(j).getItemProperty(lecDays.get(i)).getValue() );
								if (MyUI.scheduleTable.getItem(j).getItemProperty(lecDays.get(i)).getValue().toString().equals(" ")) {
									System.out.println("Cell is empty");
									MyUI.scheduleTable.getItem(j).getItemProperty(lecDays.get(i)).setValue(name);
									MyUI.selectedCourses.addItem(new Object[]{name,teacher}, new Integer(MyUI.count)); 
									MyUI.count++;
								
								}
								else {	// TODO: Bug: it needs to check all cells to be taken by the selected course, if they are empty or not. not 1 by 1
									System.out.println("cell is taken!");
									// popup notification
									
									Label label = new Label("You have another course at the same time."+"");
									FormLayout formL = new FormLayout();
									
									formL.addComponent(label);
													
									notifWindow.setCaption("Course conflict");
									notifWindow.setContent(formL);
									
					  				if (notifWindow.isAttached()) {
					  					notifWindow.close();
					  				} else {
					  					UI.getCurrent().addWindow(notifWindow);
					  				}
								}
								//if (MyUI.scheduleTable.getItem(j).getItemProperty(lecDays.get(i)))
								
							}
						}
					}
				}
			}
				
				
		}
			
	}
    	
}


