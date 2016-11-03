package com.example.ScheduleMe;

import java.util.ArrayList;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
@Theme("mytheme")
public class AddWindow extends Window {
	
	Label label = new Label("this is a test!");
    String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    String[] hours = new String[] { "8-10", "10-12", "12-14", "14-16", "16-18", "18-20"};
    static ArrayList<String> selDays = new ArrayList<String>();	// selected days for a course

    FormLayout daysForm = new FormLayout();
    
	public AddWindow() {
		this.setModal(true);
		this.setImmediate(true);
		this.center();
		this.setWidth(300, Unit.PIXELS);
	}
	
	public FormLayout addDaysForm() {
		selDays.removeAll(selDays);
		
		HorizontalLayout daysForm = new HorizontalLayout();
		
		final FormLayout layout = new FormLayout();
		
		layout.setSizeUndefined();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		layout.addComponent(new Label("\n"  + "Tick the days of the course:"));
	    //	final int i;
		  for (int i = 0; i < 5; i++) {	
	        	CheckBox checkbox = new CheckBox (days[i], false);
	        	checkbox.setValue(false);
	        	checkbox.setImmediate(false);
	        	layout.addComponent(checkbox);    	
	        	checkbox.setValue(false);
	        	checkbox.addValueChangeListener(e -> {	
	        		if (checkbox.getValue() == true) {
	            		if (!selDays.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
	    	       			selDays.add(checkbox.getCaption());
	    	       			System.out.println(selDays);		
	    	        	}
	            		System.out.println(selDays);		
	        		}
	        	});
	        	checkbox.setValue(false);
		  }
		  daysForm.addComponent(layout);
        		
        return layout;
        }
	
	public FormLayout addHoursForm() {	
		HorizontalLayout hor = new HorizontalLayout();
		FormLayout form = new FormLayout();

		hor.setSizeUndefined();
		hor.setMargin(true);
		hor.setSpacing(true);
		
		form.setSizeUndefined();
		form.setMargin(true);
		form.setSpacing(true);
		
		form.addComponent(new Label ("Tick the hours you have the course for each day:"));
		for (String day : selDays) {
		     
			FormLayout layout = new FormLayout();
			layout.addComponent(new Label(day));
			for (int i = 0; i < 6; i++) {
	        	CheckBox checkbox = new CheckBox (hours[i], false);
	        	layout.addComponent(checkbox);
			
	        	checkbox.addValueChangeListener(e -> {	
		        	if (checkbox.getValue() == true) {
		        		
		        	}
	        	});
			}
			hor.addComponent(layout);
		}
		form.addComponent(hor);
		return form;
	}
		
}