package assessment.view.panel4;

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
 * This class is our game panel
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class UnitedEarthDirectorate extends JPanel{
    private String imageLocation;
    private JLabel jlGlobe;

    /**
     * Constructor unitedEarthDirectorate
     */
    public UnitedEarthDirectorate() {
        super();
        jlGlobe = new JLabel();
        setLayout(new BorderLayout());
        imageLocation = "images/globe.gif";;
        add(jlGlobe, BorderLayout.CENTER);
    }

    public void playAnimation() {
        ImageIcon globeIcon = new ImageIcon(imageLocation);
        globeIcon.setImage(globeIcon.getImage().getScaledInstance(800, 660, Image.SCALE_DEFAULT));
        jlGlobe.setIcon(globeIcon);
    }

    /**
     * This static method plays wav file continuously using the supplied string location
     * @param location location of the wav file
     */
    public static void playSound(String location) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(location));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
