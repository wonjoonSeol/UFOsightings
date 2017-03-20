package assessment.view;

import java.awt.BorderLayout;
import java.awt.Button;

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

	public SubStatPanel(Model m) {
		super();
		setLayout(new BorderLayout());
		model = m;
		statNumber = 0;
		initWidgets();
	}

	private void initWidgets() {
		lPanel = new JPanel(new BorderLayout());
		rPanel = new JPanel(new BorderLayout());

		rButton = new JButton(">");
		rButton.addActionListener(new StatController(this));
		lButton = new JButton("<");
		lButton.addActionListener(new StatController(this));
		lPanel.add(lButton, BorderLayout.CENTER);
		rPanel.add(rButton, BorderLayout.CENTER);

		topLabel = new JLabel("Top", SwingConstants.CENTER);
		centLabel = new JLabel("Cent", SwingConstants.CENTER);

		add(lPanel, BorderLayout.WEST);
		add(rPanel, BorderLayout.EAST);
		add(topLabel, BorderLayout.NORTH);
		add(centLabel, BorderLayout.CENTER);
	}

	public void setStat(int i) {
		
		if(i < 1)
		{
			i = 4;
		}
		else if(i > 4)
		{
			i = 1;
		}
		
		if (i == 1) 
		{
				statNumber = 1;
				topLabel.setText("Hoax Stats");
				centLabel.setText(Integer.toString(model.getNumHoaxes()));
		} 
		else if (i == 2) 
		{
				statNumber = 2;
				topLabel.setText("Non-US Stats");
				centLabel.setText(Integer.toString(model.getNonUSSight()));
		} 
		else if (i == 3) 
		{
				statNumber = 3;
				topLabel.setText("Likeliest State");
				centLabel.setText(model.getLikeliestState());
		}
		else if(i == 4)
		{
				statNumber = 4;
				topLabel.setText("Top 10 UFO Recent Sights Playlist");
				centLabel.setText("");
		}
	}
	
	public int getStat()
	{
		return statNumber;
	}
	
	// Add actionlistener to button. Set stat (currentStatnumber +1).
	// Within setstat, compare to the values inside a static arraylist. if it is contained, increase. etc,etc. Then pass to current setstat method when found available stat.
	

}
