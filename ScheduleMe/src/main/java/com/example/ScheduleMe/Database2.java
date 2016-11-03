package com.example.ScheduleMe;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;

@SuppressWarnings("serial")
final class Database2 extends Window {


//	final static HorizontalLayout courseTable = new HorizontalLayout();
	//final static Grid grid = new Grid();
	//final static Grid grid = new Grid();
	public static ArrayList<Course> courses = new ArrayList<Course>();
	//static int intPer=0;
	//static Integer tempo=1;

	//private Table scheduleTable;
	
	static CourseGrid grid = new CourseGrid();
	public Database2() {
	
	//CheckboxListener checkListener = new CheckboxListener();
	System.out.println("Degree:   "+ MyInit.selectedDegree);
	System.out.println("Period: "+ MyInit.selectedPeriod);
	
  	grid.getContainerDataSource().removeAllItems();

	try {

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new File("final.xml"));
				    
		NodeList listOfCourses = doc.getElementsByTagName("record");
		int totalCourse = listOfCourses.getLength(); 
		System.out.println("Total no of courses : " + totalCourse); //silebilirsin
	/*	
		grid.setSizeFull();
		grid.addSelectionListener(checkListener);
		grid.setSelectionMode(SelectionMode.SINGLE);    // Activate single selection mode
		grid.removeAllColumns();
		grid.setImmediate(true);
		grid.addColumn("ID").setSortable(true);
	    grid.addColumn("Course Name").setSortable(true);
	    grid.addColumn("Teacher");
	    grid.addColumn("Credits");
*/
	    for(int s=0; s<totalCourse ; s++) {
	    	Node CourseNode = listOfCourses.item(s);
			
			if(CourseNode.getNodeType() == Node.ELEMENT_NODE){
				ArrayList<String> lectureDays = new ArrayList<String>();
				ArrayList<String> lectureHours = new ArrayList<String>();	
				
				//lectureDays.removeAll(lectureDays);
				//lectureHours.removeAll(lectureHours);
				
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
		         
		        
		        int databasePeriod = Integer.parseInt(((Node)textPERList.item(0)).getNodeValue().trim());
		        databasePeriod--;
		        String databaseDegree = ((Node)textDEPList.item(0)).getNodeValue().trim();

				//System.out.println(P+"   "+intPer);
				if (databasePeriod == MyInit.selectedPeriod) {
					if(MyInit.selectedDegree == 4){
						// show all courses
						grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
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
					} else if(MyInit.selectedDegree == 1){
						if(databaseDegree.equals("NSS") || databaseDegree.equals("CDS") || 
								databaseDegree.equals("ISS") || databaseDegree.equals("L") || databaseDegree.equals("GD")) {
							grid.addRow(((Node)textCCList.item(0)).getNodeValue().trim(),((Node)textCNList.item(0)).getNodeValue().trim(),((Node)textLECList.item(0)).getNodeValue().trim(), ((Node)textCREList.item(0)).getNodeValue().trim()); // Just to test the apperance in Grid!
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
					} else if(MyInit.selectedDegree == 3){
						if(((Node)textORGList.item(0)).getNodeValue().trim().equals("YES")) {
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
	
	
    class CheckboxListener implements SelectionListener {
    	
		@Override
		public void select(SelectionEvent event) {
			AddWindow notifWindow = new AddWindow();
			System.out.println("I CLICKED!!");
			boolean empty = event.getSelected().isEmpty();
			int tempo = 0;
			VerticalLayout conflictedCourses = new VerticalLayout();

			
			if (!empty) {
				int courseIndex = (int) grid.getSelectedRow() - 1;

				Course course = courses.get(courseIndex);
				String name = courses.get(courseIndex).getCourseName();
				String teacher= courses.get(courseIndex).getTeacher();
				ArrayList<String> lecDays = courses.get(courseIndex).getLecturingDays();
				ArrayList<String> lecHours = courses.get(courseIndex).getLecturingHours();
				
				System.out.println(name + lecDays + lecHours);
				
				// remove the "Empty" slots :D
				for (int i = lecHours.size()-1; i >= 0; i--) {
					if (lecHours.get(i).equals("Empty")) {
						lecHours.remove(i);
						lecDays.remove(i);
						// TODO: remove classrooms too if we use those too
					}
				}			
				
				//System.out.println("New, without empty: " + name + lecDays + lecHours);

				//////////////////////////////// IF COURSE NOT IN THE TABLE ///////////////////////////////////////////////
				if (!course.isAddedToTable()) {		// if the course is not in the table, check for free slots
					System.out.println("Course not yet in the table.");
					for (int i = 0; i < lecHours.size(); i++){		// lecHours max: 3
						for (int j = 0; j < 6; j++) {
							if (lecHours.get(i).equals(MyInit.hours[j])) {		// if the hours match
								if (MyInit.scheduleTable.cellIsEmpty(j, lecDays.get(i))) {
									//System.out.println("(No Conflict found)");
									System.out.println("Debug: (No Conflict found) Nothing scheduled for " + lecDays.get(i)+ " yet.");
								}
								else {
									System.out.println("Debug: Found a conflict: " + lecDays.get(i) + ": " + MyInit.scheduleTable.getCellValue(j, lecDays.get(i)));
									tempo++;
									Label l = new Label("On " + lecDays.get(i) + " you have " + MyInit.scheduleTable.getCellValue(j, lecDays.get(i)));
									conflictedCourses.addComponent(l);
								}
							}
						}
						
					} //////////////////////////////// Add course to the table ///////////////////////////////////////////////
					if(tempo==0){			// add the course to the table
						for (int i = 0; i < lecHours.size(); i++){
							for (int j = 0; j < 6; j++) {
								if (lecHours.get(i).equals(MyInit.hours[j])) {
									//System.out.println("debug: " + lecDays.get(i) + ": " + MyUI.scheduleTable.getItem(j).getItemProperty(lecDays.get(i)).getValue() );
									if (MyInit.scheduleTable.cellIsEmpty(j, lecDays.get(i))) {
										System.out.println("Cell is empty");
										MyInit.scheduleTable.addToCell(j, lecDays.get(i), name);
										System.out.print("Added: hour: " + j + ", day: " + lecDays.get(i) + " course: " + name +"\n");
										course.savePositionInTable(j, lecDays.get(i));
										course.setInTable(true);
										
									}
										
										//if (MyUI.scheduleTable.getItem(j).getItemProperty(lecDays.get(i)))
										
								}
							}
						}
						MyInit.selectedCourses.addItem(new Object[]{name,teacher}, new Integer(MyInit.count)); 		
					} //////////////////////////////// cell is full, give popup ///////////////////////////////////////////////
					else {	
						System.out.println("cell is taken!");
						
						// popup notification
						Label label;
						
						if (tempo == 1) {
							label = new Label("You have another course at the same time:");
						} else {
							label = new Label("You have other courses at the same time:");
						}
						
						FormLayout formL = new FormLayout();
					
						conflictedCourses.setMargin(true);
						
						formL.addComponent(label);
						formL.addComponent(conflictedCourses);	
						
						notifWindow.setCaption("Course conflict");
						notifWindow.setSizeUndefined();
						notifWindow.setContent(formL);
						
						
		  				if (notifWindow.isAttached()) {
		  					notifWindow.close();
		  				} else {  
		  					UI.getCurrent().addWindow(notifWindow);
		  				}
					}
				} else {		// TODO: course is in the table, so we want to remove it
					System.out.println("Course already in the table! Deleting...");
					MyInit.scheduleTable.deleteFromCell(course);
					course.setInTable(false);
				}
			}				
		}
    }
}
