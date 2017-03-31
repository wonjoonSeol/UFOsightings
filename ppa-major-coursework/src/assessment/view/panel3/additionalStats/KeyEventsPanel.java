package assessment.view.panel3.additionalStats;

import assessment.controller.panel3.StatController;
import assessment.model.panel3.StatsModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by wonjoonseol on 26/03/2017.
 */
public class KeyEventsPanel extends JPanel implements Observer{

    private StatsModel statsModel;
    private JButton jbNext;
    private JLabel jlInformation;

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

    public String setString(int year, String title, String description) {
        String htmlString;
        htmlString = "<html><div Style='text-align: center;'> <font color=\"Gray\", size = \"6\">Did </font>you know that in " +
                "<b>" + year + "</b>,<br>" +
                "<i>" + title + "?</i><br><br>" + description + ".</font></div></html>";
        return htmlString;
    }

    public void requestText() {
        statsModel.sendRandomInformation();
    }

    @Override
    public void update(Observable o, Object arg) {
        String information = (String) arg;
        String[] informationParts = information.split(";");
        if (informationParts[0].equals("Information")) {
            jlInformation.setText(setString(Integer.parseInt(informationParts[1]), informationParts[2], informationParts[3]));
        }
    }
}

