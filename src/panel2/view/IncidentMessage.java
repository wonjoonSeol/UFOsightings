package panel2.view;


import javax.swing.ImageIcon;

import javax.swing.JDialog;
import javax.swing.JOptionPane;


import api.ripley.Incident;

/** 
 * Displays details of selected incident 
 * @author admin
 *
 */
public class IncidentMessage extends JDialog {

	// Takes an incident, and shows a dialog based on this. 
	public IncidentMessage(Incident incident) { 
		JOptionPane.showMessageDialog(rootPane,
				incident.getSummary(), 
				"Message",
				JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon("//icons//ufo.png"));
	}
}
