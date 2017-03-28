package assessment.view.panel3.additionalStats;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import assessment.controller.panel3.StatController;

public class YoutubePanel extends JPanel implements Observer{
	//Declaring fields for the widgets
	private StatController statController;
	private JPanel videoPanels;

	public YoutubePanel(StatController statController) {
		// TODO Pass controller through the constructor
        this.statController = statController;
		setLayout(new BorderLayout());
		initWidget();
	}

	public void initWidget(){ 
		//TODO add the key listeners and pass the controller in the args
		videoPanels = new JPanel();
		videoPanels.setLayout(new GridLayout(0, 1));
		add(videoPanels, BorderLayout.CENTER);
		videoPanels.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	//Method to create an ImageIcon and that stores the URL and description	 
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
			/**
			 * 1.Stores the retrieved results and stores them in an array.
			 * 2.Iterates through the list of videos and stores the info needed.
			 */
			/**
			 * Temporary variables to store video details need for the video list.
			 * 1. The youtubeID for the link for the video.
			 * 2. The title for the description of the video.
			 * 3. The icon for the thumb-nail of the video.
			 */
		}
		revalidate();
		repaint();
    }
}
