package assessment.view.panel4;

import assessment.controller.panel4.Panel4Controller;
import assessment.model.panel4.AlienDefend;

import javax.swing.*;
import java.awt.*;

/**
 * Created by wonjoonseol on 25/03/2017.
 */
public class Panel4 extends JPanel{

    public Panel4() {
        setLayout(new CardLayout());
        initWidgets();
    }

    public void initWidgets(){
        UnitedEarthDirectorate unitedEarthDirectorate = new UnitedEarthDirectorate();
        AlienDefend alienDefend = new AlienDefend();
        Panel4Controller panel4Controller = new Panel4Controller(alienDefend);
        IntroVideo introvideo = new IntroVideo(this, unitedEarthDirectorate);
        Login login = new Login(alienDefend, introvideo, panel4Controller, this);
        alienDefend.addObserver(login);

        add(login);
        add(introvideo, "IntroVideo");
        add(unitedEarthDirectorate, "Main");
    }

    public static void createAndShowGui() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(800, 660));
        frame.setResizable(false);
        frame.add(new Panel4());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}
