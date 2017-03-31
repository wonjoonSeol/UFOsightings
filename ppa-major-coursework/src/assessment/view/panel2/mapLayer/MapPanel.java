package assessment.view.panel2.mapLayer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import assessment.controller.panel2.LabelMouseAdapter;
import assessment.model.panel2.MapUS;

/** 
 * An object of this class gives a graphical User Interface view 
 * of the US map as relevant. 
 * 
 * @author Munkhtulga Battogtokh
 *
 */
public class MapPanel extends JPanel {
	
	/**
	 * Removes warning: Default Serial Version ID 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage imageMap; 		// image for map
	private MapUS map; 						// Model for this map panel 
	private LabelMouseAdapter mouseAdapter; // Mouse adapter for all statelabels
	private BufferedImage imageMarker; 		// marker image for all state labels
	private BufferedImage imageMarkerBW; 	// black and white version of marker image for unknown incidents
	
	/** 
	 * Constructs an instance of this class to create an US map view object
	 * @param map
	 */
	public MapPanel(MapUS map) {
		setLayout(null); // Enables free positioning labels
		
		this.map = map;
		
		try {
			imageMap = ImageIO.read(new File("images/map.png"));
			imageMarker = ImageIO.read(new File("images/alien.png"));
			imageMarkerBW = ImageIO.read(new File("images/alienBW.png")); 
		} catch (IOException e) {
			System.out.println("Image initialisation failed");
		}
		
		mouseAdapter = new LabelMouseAdapter(); 
		
		initStateMarkers(); 
		
	}
	
	/** 
	 * Initialises all the markers to appear within the state boundaries if there 
	 * is any sighting incident there. 
	 */
	public void initStateMarkers() {
			
		StateLabel[] labels = new StateLabel[] {
				label("AL", 544, 360),
				label("AK", 88, 464), 
				label("AR", 464, 328),
				label("AZ", 160, 328),
				label("CA", 48, 216), 
				
				label("CO", 248, 240),
				label("CT", 728, 160), 
				label("DE", 696, 216), 
				label("FL", 624, 440), 
				label("GA", 600, 368), 
				
				label("HI", 296, 528), 
				label("IA", 432, 200), 
				label("ID", 144, 136), 
				label("IL", 496, 216),
				label("IN", 544, 216),
				
				label("KS", 360, 264), 
				label("KY", 560, 280), 
				label("LA", 456, 416), 
				label("MA", 728, 140), 
				label("MD", 676, 220), 
				
				label("ME", 748, 72), 
				label("MI", 552, 148), 
				label("MN", 416, 104), 
				label("MO", 448, 272), 
				label("MS", 500, 384), 
				
				label("MT", 224, 80), 
				label("NC", 640, 304), 
				label("ND", 336, 80), 
				label("NE", 348, 200), 
				label("NH", 732, 116), 
				
				label("NJ", 704, 200), 
				label("NM", 248, 336), 
				label("NV", 104, 216), 
				label("NY", 680, 136), 
				label("OH", 584, 216),
				
				label("OK", 376, 328),
				label("OR", 72, 104),
				label("PA", 656, 192),
				label("RI", 740, 152),
				label("SC", 636, 336),
				
				label("SD", 344, 144),
				label("TN", 544, 312),
				label("TX", 356, 408),
				label("UT", 176, 228),
				label("VA", 656, 268),
				
				label("VT", 714, 112),
				label("WA", 88, 40),
				label("WI", 480, 136),
				label("WV", 620, 248),
				label("WY", 240, 164),
				
				new StateLabel(map.getState("Not specified."), 
						imageMarkerBW, 740, 500)
		}; 
		
	
		for (int i = 0; i < labels.length; i++) {
			labels[i].addMouseListener(mouseAdapter);
			add(labels[i]); 
		}			
	}
	
	/** 
	 * Creates a single state label for the state with given abbreviation
	 * on a location specified by the coordinates x and y
	 * @param stateAbbreviation String abbreviation of the state of the label
	 * @param x	int horizontal position
	 * @param y int vertical position
	 * @return StateLabel resulting label of the specifications. 
	 */
	public StateLabel label(String stateAbbreviation, int x, int y) {
		return new StateLabel(map.getState(stateAbbreviation), 
				imageMarker, x, y); 
	}
	
	/** 
	 * Paints the map image
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageMap, 0, 0, getWidth(), getHeight(), null); 	
	}
}
