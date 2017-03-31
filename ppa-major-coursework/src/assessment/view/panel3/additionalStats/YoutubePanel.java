package assessment.view.panel3.additionalStats;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import assessment.controller.panel3.StatController;
/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This view class creates and manages the youtube sub panel within the statPanel.
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class YoutubePanel extends JPanel implements Observer{
	private StatController statController;
	private JPanel videoPanels;

	/**
	 * Constructor to instantiate new YoutubePanel and set fields.
	 * Set layout of main panel and call method to initiated widgets.
	 * @param statController for Mouse Listener.
	 */
	public YoutubePanel(StatController statController) {
        this.statController = statController;
		setLayout(new BorderLayout());
		initWidget();
	}

	/**
	 * Method to initiate widgets within the panel.
	 */
	public void initWidget(){ 
		videoPanels = new JPanel();
		videoPanels.setLayout(new GridLayout(0, 1));
		add(videoPanels, BorderLayout.CENTER);
		videoPanels.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
 
	/**
	 * Method to create an ImageIcon and that stores the URL and description	
	 * @param imgURL for video thumbnail. 
	 * @param description for video title.
	 * @return ImageIcon with video title and thumbnail URL.
	 */
	private ImageIcon createImageIcon(String imgURL, String description) {
		URL url = null;
		try {
			url = new URL(imgURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (url != null) {
			return new ImageIcon(url, description);
		} else {
			System.err.println("Couldn't find file path. " );
			return null;
		}
	}
	/**
	 * Update method for MVC structure.
	 * Adding mouse listener to label and set name as the Video ID. 
	 */
	@Override
	public void update(Observable o, Object arg) {
		String command = (String) arg;
		String[] commandParts = command.split(";");

		if (command.equals("clear")) {
			videoPanels.removeAll();
		} else if (commandParts.length == 3){
			String url = commandParts[0];
			String title = commandParts[1];
			String link = commandParts[2];
			ImageIcon icon = createImageIcon(url, title);
			JLabel videoLabel = new JLabel(title, icon, JLabel.LEFT);
			videoLabel.setName(link);
            videoPanels.add(videoLabel);
            videoLabel.addMouseListener(statController);

		}
    }
}
