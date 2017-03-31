package assessment.model.panel2.comparators;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.ripley.Incident;

/** 
 * Comparator type that can compare incidents based on their duration 
 * @author Munkhtulga Battogtokh
 *
 * @param <Object>
 */
@SuppressWarnings("hiding")
public class DurationComparator<Object> implements Comparator<Object> {

	private static final Pattern DURATION_PATTERN = Pattern.compile("(\\d)+"); 		 	// Pattern for matching duration
	private static final Pattern FRACTION_PATTERN = Pattern.compile("(\\d)+/(\\d)+"); 	// Pattern for matching fraction 
	
	/** 
	 * Compares to given Incidents considering the lengths of their durations
	 * @param incident1 Object first Incident (no comparison measure defined for other types) 
	 * @param incident2 Object second Incident (no comparison measure defined for other types) 
	 */
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
			
			return duration1.compareTo(duration2); 
		}
		return 0; 
	}
	
	/** 
	 * Given a string (expected to have some information related to time), parses and returns any time 
	 * information in minutes, assuming the matcher is designed for extracting time information. 
	 * @param matcher Matcher matcher to match the given string against
	 * @param durationString String to be parsed, and contained time information converted to minutes
	 * @return int duration in minutes
	 */
	public static int toMinutes(Matcher matcher, String durationString) {
		int duration = Integer.MAX_VALUE; 
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
				String[] hourSubStrings = durationString.split("hour"); 
				
				// If fraction of format as in "1/2" is contained, find the one right before the "hour" substring
				Matcher fractionExtractor = FRACTION_PATTERN.matcher(hourSubStrings[0]); 
				
				if (fractionExtractor.find()) {
					String fractionString = ""; 
					
					fractionString = fractionExtractor.group();
									
					// Calculate actual value of fraction in float
					String[] numbers = fractionString.split("/"); 
					double nominator; 
					double denominator; 
					double durationValue; 
				
					try {
						nominator = Integer.parseInt(numbers[0]); 
						denominator = Integer.parseInt(numbers[1]); 
						durationValue = (nominator / denominator); 
					} catch (NumberFormatException e) {
						durationValue = duration; 
					}
					duration = (int)(durationValue * 60); 
				} else {
					duration *= 60; 
				}
				
			} else if (durationString.toLowerCase().contains("se")
					    && !durationString.toLowerCase().contains("min")) {
				duration /= 60; 
			}
		}
		
		return duration; 
	}

}
