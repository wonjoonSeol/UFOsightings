package assessment.view.panel3.additionalStats;

import assessment.controller.panel3.StatController;
import assessment.model.panel3.StatsModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This class represents JPanel viewer for Key events in the history statistics
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class KeyEventsPanel extends JPanel implements Observer{

    private StatsModel statsModel;
    private JButton jbNext;
    private JLabel jlInformation;

    /**
     * Constructor KeyEventsPanel constructs with StatsController and statsModel
     *
     * @param statController StatController
     * @param statsModel StatsModel
     */
    public KeyEventsPanel(StatController statController, StatsModel statsModel) {
        super();
        this.statsModel = statsModel;
        setLayout(new BorderLayout());

        jbNext = new JButton("Next");
        jbNext.setName("wjButton");
        jbNext.addActionListener(statController);
        jlInformation = new JLabel();

        add(jlInformation, BorderLayout.CENTER);
        add(jbNext, BorderLayout.PAGE_END);
    }

    /**
     * This method sets key events information with html tags to be displayed in jlInformation label.
     *
     * @param year int year happened
     * @param title key event title
     * @param description extra details with some statistics
     */
    public String setString(int year, String title, String description) {
        String htmlString;
        htmlString = "<html><div Style='text-align: center;'> <font color=\"Gray\", size = \"6\">Did </font>you know that in " +
                "<b>" + year + "</b>,<br>" +
                "<i>" + title + "?</i><br><br>" + description + ".</font></div></html>";
        return htmlString;
    }

    /**
     * This method requests statsModel to send random key events
     */
    public void requestText() {
        statsModel.sendRandomInformation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Observable o, Object arg) {
        String information = (String) arg;
        String[] informationParts = information.split(";");
        if (informationParts[0].equals("Information")) {
            jlInformation.setText(setString(Integer.parseInt(informationParts[1]), informationParts[2], informationParts[3]));
        }
    }
}

