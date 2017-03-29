package assessment.model.panel2;

import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;

import api.ripley.Incident;

public class MapUS extends Observable {
	
	private TreeMap<String, StateUS> mapNameToState; // treemap of states and their names
	
	public MapUS() {
		super(); 
		this.mapNameToState = new TreeMap<>(); 
		initStates();
	}
	
	/** 
	 * Creates all the states in the US map
	 */
	public void initStates() {
		map("AL", "Alabama"); 
		map("AK", "Alaska"); 
		map("AZ", "Arizona"); 	
		map("AR", "Arkansas"); 
		map("CA", "California"); 
		
		map("CO", "Colorado"); 
		map("CT", "Connecticut"); 
		map("DE", "Delaware"); 
		map("FL", "Florida"); 
		map("GA", "Georgia");
		
		map("HI", "Hawai"); 
		map("ID", "Idaho"); 
		map("IL", "Illinois"); 
		map("IN", "Indiana"); 
		map("IA", "Iowa");
		
		map("KS", "Kansas"); 
		map("KY", "Kentucky"); 
		map("LA", "Louisiana"); 
		map("ME", "Maine"); 
		map("MD", "Maryland");
		
		map("MA", "Massachusetts"); 
		map("MI", "Michigan"); 
		map("MN", "Minnesota"); 
		map("MS", "Mississippi"); 
		map("MO", "Missouri");
		
		map("MT", "Montana"); 
		map("NE", "Nebraska"); 
		map("NV", "Nevada"); 
		map("NH", "New HampShire"); 
		map("NJ", "New Jersey");
		
		map("NM", "New Mexico"); 
		map("NY", "New York"); 
		map("NC", "North Carolina"); 
		map("ND", "North Dakota"); 
		map("OH", "Ohio"); 
		
		map("OK", "Oklahoma"); 
		map("OR", "Oregon"); 
		map("PA", "Pennsylvania"); 
		map("RI", "Rhode Island"); 
		map("SC", "South Carolina"); 
		
		map("SD", "South Dakota"); 
		map("TN", "Tennessee"); 
		map("TX", "Texas"); 
		map("UT", "Utah"); 
		map("VT", "Vermont"); 
		
		map("VA", "Virginia"); 
		map("WA", "Washington"); 
		map("WV", "West Virginia"); 
		map("WI", "Wisconsin"); 
		map("WY", "Wyoming"); 
		
		map("Not specified.", "No US State specified"); 
	}
	
	/**
	 * Creates a single entry in the mapping of name to state
	 */
	public void map(String abbreviation, String name) {
		mapNameToState.put(abbreviation, new StateUS(name, abbreviation)); 
	}
	
	/** 
	 * Given an array list of incidents, distributes them to the 
	 * corresponding state where they happened 
	 * @param ArrayList<Incident> list of incidents 
	 */
	public void distributeIncidents(ArrayList<Incident> incidents) {
		
		for (StateUS aState: mapNameToState.values()) {
			aState.clearIncidents();
		}
		
		for (Incident incident: incidents) {
			if (mapNameToState.get(incident.getState()) != null) {
				StateUS theState = mapNameToState.get(incident.getState()); 
				theState.addIncident(incident);
			}
		}
		
		setChanged(); 
		notifyObservers(); 
	}
	
	/** 
	 * Gets a state in the map using its name
	 * @param String name
	 * @return StateUS the state
	 */
	public StateUS getState(String name) {
		return mapNameToState.get(name);  
	}
	
	/** 
	 * Gets the state with the highest count of incidents currently 
	 */
	public StateUS getLikeliestState() {
		
		StateUS currentLikeliestState = null; 
		boolean allEqual = true; 
		
		for (StateUS state : mapNameToState.values()) {
			if (state == mapNameToState.get("Not specified.")) continue; 
			if (currentLikeliestState == null) currentLikeliestState = state;
			if (currentLikeliestState.getIncidentsCount() != state.getIncidentsCount()) allEqual = false; 
			if (currentLikeliestState.getIncidentsCount() < state.getIncidentsCount()) currentLikeliestState = state;
		}
		
		// If all states have the same incident count, return not specified
		if (allEqual) return mapNameToState.get("Not specified."); 
		
		
		return currentLikeliestState; 
	}
}
