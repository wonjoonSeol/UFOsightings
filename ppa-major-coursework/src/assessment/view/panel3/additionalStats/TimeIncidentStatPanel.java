package assessment.view.panel3.additionalStats;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import assessment.model.Model;
import assessment.model.panel2.MapUS;
import assessment.model.panel2.StateUS;
import java.util.TreeMap; 

/** 
 * A panel that illustrates the correlation of passage of time to relative number of 
 * incidents in the likeliest state. 
 * @author Munkhtulga Battogtokh
 *
 */
public class TimeIncidentStatPanel extends JPanel {

	private StateUS likeliestState; 			// state the illustrated information is based on
	private int currentStartYear; 	// start of date range
	private int currentEndYear; 	// end of date range
	private BufferedImage gridImage; // Background image for the grid
	private JLabel centerLabel; 
	
	private int[] markers; 	// Markers indicate time
	private Image pointImage; 	// Image to represent data point
	
	/** 
	 * Constructs an instance based on the parameters given 
	 * @param mapModel MapUS model to be based on
	 * @param currentStartYear int start of the date range
	 * @param currentEndYear int end of the date range
	 */
	public TimeIncidentStatPanel(StateUS likeliestState, int currentStartYear, 
									int currentEndYear) {
		setLayout(null); // Allows absolute positioning
	
		this.currentEndYear = currentEndYear; 
		this.currentStartYear = currentStartYear; 
		this.likeliestState = likeliestState; 
		
		// Initialise label to appear if range not valid
		this.centerLabel = new JLabel(); 
		add(centerLabel); 
		centerLabel.setBounds(100, 80, 120, 130);
		
		try {
			this.pointImage = ImageIO.read(new File("images/point.gif")).getScaledInstance(40, 20, Image.SCALE_SMOOTH); 
			this.gridImage = ImageIO.read(new File("images/grid.jpg")); 
		} catch (IOException e) {
			System.out.println("Images initialisation failed");
		}

		initYearMarkers(); 
	}
	
	/** 
	 * Initialises year markers of a graph on set positions
	 */
	private void initYearMarkers() {
		
		markers = new int[6]; 
		for (int i = 0; i < 6; i++) {
			markers[i] = (int)(currentStartYear + Math.ceil(currentEndYear - currentStartYear) * (0.2 * i));
		}
	
	}
	
	/** 
	 * Returns if date range is long enough to be worthwhile a graph illustration
	 * @return boolean if the date range is long enough
	 */ 
	private boolean rangeValid() {
		return ((currentEndYear - currentStartYear) >= 5); 
	}
	
	/** 
	 * Draws the year markers as strings on set positions
	 * @param g Graphics
	 */
	private void drawMarkers(Graphics g) {
		g.drawString(markers[0] + "", 20, 240);
		g.drawString(markers[1] + "", 65, 240);
		g.drawString(markers[2] + "", 110, 240);
		g.drawString(markers[3] + "", 150, 240);
		g.drawString(markers[4] + "", 195, 240);
		g.drawString(markers[5] + "", 235, 240);
	}
	
	/** 
	 * Draws the datapoints on appropriate positions.
	 */
	private void drawPoints(Graphics g) {
		// if likeliest state noUSstate then tell showing non us sightings
		int horisontalRange = currentEndYear - currentStartYear; 
		
		// need to track horisontal positions equal to the number of years that have count mapped to them
		TreeMap<Integer, Integer> yearToCount = likeliestState.countIncidentPerYear(); 
		int countYears = yearToCount.keySet().size(); 
		Integer[] horisontalPositions = new Integer[countYears]; 
		Integer[] years = new Integer[countYears];
		int index = 0; 
		for (int i : yearToCount.keySet()) {
			years[index++] = i; 
		}
		
		// store the scaled horisontal position of each datapoint in an array. 
		for (int i = 0; i < countYears; i++) {
			 horisontalPositions[i] = (int)((37 + ((float)(years[i] - currentStartYear)/horisontalRange) * 204)); 
		}
		
		// store scaled vertical position of each datapoint in a parallel-array
		int[] verticalPositions = new int[countYears]; 
		int verticalRange = 0; 
		for (int i : yearToCount.values()) {
			if (i > verticalRange) {
				verticalRange = i; 
			}
		}
		
		// Store the vertical positions in an parallel array
		for (int i = 0; i < countYears; i++) {
			verticalPositions[i] = (getHeight() - (int)((45 + (((double)yearToCount.get(years[i]))/verticalRange) * 175))); 
		}

		for (int i = 0; i < countYears; i++) {
			g.drawImage(pointImage, horisontalPositions[i], verticalPositions[i], 15, 15, null);
		}	
	
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setFont(new Font("Arial", Font.BOLD, 12));
		
		if (rangeValid() && !"Not specified.".equals(likeliestState.getAbbreviation())) {
			repaint(); 
			initYearMarkers(); 
			centerLabel.setVisible(false);
			
			g.drawImage(gridImage, 0, 0, getWidth(), getHeight(), null); 	
			g.drawString("Time", (int)(getWidth() * 0.9), (int)(getHeight() * 0.9));
			g.drawString("Incident count", (int)(getWidth() * 0.05), (int)(getHeight() * 0.1));
			g.drawString("Max", 15, 45);
			g.drawString(likeliestState.getIncidentsCount() + "", 17, 58);
			g.drawString("0", 28, getHeight() - 35);
			
			drawMarkers(g); 
			drawPoints(g); 
			
		} else {
			repaint(); 
			if ("Not specified.".equals(likeliestState.getAbbreviation())) {
				centerLabel.setText("<html><div style='text-align: center;'>" + "No likeliest state specified.  " 
						+ "\u00a9Munkhtulga Battogtokh" + "</div></html>");
			} else {
				centerLabel.setText("<html><div style='text-align: center;'>" + "Total incident count in likeliest state: " 
					+ likeliestState.getIncidentsCount() + " Please choose time range of at least 5 years to \n see graph" 
						 + "           \u00a9Munkhtulga Battogtokh" + "</div></html>");
			}
			centerLabel.setVisible(true);
		}
	
	}
	
	public void setLikeliestState(StateUS likeliestState) {
		this.likeliestState = likeliestState; 
	}
	
	public void setCurrentStartYear(int currentStartYear) {
		this.currentStartYear = currentStartYear; 
	}
	
	public void setCurrentEndYear(int currentEndYear) {
		this.currentEndYear = currentEndYear; 
	}
}
