package panel2.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import panel2.model.StateUS;

public class StateLabel extends JLabel {
	private StateUS state; 	// state the label is for 
	private int scaledSize;	// size of the marker, scaled by the incidents' count
	
	public StateLabel(StateUS state, BufferedImage imageMarker, int x, int y) {
		super(); 
		
		this.state = state;
		this.scaledSize = (int)(50 * Math.pow(1.1, state.getIncidentsCount())); 
		
		if (state.getIncidentsCount() > 0)
			setBounds(x, y, scaledSize, scaledSize); 
		
		// Scale the passed image to this label 
		ImageIcon actualIcon = new ImageIcon(
				imageMarker.getScaledInstance(getWidth(), 
						getHeight(), Image.SCALE_SMOOTH)); 
		// Set that image as the icon of this label
		setIcon(actualIcon);
		
		
	}

	public void correctImage() {
		
	}

}
