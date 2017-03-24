package assessment.model.panel2.comparators;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.Parser;

import api.ripley.Incident;

/** 
 * 
 * @author Munkhtulga Battogtokh
 *
 * @param <Object> not Incident, as generic type accepts any object unregardless of this parameter
 */
@SuppressWarnings("hiding")
public class PostedComparator<Object> implements Comparator<Object> {

private Parser parser; 
	
	public PostedComparator() {
		this.parser = new Parser(); 
	}
	
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
		
			List<Date> posted1DateListWithOneDate = parser.parse(posted1String).get(0).getDates(); 
			Date posted1 = posted1DateListWithOneDate.get(0); 
		
			List<Date> posted2DateListWithOneDate = parser.parse(posted2String).get(0).getDates(); 
			Date posted2 = posted2DateListWithOneDate.get(0); 
		
			if (posted1.compareTo(posted2) <= 0) {
				return -1; 
			} else if (posted1.compareTo(posted2) > 0) {
				return 1; 
			}
		}
		return 0;
	}
}
