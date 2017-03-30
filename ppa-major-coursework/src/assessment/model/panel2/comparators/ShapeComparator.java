package assessment.model.panel2.comparators;

import java.util.Comparator;

import api.ripley.Incident;

@SuppressWarnings("hiding")
public class ShapeComparator<Object> implements Comparator<Object> {

	@Override
	public int compare(Object incident1, Object incident2) {
		if (incident1 instanceof Incident && incident2 instanceof Incident) {
			String shape1 = ((api.ripley.Incident) incident1).getShape(); 
			String shape2 = ((api.ripley.Incident) incident2).getShape();
		
			return shape1.compareTo(shape2); 
		}
		return 0;
	}
	
}
