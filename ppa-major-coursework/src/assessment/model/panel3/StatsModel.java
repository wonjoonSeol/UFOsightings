package assessment.model.panel3;

import api.ripley.Incident;
import api.ripley.Ripley;
import assessment.model.Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wonjoonseol on 26/03/2017.
 */
public class StatsModel extends Observable {
    private ArrayList<String> information;
    private Model model;
    private Ripley ripley;
    private double percentage;
    private int difference;

    public StatsModel(Model model, Ripley ripley) {
        super();
        this.ripley = ripley;
        this.model = model;
        information = new ArrayList<String>();
        setInformation();
    }

    private void setInformation() {
        calculateChange(1947);
        information.add("1947;Rosewell UFO Crash happened;US Airforce allegedly captured a crashed UFO and the aline. This was widely covered by the media and has "+ percentage + "% increase in the number of reports from the previous year");
		calculateChange(2008);
        information.add("2008;Turkey UFO sightings happened;This resulted in " + difference + " more incidents from its previous years. This year also has the highest number of hoax reported");
		calculateChange(1995);
		information.add("1995;South Africa UFO Flap happened;A UFO flap swept South Africa from late March to mid April, widely covered by the media. Number of incident reports increased by " + percentage + "%");
		calculateChange(1952);
		information.add("1952;Washington, D.C. UFO incident happened;There were radar contacts at three separate airports in the Washington area. This lead to formation of the Robertson Panel by the CIA. Number of incident reports increased by " + percentage + "%");
		calculateChange(1954);
		information.add("1954;European UFO wave happened;First large-scale European UFO wave, Most occurred near France followed by Italy. Reported increased by " + percentage + "%");
		calculateChange(1957);
		information.add("1957;Antonio Vilas Boas Abduction happened;Vilas Boas' claims were among the first alien abduction stories to receive wide attention. Reported incidents increased by " + percentage + "%");
		calculateChange(1966);
		information.add("1966;Star Trek franchise started;Unfortunately, there are insufficient data to prove that this lead to higher reported incidents");
		calculateChange(1985);
		information.add("1985;Carl Sagan's Contact published;Unfortunately, there are insufficient data to prove that this lead to higher reported incidents. Reported sightings were increased by " + difference);
		calculateChange(1966);
		information.add("1966;Star War franchise started;This year, the number of reportings were " + difference + " less than the previous year");
    }

    private void calculateChange(int year) {
		ArrayList<Incident> previousIncidents = ripley.getIncidentsInRange(Model.appendStartYear(year), model.appendEndYear(year));
		ArrayList<Incident> incidents = ripley.getIncidentsInRange(Model.appendStartYear(year), model.appendEndYear(year));
		percentage = (incidents.size() - previousIncidents.size()) / previousIncidents.size() * 100;
		difference = incidents.size() - previousIncidents.size();
	}

    public void sendRandomInformation() {
        int index = (int) (Math.random() * information.size());
        System.out.println("random num:" + index);
        setChanged();
        notifyObservers(information.get(index));
    }

	/*
	 * Return the number of hoaxes within the current dataset.
	 */
	public int getNumHoaxes() {
		List<Incident> data = model.getRequestedData();
		int count = 0;
		for (Incident i : data) { // Iterate through the incident list, increase
								// count if a HOAX match is found.
			if (i.getSummary().contains("HOAX")) {
				count++;
			}
		}
		return count; // Return counter variable.
	}

	/*
	 * BRITTON FORSYTH Individual Statistic.
	 * Formats for the user a ratio of international to USA stateside sightings recorded.
	 */
	public String countryDistributionPercentage() {
		String returnString = "";
		double stateSideCount = 0;
		double internCount = 0;
		List<Incident> data = model.getRequestedData();

		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		for (Incident i : data) // Iterate through the incident list {
			if (i.getState().equals("Not specified.")) {
				internCount++;
			} else {
				stateSideCount++;
		}

		if(data.size() > 0) {
		double statePercentage = (stateSideCount / data.size()) * 100;
		double internPercentage = (internCount/data.size()) * 100;
		String stateDM = new DecimalFormat("#.##").format(statePercentage);
		String interDM = new DecimalFormat("#.##").format(internPercentage);
		returnString = "State: " + stateDM + "%" + " and " + "International: " + interDM + "%";
		return returnString;

		} else {
			return "No incidents found in time period!";
		}
	}

	/*
	 * Return the number of non-US sightings within the current dataset.
	 */
	public int getNonUSSight() {
		List<Incident> data = model.getRequestedData();
		int count = 0;
		for (Incident i : data) { // Iterate through the incident list, increase
								// count if a Non-US match is found.
			if (i.getState().equals("Not specified.")) {
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
		List<Incident> data = model.getRequestedData();

		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		for (Incident i : data) {// Iterate through the incident list
			if (map.containsKey(i.getState())) {
				Integer temp = map.get(i.getState());
				map.put(i.getState(), temp + 1);
			} else {
				map.put(i.getState(), 1);
			}
		}

		Entry<String, Integer> maximumEntry = null;
		for (Entry<String, Integer> entry : map.entrySet()) {
			if (maximumEntry == null || entry.getValue().compareTo(maximumEntry.getValue()) > 0) {
				maximumEntry = entry;
			}
		}
		if(maximumEntry == null) {
			return "No state specified";
		}
		return maximumEntry.getKey();
	}
	/*
	 * EUGENE: Method to return youtube sightings figures.
	 */
public String getRequest() throws Exception {

		LocalDateTime dateCurrent = LocalDateTime.now();
		LocalDateTime oneWeekAgo = dateCurrent.minusWeeks(1);
		
		String myAPIKey = "AIzaSyBCKhRHvfbPRUHoxdJBfExiSjg9mFWYiFY";
		String source = "https://www.googleapis.com/youtube/v3/search";

		URL url = new URL(source + "?part=snippet&publishedAfter="+ oneWeekAgo +"Z&type=video&q=UFO&key="+ myAPIKey);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;

		while ((line = rd.readLine()) != null) {
			Pattern pattern = Pattern.compile("totalResults");
			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				Pattern pattern1 = Pattern.compile("[0-9]+");
				Matcher matcher1 = pattern1.matcher(line);
				if (matcher1.find()) {
					String totalResults = matcher1.group();
					System.out.println(totalResults);
					
					return totalResults;
				}		  		
			}
		}
		rd.close();
		return "";
	}
}