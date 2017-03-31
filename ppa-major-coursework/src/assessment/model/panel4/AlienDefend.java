package assessment.model.panel4;

import java.util.Observable;

/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This model represents back-end for the alien defending simulator game
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class AlienDefend extends Observable{
    private String commanderName;

    public void requestMessage() {
        setChanged();
        notifyObservers("Request Message");
    }

    /**
     * This method sets commander name
     * @param name Commander name
     */
    public void setCommanderName(String name) {
        commanderName = name;
    }
}
