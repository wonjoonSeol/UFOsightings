package assessment.controller.panel2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import assessment.view.panel2.stateIncidentsLayer.StateIncidentFrame;

public class LabelMouseAdapter extends MouseAdapter {
	
	public void mouseClicked(MouseEvent e) {
		StateLabel label = (StateLabel) e.getSource(); 
		
		label.pressed(); 
		
		StateIncidentFrame incidentFrame = new StateIncidentFrame(
				label.getState(), label); 
		incidentFrame.setVisible(true); 
	}
	
}
