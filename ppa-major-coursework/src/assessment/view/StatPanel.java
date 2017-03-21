package assessment.view;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import assessment.model.Model;

public class StatPanel extends JPanel implements Observer{
	
	private SubStatPanel lTopPanel;
	private SubStatPanel rTopPanel;
	private SubStatPanel lBotPanel;
	private SubStatPanel rBotPanel;
	private Model model;
	private boolean initStats;
	
	public StatPanel(Model m)
	{
		super();
		model = m;
		initWidgets();
	}
	
	public void initWidgets()
	{
		lTopPanel = new SubStatPanel(model);
		rTopPanel = new SubStatPanel(model);
		lBotPanel = new SubStatPanel(model);
		rBotPanel = new SubStatPanel(model);
		
	
		
		setLayout(new GridLayout(2,2,8,8));
		
		
		add(lTopPanel);
		add(rTopPanel);
		add(lBotPanel);
		add(rBotPanel);
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void initStats()
	{
		lTopPanel.initializeStat(1);
		rTopPanel.initializeStat(2);
		lBotPanel.initializeStat(3);
		rBotPanel.initializeStat(4);
	}
	
	public SubStatPanel getltPanel()
	{
		return lTopPanel;
	}
	
	public SubStatPanel getrtPanel()
	{
		return rTopPanel;
	}
	
	public SubStatPanel getlbPanel()
	{
		return lBotPanel;
	}
	
	public SubStatPanel getrbPanel()
	{
		return rBotPanel;
	}
	
}
