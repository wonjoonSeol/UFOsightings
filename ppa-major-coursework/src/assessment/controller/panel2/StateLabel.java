package assessment.controller.panel2;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import assessment.model.panel2.StateUS;

public class StateLabel extends JLabel implements Observer {
	
	/**
	 * Removes warning 
	 */
	private static final long serialVersionUID = 1L;
	
	private Image imageMarker; // label image 
	private Image highResolutionMarker; // constant resolution, without this image resolution decreases after size is reset to larger
	private ImageIcon icon; // label icon with the image
	private StateUS state; 	// state the label is for 
	private int x; 			// intended x position
	private int y; 			// intended y position
	private int scaledSize;	// size of the marker, scaled by the incidents' count
	
	public StateLabel(StateUS state, BufferedImage imageMarker, int x, int y) {
		super(); 
		
		this.state = state;
		state.addObserver(this);
		
		this.scaledSize = (int)(10 + (2.0 * state.getIncidentsCount())); 
		this.x = x; 
		this.y = y; 
		this.imageMarker = imageMarker; 
		this.highResolutionMarker = imageMarker; 
		this.icon = new ImageIcon(imageMarker); 
		
		changeSize(scaledSize); 
	
	}
	
	public StateUS getState() {
		return state; 
	}
	
	public void pressed() {
		if (scaledSize > 150) {
			changeSize(120); 
		} else {
			changeSize((int)(scaledSize * 0.8)); 
		}
	}
	
	/** 
	 * Changes the size of the label, fitting the image to it. 
	 * @param scaledSize int the desired size of the label
	 */
	public void changeSize(int scaledSize) {
		if (state.getIncidentsCount() > 0) {
			if (scaledSize > 150) scaledSize = 150; 
			setBounds((int) ( x - (scaledSize/2)), (int) ( y - (scaledSize/2)), scaledSize, scaledSize);
		
			imageMarker = highResolutionMarker.getScaledInstance(getWidth(), 
					getHeight(), Image.SCALE_SMOOTH);
			
			icon.setImage(imageMarker); 
		
			setIcon(icon); 
		}
	}

	public int getScaledSize() {
		return scaledSize; 
	}
	
	public void setImageMarker(BufferedImage imageMarker) {
		this.imageMarker = imageMarker; 
		this.highResolutionMarker = imageMarker; 
	}

	@Override
	public void update(Observable o, Object arg) {
		this.scaledSize = (int)(10 + (2.0 * state.getIncidentsCount())); 
		changeSize(scaledSize); 
	}
	
}
