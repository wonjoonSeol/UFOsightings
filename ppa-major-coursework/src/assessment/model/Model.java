package assessment.model;

import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

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
	private ArrayList<Incident> currentList;
	private Ripley ripley;

	public Model(Ripley ripley) {
		this.ripley = ripley;
		ripleyMinYear = ripley.getStartYear();
		ripleyMaxYear = ripley.getLatestYear();
		indexEndYear = Integer.MIN_VALUE;
		indexStartYear = Integer.MAX_VALUE;
		incidents = new ArrayList[ripleyMaxYear - ripleyMinYear];
		currentList = new ArrayList<Incident>();

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

	public ArrayList<Incident> getRequestedData() {
		return currentList;
	}

	public int getIncidentNumbers(int year) {
		if (year > indexStartYear && year < indexEndYear) {
			return incidents[year - getRipleyMinYear()].size();
		}
		return -1;
	}

	public ArrayList<Incident> createCurrentList() {
		int endIndex = currentEndYear - ripleyMinYear;
		System.out.println("endIndex:" + endIndex);
		int startIndex = currentStartYear - ripleyMinYear;
		System.out.println("startIndex:" + startIndex);
		ArrayList<Incident> incidents = new ArrayList<Incident>();
		if (endIndex <= this.incidents.length && 0 <= startIndex) {
			for (ArrayList<Incident> list : this.incidents) {
				if (!list.isEmpty()) {
					incidents.addAll(list);
				}
			}
		}
		System.out.println("requested data:" + incidents);
		return incidents;
	}

//	private void addIncidents(ArrayList<Incident> incidents) {
//		int year = 0;
//		for (Incident element : incidents) {
//			System.out.println("add incident" + element);
//			year = parseYear(element.getDateAndTime());
//			this.incidents[year - ripleyMinYear].add(element);
//		}
//	}

//	private int parseYear(String string) {
//	    int year = 0;
//		try {
//			year = Integer.parseInt(string.substring(0, 4));
//			System.out.println("ParseYear:" + year);
//			return year;
//		} catch (NumberFormatException e) {
//			System.err.println(e);
//		}
//		return year;
//	}

	private void initCaching() {
		ArrayList<Incident> incidents = new ArrayList<Incident>();
		long startTime = System.currentTimeMillis();

		if (currentStartYear < indexStartYear && indexEndYear < currentEndYear) {
			grabData(currentStartYear, currentEndYear);
			 System.out.println("1"); //Debugging, delete later
			indexStartYear = currentStartYear;
			indexEndYear = currentEndYear;
		} else if (currentStartYear < indexStartYear) {
			grabData(currentStartYear, indexStartYear - 1);
			indexStartYear = currentStartYear;
			 System.out.println("2"); //Debugging, delete later
		} else if (indexEndYear < currentEndYear) {
			grabData(indexEndYear + 1, currentEndYear);
			indexEndYear = currentEndYear;
			 System.out.println("3"); //Debugging, delete later
		}
		currentList.clear();
		currentList = createCurrentList();
		long duration = System.currentTimeMillis() - startTime;
		notifyDuration(duration);
	}

	private void grabData(int currentStartYear, int currentEndYear) {
		if (currentStartYear == currentEndYear) {
			incidents[currentStartYear - ripleyMinYear] = ripley.getIncidentsInRange(appendStartYear(currentStartYear),
														appendEndYear(currentEndYear));
		} else {
			for (int i = currentStartYear; i < currentEndYear; i++) {
				incidents[i - ripleyMinYear] = ripley.getIncidentsInRange(appendStartYear(i), appendEndYear(i));
			}
		}
	}

	public static String appendStartYear(int year) {
		String start = year + "-01-01 00:00:00";
		return start;
	}

	public static String appendEndYear(int year) {
		String end = year + "-12-31 23:59:59";
		return end;
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
	
	/** 
	 * Returns the start of the selected date range
	 * @return int start year of selected range
	 */
	public int getCurrentStartYear() {
		return currentStartYear; 
	}
	
	/** 
	 * Returns the end of the selected date range
	 * @return int end year of selected range
	 */
	public int getCurrentEndYear() {
		return currentEndYear; 
	}
	
}