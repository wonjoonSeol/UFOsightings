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
        UFOFrame mainView = new UFOFrame(controller, ripley, model);
        controller.setView(mainView);
        mainView.setVisible(true);
        model.addObserver(mainView);

//        System.out.println(ripley.getIncidentsInRange("1561-01-01 00:00:00", "1620-01-01 00:00:00"));  //TESTING
//        ArrayList<Incident> test = new ArrayList<>();                                                  //TESTING
//        test = ripley.getIncidentsInRange("1561-01-01 00:00:00", "1620-01-01 00:00:00");
//        model.getRequestedData();
    }
}
