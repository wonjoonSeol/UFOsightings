package assessment.model.panel2.comparators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import api.ripley.Incident;

/** 
 * Compares incidents based on the chronological order of the date and time on which 
 * they happened.
 * 
 * NOTE: this class, and comparators in this package will do any meaningful comparison on objects of 
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
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
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
			
			try {
				Date date1 = formatter.parse(time1String);
				Date date2 = formatter.parse(time2String); 
				return date1.compareTo(date2); 
				
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("No date parseable");
			} 
		
		
		}
		return 0;
	}
	
}
