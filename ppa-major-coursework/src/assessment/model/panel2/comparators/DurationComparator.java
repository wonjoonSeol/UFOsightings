package assessment.model.panel2.comparators;

import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.ripley.Incident;

@SuppressWarnings("hiding")
public class DurationComparator<Object> implements Comparator<Object> {

	private static final Pattern DURATION_PATTERN = Pattern.compile("(\\d)+"); 
	
	@Override
	public int compare(Object incident1, Object incident2) {
		if (incident1 instanceof Incident && incident2 instanceof Incident) {
			Integer duration1 = -1; 
			Integer duration2 = -1; 
			String duration1String = ((Incident) incident1).getDuration(); 
			String duration2String = ((Incident) incident2).getDuration();
				
			Matcher intExtractor1 = DURATION_PATTERN.matcher(duration1String); 
			Matcher intExtractor2 = DURATION_PATTERN.matcher(duration2String); 
			
			duration1 = toMinutes(intExtractor1, duration1String); 
			duration2 = toMinutes(intExtractor2, duration2String); 
			
			if (duration1.compareTo(duration2) <= 0) {
				return -1; 
			} else if (duration1.compareTo(duration2) > 0) {
				return 1; 
			}
		}
		return 0; 
	}
	
	// Add method to take duration format string and return an int to tell how many minutes that is supposed to mean
	public static int toMinutes(Matcher matcher, String durationString) {
		int duration = 111111; 
		if (matcher.find()) {
			// Find the number right before the "min" substring
			if (durationString.contains("min")) {
				String[] subStrings = durationString.split("min"); 
				Matcher lastIntExtractor = DURATION_PATTERN.matcher(subStrings[0]); 
			
				String lastIntString = ""; 
				while (lastIntExtractor.find()) {
					lastIntString = lastIntExtractor.group(); 
				}
				duration = Integer.parseInt(lastIntString); 
			} else {
				duration = Integer.parseInt(matcher.group()); 
			}
			
			// Using unit rescale to minute
			if (durationString.toLowerCase().contains("hour")) {
				duration *= 60; 
			} else if (durationString.toLowerCase().contains("se")
					    && !durationString.toLowerCase().contains("min")) {
				duration /= 60; 
			}
		}
		
		return duration; 
	}

}
