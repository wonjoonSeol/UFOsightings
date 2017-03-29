package assessment.controller.panel2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import assessment.view.panel2.mapLayer.StateLabel;
import assessment.view.panel2.stateIncidentsLayer.StateIncidentFrame;

/** 
 * Mouse adapter used specifically for StateLabels to make it possible for users 
 * to click on a marker on any given state to see details of incidents in that state. 
 * @author Munkhtulga Battogtokh
 */
public class LabelMouseAdapter extends MouseAdapter {
	
	/** 
	 * Makes the source label of the mouse event pressed, and opens a 
	 * new StateIncidentFrame detailing the incidents inside the state that the label is for 
	 */
	public void mouseClicked(MouseEvent e) {
		StateLabel label = (StateLabel) e.getSource(); 
		
		label.pressed(); 
		
		StateIncidentFrame incidentFrame = new StateIncidentFrame(
				label.getState(), label); 
		incidentFrame.setVisible(true); 
	}
	
}
