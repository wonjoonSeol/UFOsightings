package assessment.view;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import assessment.controller.Controller;
import assessment.controller.panel4.Panel4Controller;
import assessment.model.Model;
import assessment.model.panel2.MapUS;
import assessment.model.panel3.StatsModel;
import assessment.model.panel4.AlienDefend;
import assessment.view.panel3.StatPanel;
import assessment.view.panel2.mapLayer.MapPanel;
import api.ripley.Ripley;
import assessment.view.panel4.IntroVideo;
import assessment.view.panel4.Login;
import assessment.view.panel4.UnitedEarthDirectorate;

/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This class represents the main viewer frame
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class UFOFrame extends JFrame implements Observer {

	private Model model;
	private Ripley ripley;
	private Controller controller;
	private JLabel jlLog;
	private JPanel jpCenter;
	private StatPanel panel3;
	private JPanel panel4;
	private JButton jbLeft;
	private JButton jbRight;
	private JComboBox<String> jcFrom;
	private JComboBox<String> jcTo;
	private String loadingText;
	private MapUS panel2Model;
	private StatsModel statsModel;
	private String processingText;
    private int ripleyMinYear;
    private int ripleyMaxYear;

    /**
	 * Constructor UFOFrame. This constructs our main frame
	 *
	 * @param controller Main controller
	 * @param ripley Ripley API
     * @param model Main model for data retrieval and cache management
	 */
	public UFOFrame(Controller controller, Ripley ripley, Model model) {
		super();
		this.controller = controller;
		this.ripley = ripley;
		this.model = model;
		model.addObserver(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 660));
		setResizable(false);

        ripleyMinYear = model.getRipleyMinYear();
        ripleyMaxYear = model.getRipleyMaxYear();

		setLayout(new BorderLayout());

        initTop();
		initCenter();
		initBottom();
		loadingScreen();
		pack();
	}

	/**
	 * play loading gif
	 */
	private void initLoadingGif() {
		ImageIcon loadingIcon = new ImageIcon("images/loading250.gif");
		jlLog.setIcon(loadingIcon);
		jlLog.setVerticalTextPosition(JLabel.BOTTOM);		//Set the position of the text, relative to the icon
		jlLog.setHorizontalTextPosition(JLabel.CENTER);
		jlLog.setIconTextGap(60);							//Set the gap between the text and the icon
	}

	/**
	 * Initialise top part of the widgets
	 */
	private void initTop() {
		initJComboBox();
		JLabel jlFrom = new JLabel("From: ");
		JLabel jlTo = new JLabel("To: ");
		JPanel jpTop = new JPanel(new BorderLayout());
		JPanel jpTopRight = new JPanel(new FlowLayout());
		jpTopRight.add(jlFrom);
		jpTopRight.add(jcFrom);
		jpTopRight.add(jlTo);
		jpTopRight.add(jcTo);
		jpTop.add(jpTopRight, BorderLayout.LINE_END);
		add(jpTop, BorderLayout.PAGE_START);
	}

	/**
	 * Initialise centre part of the widgets
	 */
	private void initCenter() {
		jlLog = new JLabel("", SwingConstants.CENTER);

		jpCenter = new JPanel();
		jpCenter.setBorder(BorderFactory.createLineBorder(Color.black));
		jpCenter.setPreferredSize(new Dimension(800, 400));
		jpCenter.setLayout(new CardLayout());

		this.panel2Model = new MapUS();

		statsModel = new StatsModel(model, ripley);
		panel3 = new StatPanel(statsModel, panel2Model);

		/* ------------ Card layout ------------------*/
		jpCenter.add(jlLog, "Loading");
		jpCenter.add(new MapPanel(panel2Model));
		jpCenter.add(panel3);
		initPanel4();
		jpCenter.add(panel4);
		add(jpCenter, BorderLayout.CENTER);
	}

	/**
	 * Initialise bottom part of the widgets
	 */
	private void initBottom() {
		JPanel jpBottom = new JPanel(new BorderLayout());
		jbLeft = new JButton("<");
		jbLeft.setName("jbLeft");
		jbLeft.setEnabled(false);
		jbRight = new JButton(">");
		jbRight.setName("jbRight");
		jbRight.setEnabled(false);

		JLabel jlStatus = new JLabel(ripley.getLastUpdated(), SwingConstants.CENTER);

		jpBottom.add(jbLeft, BorderLayout.LINE_START);
		jpBottom.add(jbRight, BorderLayout.LINE_END);
		jpBottom.add(jlStatus, BorderLayout.CENTER);

		jbLeft.addActionListener(controller);
		jbRight.addActionListener(controller);
		add(jpBottom, BorderLayout.PAGE_END);
	}

	/**
	 * Initialise JComboBox
	 */
	private void initJComboBox() {
		jcFrom = new JComboBox<String>();
		jcFrom.setName("fromCombo");
		jcTo = new JComboBox<String>();
		jcTo.setName("toCombo");
		jcTo.addItem("-");
		jcFrom.addItem("-");

        for (int i = ripleyMinYear; i <= ripleyMaxYear; i++) {
            jcFrom.addItem(i + "");
            jcTo.addItem(i + "");
        }
		jcFrom.addActionListener(controller);
		jcTo.addActionListener(controller);
	}

	/**
	 * Show loading message
	 */
	private void loadingScreen() {
		loadingText = "<html><div Style='text-align: center;'>Welcome to Ripley API v" + ripley.getVersion() + "<br>"
				+ "Please select the dates above, in order to<br>Begin analysing UFO sighting data.<br><br>";

		jlLog.setText(loadingText + ripley.getAcknowledgementString() + "</div></html>");
	}

	/**
	 * Switch to next panel
	 */
	public void nextCenterPanel() {
		CardLayout cards = (CardLayout) (jpCenter.getLayout());
		cards.next(jpCenter);
	}

	/**
	 * Switch to previous panel
	 */
	public void previousCenterPanel() {
		CardLayout cards = (CardLayout) (jpCenter.getLayout());
		cards.previous(jpCenter);
	}

	/**
	 * Initialise panel 4
	 */
	private void initPanel4(){
		panel4 = new JPanel(new CardLayout());
		UnitedEarthDirectorate unitedEarthDirectorate = new UnitedEarthDirectorate();
		AlienDefend alienDefend = new AlienDefend();
		Panel4Controller panel4Controller = new Panel4Controller(alienDefend);
		IntroVideo introVideo = new IntroVideo(this, unitedEarthDirectorate);
		Login login = new Login(alienDefend, introVideo, panel4Controller, this);
		alienDefend.addObserver(login);

		panel4.add(login);
		panel4.add(introVideo, "Intro Video");
		panel4.add(unitedEarthDirectorate, "Main");
	}

	/**
	 * Returns panel4. This is for CardLayout swapping
	 * @return panel4
	 */
	public JPanel getPanel4() {
		return panel4;
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void update(Observable o, Object arg) {
        String command = (String) arg;
        String[] commandParts = command.split(" ");
        if (command.contains("grabbed")) {
        	setTitle(command); 
        }
        
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

		} else if (command.equalsIgnoreCase("loadingStart")) {		// Start loading animation
        	initLoadingGif();

		} else if (command.equalsIgnoreCase("data")) {				// Data is arrived
			panel3.initStats();
			panel2Model.distributeIncidents(model.getRequestedData());
			statsModel.calculateStats();

			jlLog.setIcon(null);
			String duration = Model.calculateDuration(model.timeTakenToLoad(System.currentTimeMillis()));	// calculate total time taken fetching and processing the data
			jlLog.setText(processingText + duration
					+ "<br><br><b>Please now interact with this data using<br>the buttons to the left and the right.</b></div></html>");
			jbLeft.setEnabled(true);			// enable buttons
			jbRight.setEnabled(true);
		}
	}
}
