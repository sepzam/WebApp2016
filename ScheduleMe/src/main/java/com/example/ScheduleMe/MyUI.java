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
public class MyUI extends UI {
	
	private List<String> facultyNames = new ArrayList<String>();
    private List<String> degreeNames = new ArrayList<String>();
    private int facultySize = 3;		// static implementation, TODO: change when/if we have database
    final VerticalLayout layout = new VerticalLayout();
	private VerticalLayout main = MainLayout();
	private VerticalLayout selection = SelectionLayout();
	private VerticalLayout courseSelect = CourseSelectLayout();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        
        //VerticalLayout selection = SelectionLayout();
        // add the components to the layout:
        //VerticalLayout main = MainLayout();
        //MainLayoutC main = new MainLayoutC(selection);
        layout.addComponents(main, selection);  
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        
    }
    
    private VerticalLayout SelectionLayout() {	//---- page1 ----\\
    	
    	VerticalLayout l1 = new VerticalLayout();
    	VerticalLayout l2 = new VerticalLayout();
        NativeSelect facultySelection = new NativeSelect("Select your faculty:");  
        NativeSelect degreeSelection = new NativeSelect("Select your degree:");
        OptionGroup periodOption = new OptionGroup("Select period:");
    	Button buttonNext;
    	
        addFacultyNames();      
        
        for (int i = 0; i < facultySize; i++) {
        	facultySelection.addItem(i);
        	facultySelection.setItemCaption(i, facultyNames.get(i));
        }  
        // so that the blank option cannot be chosen
        facultySelection.setNullSelectionAllowed(false);
        degreeSelection.setNullSelectionAllowed(false);
        
        // event listener for when we select a faculty to show only the appropriate degrees
        facultySelection.addValueChangeListener(e -> {
        	degreeSelection.removeAllItems();			// clean the default value
        	degreeNames.removeAll(degreeNames);			// clean all the degree name drop down options
        	addDegreeNames((int)e.getProperty().getValue());		// which degree are we talking about? its the itemID of the choice.
        	for (int i = 0; i < degreeNames.size() ; i++) {
        		degreeSelection.addItem(i);
        		degreeSelection.setItemCaption(i, degreeNames.get(i));
        	}
        	
        });

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
        l1.addComponents(facultySelection, degreeSelection, periodOption);
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
        VerticalLayout addCourse = new VerticalLayout();
        FormLayout addForm = new FormLayout();
        
        Table scheduleTable = new Table("Schedule");
        scheduleTable.addStyleName("Schedule");
        final Accordion courseAccordion = new Accordion();
        Button buttonBack;
        
        // setup the table:
        String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] hours = new String[] { "8-10", "10-12", "12-14", "14-16", "16-18", "18-20"};
        scheduleTable.setSizeFull();
        scheduleTable.setColumnHeaders();
        scheduleTable.setPageLength(0);
        scheduleTable.setHeight("100%");
        
        scheduleTable.addContainerProperty("0", String.class, null,"", null, null);
        
        for (int i = 0; i < 5; i++) {		// set the headers
        	scheduleTable.addContainerProperty(days[i], String.class, null);
        	scheduleTable.setColumnAlignment(i, Align.CENTER);
        }
       
        for (int i=0; i<6; i++)
        		 scheduleTable.addItem(new Object[]{hours[i],
        	                 " ", " ", " ", " ", " "}, new Integer(i));
        scheduleTable.setColumnReorderingAllowed(false);
        scheduleTable.setColumnCollapsingAllowed(false);
        //scheduleTable.setCol
                
        // setup the accordion:
        courseAccordion.setHeight(100.0f, Unit.PERCENTAGE);
        courseAccordion.addTab(selectedCoursesLayout, "Tap to see your selected courses!");
        courseAccordion.addTab(coursesLayout, "Tap to see the list of courses");
        courseAccordion.addTab(addForm, "Add another course!");
        
        ///////////////////////////////////////////////////////////////////
        // setup the "add course" FormLayout:
        // i'd like this to be in a different method but i couldn't do it
        Label addCourseIntro = new Label("Didn't find a course in the list above? You can add it yourself!");
        TextField addCourseNameField = new TextField("Course Name: ");

        Window courseDaySelect = new Window();
        Window courseHourSelect = new Window();
        FormLayout daySelect = new FormLayout();
        FormLayout hourSelect = new FormLayout();
        ArrayList<String> selDays = new ArrayList<String>();	// selected days for a course
        
        // window preferences "hour"
        courseHourSelect.setImmediate(true);
        courseHourSelect.setModal(true);
        courseHourSelect.center();
        courseHourSelect.setContent(hourSelect);
        
        // form layout "hour"
        hourSelect.setMargin(true);
        hourSelect.setWidth(300, Unit.PIXELS);
        hourSelect.addComponent(new Label("\n" + "Tick the hours for each day:"));
        
        // window preferences "day"
        courseDaySelect.setImmediate(true);
        courseDaySelect.setModal(true);
        courseDaySelect.center();
        courseDaySelect.setContent(daySelect);
        
        // form layout "day"
        daySelect.setMargin(true);
        daySelect.setWidth(300, Unit.PIXELS);
        daySelect.addComponent(new Label("\n" + "Tick the days of the course:"));
        
        // FOR WINDOW "SELECT DAYS":
        // add the checkbox days in the window
        for (int i = 0; i < 5; i++) {	
        	CheckBox checkbox = new CheckBox (days[i], false);
        	daySelect.addComponent(checkbox);
        	checkbox.addValueChangeListener(e -> {
        		if (checkbox.getValue() == true) {
        			if (!selDays.contains(checkbox.getCaption()))	{	// add the day if it's not already added to the list
        				selDays.add(checkbox.getCaption());
        				daySelect.addComponent(new Label("" + checkbox.getCaption()));  // code to verify if they are all added
        			}
        		}
        	});
        }
        // setup the button to open the hour selection
        Button button = new Button("Next", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (courseHourSelect.isAttached()) {
					courseHourSelect.focus();
				} else {
					if (courseDaySelect.isAttached())
						courseDaySelect.close();
					UI.getCurrent().addWindow(courseHourSelect);
				}
				
			}
		});
        
        daySelect.addComponent(button);
        
        // FOR WINDOW "SELECT HOURS":
        for (String aDay : selDays) {
        	Panel panel = new Panel("" + aDay);
        	hourSelect.addComponent(panel);
		}

        // setup the add button to open the day selection window
        Button buttonAdd = new Button("+", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				//courseDaySelect.focus();
				if (courseDaySelect.isAttached()) {
					courseDaySelect.focus();
				} else {
					UI.getCurrent().addWindow(courseDaySelect);
				}
			}
		});
        
        addCourse.setMargin(true);
        addCourse.setSpacing(true);
   
        //addCourse.addComponents(addCourseIntro, addCourseName);
        
        // setup the form layout "add course"
        
        addCourseNameField.setInputPrompt("Add course name...");
        //add
        
        addForm.setMargin(true);
        addForm.setSpacing(true);     
        
        addForm.addComponents(addCourseIntro, addCourseNameField, buttonAdd);
        
        // setup the addDayAndTime
        
        
        // setup the back button:
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
        l1.addComponents(scheduleTable, courseAccordion);
        l2.setSpacing(true);
        l2.addComponents(l1, buttonBack);
        return l2;
    }
   
    private VerticalLayout addHoursCheckbox() {
    	VerticalLayout l = new VerticalLayout();
    	
    	//for (int i = 0; i < hour)
    	
    	return l;
    	
    }
    
    private VerticalLayout MainLayout() {	//==== UTU logo + app name ====\\
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
    private void addFacultyNames() {
        facultyNames.add("Faculty of Mathematics and Natural Sciences");
        facultyNames.add("Faculty of Medicine");
        facultyNames.add("Faculty of Education");
    }
    
    /// For static implementation: degree == 0 corresponds to (some) of the degrees of Mathematics faculty, 1 is Medicine and 2 is education.
    private void addDegreeNames(int degree) {
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
    }

    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

class MainLayoutC extends VerticalLayout {

    public MainLayoutC(VerticalLayout middle) {	//==== page3 ====\\
    	final VerticalLayout main = new VerticalLayout();
    	final HorizontalLayout header = new HorizontalLayout();
    	//final VerticalLayout middle = new VerticalLayout();
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
    	
    	main.addComponents(header, middle);
    	//header.setSpacing(true);
    	//header.setMargin(true);
    	//return main;
    }
}
    
