package assessment.view;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StatPanel extends JPanel {
	
	private SubStatPanel lTopPanel;
	private SubStatPanel rTopPanel;
	private SubStatPanel lBotPanel;
	private SubStatPanel rBotPanel;
	
	public StatPanel()
	{
		super();
		initWidgets();
	}
	
	public void initWidgets()
	{
		lTopPanel = new SubStatPanel();
		rTopPanel = new SubStatPanel();
		lBotPanel = new SubStatPanel();
		rBotPanel = new SubStatPanel();
		
		setLayout(new GridLayout(2,2,8,8));
		
		
		add(lTopPanel);
		add(rTopPanel);
		add(lBotPanel);
		add(rBotPanel);
	}
	
	
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(new StatPanel(), BorderLayout.CENTER);
		frame.setVisible(true);
		frame.pack();
	}
}
