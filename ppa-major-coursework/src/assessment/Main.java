package assessment;

import api.ripley.Ripley;
import assessment.controller.Controller;
import assessment.model.Model;
import assessment.view.UFOFrame;

/**
 * Created by wonjoonseol on 05/03/2017.
 */
public class Main {

    public static void main(String[] args) {
        Ripley ripley = new Ripley("90tLI3CSu9GyVD6ql2OMtA==", "lBgm4pRs/wHVqL46EnH7ew==");
        Model model = new Model(ripley);
        Controller controller = new Controller(model);
        UFOFrame mainView = new UFOFrame(controller, ripley);
        mainView.setVisible(true);
        model.addObserver(mainView);
    }
}
