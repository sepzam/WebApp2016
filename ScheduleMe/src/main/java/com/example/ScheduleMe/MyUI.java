package com.example.ScheduleMe;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	
    private List<String> facultyNames = new ArrayList<String>();
    private List<String> degreeNames = new ArrayList<String>();
    //private List<String> degreeNamesMed = new ArrayList<String>();
    //private List<String> degreeNamesEdu = new ArrayList<String>();
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        int facultySize = 3;		// static implementation, TODO: change when/if we have database

        NativeSelect facultySelection = new NativeSelect("Select your faculty:");  
        NativeSelect degreeSelection = new NativeSelect("Select your degree:");
        OptionGroup periodOption = new OptionGroup("Select period:");
        Button nextButton = new Button("Next");        
        
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

        for (int i = 0; i < 2; i++) {
        	periodOption.addItem(i);
        	int x=i+1;		// don't ask, lol :/
        	periodOption.setItemCaption(i, "Period " + x);
        }
        periodOption.setValue(0);
        
        layout.addComponents(facultySelection, degreeSelection, periodOption, nextButton);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }
    
    // For static implementation of faculty names
    private void addFacultyNames() {
        facultyNames.add("Faculty of Mathematics and Natural Sciences");
        facultyNames.add("Faculty of Medicine");
        facultyNames.add("Faculty of Education");
    }
    
    /// For static implementation: degree == 1 corresponds to (some) of the degrees of Mathematics faculty, 2 is Medicine and 3 is education.
    private void addDegreeNames(int degree) {
    	switch (degree) {
    	case 0:
    		degreeNames.add("Master's Degree Programme in Information Security and Cryptography ");
        	degreeNames.add("Master's Degree Program in Materials Science");
        	degreeNames.add(" Master's Degree Programme in Bioinformatics");
        	degreeNames.add("Master's Degree Programme in Embedded Computing");
        	break;
    	case 1:
        	degreeNames.add("Neuroscience study program");
        	degreeNames.add("International Career in Health Sciences");
        	break;
    	case 2:
        	degreeNames.add("Master's Degree Programme in Learning, Learning Environments and Educational Systems");
        	degreeNames.add("Doctor of Philosophy (in Education)");
        	break;
    	default:
    		degreeNames.add("error! :(");
    		break;
    	}	
    	
    }

    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
