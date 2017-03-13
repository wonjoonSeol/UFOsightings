package panel2.view;

import javax.swing.JPanel;

/** 
 * This class is for listing the incidents in a given state 
 * @author Munkhtulga Battogtokh
 *
 */
public class IncidentListFrame extends JPanel {
	// Title includes the full name of the state relevant, and its abbreviation
	// this class should present list of incidents given in a state 
	// the list elements are selectable, and leads to a message window with details of the selected incident 
	// this class can sort the sightings by any of date, city, shape, duration, and added date 
	private State state; 
}
