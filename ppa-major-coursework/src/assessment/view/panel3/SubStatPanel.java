package assessment.view.panel3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import assessment.controller.StatController;
import assessment.model.Model;

public class SubStatPanel extends JPanel {

	private JButton rButton;
	private JButton lButton;
	private JLabel topLabel;
	private JLabel centLabel;
	private JPanel lPanel;
	private JPanel rPanel;
	private Model model;
	private int statNumber;
	private static ArrayList<Integer> displayStats;

	// TODO: Only display one version of each stat. Save user statistic
	// preferences upon close.

	public SubStatPanel(Model m, StatPanel statPanel) {
		super();
		setLayout(new BorderLayout());
		model = m;
		statNumber = 0;
		displayStats = new ArrayList<Integer>(4);
		initWidgets(statPanel);
	}

	private void initWidgets(StatPanel statPanel) {
		lPanel = new JPanel(new BorderLayout());
		rPanel = new JPanel(new BorderLayout());

		rButton = new JButton(">");
		StatController statsController = new StatController(this, statPanel);
		rButton.addActionListener(statsController);
		lButton = new JButton("<");
		lButton.addActionListener(statsController);
		lPanel.add(lButton, BorderLayout.CENTER);
		rPanel.add(rButton, BorderLayout.CENTER);

		topLabel = new JLabel("Top", SwingConstants.CENTER);
		centLabel = new JLabel("Cent", SwingConstants.CENTER);

		add(lPanel, BorderLayout.WEST);
		add(rPanel, BorderLayout.EAST);
		add(topLabel, BorderLayout.NORTH);
		add(centLabel, BorderLayout.CENTER);
	}

	public void initializeStat(int i) {
		updateStatistic(i);
		displayStats.add(i);
		System.out.println(displayStats);
	}

	public void setStat(int i) {
		if (i >= displayStats.size() + 1) {
			i = 1;
		} else if (i < 1) {
			i = 5;
		}
		while (displayStats.contains(i)) {
			System.out.println(i);
			if (i >= displayStats.size() + 1) {
				i = 1;
			} else if (i < 1) {
				i = 5;
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

	private void updateStatistic(int i) {
		if (i == 1) {
			statNumber = 1;
			topLabel.setText("Hoax Stats");
			centLabel.setText(Integer.toString(model.getNumHoaxes()));
		} else if (i == 2) {
			statNumber = 2;
			topLabel.setText("Non-US Stats");
			centLabel.setText(Integer.toString(model.getNonUSSight()));
		} else if (i == 3) {
			statNumber = 3;
			topLabel.setText("Likeliest State");
			centLabel.setText(model.getLikeliestState());
		} else if (i == 4) {
			statNumber = 4;
			topLabel.setText("Top 10 UFO Recent Sights Playlist");
			centLabel.setText("");
		} else if (i == 5) {
			statNumber = 5;
			topLabel.setText("Domestic vs International Sighting Ratio");
			centLabel.setText(model.countryDistributionPercentage());
		}
	}
	// Add actionlistener to button. Set stat (currentStatnumber +1).
	// Within setstat, compare to the values inside a static arraylist. if it is
	// contained, increase. etc,etc. Then pass to current setstat method when
	// found available stat.
}
