package assessment.controller.panel3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;

import assessment.model.panel3.StatsModel;
import assessment.model.panel3.YoutubeModel;
import assessment.view.panel3.StatPanel;
import assessment.view.panel3.SubStatPanel;
/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This controller class manages panel switching in the StatPanel statistics panel.
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class StatController implements ActionListener, MouseListener {


	private SubStatPanel view;
	private StatPanel panel3;
	private StatsModel statsModel;
	private YoutubeModel youtubeModel;

	/**
	 * Constructor to set field values and create a new instance of StatController.
	 * @param view
	 * @param panel3
	 * @param keyEvent
	 * @param youtubeModel
	 */
	public StatController(SubStatPanel view, StatPanel panel3, StatsModel keyEvent, YoutubeModel youtubeModel) {
		this.view = view;
		this.youtubeModel = youtubeModel;
		this.panel3 = panel3;
		this.statsModel = keyEvent;
	}

	/**
	 * Method to detect button source, switch panel, and save the new statistic to the panel3 field reference.
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		// Check button source.
		JButton sourceButton = (JButton)arg0.getSource();
		if(sourceButton.getText().equals("<")) {
			try {
				view.setStat(view.getStat() - 1, SubStatPanel.DECREMENT);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			panel3.panelSave();
		} else if(sourceButton.getText().equals(">")) {
			try {
				view.setStat(view.getStat() + 1, SubStatPanel.INCREMENT);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			panel3.panelSave();
		} else if(sourceButton.getName().equals("wjButton")) {
			statsModel.sendRandomInformation();
		}
	}

	/**
	 * Method to open a browser page for the selecte youtube URL upon mouse click.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
        JLabel jLabel = (JLabel) e.getSource();
		if (e.getClickCount() == 1) {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				try {
					URI uri = new URI("https://www.youtube.com/watch?v=" + jLabel.getName());
					desktop.browse(uri);
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (URISyntaxException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
