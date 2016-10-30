package com.example.ScheduleMe;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */

@SuppressWarnings("serial")
@Theme("mytheme")
final public class MyUI extends UI {
	
	//final List<String> facultyNames = new ArrayList<String>();
    private List<String> degreeNames = new ArrayList<String>();
    private int degreeSize = 5;		// static implementation, TODO: change when/if we have database
    final VerticalLayout layout = new VerticalLayout();
	private VerticalLayout main = MainLayout();
	private VerticalLayout selection = SelectionLayout();
	private VerticalLayout courseSelect = CourseSelectLayout();
	 int count = 0;

	 static String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
     static String[] hours = new String[] { "8-10", "10-12", "12-14", "14-16", "16-18", "18-20"};

    static String temp="True";
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        
        // add the components to the layout:
        layout.addComponents(main, selection);  
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        
    }
    
    public VerticalLayout SelectionLayout() {	//---- page1 ----\\
    	
    	VerticalLayout l1 = new VerticalLayout();
    	VerticalLayout l2 = new VerticalLayout();
        //NativeSelect facultySelection = new NativeSelect("Select the organization :");  
        NativeSelect degreeSelection = new NativeSelect("Select your degree:");
        OptionGroup periodOption = new OptionGroup("Select period:");
    	Button buttonNext;
    	
 //       addFacultyNames();      
        
/*        for (int i = 0; i < facultySize; i++) {
        	facultySelection.addItem(i);
        	facultySelection.setItemCaption(i, facultyNames.get(i));
        } */ 
        // so that the blank option cannot be chosen
//        facultySelection.setNullSelectionAllowed(false);
        degreeSelection.setNullSelectionAllowed(false);
        
        // event listener for when we select a faculty to show only the appropriate degrees
 /*       facultySelection.addValueChangeListener(e -> {
        	degreeSelection.removeAllItems();			// clean the default value
        	degreeNames.removeAll(degreeNames);			// clean all the degree name drop down options
        	addDegreeNames((int)e.getProperty().getValue());		// which degree are we talking about? its the itemID of the choice.
        	for (int i = 0; i < degreeNames.size() ; i++) {
        		degreeSelection.addItem(i);
        		degreeSelection.setItemCaption(i, degreeNames.get(i));
        	}
        	
        });*/
    	degreeNames.add("Master's Degree Programme in Bioinformatics");
    	degreeNames.add("Master's Degree Programme in Embedded Computing");
		degreeNames.add("Master's Degree Programme in Information Security and Cryptography");
		degreeNames.add("Show only the TUCS courses");
		degreeNames.add("Show all courses");
	
        for (int i = 0; i < degreeSize; i++) {
        	degreeSelection.addItem(i);
        	degreeSelection.setItemCaption(i, degreeNames.get(i));}

    	// setup the period selection:
        for (int i = 0; i < 2; i++) {
        	periodOption.addItem(i);
        	int x=i+1;		// don't ask, lol :/
        	periodOption.setItemCaption(i, "Period " + x);
        }
        periodOption.setValue(0);		// pre-assigned: period 1
        
        // setup the button:
        buttonNext = new Button("Next", new Button.ClickListener() {
        	// button listener that loads next page
			@Override
			public void buttonClick(ClickEvent event) {	
				//if (!facultySelection.isEmpty() && !degreeSelection.isEmpty()) {		// TODO: Uncomment+ for field checking
					layout.removeAllComponents();
					layout.addComponents(main, courseSelect);
					setContent(layout);	
				//}																		// TODO: Uncomment+ for field checking
			}
		});         
        //buttonNext.setEnabled(false);													// TODO: Uncomment+ for field checking  
        //degreeSelection.addValueChangeListener(e -> buttonNext.setEnabled(true));      // TODO: Uncomment+ for field checking  
        buttonNext.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        l1.addComponents(degreeSelection, periodOption);
        l1.setSpacing(true);
        l1.setMargin(true);
        l2.addComponents(l1, buttonNext);
    	return l2;
    }
    
    @SuppressWarnings("unchecked")
	public VerticalLayout CourseSelectLayout() {	//---- page2 ----\\
    	VerticalLayout l1 = new VerticalLayout();
    	VerticalLayout l2 = new VerticalLayout();
        VerticalLayout selectedCoursesLayout = new VerticalLayout();
        VerticalLayout coursesLayout = new VerticalLayout(); 
        //VerticalLayout addCourse = new VerticalLayout();
        FormLayout addForm = new FormLayout();
         final Button buttonAdd;
        

        
        ////////////////////////////////////////////////////////////////////////////////////////////////
        Table scheduleTable = new Table("Schedule");
        scheduleTable.addStyleName("Schedule");
        final Accordion courseAccordion = new Accordion();
        Button buttonBack;
        
        scheduleTable.setSizeFull();
        scheduleTable.setColumnHeaders();
        scheduleTable.setPageLength(0);
        scheduleTable.setHeight("100%");
        scheduleTable.setColumnCollapsingAllowed(false);
        scheduleTable.addContainerProperty("0", String.class, null,"", null, null);
        
        for (int i = 0; i < 5; i++) {		// set the headers
        	scheduleTable.addContainerProperty(days[i], String.class, null);
        	scheduleTable.setColumnAlignment(i, Align.CENTER);
        }
       
        for (int i=0; i<6; i++)
        		 scheduleTable.addItem(new Object[]{hours[i],
        	                 " ", " ", " ", " ", " "}, new Integer(i));


       
        
        // setup the accordion:
        courseAccordion.setHeight(100.0f, Unit.PERCENTAGE);
        courseAccordion.addTab(selectedCoursesLayout, "Tap to see your selected courses!");
        
						
				Table selectedCourses = new Table();
				selectedCourses.setSelectable(true);
				//   selectedCourses.setMultiSelect(true);
				selectedCourses.setImmediate(true);
				selectedCourses.setColumnHeaders();
				selectedCourses.setPageLength(0);
				selectedCourses.setHeight("100%");
				
				
				//scheduleTable.addContainerProperty("0", String.class, null,"", null, null);
				
				selectedCourses.addContainerProperty("Course Name", String.class, null);
				selectedCourses.setColumnAlignment(0, Align.CENTER);
				selectedCourses.addContainerProperty("Teacher", String.class, null);
				selectedCourses.setColumnAlignment(1, Align.CENTER);
				
								
				selectedCoursesLayout.addComponent(selectedCourses);

        courseAccordion.addTab(coursesLayout, "Tap to see the list of courses");
        
         new Database();
         HorizontalLayout courseT = Database.courseTable;
         courseT.setSizeFull();
         
         
      // button to add a course

        buttonAdd = new Button("Add", new Button.ClickListener() {
			//@Override
			public void buttonClick(ClickEvent event) {			// add the course	
			//	if (temp=="True"){
			//		scheduleTable.getItem(0).getItemProperty("Thursday").setValue("test");									
			//	}
			}
		});
        
        

        
        
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
				setContent(layout);
			}
		});
         
  
        courseT.addComponent(Database.grid);

        coursesLayout.addComponents(courseT,buttonAdd);
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
   /* private void addFacultyNames() {
        facultyNames.add("Faculty of Mathematics and Natural Sciences");
        facultyNames.add("Faculty of Medicine");
        facultyNames.add("Faculty of Education");
    }*/
    
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

    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}


