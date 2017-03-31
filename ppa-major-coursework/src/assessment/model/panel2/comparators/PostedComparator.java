package assessment.model.panel2.comparators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import api.ripley.Incident;

/** 
 * Comparator type that can compare instances of Incident class by the "posted" attribute. 
 * @author Munkhtulga Battogtokh
 *
 * @param <Object> not Incident, as generic type accepts any object unregardless of this parameter
 */
@SuppressWarnings("hiding")
public class PostedComparator<Object> implements Comparator<Object> {

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	
	/** 
	 * Parses Date objects from the posted time in string form of the passed incidents. 
	 * Compares based on chronological order of those dates. 
	 * @param incident1 Object incident 1 to be compared
	 * @param incident2 Object incident 2 to be compared
	 */
	@Override
	public int compare(Object incident1, Object incident2) {
		if (incident1 instanceof Incident && incident2 instanceof Incident) {
			String posted1String = ((Incident) incident1).getPosted(); 
			String posted2String = ((Incident) incident2).getPosted();
			
			try {
				Date posted1 = formatter.parse(posted1String);
				Date posted2 = formatter.parse(posted2String); 
				
				return posted1.compareTo(posted2); 
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("No parseable date found");
			} 
		
			
		}
		return 0;
	}
}
