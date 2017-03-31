package assessment.model.panel2.comparators;

import java.util.Comparator;

import api.ripley.Incident;

/** 
 * Comparator type that can compare Incidents based on the first letter of the 
 * word describing shapes of the UFOs that are associated with these Incidents.  
 * @author Munkhtulga Battogtokh
 *
 * @param <Object>
 */
@SuppressWarnings("hiding")
public class ShapeComparator<Object> implements Comparator<Object> {

	/** 
	 * Compares incidents based on alphabetical order applied to first letter of the 
	 * word describing the UFO shape. 
	 * @param incident1 Object first Incident (no comparison measure defined for other types) 
	 * @param incident2 Object second Incident (no comparison measure defined for other types) 
	 */
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
