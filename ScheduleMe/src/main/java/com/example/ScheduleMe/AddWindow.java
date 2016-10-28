package com.example.ScheduleMe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
@Theme("mytheme")
public class AddWindow extends Window {
	
	Label label = new Label("this is a test!");
    String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    String[] hours = new String[] { "8-10", "10-12", "12-14", "14-16", "16-18", "18-20"};
    static ArrayList<String> selDays = new ArrayList<String>();	// selected days for a course
    static ArrayList<String> moHo = new ArrayList<String>();	// selected hours in Monday
    static ArrayList<String> tuHo = new ArrayList<String>();	// selected hours in Tuesday
    static ArrayList<String> weHo = new ArrayList<String>();	// selected hours in Wednesday
    static ArrayList<String> thHo = new ArrayList<String>();	// selected hours in Thursday
    static ArrayList<String> frHo = new ArrayList<String>();	// selected hours in Friday

    FormLayout daysForm = new FormLayout();
    //HoursWindow hoursWin = new HoursWindow();
    
	public AddWindow() {
		this.setModal(true);
		this.setImmediate(true);
		this.center();
		this.setWidth(300, Unit.PIXELS);
	}
	
	public FormLayout addDaysForm() {
		selDays.removeAll(selDays);
		
		
		FormLayout layout = new FormLayout();
		
		layout.setSizeUndefined();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		layout.addComponent(new Label("\n"  + "Tick the days of the course:"));
	    //	final int i;
		  for (int i = 0; i < 5; i++) {	
	        	CheckBox checkbox = new CheckBox (days[i], false);
	        	layout.addComponent(checkbox);
	        	checkbox.addValueChangeListener(e -> {	 //TODO: Error! If we uncheck a day it will not be unchecked now !
	        		if (checkbox.getValue() == true) {

	            				if (!selDays.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
	    	        				selDays.add(checkbox.getCaption());
	    	        				System.out.println(selDays);
	    	        				//layout.addComponent(new Label("" + checkbox.getCaption()));  // code to verify if they are all added
	    	        				//hourSelect.addComponent(new Label(selDays.toString()));			
	    	        			}
	            				System.out.println(selDays);
	        		}
	        	});
		  }
        		
        return layout;
        }
	
	public FormLayout addHoursForm() {	
		HorizontalLayout hor = new HorizontalLayout();
		FormLayout form = new FormLayout();
		//MyUI updateTable = new MyUI();	
		
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
			
			
			if (day=="Monday"){
				
				for (int i = 0; i < 6; i++) {
		        	CheckBox checkbox = new CheckBox (hours[i], false);
		        	
		        	layout.addComponent(checkbox);
		        	checkbox.addValueChangeListener(e -> {	
		        	if (checkbox.getValue() == true) {
	        			if (!moHo.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
	        				moHo.add(checkbox.getCaption());
	        				//layout.addComponent(new Label("" + checkbox.getCaption()));  // code to verify if they are all added
	        				//hourSelect.addComponent(new Label(selDays.toString()));			
	        			}
	        			System.out.println(moHo);
		        	} });

				}
			} 
			if (day=="Tuesday"){
				for (int i = 0; i < 6; i++) {
		        	CheckBox checkbox = new CheckBox (hours[i], false);
		        	
		        	layout.addComponent(checkbox);
		        	checkbox.addValueChangeListener(e -> {	
		        	if (checkbox.getValue() == true) {
	        			if (!tuHo.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
	        				tuHo.add(checkbox.getCaption());
	        				//layout.addComponent(new Label("" + checkbox.getCaption()));  // code to verify if they are all added
	        				//hourSelect.addComponent(new Label(selDays.toString()));			
	        			}
			
		        	} });
		        }
			}
			if (day=="Wednesday"){
				for (int i = 0; i < 6; i++) {
		        	CheckBox checkbox = new CheckBox (hours[i], false);
		        	
		        	layout.addComponent(checkbox);
		        	checkbox.addValueChangeListener(e -> {	
		        	if (checkbox.getValue() == true) {
	        			if (!weHo.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
	        				weHo.add(checkbox.getCaption());
	        				//layout.addComponent(new Label("" + checkbox.getCaption()));  // code to verify if they are all added
	        				//hourSelect.addComponent(new Label(selDays.toString()));			
	        			}
			
		        	} });
		        }
				
			}
			if (day=="Thursday"){	
				for (int i = 0; i < 6; i++) {
		        	CheckBox checkbox = new CheckBox (hours[i], false);
		        	
		        	layout.addComponent(checkbox);
		        	checkbox.addValueChangeListener(e -> {	
		        	if (checkbox.getValue() == true) {
	        			if (!thHo.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
	        				thHo.add(checkbox.getCaption());
	        				//layout.addComponent(new Label("" + checkbox.getCaption()));  // code to verify if they are all added
	        				//hourSelect.addComponent(new Label(selDays.toString()));			
	        			}		 
			
		        	} });
		        }
			}
			if (day=="Friday"){
				for (int i = 0; i < 6; i++) {
		        	CheckBox checkbox = new CheckBox (hours[i], false);
		        	
		        	layout.addComponent(checkbox);
		        	checkbox.addValueChangeListener(e -> {	
		        	if (checkbox.getValue() == true) {
	        			if (!frHo.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
	        				frHo.add(checkbox.getCaption());
	        				//layout.addComponent(new Label("" + checkbox.getCaption()));  // code to verify if they are all added
	        				//hourSelect.addComponent(new Label(selDays.toString()));			
	        			}
			
		        	} });
		        }
			}
	        
	        hor.addComponent(layout);
		}
		//selDays.removeAll(selDays);
		form.addComponent(hor);
		showArrayList();
		return form;
	}
	
	public void showArrayList() {
		for (int i = 0; i < selDays.size(); i++) {
			System.out.println(selDays.get(i));
		}
	}
}