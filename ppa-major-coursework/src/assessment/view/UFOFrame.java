package assessment.view;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import assessment.controller.Controller;
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
    private JComboBox<String> jcFrom;
    private JComboBox<String> jcTo;
    private Controller controller;
    private String loadingText;
    private String processingText;
    private Ripley ripley;

    public UFOFrame(Controller controller, Ripley ripley) {
        super();
        this.controller = controller;
        this.ripley = ripley;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initJComboBox();
        initWidgets();
        pack();
    }

    public void initWidgets() {
        setLayout(new BorderLayout());

        jlLog = new JLabel("", SwingConstants.CENTER);
        jlLog.setBorder(BorderFactory.createLineBorder(Color.black));
        jlLog.setPreferredSize(new Dimension(800, 400));

        jbLeft = new JButton("<");
        jbLeft.setEnabled(false);
        jbRight = new JButton(">");
        jbRight.setEnabled(false);

        jlFrom = new JLabel("From: ");
        jlTo = new JLabel("To: ");
        jlStatus = new JLabel(ripley.getLastUpdated(), SwingConstants.CENTER);

        jpTop = new JPanel();
        jpTop.setLayout(new BorderLayout());

        jpTopRight = new JPanel();
        jpTopRight.setLayout(new FlowLayout());

        jpBottom = new JPanel();
        jpBottom.setLayout(new BorderLayout());

        jpTopRight.add(jlFrom);
        jpTopRight.add(jcFrom);
        jpTopRight.add(jlTo);
        jpTopRight.add(jcTo);
        jpTop.add(jpTopRight, BorderLayout.LINE_END);

        add(jpTop, BorderLayout.PAGE_START);
        add(jlLog, BorderLayout.CENTER);
        add(jpBottom, BorderLayout.PAGE_END);

        jpBottom.add(jbLeft, BorderLayout.LINE_START);
        jpBottom.add(jbRight, BorderLayout.LINE_END);
        jpBottom.add(jlStatus, BorderLayout.CENTER);
        loadingScreen();
    }

    public void initJComboBox() {
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
            jlLog.setText(loadingText + "Please provide correct start and end year<br></div></html>");

        } else if (commandParts[0].equals("DATA")) {
            String string = "";
            for (int i = 1; i < commandParts.length; i++) {
                string += commandParts[i] + " ";
            }
            jlLog.setText(processingText + string + "<br><br><b>Please now interact with this data using<br>the buttons to the left and the right.</b></div></html>");
            jbLeft.setEnabled(true);
            jbRight.setEnabled(true);
        }
    }
}
