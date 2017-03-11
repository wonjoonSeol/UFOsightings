package assessment.controller;

import assessment.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by wonjoonseol on 05/03/2017.
 */
public class Controller implements ActionListener{

    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox combo = (JComboBox)e.getSource();

        if (!combo.getSelectedItem().equals("-")) {
            if (combo.getName().equals("fromCombo")) {
                String from = (String) combo.getSelectedItem();
                model.setStartYear(Integer.parseInt(from));

            } else if (combo.getName().equals("toCombo")) {
                String to = (String) combo.getSelectedItem();
                model.setEndYear(Integer.parseInt(to));
            }
        }
    }
}
