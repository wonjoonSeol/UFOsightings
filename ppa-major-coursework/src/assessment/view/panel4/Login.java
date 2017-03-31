package assessment.view.panel4;

import assessment.controller.panel4.Panel4Controller;
import assessment.model.panel4.AlienDefend;
import assessment.view.UFOFrame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This class represents login panel for optional panel
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class Login extends JPanel implements Observer{
    private AlienDefend alienDefend;
    private UFOFrame ufoFrame;
    private IntroVideo introvideo;
    private Panel4Controller panel4Controller;
    private JTextField jtfName;
    private BufferedImage bgImage;
    private Dimension dimension;

    /**
     * Constructor Login constructs with AlienDefend, IntroVideo, Panel4Controller and UFOFrame
     *
     * @param alienDefend AlienDefend model
     * @param introVideo Video player
     * @param panel4Controller panel 4 controller
     * @param ufoframe Main frame
     */
    public Login(AlienDefend alienDefend, IntroVideo introVideo, Panel4Controller panel4Controller, UFOFrame ufoframe) {
        super();
        this.alienDefend = alienDefend;
        this.introvideo = introVideo;
        this.ufoFrame = ufoframe;
        this.panel4Controller = panel4Controller;
        dimension = new Dimension(800, 660);
        initImages();
        setLayout(new BorderLayout());

        setPreferredSize(dimension);
        welcomeBackCommander();
        acknowledgements();
    }

    /**
     * initialise login image
     */
    private void initImages() {
        try {
            bgImage = ImageIO.read(new File("images/login.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialise login widgets
     */
    public void welcomeBackCommander() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel loginPanel = new JPanel(new FlowLayout());

        String string = "<html><div Style='text-align: center;'><b><br><br><br><br><br><br>" +
                "<br><br><br><br><br><br><br><br><br><br><br><br><br><font color=\"white\">" +
                "Please authenticate your name, Commmander</font><br><font color=\"Gray\", size = \"3\">i" +
                "※ Make sure you have suitable audio system set up for the military communication</font><br><br></div></html>";
        JLabel label = new JLabel(string, SwingConstants.CENTER);

        jtfName = new JTextField();
        jtfName.setPreferredSize(new Dimension(200,20));
        jtfName.addKeyListener(panel4Controller);
        loginPanel.add(jtfName);
        loginPanel.setOpaque(false);

        topPanel.add(label, BorderLayout.PAGE_START);
        topPanel.add(loginPanel, BorderLayout.CENTER);
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.PAGE_START);
    }

    /**
     * shows acknowledgment in the bottom left panel
     */
    private void acknowledgements() {
        JLabel jlacknowlegement = new JLabel();
        String string = "<html>Intro video : C&C Tiberian Sun<br>Images : XCOM: Enemy Within<br>" +
                "Ost : Daft punk - The game has changed<br>Logo icon : Star Trek: StarFleet command";
        jlacknowlegement.setText(string);
        jlacknowlegement.setForeground(Color.DARK_GRAY);
        add(jlacknowlegement, BorderLayout.PAGE_END);
    }

    /**
     * Scales the image to fit the screen
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        double scaleWidth = dimension.getWidth() / bgImage.getWidth();
        double scaleHeight = dimension.getHeight() / bgImage.getHeight();
        double scale = Math.min(scaleHeight, scaleWidth);
        setBackground(Color.black);                                         // fill the remaining space in black
        g.drawImage(bgImage.getScaledInstance((int) (bgImage.getWidth() * scale), -1, Image.SCALE_SMOOTH), 0, 0, null);
    }

    /**
     * Sets commander name and show intro video
     * @param o
     * @param arg
     * {@inheritDoc}
     */
    @Override
    public void update(Observable o, Object arg) {
        String command = (String) arg;

        if (command.equals("Request Message")) {
            alienDefend.setCommanderName(jtfName.getText());
            CardLayout cardLayout = (CardLayout) ufoFrame.getPanel4().getLayout();
            cardLayout.show(ufoFrame.getPanel4(), "Intro Video");
            introvideo.playVideoSeparateThread();
        }
    }
}
