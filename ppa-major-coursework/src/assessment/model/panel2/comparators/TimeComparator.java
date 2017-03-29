package assessment.model.panel2.comparators;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.Parser;

import api.ripley.Incident;

/** 
 * Compares incidents based on the chronological order of the date and time on which 
 * they happened. NOTE: this class will do any meaningful comparison on objects of 
 * Incident type only. Even if the generic type was declared to be of Incident, one can 
 * pass any object to the generic type. In order to limit the functionality to only 
 * Incident types, the generic type is checked by instanceof if it is of this type. 
 * This instanceof check is not possible if the generic type parameter in the class defintion 
 * is given as Incident, not Object. 
 * 
 * @author Munkhtulga Battogtokh
 */
@SuppressWarnings("hiding")
public class TimeComparator<Object> implements Comparator<Object> {
	
	private Parser parser; 
	
	
	public TimeComparator() {
		this.parser = new Parser(); 
	}
	
	/** 
	 * Parses Date objects from the dateAndTime of the passed incidents. 
	 * Compares based on chronological order of those dates. 
	 * @param incident1 Object incident 1 to be compared
	 * @param incident2 Object incident 2 to be compared
	 */
	@Override
	public int compare(Object incident1, Object incident2) {
		if (incident1 instanceof Incident && incident2 instanceof Incident) {
			String time1String = ((Incident) incident1).getDateAndTime(); 
			String time2String = ((Incident) incident2).getDateAndTime();
		
			List<Date> time1DateListWithOneDate = parser.parse(time1String).get(0).getDates(); 
			Date date1 = time1DateListWithOneDate.get(0); 
		
			List<Date> time2DateListWithOneDate = parser.parse(time2String).get(0).getDates(); 
			Date date2 = time2DateListWithOneDate.get(0); 
		
			if (date1.compareTo(date2) <= 0) {
				return -1; 
			} else if (date1.compareTo(date2) > 0) {
				return 1; 
			}
		}
		return 0;
	}
	
}
