package com.example.ScheduleMe;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("mytheme")
final public class MyInit extends UI {

    final VerticalLayout layout = new VerticalLayout();
	private VerticalLayout main = MainLayout();
	private VerticalLayout selection = SelectionLayout();
	private VerticalLayout courseSelect = CourseSelectLayout();
	
	static int selectedDegree = 4;
	static int selectedPeriod = 0; 		// the default value is Period 1 (0), therefore if no change is made, the selected period should be 0.
	ArrayList<String> degreeNames;
	boolean isBackBtnUsed = false;

	static ScheduleTable scheduleTable;
	static SelectedCourses selectedCourses;
	
	static String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	static String[] hours = new String[] { "8-10", "10-12", "12-14", "14-16", "16-18", "18-20"};

	static Database2 db;
	static CourseGrid grid;
	static ArrayList<Course> temporaryCourses = new ArrayList<Course>();
	//Grid coursesGrid = new Grid();
	
    ArrayList<String> selDays = new ArrayList<String>();	// selected days for a course
    ArrayList<String> lecDays = new ArrayList<String>();
    ArrayList<String> lecHours = new ArrayList<String>();	
   
    
	@Override
	protected void init(VaadinRequest request) {
        layout.addComponents(main, selection);  
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
	}
	
	   public VerticalLayout SelectionLayout() {	//---- page1 ----\\
	    	
	    	VerticalLayout l1 = new VerticalLayout();
	    	VerticalLayout l2 = new VerticalLayout();

	        NativeSelect degreeSelection = new NativeSelect("Select your degree:");
	        OptionGroup periodOption = new OptionGroup("Select period:");
	    	Button buttonNext;
	    	
	        
	    	//addDegreeNames();      
	    	degreeNames = new ArrayList<String>();
	    	degreeNames.add("Master's Degree Programme in Bioinformatics");
	        degreeNames.add("Master's Degree Programme in Information Security and Cryptography");
	        degreeNames.add("Master's Degree Programme in Embedded Computing");
	        degreeNames.add("Show only the TUCS courses");
	        degreeNames.add("Show all courses");
	        
	        for (int i = 0; i < 5; i++) {
	        	degreeSelection.addItem(i);
	        	degreeSelection.setItemCaption(i, degreeNames.get(i));
	        	
	        	
	        }  
	        degreeSelection.addValueChangeListener(e -> {
	              selectedDegree = (int)e.getProperty().getValue();
	         });
	       
	   
	    	// setup the period selection:
	        for (int j = 0; j < 2; j++) {
	        	periodOption.addItem(j);
	        	int x=j+1;		// don't ask, lol :/
	        	periodOption.setItemCaption(j, "Period " + x);	        	
	        }
	        periodOption.setValue(0);    	// pre-assigned: period 1		        
	        periodOption.addValueChangeListener(e -> {
	          	
	    		selectedPeriod = (int)e.getProperty().getValue();
	    	
	        });
	        
	        // setup the button:
	        buttonNext = new Button("Next", new Button.ClickListener() {
	        	// button listener that loads next page
				@Override
				public void buttonClick(ClickEvent event) {	
					if (!degreeSelection.isEmpty()) {
												layout.removeAllComponents();
						layout.addComponents(main, courseSelect);
						setContent(layout);					
					}
					
				}
			});          
	        buttonNext.setEnabled(false);													
	        degreeSelection.addValueChangeListener(e -> buttonNext.setEnabled(true));        
	       
	        buttonNext.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	        l1.addComponents(degreeSelection, periodOption);
	        l1.setSpacing(true);
	        l1.setMargin(true);
	        l2.addComponents(l1, buttonNext);
	    	return l2;
	    }
	     
