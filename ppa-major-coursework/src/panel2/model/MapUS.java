package panel2.model;

import java.util.ArrayList;

public class MapUS {
	
	private StateUS[] states; // map has array of states 
	
	public MapUS() {
		initStates(); 
	}
	
	/** 
	 * Creates the states of the map 
	 */
	public void initStates() {
		StateUS arizona = new StateUS("Arizona", "ARZ"); 
		arizona.setIncidentsCount(2);
		
		states = new StateUS[] { arizona 
		}; 
		
	}
	
	public StateUS getState(int i) {
		return states[i]; 
	}
}
