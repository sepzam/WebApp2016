package com.example.ScheduleMe;

import com.vaadin.ui.Table;


@SuppressWarnings("serial")
public class ScheduleTable extends Table {

	//private Table table = new Table();
	private String[] listOfDays = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	private String[] listOfHours = new String[] { "8-10", "10-12", "12-14", "14-16", "16-18", "18-20"};

	public ScheduleTable(String caption) {

		this.setCaption(caption);
        this.addStyleName("Schedule");
        this.setSizeFull();
        this.setPageLength(0);
     //   scheduleTable.setColumnHeaders();
        this.setHeight("100%");
        this.setColumnCollapsingAllowed(false);
        this.addContainerProperty("", String.class, null);
    	this.getContainerDataSource().removeAllItems();
    	
		
        for (int i = 0; i < 5; i++) {		// set the headers
        	this.addContainerProperty(listOfDays[i], String.class, null);
        	this.setColumnAlignment(i, Align.CENTER);
        }
       
        for (int i=0; i<6; i++)
        		 this.addItem(new Object[]{ listOfHours[i], "", "", "", "", ""}, new Integer(i));
	}
	
	@SuppressWarnings("unchecked")
	public void addToCell(int i, String str, String value) {
		this.getItem(i).getItemProperty(str).setValue(value);
		// TODO: mark course as added?
	}
	
	@SuppressWarnings("unchecked")
	public void clearFromCell(int i, int j) {
		this.getItem(i).getItemProperty(j).setValue("");
		// TODO: mark course as removed?
	}
	
	public String getCellValue(int i, String str) {
		return (String)this.getItem(i).getItemProperty(str).getValue();
	}
	public boolean cellIsEmpty(int i, String str) {
		if (this.getItem(i).getItemProperty(str).getValue().equals(" ") 
				|| this.getItem(i).getItemProperty(str).getValue().equals("")
				|| this.getItem(i).getItemProperty(str).getValue().equals(null))
			return true;
		else 
			return false;	
	}
	
	public void resetSchedule() {
		this.removeAllItems();
        for (int i = 0; i < 5; i++) {		// set the headers
        	this.addContainerProperty(listOfDays[i], String.class, null);
        	this.setColumnAlignment(i, Align.CENTER);
        }
       
        for (int i=0; i<6; i++)
        		 this.addItem(new Object[]{ listOfHours[i], "", "", "", "", ""}, new Integer(i));
	}
	

	
}
	