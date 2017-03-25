package assessment.model.panel2;

import java.util.ArrayList;
import java.util.Observable;

import api.ripley.Incident;

public class StateUS extends Observable {
	
	private String name; 					// state name
	private String abbreviation;			// state abbreviation, eg. ARZ
	private ArrayList<Incident> incidents; 	// list of incidents 
	private int incidentsCount; 			// count of incidents 
	
	public StateUS(String name, String abbreviation) {
		this.name = name; 
		this.abbreviation = abbreviation; 
		this.incidents = new ArrayList<Incident>(); 
	}
	
	/** 
	 * Adds an incident associated with this state 
	 * @param incident Incident happened at this state 
	 */
	public void addIncident(Incident incident) {
		incidents.add(incident); 
		incidentsCount++; 
		
		setChanged(); 
		notifyObservers(); 
	}
	
	public ArrayList<Incident> getIncidents() {
		return incidents; 
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
	
	public void clearIncidents() {
		incidents.clear();
		incidentsCount = 0; 
	}
}
