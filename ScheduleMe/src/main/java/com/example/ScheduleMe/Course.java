package com.example.ScheduleMe;

import java.util.ArrayList;
import com.vaadin.annotations.Theme;


@Theme("mytheme")
public class Course {
	
	private String courseName;
	private ArrayList<String> lecDays;
	private ArrayList<String> lecHours;
	private String teacher;
	private boolean isInTable = false;
	private ArrayList<Integer> hourSavedSlots = new ArrayList<Integer>();
	private ArrayList<String> daySavedSlots = new ArrayList<String>();
	
	public Course(String courseName, ArrayList lecDays, ArrayList lecHours, String teacher) {
		this.courseName = courseName;
		this.lecDays = lecDays;
		this.lecHours = lecHours;
		this.teacher = teacher;
	}
	public Course(String courseName) {
		this.courseName = courseName;

	}
	
	public Course(String courseName, ArrayList lecDays, ArrayList lecHours) {
		this.courseName = courseName;
		this.lecDays = lecDays;
		this.lecHours = lecHours;
	}
///////////////////////////////////////////////////////////////
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setLecturingDays(ArrayList<String> lecDays) {
		this.lecDays = lecDays;
	}
	
	public ArrayList<String> getLecturingDays() {
		return lecDays;
	}
	
	public void setLecturingHours(ArrayList<String> lecHours) {
		this.lecHours = lecHours;
	}
	
	public ArrayList<String> getLecturingHours() {
		return lecHours;
	}
	
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public boolean isAddedToTable() {
		return isInTable;
	}
	
	public void setInTable(boolean b) {
		if (b)
			isInTable = true;
		else
			isInTable = false;
	}
	
	public void savePositionInTable(int i, String str) {
		hourSavedSlots.add(i);
		daySavedSlots.add(str);
	}
	
	public ArrayList<Integer> getHourSavedSlots() {
		return hourSavedSlots;
	}
	public ArrayList<String> getDaySavedSlots() {
		return daySavedSlots;
	}
	
	public void resetCourseStatus() {
		isInTable = false;
		hourSavedSlots.removeAll(hourSavedSlots);
		daySavedSlots.removeAll(daySavedSlots);
	}
}
