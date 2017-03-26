package assessment.controller.panel3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import assessment.model.Model;
import assessment.view.panel3.StatPanel;
import assessment.view.panel3.SubStatPanel;

public class StatController implements ActionListener{


	private SubStatPanel view;
	private StatPanel panel3;

	public StatController(SubStatPanel v, StatPanel panel3)
	{
		view = v;
		this.panel3 = panel3;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		// Check button source.
		JButton sourceButton = (JButton)arg0.getSource();
		if(sourceButton.getText().equals("<")) {
			view.setStat(view.getStat() - 1);
			panel3.panelSave();
		} else if(sourceButton.getText().equals(">")) {
			view.setStat(view.getStat() + 1);
			panel3.panelSave();
		}
	}

}
