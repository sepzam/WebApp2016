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
		
        for (int i = 0; i < 5; i++) {	
        	CheckBox checkbox = new CheckBox (days[i], false);
        	layout.addComponent(checkbox);
        	checkbox.addValueChangeListener(e -> {	
        		if (checkbox.getValue() == true) {
        			if (!selDays.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
        				selDays.add(checkbox.getCaption());
        				System.out.println(selDays);
        				//layout.addComponent(new Label("" + checkbox.getCaption()));  // code to verify if they are all added
        				//hourSelect.addComponent(new Label(selDays.toString()));			
        			}
        		}
        	});
        }       
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
	        }
	        hor.addComponent(layout);
		}
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