	    private VerticalLayout CourseSelectLayout() {	//---- page2 ----\\
	  	
	    	VerticalLayout l1 = new VerticalLayout();
	    	VerticalLayout l2 = new VerticalLayout();
	        VerticalLayout selectedCoursesLayout = new VerticalLayout();
	        VerticalLayout coursesLayout = new VerticalLayout(); 
	        FormLayout addForm = new FormLayout();
	        HorizontalLayout courseT = new HorizontalLayout();
	        
	      ////////////////////////////////////////////////////////////////////////////////////////////////
	        
	        scheduleTable = new ScheduleTable("Weekly Schedule"); // moved it to "Next" button listener
	        selectedCourses = new SelectedCourses("");
	        final Accordion courseAccordion = new Accordion();
	        Button buttonBack;
	        
	        // setup the accordion:

	        courseAccordion.setHeight(100.0f, Unit.PERCENTAGE);
	        courseAccordion.addTab(selectedCoursesLayout, "Tap to see your selected courses!", FontAwesome.CHEVRON_RIGHT);
	        
	      		
			selectedCoursesLayout.addComponent(selectedCourses);

	        courseAccordion.addTab(coursesLayout, "Tap to see the list of courses", FontAwesome.BOOK);
	        courseAccordion.addSelectedTabChangeListener(
	        	new Accordion.SelectedTabChangeListener() {
	        		private static final long serialVersionUID = -2358653511430014752L;
	        		public void selectedTabChange(SelectedTabChangeEvent event) {
		                 // Find the accordion (as a TabSheet)
		                 TabSheet accordion = event.getTabSheet();
		                 
		                 // Find the tab (here we know it's a layout)
		                 Layout tab = (Layout) accordion.getSelectedTab();
	
		                 // Get the tab caption from the tab object
		                 String caption = accordion.getTab(tab).getCaption();
	                
		                 System.out.println(caption);  

		                 if(caption.equals("Tap to see the list of courses")){
				             courseT.setSizeFull();
				             // If CourseGrid (grid) already exists, delete it and make a new one with the appropriate courses
				             int gridIndex = courseT.getComponentIndex(grid);  
				             if (gridIndex != -1 && courseT.getComponent(gridIndex).isAttached()) {      
				            	 if (isBackBtnUsed) {
					            	 CourseGrid oldGrid = grid;
				                	 grid = new CourseGrid();
					            	 db = new Database2();
					            	 courseT.replaceComponent(oldGrid, grid);
					            	 isBackBtnUsed = false;
				            	 }
				            	 else {
				            		 grid.deselectAll();
				            	 }
				             } else {
			                	 grid = new CourseGrid();
				            	 db = new Database2();
				            	 courseT.addComponent(grid);
				             }
				             coursesLayout.addComponents(courseT);
		               }								
	        	  }  
	         });
	 			

	        courseAccordion.addTab(addForm, "Add another course!", FontAwesome.PLUS);
	        FormLayout addingACourse = new FormLayout();
	        Label addCourseIntro = new Label("Didn't find a course in the list above? You can add it yourself!");
	        TextField addCourseNameField = new TextField("Course Name: ");      
	        addCourseNameField.setInputPrompt("Add course name...");
    
		    Button buttonAddNewCourse = new Button("", new Button.ClickListener() {		// button to add a course
					
		  			@Override
		  			public void buttonClick(ClickEvent event) {  
		  			  /////// ADD DAY AND HOURS OF A COURSE ////////
		  		        FormLayout daysForm = new FormLayout(); 
		  		        AddWindow selectDaysWind = new AddWindow();		// new window for the day of the course selection
			  		        if (!addCourseNameField.isEmpty()) {
				  		        selectDaysWind.setCaption("Course Days");
				  		        selectDaysWind.setSizeUndefined();
				  		        daysForm = addDaysForm();
				  				if (selectDaysWind.isAttached()) {
				  					selectDaysWind.focus();
				  				} else {
				  					UI.getCurrent().addWindow(selectDaysWind);
				  				}
				  				
				  				////
				  				Button buttonToHourSelection = new Button("Next", new Button.ClickListener(){
				  					
				  					@Override
				  					public void buttonClick(ClickEvent event) {
				  						AddWindow hoursWindow = new AddWindow();
				  						FormLayout hoursForm = new FormLayout();
				  						if (!selDays.isEmpty()) {	
					  						hoursWindow.setCaption("Course Hours");
					  						hoursWindow.setSizeUndefined();
					  						//hoursForm = hoursWindow.addHoursForm();
					  						hoursForm = addHoursForm();
			
					  				        Button buttonDoneSelecting = new Button("Done", new Button.ClickListener() {
					  							
					  							@Override
					  							public void buttonClick(ClickEvent event) {
					  								VerticalLayout conflictedCourses = new VerticalLayout();
					  								
						  								if (!lecHours.isEmpty()) {
						  								int tempo = 0;
						  								String courseName = addCourseNameField.getValue();
						  								Course c = new Course(courseName, lecDays, lecHours);
						  								System.out.println("selection is: " + courseName + " " + lecDays + " " + lecHours);
						  								temporaryCourses.add(c);
						  								
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
						  												Label l = new Label("On " + lecDays.get(i) + " at " + lecHours.get(i) + " you have " + MyInit.scheduleTable.getCellValue(j, lecDays.get(i)));
						  												//Label l = new Label("On " + lecDays.get(i) + " you have " + MyInit.scheduleTable.getCellValue(j, lecDays.get(i)));
						  												conflictedCourses.addComponent(l);
						  											}
						  										}
						  									}
						  									
						  								} //////////////////////////////// Add course to the table ///////////////////////////////////////////////
						  								if(tempo==0){			// add the course to the table
						  									int added=0;
						  									for (int i = 0; i < lecHours.size(); i++){
						  										for (int j = 0; j < 6; j++) {
						  											if (lecHours.get(i).equals(MyInit.hours[j])) {
						  												if (MyInit.scheduleTable.cellIsEmpty(j, lecDays.get(i))) {
						  													System.out.println("Cell is empty");
						  													MyInit.scheduleTable.addToCell(j, lecDays.get(i), courseName);
						  													System.out.print("Added: hour: " + j + ", day: " + lecDays.get(i) + " course: " + courseName +"\n");
						  														added=1;
						  													
						  												}	
						  											}
						  										}
						  									}
						  									if(added==1){   
						  										CourseGrid.addedCourse.add(CourseGrid.addedCourse.size(), courseName);

						  									}
						  									MyInit.selectedCourses.removeAllItems();
						  								    for(int j=0; j<CourseGrid.addedCourse.size();j++){
						  						 				MyInit.selectedCourses.addItem(new Object[]{CourseGrid.addedCourse.get(j)}, new Integer(j)); 
						  						 							}
						  								} //////////////////////////////// cell is full, give popup ///////////////////////////////////////////////
						  								else {	
						  									System.out.println("cell is taken!");
						  									AddWindow notifWindow = new AddWindow();
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
						  								// resetting the fields:
						  								hoursWindow.close();
						  								addCourseNameField.setValue("");
													    System.out.println(courseName);		
													    selDays.removeAll(selDays);
													    lecDays.removeAll(lecDays);
													    lecHours.removeAll(lecHours);
													    
						  							} else {
						  								Notification notif = new Notification("You must select the hour the course takes place!", Type.WARNING_MESSAGE);
						  								notif.setIcon(FontAwesome.EXCLAMATION);
						  								notif.show(getPage());
						  							}		
					  							}
					  						});
					  				        hoursForm.addComponent(buttonDoneSelecting);
					  						hoursWindow.setContent(hoursForm);
					  						
					  						if (hoursWindow.isAttached()) {
					  							hoursWindow.focus();
					  						} else {
					  							if (selectDaysWind.isAttached()) {
					  								selectDaysWind.close();
					  							}
					  							UI.getCurrent().addWindow(hoursWindow);
					  						}	
				  						} else {
			  								Notification notif = new Notification("You must select at least one day!", Type.WARNING_MESSAGE);
			  								notif.setIcon(FontAwesome.EXCLAMATION);
			  								notif.show(getPage());
				  						}
				  					}
				  				});
	
				  			  daysForm.addComponent(buttonToHourSelection);				// add the button for the hour selection
				  			  selectDaysWind.setContent(daysForm);						// put the form to show in DAYS window	
			  		        }
			  		        else {
  								Notification notif = new Notification("Please add a name for the course", Type.WARNING_MESSAGE);
  								notif.setIcon(FontAwesome.EXCLAMATION);
  								notif.show(getPage());
			  		        }
		  			}
		  	});
       
	              //// END OF DAYS WINDOW /////
	        ////////////////////////////////////////////////////////////////////////////////////////////

		    buttonAddNewCourse.addStyleName("nobackground");
		    buttonAddNewCourse.setIcon(FontAwesome.PLUS);
	     
	        ////////////////////////////////////////////////////////////////////////////////////////////
	        ///// ADD NEW COURSE FORM LAYOUT ///////
	        
	      buttonBack = new Button("Back", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {			// go back to previous "page", page1		
					layout.removeAllComponents();
					layout.addComponents(main, selection);
					layout.setMargin(true);
					layout.setSpacing(true);
					//grid.removeAllColumns();
					setContent(layout);
					courseAccordion.setSelectedTab(0);
					scheduleTable.resetSchedule();					// clean up the schedule table
					selectedCourses.removeAllItems();
					CourseGrid.addedCourse.clear();
					for (Course c : Database2.courses) {
						c.resetCourseStatus();
					}
					isBackBtnUsed = true;
				}
			});
	        addingACourse.addComponents(addCourseIntro, addCourseNameField, buttonAddNewCourse);	        
	        addForm.addComponents(addingACourse);
	        
	        //////////////////////////////////////////////////////////////////////////////////////////////
	        l1.addComponents(scheduleTable, courseAccordion);
	        l2.setSpacing(true);
	        l2.addComponents(l1, buttonBack);
	        return l2;
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
			        	checkbox.setImmediate(true);
			        	layout.addComponent(checkbox);
			        	
			        	checkbox.setValue(false);
			        	checkbox.addValueChangeListener(e -> {	
			        		if (checkbox.getValue() == true) {
			        			if (!selDays.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
			    	        		selDays.add(checkbox.getCaption());
			    	        		System.out.println(selDays);	
			    	        	}			
			        		}
			        		else {
			        			selDays.remove(checkbox.getCaption());
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
					for (int i = 0; i < 6; i++) {
			        	CheckBox checkbox = new CheckBox (hours[i], false);
			        	layout.addComponent(checkbox);
					
			        	checkbox.addValueChangeListener(e -> {	
				        	if (checkbox.getValue() == true) {
				        		lecDays.add(day);
				        		lecHours.add(checkbox.getCaption());
				        	}
				        	else {
				        		lecDays.remove(day);
				        		lecHours.remove(checkbox.getCaption());
				        	}
			        	});
					}
					hor.addComponent(layout);
				}
				form.addComponent(hor);
				return form;
			}  
	    public VerticalLayout MainLayout() {	//==== UTU logo + app name ====\\
	    	final VerticalLayout main = new VerticalLayout();
	    	final HorizontalLayout header = new HorizontalLayout();
	    	final VerticalLayout middle = new VerticalLayout();
	    	final HorizontalLayout footer = new HorizontalLayout();
	    	
	    	
	    	Button buttonBack = new Button("Back");
	    	Button buttonNext = new Button("Next");
	    	
	    	Label label = new Label("Schedule Me!");
	    	label.addStyleName(ValoTheme.LABEL_LARGE);
	    	label.addStyleName(ValoTheme.LABEL_BOLD);
	    	label.addStyleName(ValoTheme.LABEL_HUGE);
	    	Image headerImage = new Image();
	    	final ExternalResource externalResource = new ExternalResource("http://www.utu.fi/_LAYOUTS/Neoxen/UTUInternet/Styles/utu_logo.jpg");	
	    	
	    	headerImage.setSource(externalResource);
	    	headerImage.setVisible(true);
	    	
	    	//header.setMargin(true);
	    	header.setSpacing(true);
	    	header.setMargin(true);
	    	header.setSizeFull();
	    	header.addComponents(headerImage, label);
	    	header.setComponentAlignment(label, Alignment.MIDDLE_RIGHT);
	    	
	    	middle.setSpacing(true);
	    	middle.setMargin(true);
	    	
	    	footer.setSpacing(true);
	    	footer.setSizeFull();
	    	footer.addComponents(buttonBack, buttonNext);
	    	footer.setComponentAlignment(buttonBack, Alignment.MIDDLE_LEFT);
	    	footer.setComponentAlignment(buttonNext, Alignment.MIDDLE_RIGHT);
	    	
	    	main.addComponents(header);
	    	return main;
	    	

	    }

		@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	    @VaadinServletConfiguration(ui = MyInit.class, productionMode = true)
	    public static class MyUIServlet extends VaadinServlet {
	    }


}
