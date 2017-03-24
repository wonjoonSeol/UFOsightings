package assessment.view.panel3;
import java.awt.GridLayout;
import java.io.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import assessment.model.Model;

public class StatPanel extends JPanel implements Observer{
	
	private SubStatPanel lTopPanel;
	private SubStatPanel rTopPanel;
	private SubStatPanel lBotPanel;
	private SubStatPanel rBotPanel;
	private Model model;
	private boolean initStats;
	private BufferedWriter saveWriter;
	private static String savePath;
	private String[] stats = new String[4];

	public StatPanel(Model m)
	{
		super();
		model = m;
		savePath = "Save";
		readFromFile();
		initWidgets();
	}
	
	public void initWidgets()
	{
		lTopPanel = new SubStatPanel(model, this);
		rTopPanel = new SubStatPanel(model, this);
		lBotPanel = new SubStatPanel(model, this);
		rBotPanel = new SubStatPanel(model, this);
		
	
		
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
		lTopPanel.initializeStat(Integer.parseInt(stats[0]));
		rTopPanel.initializeStat(Integer.parseInt(stats[1]));
		lBotPanel.initializeStat(Integer.parseInt(stats[2]));
		rBotPanel.initializeStat(Integer.parseInt(stats[3]));
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

	public void panelSave() {
		String retString = "";
		retString = retString + this.getltPanel().getStat() + " ";
		retString = retString + this.getrtPanel().getStat() + " ";
		retString = retString + this.getlbPanel().getStat() + " ";
		retString = retString + this.getrbPanel().getStat() + " ";
		try {
			saveWriter = new BufferedWriter(new FileWriter(savePath));
			saveWriter.write(retString);
			saveWriter.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private String[] readFromFile() {
		String[] ret = new String[4];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(savePath));
			System.out.println("setting up reader");
			String line = reader.readLine();
			if (line != null) {
				ret = line.split(" ");
				System.out.println(ret);
			} else {
				setDefaultSubPanels(ret);
			}
		} catch (IOException e) {
			System.out.println("We are reaching this part");
			setDefaultSubPanels(ret);
		}
		return ret;
	}

	private void setDefaultSubPanels(String[] ret) {
		ret[0] = "1";
		ret[1] = "2";
		ret[2] = "3";
		ret[3] = "4";
	}
}