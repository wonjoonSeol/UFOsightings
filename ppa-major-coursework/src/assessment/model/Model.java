package assessment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import api.ripley.Incident;
import api.ripley.Ripley;

/**
 * Created by wonjoonseol on 09/03/2017.
 */
public class Model extends Observable{
    private int currentStartYear;
    private int currentEndYear;
    private int minYear;
    private int maxYear;
    private ArrayList<Incident> incidents;
    private Ripley ripley;

    public Model(Ripley ripley) {
        this.ripley = ripley;
        incidents = new ArrayList<Incident>();
        minYear = Integer.MAX_VALUE;
        maxYear = Integer.MIN_VALUE;
    }

    public void setStartYear(int year) {
        currentStartYear = year;
        notifyYear();
    }

    public void setEndYear(int year) {
        currentEndYear = year;
        notifyYear();
    }

    public List<Incident> getRequestedData() {
        // Currently the data returned from Ripley is unsorted. So this doesn't work.
        // Eugene can do this while implementing 'Sorting Sightings' in the brief?
        int startIndex = currentStartYear - minYear;
        int endIndex = incidents.size() - (maxYear - currentEndYear) - 1;
        return incidents.subList(startIndex, endIndex);
    }

    private void initCaching() {
        ArrayList<Incident> incidents = new ArrayList<Incident>();
        long startTime = System.currentTimeMillis();

        if (currentStartYear < minYear && maxYear < currentEndYear || this.incidents.size() == 0) {
            this.incidents = grabData(currentStartYear, currentEndYear);
//            System.out.println("1");              //Debugging, delete later
            minYear = currentStartYear;
            maxYear = currentEndYear;
        } else if (currentStartYear < minYear) {
            incidents = grabData(currentStartYear, minYear - 1);
            this.incidents.addAll(0, incidents);
            minYear = currentStartYear;
//            System.out.println("2");              //Debugging, delete later
        } else if (maxYear < currentEndYear) {
            incidents = grabData(maxYear + 1, currentEndYear);
            this.incidents.addAll(this.incidents.size() - 1, incidents);
            maxYear = currentEndYear;
//            System.out.println("3");              //Debugging, delete later
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println(this.incidents);
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
}
