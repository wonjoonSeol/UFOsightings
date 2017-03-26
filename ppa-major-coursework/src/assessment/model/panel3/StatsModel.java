package assessment.model.panel3;

import api.ripley.Incident;
import assessment.model.Model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Created by wonjoonseol on 26/03/2017.
 */
public class StatsModel extends Observable {
    private ArrayList<String> information;
    private Model model;

    public StatsModel(Model model) {
        super();
        this.model = model;
        information = new ArrayList<String>();
        setInformation();
    }

    private void setInformation() {
        information.add("1947;is when 'Rosewell UFO incident happend");
        information.add("2008;is when 'Turkey UFO sightings happened");
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
}
