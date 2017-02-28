package panel2.model;

import java.util.ArrayList;

import api.ripley.Incident;
import api.ripley.Ripley;

public class Map {
	// User gives the date range 
		// Application loads the incidents across US within this range. 
				// gives an arraylist of incidents 
		// Panel2 categorize the incidents by the states. 
		// Panel2 places appropriately sized markers on each state. 
	
	private State[] states; 	// Map has an array of states 
	private ArrayList<Incident> incidents; 	// Map has given list of incidents 
	
	public Map(ArrayList<Incident> incidents) {
		// initialise all states 
		// give initial list of incidents to map 
		this.incidents = incidents; 
	}
}
