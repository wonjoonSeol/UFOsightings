package assessment.model.panel2.comparators;

import java.util.Comparator;

import api.ripley.Incident;

@SuppressWarnings("hiding")
public class CityComparator<Object> implements Comparator<Object> {

	@Override
	public int compare(Object incident1, Object incident2) {
		if (incident1 instanceof Incident && incident2 instanceof Incident) {
			String city1 = ((Incident) incident1).getCity(); 
			String city2 = ((Incident) incident2).getCity();
		
			if (city1.compareTo(city2) <= 0) {
				return -1; 
			} else if (city1.compareTo(city2) > 0) {
				return 1; 
			}
		}
		return 0;
	}

}
