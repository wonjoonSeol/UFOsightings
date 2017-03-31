package assessment.model.panel2;

import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;

import api.ripley.Incident;

/** 
 * An object of this class represents a single state, which is not necessarily 
 * but intended to be an US state.
 * 
 * @author Munkhtulga Battogtokh
 */
public class StateUS extends Observable {
	
	private String name; 					// state name
	private String abbreviation;			// state abbreviation, eg. ARZ
	private ArrayList<Incident> incidents; 	// list of incidents 
	private int incidentsCount; 			// count of incidents 
	
	/** 
	 * Creates an instance of this class using a string as name of the state object, 
	 * and a string as abbreviation of the name. 
	 * @param name String name of the state
	 * @param abbreviation String abbreviation of the name
	 */
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
	}
	
	/**
	 * Clears all incidents registered with this state object 
	 */
	public void clearIncidents() {
		incidents.clear();
		incidentsCount = 0;
	}

	/** 
	 * Suggests an update to observers
	 */
	public void requestImageUpdate() {
		setChanged();
		notifyObservers(incidentsCount);
	}
	
	/** 
	 * Gives the string representation (name of the state) of this state object. 
	 */
	public String toString() {
		return name; 
	}
	
	/** 
	 * Gives map that maps incident years to number of incidents that year
	 * @return TreeMap<Integer, Integer> map of incident years to incident count that year
	 */
	public TreeMap<Integer, Integer> getIncidentPerYear() {
		TreeMap<Integer, Integer> mapYearToCount = new TreeMap<>();

		for (Incident incident : incidents) {
			int incidentYear = Integer.parseInt(incident.getDateAndTime().substring(0, 4)); 
		
			if (mapYearToCount.containsKey(incidentYear)) {
				mapYearToCount.put(incidentYear, mapYearToCount.get(incidentYear) + 1); 
			} else {
				mapYearToCount.put(incidentYear, 1); 
			}	
		}
		return mapYearToCount;
	}
	
	/** 
	 * Gives the total number of incidents listed for this state
	 * @return int incidentsCount number of incidents
	 */
	public ArrayList<Incident> getIncidents() {
		return incidents; 
	}
	
	/** 
	 * Gives the total number of incidents listed for this state
	 * @return int incidentsCount number of incidents
	 */
	public int getIncidentsCount() {
		return incidentsCount; 
	}

	/** 
	 * Gives the name of this state
	 * @return String name of this state
	 */
	public String getName() {
		return name; 
	}
	
	
	/** 
	 * Gives abbreviation of the name for this state
	 * @return String abbreviation 
	 */
	public String getAbbreviation() {
		return abbreviation; 
	}
	
}
