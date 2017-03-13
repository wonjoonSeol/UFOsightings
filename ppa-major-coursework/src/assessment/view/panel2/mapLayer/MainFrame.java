package assessment.view.panel2.mapLayer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import api.ripley.Incident;
import api.ripley.Ripley;
import assessment.model.panel2.MapUS;

public class MainFrame extends JFrame  {
	/**
	 * Removes warning: Serial Version ID; 
	 */
	private static final long serialVersionUID = -304500755720480657L;
	
	private MapPanel mapPanel; 	// the frame has panel 2, map panel
	
	public MainFrame() {
		super("UFO app"); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setLayout(new BorderLayout()); 
		setSize(new Dimension(1000, 750)); 
		setResizable(false); 
		
		Ripley myRipley = new Ripley("90tLI3CSu9GyVD6ql2OMtA==", 
				"lBgm4pRs/wHVqL46EnH7ew=="); 
		
		ArrayList<Incident> incidentsInRange = 
				myRipley.getIncidentsInRange("2000-01-01 00:00:00", 
												"2000-02-01 00:00:00");

		
		
		//Create the map model, and the panel based on it 
		MapUS mapUS = new MapUS(incidentsInRange); 
		this.mapPanel = new MapPanel(mapUS); 
		
		add(mapPanel, BorderLayout.CENTER); 
	}
	
}
