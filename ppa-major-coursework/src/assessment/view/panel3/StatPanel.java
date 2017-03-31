package assessment.view.panel3;
import java.awt.GridLayout;
import java.io.*;
import java.util.Arrays;
import javax.swing.JPanel;

import assessment.model.panel2.MapUS;
import assessment.model.panel3.StatsModel;
/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This view class creates and manages the main statistics panel within which are placed 4 subStatPanels.
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class StatPanel extends JPanel{

	private StatsModel statsModel;
	private MapUS mapModel; 
	private SubStatPanel[] subPanels;
	private BufferedWriter saveWriter;
	private static String savePath;
	private String[] stats;

	/**
	 * Constructor to create new instance of StatPanel, initialize fields, and read saved statistics numbers from a local Save file.
	 * @param statsModel
	 * @param mapModel
	 */
	public StatPanel(StatsModel statsModel, MapUS mapModel) {
		super();
		savePath = "Save";
		this.statsModel = statsModel;
		this.mapModel = mapModel; 
		subPanels = new SubStatPanel[4];
		stats = new String[4];
		readFromFile();
		initWidgets();
	}
	
	/**
	 * Method to initiate the subPanel widgets contained within the StatPanel.
	 */
	public void initWidgets() {
		setLayout(new GridLayout(2, 2, 8, 8));
		for (int i = 0; i < subPanels.length; i++) {
			subPanels[i] = new SubStatPanel(statsModel, mapModel, this);
			add(subPanels[i]);
		}
	}

	/**
	 * Method to initiate the statistics to be displayed according to contents of the stats Array field.
	 */
	public void initStats() {
			subPanels[0].resetDisplayStats();
	    for (int i = 0; i < subPanels.length; i++) {
			try {
				subPanels[i].initializeStat(Integer.parseInt(stats[i]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to save the current statistics types being displayed in the panel.
	 */
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

	/**
	 * Method to read the saved statistics numbers from a local Save file.
	 */
	private void readFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(savePath));
			String line = reader.readLine();
			if (!line.isEmpty()) {
				stats = line.split(" ");
				System.out.println(Arrays.toString(stats));
			} else {
				setDefaultSubPanels();
			}
		} catch (IOException e) {
			setDefaultSubPanels();
		}
	}

	/**
	 * Method to set default statistics to be displayed.
	 */
	private void setDefaultSubPanels() {
		for (int i = 0; i < subPanels.length; i++) {
		    stats[i] = i + "";
		}
	}
}
