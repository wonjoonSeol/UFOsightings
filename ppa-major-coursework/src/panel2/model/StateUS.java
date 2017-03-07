package panel2.model;

import java.util.ArrayList;

public class StateUS {
	// list of the incidents in this state 
	private String name; 
	private String abbreviation; 
	private ArrayList<Integer> incidents; 
	private int incidentsCount; 
	
	public StateUS(String name, String abbreviation) {
		this.name = name; 
		this.abbreviation = abbreviation; 
	}
	
	// Frome here get and set methods for the fields s
	public void setIncidents(ArrayList<Integer> incidents) {
		this.incidents = incidents; 
		incidentsCount = incidents.size(); 
	}
	
	public int getIncidentsCount() {
		return incidentsCount; 
	}

	public String getName() {
		return name; 
	}
	
	public String getAbbreviation() {
		return abbreviation; 
	}
	
	///// REMOVE LATER
	public void setIncidentsCount(int n) {
		this.incidentsCount = n; 
	}
}
