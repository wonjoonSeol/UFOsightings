package assessment.view.panel3.additionalStats;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import assessment.model.panel2.StateUS;

import java.util.ConcurrentModificationException;
import java.util.TreeMap; 

/** 
 * A panel that illustrates the correlation of passage of time to relative number of 
 * incidents in the likeliest state. 
 * 
 * @author Munkhtulga Battogtokh
 * 
 */
public class TimeIncidentStatPanel extends JPanel {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	
	private StateUS likeliestState; 			// state the illustrated information is based on
	private int currentStartYear; 	// start of date range
	private int currentEndYear; 	// end of date range
	private BufferedImage gridImage; // Background image for the grid
	private JLabel centerLabel; 
	
	private int[] markers; 	// Markers indicate time
	private Image pointImage; 	// Image to represent data point
	
	/** 
	 * Constructs an instance based on the parameters given 
	 * @param likeliestState MapUS model to be based on
	 * @param currentStartYear int start of the date range
	 * @param currentEndYear int end of the date range
	 */
	public TimeIncidentStatPanel(StateUS likeliestState, int currentStartYear, int currentEndYear) {
		setLayout(new BorderLayout()); 
	
		this.currentEndYear = currentEndYear; 
		this.currentStartYear = currentStartYear; 
		this.likeliestState = likeliestState; 
		
		// Initialise label to appear if range not valid
		this.centerLabel = new JLabel("", SwingConstants.CENTER);
		add(centerLabel, BorderLayout.CENTER); 
		
		
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
		g.drawString(markers[0] + "", (int)(getWidth() * 0.1), (int)(getHeight() * 0.92));
		g.drawString(markers[1] + "", (int)(getWidth() * 0.23), (int)(getHeight() * 0.92));
		g.drawString(markers[2] + "", (int)(getWidth() * 0.36), (int)(getHeight() * 0.92));
		g.drawString(markers[3] + "", (int)(getWidth() * 0.49), (int)(getHeight() * 0.92));
		g.drawString(markers[4] + "", (int)(getWidth() * 0.62), (int)(getHeight() * 0.92));
		g.drawString(markers[5] + "", (int)(getWidth() * 0.75), (int)(getHeight() * 0.92));
	}
	
	/** 
	 * Draws the datapoints on appropriate positions.
	 */
	private void drawPoints(Graphics g) {
		// if likeliest state noUSstate then tell showing non us sightings
		int horisontalRange = currentEndYear - currentStartYear; 
		
		// set up array "years" to have parallel arrays with it 
		TreeMap<Integer, Integer> yearToCount = likeliestState.getIncidentPerYear(); 
		
		int countYears = yearToCount.keySet().size();  
		
		Integer[] years = new Integer[countYears];
		int index = 0; 
		for (int i : yearToCount.keySet()) {
			years[index++] = i; 
		}

		// store the scaled horisontal position of each datapoint in a parallel array. 
		Integer[] horisontalPositions = new Integer[countYears]; 
		
		for (int i = 0; i < countYears; i++) {
			 horisontalPositions[i] = (int)((getWidth()*0.125) + ((float)(years[i] - currentStartYear)/horisontalRange) * getWidth()* 0.655); 
		}
		
		
		// store scaled vertical position of each datapoint in a parallel-array
		int[] verticalPositions = new int[countYears]; 
		
		int maxCount = 0; 
		for (int i : yearToCount.values()) {
			if (i > maxCount) {
				maxCount = i; 
			}
		}
		int verticalRange = maxCount; 
		
		// Store the vertical positions in an parallel array
		for (int i = 0; i < countYears; i++) {
			verticalPositions[i] = (getHeight() - (int)(getHeight() * 0.18 + ((double)yearToCount.get(years[i])/verticalRange)   * getHeight() * 0.67));
			
		}
		
		// Finally draw all point images using the horizontal, and vertical positions	
		for (int i = 0; i < countYears; i++) {
			g.drawImage(pointImage, horisontalPositions[i], verticalPositions[i],
							(int)(getWidth() * 0.05), (int)(getHeight() * 0.05), null);
		}	
		
		g.drawString(verticalRange + "", (int)(getWidth() * 0.07), (int)(getHeight() * 0.24));	
	}
	
	
	/** 
	 * Overrides parent method to draw graph, and its components on the right places
	 */
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
			g.drawString("Max", (int)(getWidth() * 0.05), (int)(getHeight() * 0.18));
			g.drawString("0", (int)(getWidth() * 0.1), (int)(getHeight() * 0.85));
			drawMarkers(g);
			try {
				drawPoints(g);
			} catch (ConcurrentModificationException e) {
				System.out.println("Redrawing time incident frame");
			}
		} else {
			repaint(); 
			if ("Not specified.".equals(likeliestState.getAbbreviation())) {
				centerLabel.setText("<html><div style='text-align: center;'>" + "No likeliest state specified.  " 
						+ "</div></html>");
			} else {
				centerLabel.setText("<html><div style='text-align: center;'>" + "Total incident count in likeliest state: " 
					+ likeliestState.getIncidentsCount() + " Please choose time range of at least 5 years to \n see graph" 
					 + "</div></html>");
			}
			centerLabel.setVisible(true);
		}
	
	}
	
	/** 
	 * Sets the StateUS on which the graph is based 
	 * @param likeliestState StateUS the current likeliest state
	 */
	public void setLikeliestState(StateUS likeliestState) {
		this.likeliestState = likeliestState; 
	}
	
	/** 
	 * Specifies the current start year of the time range for the graph
	 * @param currentStartYear int the start year
	 */
	public void setCurrentStartYear(int currentStartYear) {
		this.currentStartYear = currentStartYear; 
	}
	
	/** 
	 * Specifies the current end year of the time range for the graph
	 * @param currentEndYear int the end year
	 */	
	public void setCurrentEndYear(int currentEndYear) {
		this.currentEndYear = currentEndYear; 
	}
}
