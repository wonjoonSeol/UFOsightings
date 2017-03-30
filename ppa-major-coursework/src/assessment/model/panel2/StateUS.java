package assessment.model.panel2;

import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;

import api.ripley.Incident;

public class StateUS extends Observable {
	
	private String name; 					// state name
	private String abbreviation;			// state abbreviation, eg. ARZ
	private ArrayList<Incident> incidents; 	// list of incidents 
	private int incidentsCount; 			// count of incidents 
	private TreeMap<Integer, Integer> mapYearToCount; // map years to incident counts that year
	
	public StateUS(String name, String abbreviation) {
		this.name = name; 
		this.abbreviation = abbreviation; 
		this.incidents = new ArrayList<Incident>(); 
		this.mapYearToCount = new TreeMap<>(); 
	}
	
	/** 
	 * Adds an incident associated with this state 
	 * @param incident Incident happened at this state 
	 */
	public void addIncident(Incident incident) {
		incidents.add(incident); 
		incidentsCount++; 
		
		int incidentYear = Integer.parseInt(incident.getDateAndTime().substring(0, 4)); 
	
		if (mapYearToCount.containsKey(incidentYear)) {
			mapYearToCount.put(incidentYear, mapYearToCount.get(incidentYear) + 1); 
		} else {
			mapYearToCount.put(incidentYear, 1); 
		}	
		
		
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
		mapYearToCount.clear();

		setChanged(); 
		notifyObservers(); 
	}
	
	public String toString() {
		return name; 
	}
	
	/** 
	 * Gives the map that maps incident years to number of incidents that year
	 * @return TreeMap<Integer, Integer> map of incident years to incident count that year
	 */
	public TreeMap<Integer, Integer> getIncidentPerYear() {
		return mapYearToCount; 
	}
	
	/** 
	 * Gives the current highest count of incidents that occured in any of the years. 
	 * @return
	 */
	public int maxIncidentCountAnyYear() {
		int count = 0; 
		for (int i : mapYearToCount.values()) {
			if (i > count) {
				count = i; 
			}
		}
		return count; 
	}
	
	/** 
	 * Gives all the years that the State's current incidents happen in 
	 * @return years Integer[] years that have incidents
	 */
	public Integer[] years() {
	
		Integer[] years = new Integer[ mapYearToCount.keySet().size()];
		int index = 0; 
		for (int i : mapYearToCount.keySet()) {
			years[index++] = i; 
		}
	
		return years; 
	}
	
	/** 
	 * Gives how many different years there are that have incidents currently within this State
	 * @return int how many different years with incidents
	 */
	public int countYears() {
		return mapYearToCount.keySet().size();  
	}
}
