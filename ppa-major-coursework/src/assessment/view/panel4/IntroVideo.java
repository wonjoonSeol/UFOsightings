package assessment.view.panel4;

import assessment.view.UFOFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This class represents C&C video player
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class IntroVideo extends JPanel {
    private String imageLocation;
    private JLabel jlWelcome;
    private UFOFrame ufoFrame;
    private UnitedEarthDirectorate unitedEarthDirectorate;

    /**
     * Constructor IntroVideo constructs with UFOFrame, UnitedEarthDirectorate
     *
     * @param ufoFrame mainFrame
     * @param unitedEarthDirectorate game panel
     */
    public IntroVideo(UFOFrame ufoFrame, UnitedEarthDirectorate unitedEarthDirectorate) {
        this.unitedEarthDirectorate = unitedEarthDirectorate;
        this.ufoFrame = ufoFrame;
        setLayout(new BorderLayout());
        imageLocation = "images/Welcome Back Commander.gif";
        jlWelcome = new JLabel();
        add(jlWelcome, BorderLayout.CENTER);
    }
    /**
     * This static method plays wav file single time using the supplied string location
     * @param location location of the wav file
     */
    public static void playSound(String location) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(location).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Plays gif file
     */
    public void playImage() {
        ImageIcon welcomeIcon = new ImageIcon(imageLocation);
        welcomeIcon.setImage(welcomeIcon.getImage().getScaledInstance(800, 660, Image.SCALE_DEFAULT));
        jlWelcome.setIcon(welcomeIcon);
    }

    /**
     * Plays gif and sound in a separate thread to play video
     */
    public void playVideoSeparateThread() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                playImage();
                Thread.sleep(1500);                         // syncs with gif, this is due to converting avi to gif. The exported video file does not have same number of frames per second.
                playSound("sound/Welcome Back Commander.wav");

                Thread.sleep(58000);                        // when the video is over
                CardLayout cardLayout = (CardLayout) ufoFrame.getPanel4().getLayout();
                cardLayout.show(ufoFrame.getPanel4(), "Main");
                unitedEarthDirectorate.playSound("sound/tronlegacy.wav");
                unitedEarthDirectorate.playAnimation();
                return null;
            }
        }.execute();
    }
}
