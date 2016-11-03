package com.example.ScheduleMe;

import java.util.ArrayList;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class CourseGrid extends Grid {

	
	public CourseGrid() {
		CheckboxListener checkListener = new CheckboxListener();
		this.addSelectionListener(checkListener);
		//this.addSelectionListener(new CheckboxListener());
		this.setSizeFull();
		this.setSelectionMode(SelectionMode.SINGLE);    // Activate single selection mode
		this.removeAllColumns();
		this.setImmediate(true);
		this.addColumn("ID").setSortable(true);
	    this.addColumn("Course Name").setSortable(true);
	    this.addColumn("Teacher");
	    this.addColumn("Credits");
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
				int courseIndex = (int) MyInit.grid.getSelectedRow() - 1;

				
				Course course = Database2.courses.get(courseIndex);
				String name = Database2.courses.get(courseIndex).getCourseName();
				String teacher= Database2.courses.get(courseIndex).getTeacher();
				ArrayList<String> lecDays = Database2.courses.get(courseIndex).getLecturingDays();
				ArrayList<String> lecHours = Database2.courses.get(courseIndex).getLecturingHours();
				
				System.out.println(name + lecDays + lecHours);
				
				// remove the "Empty" slots :D
				for (int i = lecHours.size()-1; i >= 0; i--) {
					if (lecHours.get(i).equals("Empty")) {
						lecHours.remove(i);
						lecDays.remove(i);
						// TODO: remove classrooms too if we use those too
					}
				}			

				//////////////////////////////// IF COURSE NOT IN THE TABLE ///////////////////////////////////////////////
				if (!course.isAddedToTable()) {		// if the course is not in the table, check for free slots
					System.out.println("Course not yet in the table.");
					for (int i = 0; i < lecHours.size(); i++){		// lecHours max: 3
						for (int j = 0; j < 6; j++) {
							if (lecHours.get(i).equals(MyInit.hours[j])) {		// if the hours match
								if (MyInit.scheduleTable.cellIsEmpty(j, lecDays.get(i))) {
									System.out.println("Debug: (No Conflict found) Nothing scheduled for " + lecDays.get(i)+ " yet.");
								}
								else {
									System.out.println("Debug: Found a conflict: " + lecDays.get(i) + ": " + MyInit.scheduleTable.getCellValue(j, lecDays.get(i)));
									tempo++;
									Label l = new Label("On " + lecDays.get(i) + " at " + lecHours.get(i) + " you have " + MyInit.scheduleTable.getCellValue(j, lecDays.get(i)));
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
								}
							}
							
						}
						int added=0;
						for(int i=0; i<MyInit.count; i++)
						  if(MyInit.selectedCourses.getItem(i).getItemProperty("Course Name").getValue()==name)
							   added = 1; //Already added!
						if(added==0){   
							MyInit.selectedCourses.addItem(new Object[]{name,teacher}, new Integer(MyInit.count)); 
							MyInit.count++;
							}
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
				} else { 	//course is in the table, so we want to remove it
					System.out.println("Course already in the table! Deleting...");
					MyInit.scheduleTable.deleteFromCell(course);
					course.setInTable(false);
					
					for(int i=0; i<MyInit.count; i++)
					  if(MyInit.selectedCourses.getItem(i).getItemProperty("Course Name").getValue()==name){
						  MyInit.selectedCourses.removeItem(i);
						   break;
					  }
						
					
				}
			}				
		}
    }
}
