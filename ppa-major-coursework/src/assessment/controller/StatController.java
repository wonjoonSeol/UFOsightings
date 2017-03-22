package assessment.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import assessment.panel3.view.SubStatPanel;

public class StatController implements ActionListener{


	private SubStatPanel view;
	
	public StatController(SubStatPanel v)
	{
		view = v;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		// Check button source.
		JButton sourceButton = (JButton)arg0.getSource();
		if(sourceButton.getText().equals("<"))
			view.setStat(view.getStat() -1);
		else if(sourceButton.getText().equals(">"))
			view.setStat(view.getStat() + 1);
	}

}
