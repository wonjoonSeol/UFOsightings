package assessment;

import api.ripley.Incident;
import api.ripley.Ripley;
import assessment.controller.Controller;
import assessment.model.Model;
import assessment.view.UFOFrame;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wonjoonseol on 05/03/2017.
 */
public class Main {

    public static void main(String[] args) {
        Ripley ripley = new Ripley("90tLI3CSu9GyVD6ql2OMtA==", "lBgm4pRs/wHVqL46EnH7ew==");
        System.out.println(ripley.getAcknowledgementString());
        Model model = new Model(ripley);            // If you are passing this ripley to any other class then most likely wrong..
        
        Controller controller = new Controller(model);
        UFOFrame mainView = new UFOFrame(controller, ripley, model);
        controller.setView(mainView);
        mainView.setVisible(true);

    }
}
