package assessment.controller.panel2;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import assessment.model.panel2.StateUS;

public class StateLabel extends JLabel {
	
	/**
	 * Removes warning 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage imageMarker; // label image 
	private StateUS state; 	// state the label is for 
	private int x; 			// intended x position
	private int y; 			// intended y position
	private int scaledSize;	// size of the marker, scaled by the incidents' count
	
	public StateLabel(StateUS state, BufferedImage imageMarker, int x, int y) {
		super(); 
		
		this.state = state;
		this.scaledSize = (int)(20 + (2.0 * state.getIncidentsCount())); 
		this.x = x; 
		this.y = y; 
		this.imageMarker = imageMarker; 
		
		changeSize(scaledSize); 
	
	}
	
	public StateUS getState() {
		return state; 
	}
	
	public void pressed() {
	
		changeSize((int)(scaledSize * 0.8)); 
		
	}
	
	/** 
	 * Changes the size of the label, fitting the image to it. 
	 * @param scaledSize int the desired size of the label
	 */
	public void changeSize(int scaledSize) {
		if (state.getIncidentsCount() > 0) {
			
			setBounds((int) ( x - (scaledSize/2)), (int) ( y - (scaledSize/2)), scaledSize, scaledSize);
		
			ImageIcon smallerIcon = new ImageIcon(
					imageMarker.getScaledInstance(getWidth(), 
							getHeight(), Image.SCALE_SMOOTH)); 
		
			setIcon(smallerIcon); 
		}
	}

	public int getScaledSize() {
		return scaledSize; 
	}
}
