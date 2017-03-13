package assessment.view;

import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SubStatPanel extends JPanel {

	private JButton rButton;
	private JButton lButton;
	private JLabel topLabel;
	private JLabel centLabel;
	private JPanel lPanel;
	private JPanel rPanel;

	public SubStatPanel() {
		super();
		setLayout(new BorderLayout());
		initWidgets();
	}

	private void initWidgets() {
		lPanel = new JPanel(new BorderLayout());
		rPanel = new JPanel(new BorderLayout());

		rButton = new JButton(">");
		lButton = new JButton("<");
		lPanel.add(lButton, BorderLayout.CENTER);
		rPanel.add(rButton, BorderLayout.CENTER);

		topLabel = new JLabel("Top", SwingConstants.CENTER);
		centLabel = new JLabel("Cent", SwingConstants.CENTER);

		add(lPanel, BorderLayout.WEST);
		add(rPanel, BorderLayout.EAST);
		add(topLabel, BorderLayout.NORTH);
		add(centLabel, BorderLayout.CENTER);
	}

}
