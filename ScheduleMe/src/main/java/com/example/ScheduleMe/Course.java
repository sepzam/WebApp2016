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
public class Course {
	
	private String courseName;
	private ArrayList teachingSchedule;
	private ArrayList lecDays;
	private ArrayList lecHours;
	
	public Course(String courseName) {
		this.courseName = courseName;
	}
	
	public Course(String courseName, ArrayList teachingSchedule) {
		this.courseName = courseName;
		this.teachingSchedule = teachingSchedule;
	}
	
///////////////////////////////////////////////////////////////
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void  setLecturingDays(ArrayList lecDays) {
		this.lecDays = lecDays;
	}
	
	public ArrayList  getLecturingDays() {
		return lecDays;
	}
	
	public void setLecturingHours(ArrayList lecHours) {
		this.lecHours = lecHours;
	}
	
	public ArrayList getLecturingHours() {
		return lecHours;
	}
	
	public void addCourseTime(String name, String day, String time) {
		
	}

}
