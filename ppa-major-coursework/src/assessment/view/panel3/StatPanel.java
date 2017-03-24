package assessment.view.panel3;
import java.awt.GridLayout;
import java.io.*;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import assessment.model.Model;

public class StatPanel extends JPanel{
	
	private SubStatPanel[] subPanels;
	private Model model;
	private BufferedWriter saveWriter;
	private static String savePath;
	private String[] stats;

	public StatPanel(Model m) {
		super();
		model = m;
		savePath = "Save";
		subPanels = new SubStatPanel[4];
		stats = new String[4];
		readFromFile();
		initWidgets();
	}
	
	public void initWidgets() {
		setLayout(new GridLayout(2, 2, 8, 8));
		for (int i = 0; i < subPanels.length; i++) {
			subPanels[i] = new SubStatPanel(model, this);
			add(subPanels[i]);
		}
	}

	public void initStats() {
			subPanels[0].resetDsiplayStats();
	    for (int i = 0; i < subPanels.length; i++) {
			subPanels[i].initializeStat(Integer.parseInt(stats[i]));
		}
	}

	public String[] getDisplayConfiguration() {
		return stats;
	}
	
	public void panelSave() {
		String retString = "";
		for (int i = 0; i < subPanels.length; i++) {
			retString += subPanels[i].getStat() + " ";
		}

		try {
			saveWriter = new BufferedWriter(new FileWriter(savePath));
			saveWriter.write(retString);
			saveWriter.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void readFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(savePath));
			System.out.println("setting up reader");
			String line = reader.readLine();
			if (!line.isEmpty()) {
				stats = line.split(" ");
				System.out.println(Arrays.toString(stats));
			} else {
				setDefaultSubPanels();
			}
		} catch (IOException e) {
			System.out.println("We are reaching this part");
			setDefaultSubPanels();
		}
	}

	private void setDefaultSubPanels() {
		for (int i = 0; i < subPanels.length; i++) {
		    stats[i] = i + "";
		}
	}
}
