package assessment.model;

import java.util.*;
import api.ripley.Incident;
import api.ripley.Ripley;

import javax.swing.*;

/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This model class manages caching and pulling data from Ripley API.
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
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

	/**
	 * Constructor Model constructs with Ripley API. This initialises important fields to be used in caching.
	 *
	 * @param ripley Ripley api
	 */
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

	/**
	 * This method sets start year of the data to query
	 *
     * @param year starting year
	 */
	public void setStartYear(int year) {
		currentStartYear = year;
		notifyYear();
	}

	/**
	 * This method sets end year of the data to query
	 *
	 * @param year ending year
	 */
	public void setEndYear(int year) {
		currentEndYear = year;
		notifyYear();
	}

	/**
	 * This method returns earliest year of UFO sighting report
	 *
     * @return int earliest year of UFO sighting report
	 */
	public int getRipleyMinYear() {
		return ripleyMinYear;
	}

	/**
	 * This method returns the most recent year of UFO sighting report
	 *
	 * @return int the most year of UFO sighting report
	 */
	public int getRipleyMaxYear() {
		return ripleyMaxYear;
	}

	/**
	 * getRequestedData() Method provides cached list of UFO sightings data.
	 * This will be primarily used by other models to create useful statistics
	 *
	 * @return ArrayList<Incident> cached list of UFO sightings data in the set interval
	 */
	public ArrayList<Incident> getRequestedData() {
		return currentList;
	}

	/**
	 * This method is used to calculate time taken to load data.
	 *
	 * @param endTime long Time finished loading
	 * @return time taken to load data
	 */
	public long timeTakenToLoad(long endTime) {
		return endTime - startTime;
	}

	/**
	 * initCaching() is responsible for caching UFO sightings data from ripley server.
	 * This operates by checking start and end index of the currently cached array
	 * and skips fetching duplicate data from the API.
	 *
	 * The array indexes are linearly proportional to year date.
	 * Therefore similar mapping functionality were achieved without relying on the more complex
	 * and hence slower data structure such as treemap.
	 *
	 * Please note that this method is run on SwingWorker thread.
	 * One particular benefit of this is to free widgets from hang due to EDT thread being busy.
	 * Furthermore, paintComponent() and repaint() methods can now be invoked
	 * to implement loading animation or image is desired.
	 *
	 * @return int the most year of UFO sighting report
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/SwingWorker.html" target="_top">SwingWorker</a>
	 */
	public void initCaching() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {				// Initalising SwingWorker thread
				setChanged();
				notifyObservers("loadingStart");

				ArrayList<Incident> incidents = new ArrayList<Incident>();
				startTime = System.currentTimeMillis();
				if (currentStartYear < indexStartYear && indexEndYear < currentEndYear) { 		// No data exists in the given interval
					incidents = grabData(currentStartYear, currentEndYear);
					addIncidents(incidents);
					indexStartYear = currentStartYear;
					indexEndYear = currentEndYear;
				} else if (currentStartYear < indexStartYear) {
					incidents = grabData(currentStartYear, indexStartYear - 1);   // Only need to fetch earlier year data
					addIncidents(incidents);
					indexStartYear = currentStartYear;
				} else if (indexEndYear < currentEndYear) {
					incidents = grabData(indexEndYear + 1, currentEndYear);
					addIncidents(incidents);
					indexEndYear = currentEndYear;
				}
				currentList = createCurrentList();
				setChanged();
				notifyObservers("Data");													// Notify to the viewer data is available.
				return null;
			}
		}.execute();
	}

	/**
	 * This private method is used to create an ArrayList consisting only of the supplied date range from the local cache.
	 *
	 * @return ArrayList to be used in this supplied date interval
	 */
	private ArrayList<Incident> createCurrentList() {
		currentList.clear();
		int endIndex = currentEndYear - ripleyMinYear;
		int startIndex = currentStartYear - ripleyMinYear;
		for (int i = startIndex; i <= endIndex; i++) {
			currentList.addAll(incidents[i]);
		}
		return currentList;
	}

	/**
	 * This private method supplements initCaching() method.
	 * Since the data returned from the Ripley API is unsorted,
	 * This method goes through each incident in the provided Arraylist and allocate each to suitable index in our cache
	 *
	 * @param incidents retrieved incident data from Ripley API
	 */
	private void addIncidents(ArrayList<Incident> incidents) {
		int year = 0;
		for (Incident element : incidents) {
			year = parseYear(element.getDateAndTime());
			this.incidents[year - ripleyMinYear].add(element);
		}
	}

	/**
	 * This private method is used to parse year data out of entire date from the Ripley data.
	 * Used to caculate index to be stored.
	 *
	 * @param string Date string
	 * @return int Year
	 */
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
     * Grabs data from Ripley API using supplied dates
	 *
	 * @param currentStartYear int starting year to fetch data
	 * @param currentEndYear int end year to fetch data
	 */
	private ArrayList<Incident> grabData(int currentStartYear, int currentEndYear) {
		return ripley.getIncidentsInRange(appendStartYear(currentStartYear), appendEndYear(currentEndYear));
	}

	/**
	 * This static method appends month and days to the supplied year
	 * This is used to set beginning date for Ripley data retrieval
	 *
	 * @param year int to append january 1st
	 * @return String value of starting date
	 */
	public static String appendStartYear(int year) {
		String start = year + "-01-01 00:00:00";
		return start;
	}

	/**
	 * This private method appends month and days to the supplied year
	 * This is used to set end date for Ripley data retrieval
	 *
	 * @param year int to append december 31st.
	 * @return String value of ending date
	 */
	public static String appendEndYear(int year) {
		String end = year + "-12-31 23:59:59";
		return end;
	}

	/**
	 * calculateDuration method calculates miliseconds in to hours, minute and seconds format
	 *
	 * @param duration miliseconds
	 * @return "m minues, s seconds" or "h hour, m minues, s seconds" if takes longer than 1 hour
	 */
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

	/**
	 * This method notifies viewer on valid date range
	 * and intialises initCaching if the supplied date is valid
	 */
	private void notifyYear() {
		setChanged();
		if (currentStartYear != 0 && currentStartYear <= currentEndYear) {
			notifyObservers(currentStartYear + " " + currentEndYear);		// correct date. Initilise caching
			initCaching();

		} else if (currentStartYear != 0) {
			notifyObservers("wrongStart");		// Starting date is not set
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