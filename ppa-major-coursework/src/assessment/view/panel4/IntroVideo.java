package assessment.view.panel4;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by wonjoonseol on 25/03/2017.
 */
public class IntroVideo extends JPanel {
    private String imageLocation;
    private JLabel jlWelcome;
    private Panel4 panel4;
    private UnitedEarthDirectorate unitedEarthDirectorate;

    public IntroVideo(Panel4 panel4, UnitedEarthDirectorate unitedEarthDirectorate) {
        this.unitedEarthDirectorate = unitedEarthDirectorate;
        this.panel4 = panel4;
        setLayout(new BorderLayout());
        imageLocation = "images/Welcome Back Commander.gif";
        jlWelcome = new JLabel();
        add(jlWelcome, BorderLayout.CENTER);
    }

    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/Welcome Back Commander.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void playImage() {
        ImageIcon welcomeIcon = new ImageIcon(imageLocation);
        welcomeIcon.setImage(welcomeIcon.getImage().getScaledInstance(800, 660, Image.SCALE_DEFAULT));
        jlWelcome.setIcon(welcomeIcon);
    }

    public void playVideoSeparateThread() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                playImage();
                Thread.sleep(1500);
                playSound();

                Thread.sleep(58000);
                CardLayout cardLayout = (CardLayout) panel4.getLayout();
                cardLayout.show(panel4, "Main");
                unitedEarthDirectorate.playSound();
                unitedEarthDirectorate.playAnimation();
                return null;
            }
        }.execute();
    }
}
