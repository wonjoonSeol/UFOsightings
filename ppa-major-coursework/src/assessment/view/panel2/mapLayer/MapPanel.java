package assessment.view.panel2.mapLayer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import assessment.controller.panel2.LabelMouseAdapter;
import assessment.controller.panel2.StateLabel;
import assessment.model.panel2.MapUS;

public class MapPanel extends JPanel {
	
	/**
	 * Removes warning: Default Serial Version ID 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage imageMap; 		// image for map
	private MapUS map; 						// Model for this map panel 
	private LabelMouseAdapter mouseAdapter; // Mouse adapter for all statelabels
	private BufferedImage imageMarker; 		// marker image for all state labels
	
	public MapPanel(MapUS map) {
		setLayout(null); // Enables free positioning labels
		
		this.map = map; 	
		
		try {
			imageMap = ImageIO.read(new File("images/map.png"));
			imageMarker = ImageIO.read(new File("images/alien.png"));
		} catch (IOException e) {
			System.out.println("Image failed");
			e.getStackTrace();
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
				label("AL", 680, 450),
				label("AK", 110, 580), 
				label("AR", 580, 410),
				label("AZ", 200, 410),
				label("CA", 60, 270), 
				
				label("CO", 330, 300),
				label("CT", 910, 200), 
				label("DE", 870, 270), 
				label("FL", 780, 550), 
				label("GA", 750, 460), 
				
				label("HI", 370, 660), 
				label("IA", 540, 250), 
				label("ID", 180, 170), 
				label("IL", 620, 270),
				label("IN", 680, 270),
				
				label("KS", 450, 330), 
				label("KY", 700, 350), 
				label("LA", 570, 520), 
				label("MA", 910, 175), 
				label("MD", 845, 275), 
				
				label("ME", 935, 90), 
				label("MI", 690, 185), 
				label("MN", 520, 130), 
				label("MO", 560, 340), 
				label("MS", 625, 480), 
				
				label("MT", 280, 100), 
				label("NC", 800, 380), 
				label("ND", 420, 100), 
				label("NE", 435, 250), 
				label("NH", 915, 145), 
				
				label("NJ", 880, 250), 
				label("NM", 310, 420), 
				label("NV", 130, 270), 
				label("NY", 850, 170), 
				label("OH", 730, 270),
				
				label("OK", 470, 410),
				label("OR", 90, 130),
				label("PA", 820, 240),
				label("RI", 925, 190),
				label("SC", 795, 420),
				
				label("SD", 430, 180),
				label("TN", 680, 390),
				label("TX", 445, 510),
				label("UT", 220, 285),
				label("VA", 820, 335),
				
				label("VT", 892, 140),
				label("WA", 110, 50),
				label("WI", 600, 170),
				label("WV", 775, 310),
				label("WY", 300, 205)
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
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageMap, 0, 0, getWidth(), getHeight(), null); 	
	}
}
