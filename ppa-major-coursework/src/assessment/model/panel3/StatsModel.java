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
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This model class manages statistics calculation on data returned from Ripley API.
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class StatsModel extends Observable {
    private ArrayList<String> information;
    private Model model;
    private Ripley ripley;
    private double percentage;
    private int difference;
    private int previousRandomNumber;
    private int numberOfHoax;
    private int nonUSsight;
	private double stateSideCount;
	private	double internCount;
	private String distributionPercentage;

/**
* Constructor StatsModel constructs with Ripley API. This initialises important fields to be used in statistics retrieval and calculation.
*
* @param ripley Ripley api
* @param model Model data
*/
    public StatsModel(Model model, Ripley ripley) {
        super();
        this.ripley = ripley;
        this.model = model;
        information = new ArrayList<String>();
        setInformation();
        previousRandomNumber = -1;
    }
    /**
    * Method to set all results for Wonjoon Seol's additional statistic.
    */
    private void setInformation() {
        calculateChange(1947);
        information.add("1947;Rosewell UFO Crash happened;US Airforce allegedly captured a crashed UFO and the aline. This was widely covered by the media and has "+ (int)percentage + "% increase in the number of reports from the previous year");
		calculateChange(2008);
        information.add("2008;Turkey UFO sightings happened;This resulted in " + difference + " more incidents from its previous years. This year also has the highest number of hoax reported");
		calculateChange(1995);
		information.add("1995;South Africa UFO Flap happened;A UFO flap swept South Africa from late March to mid April, widely covered by the media. Number of incident reports increased by " + (int)percentage + "%");
		calculateChange(1952);
		information.add("1952;Washington, D.C. UFO incident happened;There were radar contacts at three separate airports in the Washington area. This lead to formation of the Robertson Panel by the CIA. Number of incident reports increased by " + (int)percentage + "%");
		calculateChange(1954);
		information.add("1954;European UFO wave happened;First large-scale European UFO wave, Most occurred near France followed by Italy. Reported increased by " + (int)percentage + "%");
		calculateChange(1957);
		information.add("1957;Antonio Vilas Boas Abduction happened;Vilas Boas' claims were among the first alien abduction stories to receive wide attention. Reported incidents increased by " + (int)percentage + "%");
		calculateChange(1966);
		information.add("1966;Star Trek franchise started;Unfortunately, there are insufficient data to prove that this lead to higher reported incidents");
		calculateChange(1985);
		information.add("1985;Carl Sagan's Contact published;Unfortunately, there are insufficient data to prove that this lead to higher reported incidents. Reported sightings were increased by " + difference);
		calculateChange(1977);
		information.add("1977;Star War franchise started;This year, the number of reportings were " + (-difference) + " less than the previous year");
    }

   /**
   * Method to calculate incident number changes after key events for Wonjoon Seol's individual statistic.
   * @param year
   */
    private void calculateChange(int year) {
		ArrayList<Incident> previousIncidents = ripley.getIncidentsInRange(Model.appendStartYear(year-1), model.appendEndYear(year-1));
		ArrayList<Incident> incidents = ripley.getIncidentsInRange(Model.appendStartYear(year), model.appendEndYear(year));
		percentage = (double) (incidents.size() - previousIncidents.size()) / previousIncidents.size() * 100;
		difference = incidents.size() - previousIncidents.size();
	}

   /**
   * Method to send a random String of information to be displayed for Wonjoon Seol's individual statistic.
   */
    public void sendRandomInformation() {
        int index;
    	do {
			index = (int) (Math.random() * information.size());
		} while (previousRandomNumber == index);
    	previousRandomNumber = index;
        setChanged();
        notifyObservers("Information;" + information.get(index));
    }

    /**
     * Method to calculate all statistics for the statistic panel once, and set fields accordingly.
     */
    public void calculateStats() {
    	numberOfHoax = 0;
    	nonUSsight = 0;
    	stateSideCount = 0;
    	internCount = 0;

		List<Incident> data = model.getRequestedData();
    	for (Incident i : data) {
			if (i.getState().equals("Not specified.")) {
				nonUSsight++;
				internCount++;
			} else {
				stateSideCount++;
			}
			if (i.getSummary().toLowerCase().contains("hoax")) numberOfHoax++;
		}
		calculateDistributionPercentage(data.size());
    	setChanged();
    	notifyObservers("Data");
	}

    /**
     * Method to calculate stateside versus international sightings for Britton Forsyth's individual statistic.
     * @param size
     */
	private void calculateDistributionPercentage(int size) {
		String returnString = "";
		if(size > 0) {
			double statePercentage =  (stateSideCount / size) * 100;
			double internPercentage = (internCount / size) * 100;
			String stateDM = new DecimalFormat("#.##").format(statePercentage);   //
			String interDM = new DecimalFormat("#.##").format(internPercentage);
			returnString = "State: " + stateDM + "%" + "<br>" + "International: " + interDM + "%";
		} else {
			returnString = "No incidents found in time period!";
		}
		distributionPercentage = returnString;
	}

	
	/**
	 * Method to return youtube sightings figures.
	 * @return String total videos uploaded in past week
	 * @throws Exception
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
				    String htmlString;
				    htmlString = "<html><div Style='text-align: center;'> <font color=\"Gray\", size = \"6\">YouTube Videos </font>uploaded in the past week: " +
				                "<i></i><br><br>" + totalResults + ".</font></div></html>";
				    return htmlString;
				}		  		
			}
		}
		rd.close();
		return "";
	}
	/** 
	 * Returns the start of the selected date range
	 * @return int start year of selected range
	 */
	public int getCurrentStartYear() {
		return model.getCurrentStartYear(); 
	}
	/** 
	 * Returns the end of the selected date range
	 * @return int end year of selected range
	 */
	public int getCurrentEndYear() {
		return model.getCurrentEndYear(); 
	}

	/**
	 * Returns the number of hoaxes in selected date range
	 * @return int number of hoaxes
	 */
	public int getNumberOfHoax() {
		return numberOfHoax;
	}

	/**
	 * Returns the number of non-US sightings in selected date range
	 * @return int number of non-US sightings
	 */
	public int getNonUSsight() {
		return nonUSsight;
	}

	/**
	 * Returns the distribution percentage of stateSide vs International sightings in selected date range
	 * @return String worldwide distribution percentage
	 */
	public String getDistributionPercentage() {
		return distributionPercentage;
	}
}
