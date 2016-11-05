package com.example.ScheduleMe;

import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class SelectedCourses extends Table {
	
		public SelectedCourses(String caption) {
			
			this.setSelectable(true);
			this.setImmediate(true);
			this.setPageLength(0);
			this.setHeight("100%");					
			//	this.getContainerDataSource().removeAllItems();
			
			//scheduleTable.addContainerProperty("0", String.class, null,"", null, null);
			
			this.addContainerProperty("Course", String.class, null);
			this.setColumnAlignment(0, Align.CENTER);
		//	this.addContainerProperty("Teacher", String.class, null);
		//	this.setColumnAlignment(1, Align.CENTER);
			
			
	       	}
		
		@SuppressWarnings("unchecked")
		public void addRow(int i, String str, String teacher) {
			 this.addItem(new Object[]{str,teacher}, new Integer(i));  
				
			// TODO: mark course as added?
		}
		
		public String getCellValue(int i, String str) {
			return (String)this.getItem(i).getItemProperty(str).getValue();
		}
		
	
	}
			