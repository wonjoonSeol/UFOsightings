package assessment.view.panel3;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import assessment.controller.panel3.StatController;
import assessment.model.panel2.MapUS;
import assessment.model.panel3.StatsModel;
import assessment.model.panel3.YoutubeModel;
import assessment.view.panel3.additionalStats.TimeIncidentStatPanel;
import assessment.view.panel3.additionalStats.KeyEventsPanel;
import assessment.view.panel3.additionalStats.YoutubePanel;
/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This view class creates and manages a SubStatPanel to be displayed within the main statistics panel.
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class SubStatPanel extends JPanel implements Observer {

	private JButton rButton;
	private JButton lButton;
	private JLabel topLabel;
	private JLabel centLabel;
	private JPanel lPanel;
	private JPanel rPanel;
	private JPanel jpCenter;
	private StatsModel statsModel;

	private MapUS mapModel;
	private TimeIncidentStatPanel timePanel;

	private int statNumber;
	private static ArrayList<Integer> displayStats;
	public final static int INCREMENT = 1;
	public final static int DECREMENT = 0;

	// TODO: Only display one version of each stat. Save user statistic
	// preferences upon close.

	/**
	 * Constructor to create new instance of SubStatPanel and set fields.
	 * @param statsModel
	 * @param mapModel
	 * @param statPanel
	 */
	public SubStatPanel(StatsModel statsModel, MapUS mapModel,  StatPanel statPanel){
		super();
		setLayout(new BorderLayout());
		this.statsModel = statsModel;
		this.mapModel = mapModel;
		mapModel.addObserver(this);
		statsModel.addObserver(this);
		statNumber = 0;
		displayStats = new ArrayList<Integer>(4);
		initWidgets(statPanel);
	}

	/**
	 * Method to initiate widgets and construct layout.
	 * @param statPanel
	 */
	private void initWidgets(StatPanel statPanel){
		lPanel = new JPanel(new BorderLayout());
		rPanel = new JPanel(new BorderLayout());
		jpCenter = new JPanel(new CardLayout());

		rButton = new JButton(">");
		lButton = new JButton("<");
		lPanel.add(lButton, BorderLayout.CENTER);
		rPanel.add(rButton, BorderLayout.CENTER);

		initAdditionalPanels(statPanel);
		topLabel = new JLabel("Top", SwingConstants.CENTER);
		centLabel = new JLabel("Cent", SwingConstants.CENTER);
		centLabel.setPreferredSize(new Dimension (140, 140));

		jpCenter.add(centLabel, "default");

		add(lPanel, BorderLayout.WEST);
		add(rPanel, BorderLayout.EAST);
		add(jpCenter, BorderLayout.CENTER);
		add(topLabel, BorderLayout.NORTH);
	}


	/**
	 * Method to initialize the statistic to be displayed.
	 * @param i
	 * @throws Exception
	 */
	public void initializeStat(int i) throws Exception {
		int panelNumbers = 8;
		if (i > panelNumbers) {
			i = 1;
		} else if (i <= 0) {
			i = panelNumbers;
		}
		updateStatistic(i);
		displayStats.add(i);
	}

	/**
	 * Method to set the current stat to be displayed and update the displayStats ArrayList field accordingly.
	 * @param i
	 * @param constant
	 * @throws Exception
	 */
	public void setStat(int i, int constant) throws Exception {
		int panelNumbers = 8;
		if (i > panelNumbers) {
			i = 1;
		} else if (i <= 0) {
			i = panelNumbers;
		}
        while (displayStats.contains(i)) {
			System.out.println(i);
			if (i > 0 && i <= panelNumbers && constant == INCREMENT) {
				i++;
            } else if (i > 0 && i <= panelNumbers && constant == DECREMENT) {
				i--;
			}

			if (i > panelNumbers) {
				i = 1;
			} else if (i <= 0) {
				i = panelNumbers;
			}
		}
		displayStats.remove(displayStats.indexOf(statNumber));
		updateStatistic(i);
		displayStats.add(i);
		System.out.println(displayStats);
	}

	/**
	 * Method to initiate the values to be displayed in the 3 additional statistics panels.
	 * @param statPanel
	 */
	private void initAdditionalPanels(StatPanel statPanel) {
		YoutubeModel ytModel = new YoutubeModel();
		StatController statController = new StatController(this, statPanel, statsModel, ytModel);
		KeyEventsPanel keyEventsPanel = new KeyEventsPanel(statController, statsModel);
		statsModel.addObserver(keyEventsPanel);
		keyEventsPanel.requestText();
		YoutubePanel youtubePanel = new YoutubePanel(statController);
		ytModel.addObserver(youtubePanel);
		ytModel.runSearch();

		rButton.addActionListener(statController);
		lButton.addActionListener(statController);

		this.timePanel = new TimeIncidentStatPanel(mapModel.getLikeliestState(), statsModel.getCurrentStartYear(), statsModel.getCurrentEndYear());

		jpCenter.add(timePanel, "Munkhtulga");
		jpCenter.add(keyEventsPanel, "wonjoon");
		jpCenter.add(youtubePanel, "eugene");
	}

	/**
	 * Method to reset the displayStats ArrayList field.
	 */
	public void resetDisplayStats() {
		displayStats.clear();
	}

	/**
	 * Method to return the statNumber int field.
	 * @return int statistic number
	 */
	public int getStat() {
		return statNumber;
	}

	/**
	 * Method to update the displayed statistic when changed.
	 * @param i
	 * @throws Exception
	 */
	private void updateStatistic(int i) throws Exception {
		CardLayout cards = (CardLayout) (jpCenter.getLayout());
		cards.show(jpCenter, "default");
		statNumber = i;
		if (i == 1) {
			topLabel.setText("Hoax Stats");
			centLabel.setText(Integer.toString(statsModel.getNumberOfHoax()));
		} else if (i == 2) {
			topLabel.setText("Non-US Stats");
			centLabel.setText(Integer.toString(statsModel.getNonUSsight()));
		} else if (i == 3) {
			topLabel.setText("Likeliest State");
			if (mapModel.getLikeliestState().getAbbreviation().equals("Not specified.")) {
				centLabel.setText("<html><div style='text-align: center;'>" +
							"No US state specified. All states equally likely.<br>(Graph is for one random state)"
									+ "</div></html>");
			} else {
				centLabel.setText(mapModel.getLikeliestState().toString());
			}
		} else if (i == 4) {
			topLabel.setText("Top 10 UFO Recent Sights Playlist");
			cards.show(jpCenter, "eugene");
		} else if (i == 5) {
			topLabel.setText("Domestic vs International Sighting Ratio");
			centLabel.setText("<html><div style='text-align: center;'>" + statsModel.getDistributionPercentage() + "</div></html>");
		} else if (i == 6) {
			topLabel.setText("Key events in history");
			cards.show(jpCenter, "wonjoon");
		} else if(i == 7) {
			topLabel.setText("Youtube Videos published within past week");
			centLabel.setText(statsModel.getRequest());
		} else if(i == 8) {
			topLabel.setText("Relative incident count in likeliest state through years");
			cards.show(jpCenter, "Munkhtulga");
		}
	}

	@Override
	/**
	 * Update method for MVC structure.
	 */
	public void update(Observable arg0, Object arg1) {
	    if (arg0 instanceof MapUS) {
			timePanel.setLikeliestState(mapModel.getLikeliestState());
			timePanel.setCurrentStartYear(statsModel.getCurrentStartYear());
			timePanel.setCurrentEndYear(statsModel.getCurrentEndYear());
		} else if (arg1 instanceof String) {
			String command = (String) arg1;
			String[] commandPart = command.split(";");

			if (commandPart[0].equalsIgnoreCase("Data")) {
				try {
					updateStatistic(statNumber);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
