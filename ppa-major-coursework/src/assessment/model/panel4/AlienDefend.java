package assessment.model.panel4;

import java.util.Observable;

/**
 * Created by wonjoonseol on 26/03/2017.
 */
public class AlienDefend extends Observable{
    private String commanderName;

    public void requestMessage() {
        setChanged();
        notifyObservers("Request Message");
    }

    public void setCommanderName(String name) {
        commanderName = name;
    }
}
