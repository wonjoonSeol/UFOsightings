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

	public SubStatPanel(StatsModel statsModel, MapUS mapModel,  StatPanel statPanel){
		super();
		setLayout(new BorderLayout());
		this.statsModel = statsModel;
		this.mapModel = mapModel;
		mapModel.addObserver(this);
		statNumber = 0;
		displayStats = new ArrayList<Integer>(4);
		initWidgets(statPanel);
	}

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


	public void initializeStat(int i) throws Exception {
		updateStatistic(i);
		displayStats.add(i);
	}

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

	public void resetDisplayStats() {
		displayStats.clear();
	}

	public int getStat() {
		return statNumber;
	}

	private void updateStatistic(int i) throws Exception {
		CardLayout cards = (CardLayout) (jpCenter.getLayout());
		cards.show(jpCenter, "default");
		statNumber = i;
		if (i == 1) {
			topLabel.setText("Hoax Stats");
			centLabel.setText(Integer.toString(statsModel.getNumHoaxes()));
		} else if (i == 2) {
			topLabel.setText("Non-US Stats");
			centLabel.setText(Integer.toString(statsModel.getNonUSSight()));
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
			centLabel.setText("<html><div style='text-align: center;'>" + statsModel.countryDistributionPercentage() + "</div></html>");
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
		repaint();
	}
	// Add actionlistener to button. Set stat (currentStatnumber +1).
	// Within setstat, compare to the values inside a static arraylist. if it is
	// contained, increase. etc,etc. Then pass to current setstat method when
	// found available stat.

	@Override
	public void update(Observable arg0, Object arg1) {
		timePanel.setLikeliestState(mapModel.getLikeliestState());
		timePanel.setCurrentStartYear(statsModel.getCurrentStartYear());
		timePanel.setCurrentEndYear(statsModel.getCurrentEndYear());
	}
}
