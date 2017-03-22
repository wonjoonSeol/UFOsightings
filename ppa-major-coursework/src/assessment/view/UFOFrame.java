package assessment.view;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import assessment.controller.Controller;
import assessment.model.Model;
import assessment.model.panel2.MapUS;
import assessment.view.panel2.mapLayer.MapPanel;
import api.ripley.Ripley;

/**
 * Created by wonjoonseol on 05/03/2017.
 */
public class UFOFrame extends JFrame implements Observer{

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
    private JComboBox<String> jcFrom;
    private JComboBox<String> jcTo;
    private Controller controller;
    private String loadingText;
    private Model model;
    private StatPanel panel2;
    private String processingText;
    private Ripley ripley;

    public UFOFrame(Controller controller, Ripley ripley, Model model) {
        super();
        this.controller = controller;
        this.ripley = ripley;
        this.model = model;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 680)); 
		setResizable(false); 

        initTop();
        initCenter();
        initBottom();
        initFrame();
        pack();
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
        MapUS panel2Model = new MapUS(ripley.
        		getIncidentsInRange("2000-01-01 00:00:00", "2000-02-01 00:00:00")); 
        jpCenter.add(new MapPanel(panel2Model)); 
        //panel2 = new StatPanel(model);
        //jpCenter.add(panel2);
  
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

        int startYear = ripley.getStartYear();
        int lastYear = ripley.getLatestYear();
        for (int i = startYear; i < lastYear; i++) {
            jcFrom.addItem(i + "");
            jcTo.addItem(i + "");
        }

        jcFrom.addActionListener(controller);
        jcTo.addActionListener(controller);

    }

    private void loadingScreen() {
        loadingText = "<html><div Style='text-align: center;'>Welcome to Ripley API v" + ripley.getVersion() + "<br>" +
                             "Please select the dates above, in order to<br>Begin analysing UFO sighting data.<br><br>";

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

    @Override
    public void update(Observable o, Object arg) {
        String command = (String) arg;
        String[] commandParts = command.split(" ");

        if (commandParts.length == 2) {
            try {
                int startYear = Integer.parseInt(commandParts[0]);
                int endYear = Integer.parseInt(commandParts[1]);

                processingText = loadingText + "Date range selected, " + startYear + " - " + endYear + "<br><br>Grabbing data...<br><br>";
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
            jlLog.setText(processingText + string + "<br><br><b>Please now interact with this data using<br>the buttons to the left and the right.</b></div></html>");
            jbLeft.setEnabled(true);
            jbRight.setEnabled(true);
            panel2.initStats();
        }
    }
}
