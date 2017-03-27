package assessment.view.panel3;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import assessment.controller.panel3.StatController;
import assessment.model.panel3.StatsModel;
import assessment.model.panel3.YoutubeModel;
import assessment.view.panel3.additionalStats.WonjoonStats;
import assessment.view.panel3.eugeneStats.YoutubePanel;

public class SubStatPanel extends JPanel {

	private JButton rButton;
	private JButton lButton;
	private JLabel topLabel;
	private JLabel centLabel;
	private JPanel lPanel;
	private JPanel rPanel;
	private JPanel jpCenter;
	private WonjoonStats wonjoonStats;
	private StatsModel statsModel;
	private int statNumber;
	private YoutubePanel ytView;
	private static ArrayList<Integer> displayStats;

	// TODO: Only display one version of each stat. Save user statistic
	// preferences upon close.

	public SubStatPanel(StatsModel statsModel, StatPanel statPanel) {
		super();
		setLayout(new BorderLayout());
		this.statsModel = statsModel;
		statNumber = 0;
		displayStats = new ArrayList<Integer>(4);
		initWidgets(statPanel);
	}

	private void initWidgets(StatPanel statPanel) {
		lPanel = new JPanel(new BorderLayout());
		rPanel = new JPanel(new BorderLayout());
		jpCenter = new JPanel(new CardLayout());

		StatController statsController = new StatController(this, statPanel, statsModel);
		wonjoonStats = new WonjoonStats(statsController, statsModel);
		wonjoonStats.requestText();
		statsModel.addObserver(wonjoonStats);
		
		YoutubeModel ytModel = new YoutubeModel();
		ytView = new YoutubePanel();
		
		ytModel.addObserver(ytView);
		ytModel.handleVideos();

		rButton = new JButton(">");
		rButton.addActionListener(statsController);
		lButton = new JButton("<");
		lButton.addActionListener(statsController);
		lPanel.add(lButton, BorderLayout.CENTER);
		rPanel.add(rButton, BorderLayout.CENTER);

		topLabel = new JLabel("Top", SwingConstants.CENTER);
		centLabel = new JLabel("Cent", SwingConstants.CENTER);

		jpCenter.add(centLabel, "default");
		jpCenter.add(wonjoonStats, "wonjoon");
		jpCenter.add(ytView, "eugene");

		add(lPanel, BorderLayout.WEST);
		add(rPanel, BorderLayout.EAST);
		add(jpCenter, BorderLayout.CENTER);
		add(topLabel, BorderLayout.NORTH);
	}

	public void initializeStat(int i) throws Exception {
		updateStatistic(i);
		displayStats.add(i);
	}

	public void setStat(int i) throws Exception {
		int statsNumber = 6;
		if (i > statsNumber) {
			i = 1;
		} else if (i < 1) {
			i = 6;
		}
		while (displayStats.contains(i)) {
			System.out.println(i);
			if (i > statsNumber) {
				i = 1;
			} else if (i < 1) {
				i = 6;
			} else
				i++;
		}
		displayStats.remove((Integer) statNumber);
		updateStatistic(i);
		displayStats.add(statNumber);
		System.out.println(displayStats);
	}

	public void resetDsiplayStats() {
		displayStats.clear();
	}

	public int getStat() {
		return statNumber;
	}

	private void updateStatistic(int i) throws Exception {
		CardLayout cards = (CardLayout) (jpCenter.getLayout());
		cards.show(jpCenter, "default");
		if (i == 1) {
			statNumber = 1;
			topLabel.setText("Hoax Stats");
			centLabel.setText(Integer.toString(statsModel.getNumHoaxes()));
		} else if (i == 2) {
			statNumber = 2;
			topLabel.setText("Non-US Stats");
			centLabel.setText(Integer.toString(statsModel.getNonUSSight()));
		} else if (i == 3) {
			statNumber = 3;
			topLabel.setText("Likeliest State");
			centLabel.setText(statsModel.getLikeliestState());
		} else if (i == 4) {
			statNumber = 4;
			topLabel.setText("Top 10 UFO Recent Sights Playlist");
			cards.show(jpCenter, "eugene");
			
		} else if (i == 5) {
			statNumber = 5;
			topLabel.setText("Domestic vs International Sighting Ratio");
			centLabel.setText(statsModel.countryDistributionPercentage());
		} else if (i == 6) {
			topLabel.setText("Key events in history");
			cards.show(jpCenter, "wonjoon");
			statNumber = 6;
		}
		else if(i == 7)
		{
			topLabel.setText("Youtube Videos published within past week");
			centLabel.setText(statsModel.getRequest());
			statNumber = 7;
		}
		repaint();
	}
	// Add actionlistener to button. Set stat (currentStatnumber +1).
	// Within setstat, compare to the values inside a static arraylist. if it is
	// contained, increase. etc,etc. Then pass to current setstat method when
	// found available stat.
}
