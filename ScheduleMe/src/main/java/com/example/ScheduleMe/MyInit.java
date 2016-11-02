package com.example.ScheduleMe;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.Table.Align;
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
	//private static Table scheduleTable = new Table("Weekly Schedule");
	static ScheduleTable scheduleTable;
	static Table selectedCourses = new Table();
	
	//private String[] listOfDays = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	//private String[] listOfHours = new String[] { "8-10", "10-12", "12-14", "14-16", "16-18", "18-20"};
	
	static String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	static String[] hours = new String[] { "8-10", "10-12", "12-14", "14-16", "16-18", "18-20"};
	
	static int count;
	static Database2 db;
	Grid coursesGrid = new Grid();
	
	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		//scheduleTable.resetSchedule();
		//selectedCourses.removeAllItems();
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
					//if (!facultySelection.isEmpty() && !degreeSelection.isEmpty()) {		// TODO: Uncomment+ for field checking
					if (!degreeSelection.isEmpty()) {
						//scheduleTable.resetSchedule();
						//selectedCourses.removeAllItems();
						layout.removeAllComponents();
						layout.addComponents(main, courseSelect);
						setContent(layout);	
						//db = new Database2(selectedPeriod, selectedDegree);											// load the database here
						//coursesGrid = Database2.getSchedule();
					}																		// TODO: Uncomment+ for field checking
				}
			});          
	        buttonNext.setEnabled(false);													// TODO: Uncomment+ for field checking  
	        degreeSelection.addValueChangeListener(e -> buttonNext.setEnabled(true));      // TODO: Uncomment+ for field checking  
	       
	        buttonNext.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	        l1.addComponents(degreeSelection, periodOption);
	        l1.setSpacing(true);
	        l1.setMargin(true);
	        l2.addComponents(l1, buttonNext);
	    	return l2;
	    }
	     
	    @SuppressWarnings("unchecked")
		private VerticalLayout CourseSelectLayout() {	//---- page2 ----\\
	  	
	    	VerticalLayout l1 = new VerticalLayout();
	    	VerticalLayout l2 = new VerticalLayout();
	        VerticalLayout selectedCoursesLayout = new VerticalLayout();
	        VerticalLayout coursesLayout = new VerticalLayout(); 
	        FormLayout addForm = new FormLayout();
	        
	      ////////////////////////////////////////////////////////////////////////////////////////////////
	        
	        scheduleTable = new ScheduleTable("Weekly Schedule"); // moved it to "Next" button listener
	        final Accordion courseAccordion = new Accordion();
	        Button buttonBack;
	        
	        // setup the accordion:
	        courseAccordion.setHeight(100.0f, Unit.PERCENTAGE);
	        courseAccordion.addTab(selectedCoursesLayout, "Tap to see your selected courses!");
	        
	        
					selectedCourses.setSelectable(true);
					selectedCourses.setImmediate(true);
					selectedCourses.setPageLength(0);
					selectedCourses.setHeight("100%");					
					selectedCourses.getContainerDataSource().removeAllItems();
					
					//scheduleTable.addContainerProperty("0", String.class, null,"", null, null);
					
					selectedCourses.addContainerProperty("Course Name", String.class, null);
					selectedCourses.setColumnAlignment(0, Align.CENTER);
					selectedCourses.addContainerProperty("Teacher", String.class, null);
					selectedCourses.setColumnAlignment(1, Align.CENTER);
					
									
					selectedCoursesLayout.addComponent(selectedCourses);

	        courseAccordion.addTab(coursesLayout, "Tap to see the list of courses");
           /* HorizontalLayout courseT = new HorizontalLayout();
            courseT.setSizeFull();
            //db = new Database2();
            Grid coursesGrid = Database2.getSchedule();
            courseT.addComponent(coursesGrid);
            coursesLayout.addComponents(courseT);
	         */
	         courseAccordion.addSelectedTabChangeListener(
	                 new Accordion.SelectedTabChangeListener() {
	             private static final long serialVersionUID = -2358653511430014752L;

	             public void selectedTabChange(SelectedTabChangeEvent event){
	                 // Find the accordion (as a TabSheet)
	                 TabSheet accordion = event.getTabSheet();
	                 
	                 // Find the tab (here we know it's a layout)
	                 Layout tab = (Layout) accordion.getSelectedTab();

	                 // Get the tab caption from the tab object
	                String caption = accordion.getTab(tab).getCaption();
	                // String tabId = accordion.getTab(tab).getId();
	                 
	               System.out.println(caption);  
	          //     ((class) Database.grid).checkColumnIsAttached();
	               if(caption.equals("Tap to see the list of courses")){
	            	   
	            	   //db = new Database2(selectedPeriod, selectedDegree);
	         //      Database.grid.removeAllColumns();
	            	   db = new Database2();
		               HorizontalLayout courseT = new HorizontalLayout();
		               courseT.setSizeFull();
		               courseT.addComponent(Database2.grid);
		               coursesLayout.addComponents(courseT);
	               }
	             }
	         });
	 			
	         
	      // button to add a course

	        courseAccordion.addTab(addForm, "Add another course!");
	        
	       
	        FormLayout addingACourse = new FormLayout();
	        Label addCourseIntro = new Label("Didn't find a course in the list above? You can add it yourself!");
	        TextField addCourseNameField = new TextField("Course Name: ");
	        
	         
	        // setup the back button:
	       
	       
	        addCourseNameField.setInputPrompt("Add course name...");
	        
	      Button buttonAddNewCourse = new Button("+", new Button.ClickListener() {		// button to add a course
				
	  			@Override
	  			public void buttonClick(ClickEvent event) {  
	  				//courseDaySelect.focus();
	  			  /////// ADD DAY AND HOURS OF A COURSE ////////
	  		        FormLayout daysForm = new FormLayout(); 
	  		        AddWindow selectDaysWind = new AddWindow();		// new window for the day of the course selection
	  		        selectDaysWind.setCaption("Course Days");
	  		        selectDaysWind.setSizeUndefined();
	  		        		// new formlayout for the content of DAYS window
	  		        
	  		        
	  		        daysForm = selectDaysWind.addDaysForm();		// build up the days form
	  		        
	  		        // TODO: Validation checking before enabling the "Next" button
	  		        // setup the button to open the hour selection
	  		        
	  		       
	  				if (selectDaysWind.isAttached()) {
	  					selectDaysWind.focus();
	  				} else {
	  					UI.getCurrent().addWindow(selectDaysWind);
	  				}
	  				
	  				////
	  				Button buttonToHourSelection = new Button("Next", new Button.ClickListener(){
	  					
	  					@Override
	  					public void buttonClick(ClickEvent event) {
	  						
	  						FormLayout hoursForm = new FormLayout();
	  						AddWindow hoursWindow = new AddWindow();
	  						hoursWindow.setCaption("Course Hours");
	  						hoursWindow.setSizeUndefined();
	  						hoursForm = hoursWindow.addHoursForm();

	  						// very very messy!! TODO: improveeeeeeeee
	  				        Button buttonDoneSelecting = new Button("Done", new Button.ClickListener() {
	  							
	  							@Override
	  							public void buttonClick(ClickEvent event) {
	  								hoursWindow.close();
	  								String course = addCourseNameField.getValue();
	  								//for (String temp0 :  hoursWindow.selDays) {
	  										for(String temp :  AddWindow.moHo ){
	  											for (int j=0; j<6;j++)
	  												if (temp== hours[j])
	  													scheduleTable.getItem(j).getItemProperty("Monday").setValue(course);									
	  										}
	  										for(String temp :  AddWindow.tuHo ){
	  											for (int j=0; j<6;j++)
	  												if (temp== hours[j])
	  													scheduleTable.getItem(j).getItemProperty("Tuesday").setValue(course);									
	  										}
	  										for(String temp :  AddWindow.weHo ){
	  											for (int j=0; j<6;j++)
	  												if (temp== hours[j])
	  													scheduleTable.getItem(j).getItemProperty("Wednesday").setValue(course);									
	  										}
	  										for(String temp :  AddWindow.thHo ){
	  											for (int j=0; j<6;j++)
	  												if (temp== hours[j])
	  													scheduleTable.getItem(j).getItemProperty("Thursday").setValue(course);									
	  										}
	  										for(String temp :  AddWindow.frHo ){
	  											for (int j=0; j<6;j++)
	  												if (temp== hours[j])
	  													scheduleTable.getItem(j).getItemProperty("Friday").setValue(course);									
	  										}
	  								//}
	  										
	  										AddWindow.selDays.removeAll(AddWindow.selDays);
	  										AddWindow.moHo.removeAll(AddWindow.moHo);
	  										AddWindow.tuHo.removeAll(AddWindow.tuHo);
	  										AddWindow.weHo.removeAll(AddWindow.weHo);
	  										AddWindow.thHo.removeAll(AddWindow.thHo);
	  										AddWindow.frHo.removeAll(AddWindow.frHo);
	  										addCourseNameField.setValue("");
	  										     		System.out.println(course);		
	  										selectedCourses.addItem(new Object[]{course,"---"}, new Integer(count));    
	  										
	  									  	count++;
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
	  					}
	  				});
	  			  daysForm.addComponent(buttonToHourSelection);				// add the button for the hour selection
	  	        selectDaysWind.setContent(daysForm);						// put the form to show in DAYS window

	  			}
	  		});
	          
	        

	        
	              //// END OF DAYS WINDOW /////
	        ////////////////////////////////////////////////////////////////////////////////////////////

	        ////////////////////////////////////////////////////////////////////////////////////////////
	        ///// ADD NEW COURSE FORM LAYOUT ///////
	        
	      buttonBack = new Button("Back", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {			// go back to previous "page", page1		
					layout.removeAllComponents();
					layout.addComponents(main, selection);
					layout.setMargin(true);
					layout.setSpacing(true);
					Database2.grid.removeAllColumns();
					setContent(layout);
					courseAccordion.setSelectedTab(0);
					scheduleTable.resetSchedule();					// clean up the schedule table
					selectedCourses.removeAllItems();	
					for (Course c : Database2.courses) {
						c.resetCourseStatus();
					}
					//new Database2();
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
	    	label.addStyleName(ValoTheme.LABEL_H1);
	    	label.addStyleName(ValoTheme.LABEL_HUGE);
	    	Image headerImage = new Image();
	    	final ExternalResource externalResource = new ExternalResource("http://www.utu.fi/_LAYOUTS/Neoxen/UTUInternet/Styles/utu_logo.jpg");	
	    	
	    	headerImage.setSource(externalResource);
	    	//headerImage.setSource(new ThemeResource("utu_logo.jpg"));
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
	    	//header.setSpacing(true);
	    	//header.setMargin(true);
	    	return main;
	    	

	    }
	    
	    // For static implementation of faculty names
	    private void addDegreeNames() {
	        degreeNames.add("Master's Degree Programme in Bioinformatics");
	        degreeNames.add("Master's Degree Programme in Information Security and Cryptography");
	        degreeNames.add("Master's Degree Programme in Embedded Computing");
	        degreeNames.add("Show only the TUCS courses");
	        degreeNames.add("Show all courses");
	     
	    }

	    /// For static implementation: degree == 0 corresponds to (some) of the degrees of Mathematics faculty, 1 is Medicine and 2 is education.
	   /* private void addDegreeNames(int degree) {
	    	switch (degree) {
	    	case 0:		// math
	    		degreeNames.add("Master's Degree Programme in Information Security and Cryptography ");
	        	degreeNames.add("Master's Degree Program in Materials Science");
	        	degreeNames.add(" Master's Degree Programme in Bioinformatics");
	        	degreeNames.add("Master's Degree Programme in Embedded Computing");
	        	break;
	    	case 1:		// Med
	        	degreeNames.add("Neuroscience study program");
	        	degreeNames.add("International Career in Health Sciences");
	        	break;
	    	case 2:		// edu
	        	degreeNames.add("Master's Degree Programme in Learning, Learning Environments and Educational Systems");
	        	degreeNames.add("Doctor of Philosophy (in Education)");
	        	break;
	    	default:		// otherwise
	    		degreeNames.add("error! :(");
	    		break;
	    	}
	    }*/		

	    
	    public int getDegree() {
			return selectedDegree;
		}

		public void setDegree(int selectedDegree) {
			this.selectedDegree = selectedDegree;
		}
		

		@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	    @VaadinServletConfiguration(ui = MyInit.class, productionMode = false)
	    public static class MyUIServlet extends VaadinServlet {
	    }


}
