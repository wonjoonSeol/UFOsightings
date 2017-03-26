package assessment.view.panel4;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by wonjoonseol on 22/03/2017.
 */
public class UnitedEarthDirectorate extends JPanel{
    private String imageLocation;
    private JLabel jlGlobe;

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

    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/tronlegacy.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}
