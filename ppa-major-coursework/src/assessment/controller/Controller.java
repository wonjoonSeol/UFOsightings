package assessment.controller;

import assessment.model.Model;
import assessment.panel3.view.StatPanel;
import assessment.view.UFOFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;

/**
 * Created by wonjoonseol on 05/03/2017.
 */
public class Controller implements ActionListener, ComponentListener{


    private Model model;
    private UFOFrame view;
    private BufferedWriter saveWriter;

    public Controller(Model model) {
        this.model = model;
    }

    public void addWriter(BufferedWriter writer){
        saveWriter = writer;
    }

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

    public void setView(UFOFrame view) {
        // don't abuse having instance of view in this class,
        // this should really be used for cardLayout only
        // for further info: https://keats.kcl.ac.uk/mod/forum/discuss.php?d=108103
        this.view = view;
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        model.notifySavePanel();
    }
}
