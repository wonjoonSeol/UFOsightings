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

	private void updateCurrentList() {
		currentList.clear();
		int endIndex = currentEndYear - ripleyMinYear;
		int startIndex = currentStartYear - ripleyMinYear;
		for (int i = startIndex; i <= endIndex; i++) {
			currentList.addAll(incidents[i]);
		}
//		System.out.println("After update current list: " + currentList);
//		System.out.println("requested data:" + incidents);
	}

	private void initCaching() {
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
		updateCurrentList();
		long duration = System.currentTimeMillis() - startTime;
		notifyDuration(duration);
	}

	/** 
	 * Adds to the arraylist of incidents for each year, 
	 * data from the specified time range
	 * @param currentStartYear
	 * @param currentEndYear
	 */
	private void grabData(int currentStartYear, int currentEndYear) {
		if (currentStartYear == currentEndYear) {
			incidents[currentStartYear - ripleyMinYear] = ripley.getIncidentsInRange(appendStartYear(currentStartYear),
														appendEndYear(currentEndYear));
		} else {
			for (int i = currentStartYear; i <= currentEndYear; i++) {
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