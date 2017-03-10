package assessment.model;

import java.util.ArrayList;
import java.util.Observable;
import api.ripley.Incident;
import api.ripley.Ripley;

/**
 * Created by wonjoonseol on 09/03/2017.
 */
public class Model extends Observable{
    private String startYear;
    private String endYear;
    private ArrayList<Incident> incidents;
    private Ripley ripley;

    public Model(Ripley ripley) {
        incidents = new ArrayList<Incident>();
        this.ripley = ripley;
    }

    public void setStartYear(String year) {
        startYear = year;
        setChanged();
        if (endYear != null && Integer.parseInt(endYear) >= Integer.parseInt(startYear)) {
            notifyObservers(startYear + " " + endYear);
        } else if (endYear != null) {
            notifyObservers("wrongStart");
        }
    }

    public void setEndYear(String year) {
        endYear = year;
        setChanged();
        if (startYear != null && Integer.parseInt(endYear) >= Integer.parseInt(startYear)) {
            notifyObservers(startYear + " " + endYear);
        } else if (startYear != null) {
            notifyObservers("wrongStart");
        }
    }

    public void grabData() {
        long startTime = System.currentTimeMillis();
        String start = startYear + "-01-01 00:00:00";
        String end = endYear + "-12-31 23:59:59";
        incidents = ripley.getIncidentsInRange(start, end);

        long duration = System.currentTimeMillis() - startTime;

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

    public boolean isBothValueCorrect() {
        if (startYear != null && endYear != null && Integer.parseInt(endYear) >= Integer.parseInt(startYear)) return true;
        return false;
    }
}
