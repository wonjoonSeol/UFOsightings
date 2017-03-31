package assessment.controller;

import assessment.model.Model;
import assessment.view.UFOFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This class represents the main viewer frame
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class Controller implements ActionListener{


    private Model model;
    private UFOFrame view;
    /**
     * Constructor to instantiate new Controller object and set fields.
     * @param model
     */
    public Controller(Model model) {
        this.model = model;
    }

    /**
     * Method to handle year selection and update model.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JComboBox) {
            JComboBox combo = (JComboBox) e.getSource();

            if (!combo.getSelectedItem().equals("-")) {
                if (combo.getName().equals("fromCombo")) {
                    String from = (String) combo.getSelectedItem();
                    model.setStartYear(Integer.parseInt(from));

                } else if (combo.getName().equals("toCombo")) {
                    String to = (String) combo.getSelectedItem();
                    model.setEndYear(Integer.parseInt(to));
                }
            }
        } else {
            JButton button = (JButton) e.getSource();
            if (button.getName().equals("jbLeft")) {
                view.previousCenterPanel();
            } else if (button.getName().equals("jbRight")) {
                view.nextCenterPanel();
            }
        }
    }

    /**
     * Method to set the view private instance variable.
     * @param view
     */
    public void setView(UFOFrame view) {
        // don't abuse having instance of view in this class,
        // this should really be used for cardLayout only
        // for further info: https://keats.kcl.ac.uk/mod/forum/discuss.php?d=108103
        this.view = view;
    }
}
