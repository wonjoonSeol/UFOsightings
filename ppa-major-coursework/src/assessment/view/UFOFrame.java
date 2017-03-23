package assessment.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import assessment.controller.Controller;
import assessment.model.Model;
import assessment.model.panel2.MapUS;
import assessment.panel3.view.StatPanel;
import assessment.view.panel2.mapLayer.MapPanel;
import api.ripley.Ripley;

/**
 * Created by wonjoonseol on 05/03/2017.
 */
public class UFOFrame extends JFrame implements Observer {

	private JButton jbLeft;
	private JButton jbRight;
	private JLabel jlFrom;
	private JLabel jlTo;
	private JLabel jlStatus;
	private JLabel jlLog;
	private JPanel jpTop;
	private JPanel jpBottom;
	private JPanel jpTopRight;
	private JPanel jpCenter;
	private StatPanel panel3;
	private JComboBox<String> jcFrom;
	private JComboBox<String> jcTo;
	private Controller controller;
	private String loadingText;
	private Model model;
	private String processingText;
	private Ripley ripley;
    private BufferedWriter saveWriter;
	private static String savePath;
    private String[] prefArray = new String[4];
    private int ripleyMinYear;
    private int ripleyMaxYear;

	public UFOFrame(Controller controller, Ripley ripley, Model model) {
		super();
		this.controller = controller;
		this.ripley = ripley;
		this.model = model;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 805));
		setResizable(false);

        ripleyMinYear = model.getRipleyMinYear();
        ripleyMaxYear = model.getRipleyMaxYear();

        initTop();
		initCenter();
		initBottom();
		initFrame();
		pack();

		addComponentListener(controller);
		panelSavePreparation();
	}

	private void initTop() {
		initJComboBox();
		jlFrom = new JLabel("From: ");
		jlTo = new JLabel("To: ");
		jpTop = new JPanel(new BorderLayout());
		jpTopRight = new JPanel(new FlowLayout());
		jpTopRight.add(jlFrom);
		jpTopRight.add(jcFrom);
		jpTopRight.add(jlTo);
		jpTopRight.add(jcTo);
		jpTop.add(jpTopRight, BorderLayout.LINE_END);
	}

	private void initCenter() {
		jlLog = new JLabel("", SwingConstants.CENTER);

		jpCenter = new JPanel();
		jpCenter.setBorder(BorderFactory.createLineBorder(Color.black));
		jpCenter.setPreferredSize(new Dimension(800, 400));
		jpCenter.setLayout(new CardLayout());
		jpCenter.add(jlLog);
		 MapUS panel2Model = new MapUS(ripley.getIncidentsInRange("2000-01-01 00:00:00", "2000-02-01 00:00:00"));
		 jpCenter.add(new MapPanel(panel2Model));
		panel3 = new StatPanel(model);
		jpCenter.add(panel3);

	}

	private void initBottom() {
		jpBottom = new JPanel(new BorderLayout());

		jbLeft = new JButton("<");
		jbLeft.setName("jbLeft");
		jbLeft.setEnabled(false);
		jbRight = new JButton(">");
		jbRight.setName("jbRight");
		jbRight.setEnabled(false);
		jlStatus = new JLabel(ripley.getLastUpdated(), SwingConstants.CENTER);

		jpBottom.add(jbLeft, BorderLayout.LINE_START);
		jpBottom.add(jbRight, BorderLayout.LINE_END);
		jpBottom.add(jlStatus, BorderLayout.CENTER);

		jbLeft.addActionListener(controller);
		jbRight.addActionListener(controller);
	}

	private void initFrame() {
		setLayout(new BorderLayout());
		add(jpTop, BorderLayout.PAGE_START);
		add(jpCenter, BorderLayout.CENTER);
		add(jpBottom, BorderLayout.PAGE_END);
		loadingScreen();
	}

	private void initJComboBox() {
		jcFrom = new JComboBox<String>();
		jcFrom.setName("fromCombo");
		jcTo = new JComboBox<String>();
		jcTo.setName("toCombo");
		jcTo.addItem("-");
		jcFrom.addItem("-");

        for (int i = ripleyMinYear; i < ripleyMaxYear; i++) {
            jcFrom.addItem(i + "");
            jcTo.addItem(i + "");
        }

		jcFrom.addActionListener(controller);
		jcTo.addActionListener(controller);

	}

	private void loadingScreen() {
		loadingText = "<html><div Style='text-align: center;'>Welcome to Ripley API v" + ripley.getVersion() + "<br>"
				+ "Please select the dates above, in order to<br>Begin analysing UFO sighting data.<br><br>";

		jlLog.setText(loadingText + ripley.getAcknowledgementString() + "</div></html>");
	}

	public void nextCenterPanel() {
		CardLayout cards = (CardLayout) (jpCenter.getLayout());
		cards.next(jpCenter);
	}

	public void previousCenterPanel() {
		CardLayout cards = (CardLayout) (jpCenter.getLayout());
		cards.previous(jpCenter);
	}

	public String[] readFromFile(String fileName) throws FileNotFoundException, IOException {
		String[] ret = new String[4];
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			System.out.println("setting up reader");
			String line = reader.readLine();
			if (line == null) {
				System.out.println("We are reaching this part");
				ret[0] = "1";
				ret[1] = "2";
				ret[2] = "3";
				ret[3] = "4";
				System.out.println(ret);
				return ret;
			} else {
				System.out.println("Input!");
				ret = line.split(" ");
				System.out.println(ret);
			}
			return ret;
		}
	}

	    private void panelSavePreparation() {
        savePath = "Save";
        try {
            saveWriter = new BufferedWriter(new FileWriter(savePath));
            System.out.println("Getting here");
            prefArray = readFromFile(savePath);
            System.out.println(prefArray);// private field pass to statpanel
			controller.addWriter(saveWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update invoked");
        String command = (String) arg;
        String[] commandParts = command.split(" ");
        System.out.println(Arrays.toString(commandParts));

		if (commandParts.length == 2) {
			try {
				int startYear = Integer.parseInt(commandParts[0]);
				int endYear = Integer.parseInt(commandParts[1]);

				processingText = loadingText + "Date range selected, " + startYear + " - " + endYear
						+ "<br><br>Grabbing data...<br><br>";
				jlLog.setText(processingText + "</div></html>");
			} catch (NumberFormatException e) {
				System.err.print("notifyObserver has two parts that are not numbers");
				e.printStackTrace();
			}

		} else if (command.equalsIgnoreCase("wrongStart")) {
			jlLog.setText(loadingText + "<b>Please provide correct start and end dates</b><br></div></html>");

		} else if (commandParts[0].equals("DATA")) {
			String string = "";
			for (int i = 1; i < commandParts.length; i++) {
				string += commandParts[i] + " ";
			}
			jlLog.setText(processingText + string
					+ "<br><br><b>Please now interact with this data using<br>the buttons to the left and the right.</b></div></html>");
			jbLeft.setEnabled(true);
			jbRight.setEnabled(true);
			System.out.println("Initiating stats!");
			panel3.initStats(prefArray);

		} else if (commandParts[0].equals("SAVE")) {
			String retString = "";
			retString = retString + panel3.getltPanel().getStat() + " ";
			retString = retString + panel3.getrtPanel().getStat() + " ";
			retString = retString + panel3.getlbPanel().getStat() + " ";
			retString = retString + panel3.getrbPanel().getStat() + " ";
			try {
				saveWriter.write(retString);
				saveWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
