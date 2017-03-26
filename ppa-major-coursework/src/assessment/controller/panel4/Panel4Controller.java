package assessment.controller.panel4;

import assessment.model.panel4.AlienDefend;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by wonjoonseol on 26/03/2017.
 */
public class Panel4Controller implements KeyListener {
    private AlienDefend alienDefend;

    public Panel4Controller(AlienDefend alienDefend) {
        this.alienDefend = alienDefend;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            alienDefend.requestMessage();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
