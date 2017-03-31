package assessment;

import api.ripley.Ripley;
import assessment.controller.Controller;
import assessment.model.Model;
import assessment.view.UFOFrame;

/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This class is a driver class
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class Main {

    public static void main(String[] args) {
        Ripley ripley = new Ripley("90tLI3CSu9GyVD6ql2OMtA==", "lBgm4pRs/wHVqL46EnH7ew==");
        System.out.println(ripley.getAcknowledgementString());
        Model model = new Model(ripley);
        
        Controller controller = new Controller(model);
        UFOFrame mainView = new UFOFrame(controller, ripley, model);
        controller.setView(mainView);
        mainView.setVisible(true);
    }
}
