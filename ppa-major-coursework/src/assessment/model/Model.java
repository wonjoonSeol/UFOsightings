package assessment.model;

import java.util.*;
import api.ripley.Incident;
import api.ripley.Ripley;

import javax.swing.*;

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
	private long startTime;

	public Model(Ripley ripley) {
		this.ripley = ripley;
		ripleyMinYear = ripley.getStartYear();
		ripleyMaxYear = ripley.getLatestYear();
		indexEndYear = Integer.MIN_VALUE;
		indexStartYear = Integer.MAX_VALUE;
		incidents = new ArrayList[ripleyMaxYear - ripleyMinYear + 1];
		currentList = new ArrayList<Incident>();

		for (int i = 0; i < ripleyMaxYear - ripleyMinYear + 1; i++) {
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

	public long timeTakenToLoad(long endTime) {
		return endTime - startTime;
	}

	public void initCaching() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				setChanged();
				notifyObservers("loadingStart");

				ArrayList<Incident> incidents = new ArrayList<Incident>();
				startTime = System.currentTimeMillis();
				if (currentStartYear < indexStartYear && indexEndYear < currentEndYear) {
					incidents = grabData(currentStartYear, currentEndYear);
					addIncidents(incidents);
					indexStartYear = currentStartYear;
					indexEndYear = currentEndYear;
				} else if (currentStartYear < indexStartYear) {
					incidents = grabData(currentStartYear, indexStartYear - 1);
					addIncidents(incidents);
					indexStartYear = currentStartYear;
				} else if (indexEndYear < currentEndYear) {
					incidents = grabData(indexEndYear + 1, currentEndYear);
					addIncidents(incidents);
					indexEndYear = currentEndYear;
				}
				currentList = createCurrentList();
				setChanged();
				notifyObservers("Data");
				return null;
			}
		}.execute();
	}

	private ArrayList<Incident> createCurrentList() {
		currentList.clear();
		int endIndex = currentEndYear - ripleyMinYear;
		int startIndex = currentStartYear - ripleyMinYear;
		for (int i = startIndex; i <= endIndex; i++) {
			currentList.addAll(incidents[i]);
		}
		return currentList;
	}

	private void addIncidents(ArrayList<Incident> incidents) {
		int year = 0;
		for (Incident element : incidents) {
			year = parseYear(element.getDateAndTime());
			this.incidents[year - ripleyMinYear].add(element);
		}
	}

	private int parseYear(String string) {
	    int year = 0;
		try {
			year = Integer.parseInt(string.substring(0, 4));
			return year;
		} catch (NumberFormatException e) {
			System.err.println(e);
		}
		return year;
	}

	/**
	 * Adds to the arraylist of incidents for each year,
	 * data from the specified time range
	 * @param currentStartYear
	 * @param currentEndYear
	 */
	private ArrayList<Incident> grabData(int currentStartYear, int currentEndYear) {
		return ripley.getIncidentsInRange(appendStartYear(currentStartYear), appendEndYear(currentEndYear));
	}


	public static String appendStartYear(int year) {
		String start = year + "-01-01 00:00:00";
		return start;
	}

	public static String appendEndYear(int year) {
		String end = year + "-12-31 23:59:59";
		return end;
	}

	public static String calculateDuration(long duration) {
		int h = (int) ((duration / 1000) / 3600);
		int m = (int) (((duration / 1000) / 60) % 60);
		int s = (int) ((duration / 1000) % 60);

		if (h == 0) {
			return m + " minutes, " + s + " seconds";
		} else {
			return h + " hours, " + m + " minutes, " + s + " seconds";
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