package assessment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.TreeMap;

import api.ripley.Incident;
import api.ripley.Ripley;

/**
 * Created by wonjoonseol on 09/03/2017.
 */
public class Model extends Observable {
	private int currentStartYear;
	private int currentEndYear;
	private int indexStartYear;
	private int indexEndYear;
	private int ripleyMinYear;
	private int ripleyMaxYear;
	private ArrayList<Incident>[] incidents;
	private Ripley ripley;
	private int methodNumber; // do we need this?

	public Model(Ripley ripley) {
		this.ripley = ripley;
		ripleyMinYear = ripley.getStartYear();
		ripleyMaxYear = ripley.getLatestYear();
		indexEndYear = Integer.MIN_VALUE;
		indexStartYear = Integer.MAX_VALUE;
		incidents = new ArrayList[ripleyMaxYear - ripleyMinYear];
		System.out.println("total length: " + incidents.length);

		for (int i = 0; i < ripleyMaxYear - ripleyMinYear; i++) {
			incidents[i] = new ArrayList<Incident>();
		}
	}

	public void setStartYear(int year) {
		currentStartYear = year;
		notifyYear();
	}

	public void setEndYear(int year) {
		currentEndYear = year;
		notifyYear();
	}

	public int getRipleyMinYear() {
		return ripleyMinYear;
	}

	public int getRipleyMaxYear() {
		return ripleyMaxYear;
	}

	public List<Incident> getRequestedData() {
		int endIndex = currentEndYear - ripleyMinYear;
		System.out.println("endIndex:" + endIndex);
		int startIndex = currentStartYear - ripleyMinYear;
		System.out.println("startIndex:" + startIndex);
		ArrayList<Incident> incidents = new ArrayList<Incident>();
		if (endIndex < this.incidents.length && 0 < startIndex) {
			for (ArrayList<Incident> element : this.incidents) {
				if (element != null) {
					incidents.addAll(element);
				}
			}
		}
		return incidents;
	}

	/*
	 * Return the number of hoaxes within the current dataset.
	 */
	public int getNumHoaxes() {
		List<Incident> data = getRequestedData();
		int count = 0;
		for (Incident i : data) // Iterate through the incident list, increase
								// count if a HOAX match is found.
		{
			if (i.getSummary().contains("HOAX")) {
				count++;
			}
		}
		return count; // Return counter variable.

	}

	public String fiveLargestStates() {
		String returnString = "";

		List<Incident> data = getRequestedData();

		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		for (Incident i : data) // Iterate through the incident list
		{
			if (map.containsKey(i.getState())) {
				Integer temp = map.get(i.getState());
				map.put(i.getState(), temp + 1);
			} else
				map.put(i.getState(), 1);
		}
		for (int i = 0; i < 5; i++) {
			Entry<String, Integer> maximumEntry = null;
			for (Entry<String, Integer> entry : map.entrySet()) {
				if (maximumEntry == null || entry.getValue().compareTo(maximumEntry.getValue()) > 0) {
					maximumEntry = entry;
				}
			}
			map.remove(maximumEntry.getKey());
			returnString += maximumEntry.getKey() + ": " + maximumEntry.getValue() + ", ";
		}

		return returnString;
	}

	/*
	 * Return the number of non-US sightings within the current dataset.
	 */
	public int getNonUSSight() {
		List<Incident> data = getRequestedData();
		int count = 0;
		for (Incident i : data) // Iterate through the incident list, increase
								// count if a Non-US match is found.
		{
			if (!i.getState().equals("Not specified.")) {
				count++;
			}
		}
		return count; // Return counter variable.
	}

	/*
	 * Returns the likeliest state to receive a sighting within the current
	 * dataset.
	 */
	public String getLikeliestState() {
		List<Incident> data = getRequestedData();

		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		for (Incident i : data) // Iterate through the incident list
		{
			if (map.containsKey(i.getState())) {
				Integer temp = map.get(i.getState());
				map.put(i.getState(), temp + 1);
			} else
				map.put(i.getState(), 1);
		}

		Entry<String, Integer> maximumEntry = null;
		for (Entry<String, Integer> entry : map.entrySet()) {
			if (maximumEntry == null || entry.getValue().compareTo(maximumEntry.getValue()) > 0) {
				maximumEntry = entry;
			}
		}
		return maximumEntry.getKey();
	}

	private int parseYear(String string) {
	    int year = 0;
		try {
			year = Integer.parseInt(string.substring(0, 4));
			System.out.println("ParseYear:" + year);
			return year;
		} catch (NumberFormatException e) {
			System.err.println(e);
		}
		return year;
	}
	private void addIncidents(ArrayList<Incident> incidents){
		int year = 0;
		for (Incident element : incidents) {
			System.out.println("add incident" + element);
			year = parseYear(element.getDateAndTime());
			this.incidents[year - ripleyMinYear].add(element);
		}
	}

	private void initCaching() {
		ArrayList<Incident> incidents = new ArrayList<Incident>();
		long startTime = System.currentTimeMillis();

		if (currentStartYear < indexStartYear && indexEndYear < currentEndYear) {
			incidents = grabData(currentStartYear, currentEndYear);
			 System.out.println("1"); //Debugging, delete later
			addIncidents(incidents);
			indexStartYear = currentStartYear;
			indexEndYear = currentEndYear;
		} else if (currentStartYear < indexStartYear) {
			incidents = grabData(currentStartYear, indexStartYear - 1);
			addIncidents(incidents);
			indexStartYear = currentStartYear;
			 System.out.println("2"); //Debugging, delete later
		} else if (ripleyMaxYear < currentEndYear) {
			incidents = grabData(indexEndYear + 1, currentEndYear);
			addIncidents(incidents);
			indexEndYear = currentEndYear;
			 System.out.println("3"); //Debugging, delete later
		}

		long duration = System.currentTimeMillis() - startTime;
		System.out.println("init cache" + incidents.toString());
		notifyDuration(duration);
	}

	private ArrayList<Incident> grabData(int currentStartYear, int currentEndYear) {
		String start = currentStartYear + "-01-01 00:00:00";
		String end = currentEndYear + "-12-31 23:59:59";
		return ripley.getIncidentsInRange(start, end);
	}

	private void notifyDuration(long duration) {
		int h = (int) ((duration / 1000) / 3600);
		int m = (int) (((duration / 1000) / 60) % 60);
		int s = (int) ((duration / 1000) % 60);
		setChanged();
		if (h == 0) {
			notifyObservers("DATA" + " " + m + " minutes, " + s + " seconds");
		} else {
			notifyObservers("DATA" + " " + h + " hours, " + m + " minutes, " + s + " seconds");
		}
	}

	private void notifyYear() {
		setChanged();
		if (currentStartYear != 0 && currentStartYear <= currentEndYear) {
			notifyObservers(currentStartYear + " " + currentEndYear);
			initCaching();

		} else if (currentStartYear != 0) {
			notifyObservers("wrongStart");
		}
	}

	public void notifySavePanel() {
		setChanged();
		notifyObservers("SAVE");
	}
}
