package assessment.model.panel2.comparators;

import java.util.Comparator;

import api.ripley.Incident;

/** 
 * Comparator that can compare incidents by the alphabetical order 
 * of the names of cities in which they happen. 
 * @author Munkhtulga Battogtokh
 *
 * @param <Object>
 */
@SuppressWarnings("hiding")
public class CityComparator<Object> implements Comparator<Object> {

	/** 
	 * Compares two incidents based on the city names' alphabetical order 
	 * @param incident1 Object first incident to be compared (no comparison measure defined for other class types) 
	 * @param incident2 Object second incident to be compared (no comparison measure defined for other class types) 
	 */
	@Override
	public int compare(Object incident1, Object incident2) {
		if (incident1 instanceof Incident && incident2 instanceof Incident) {
			String city1 = ((Incident) incident1).getCity(); 
			String city2 = ((Incident) incident2).getCity();
		
			return city1.compareTo(city2); 
		}
		return 0;
	}

}
