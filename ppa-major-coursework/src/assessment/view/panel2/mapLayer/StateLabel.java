package assessment.view.panel2.mapLayer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import assessment.model.panel2.StateUS;

/** 
 * Label to mark states as having incidents. 
 * @author Munkhtulga Battogtokh
 */
public class StateLabel extends JLabel implements Observer {
	
	/**
	 * Serial version 
	 */
	private static final long serialVersionUID = 1L;
	
	private Image imageMarker; // label image 
	private Image highResolutionMarker; // constant resolution, without this image resolution decreases after size is reset to larger
	private ImageIcon icon; // label icon with the image
	private StateUS state; 	// state the label is for 
	private int x; 			// intended x position
	private int y; 			// intended y position
	private int scaledSize;	// size of the marker, scaled by the incidents' count

	/** 
	 * Constructs a state marker label based on given US state, image marker, and 
	 * position in frame as specified by coordinates. 
	 * @param state StateUS the US state this marker is for
	 * @param imageMarker BufferedImage the image this marker appears as 
	 * @param x int horizontal positioning of the marker
	 * @param y int vertical positioning of the marker
	 */
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
	
	/** 
	 * Indicates that this label object is pressed by reducing its visible size 	
	 */
	public void pressed() {
		if (scaledSize > 100) {
			changeSize(80); 
		} else {
			changeSize((int)(scaledSize * 0.8)); 
		}
	}
	
	/** 
	 * Changes the size of the label, while ensuring the image fits as appropriate
	 * @param scaledSize int the desired size of the label
	 */
	public void changeSize(int scaledSize) {
		if (state.getIncidentsCount() > 0) {
			if (scaledSize > 100) scaledSize = 100; 
			setBounds((int) ( x - (scaledSize/2)), (int) ( y - (scaledSize/2)), scaledSize, scaledSize);
		
			imageMarker = highResolutionMarker.getScaledInstance(getWidth(),
					getHeight(), Image.SCALE_SMOOTH);
			
			icon.setImage(imageMarker); 
			setIcon(icon);
		}
	}

	/** 
	 * Returns the size scaled to be positioned correctly
	 * @return int scaledSize
	 */
	public int getScaledSize() {
		return scaledSize; 
	}
	
	/** 
	 * Returns the state this label is for 	
	 * @return
	 */
	public StateUS getState() {
		return state; 
	}
	
	
	/** 
	 * Updates by changing size as according to the corresponding state, 
	 * and its incident count
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (state.getIncidentsCount() == 0) {
		    setVisible(false);
		} else {
			setVisible(true);
		}
		this.scaledSize = (int)(10 + (2.0 * state.getIncidentsCount()));
		changeSize(scaledSize);
	}
	
}
