package panel2.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import panel2.model.MapUS;

public class MainFrame extends JFrame  {

	private MapPanel mapPanel; 	// the frame has panel 2, map panel
	
	public MainFrame() {
		super("UFO app"); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setLayout(new BorderLayout()); 
		setMinimumSize(new Dimension(1000, 750)); 
		
		//Create the map model, and the panel based on it 
		MapUS mapUS = new MapUS(); 
		this.mapPanel = new MapPanel(mapUS); 
		
		add(mapPanel, BorderLayout.CENTER); 
	}
	
}
