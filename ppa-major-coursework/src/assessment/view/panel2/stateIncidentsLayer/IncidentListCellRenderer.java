package assessment.view.panel2.stateIncidentsLayer;

import java.awt.Color;
import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import api.ripley.Incident;
import assessment.model.panel2.comparators.DurationComparator;

/**
 * Renders list entries so that a JList of incidents would be displayed in desired way
 * @author Munkhtulga Battogtokh
 */
@SuppressWarnings("serial")
public class IncidentListCellRenderer extends JLabel implements ListCellRenderer<Object> {

	private static final Pattern DURATION_PATTERN = Pattern.compile("(\\d)+");
	
	/**
	 * Overrides the parent method to specialise for rendering 
	 * incident entries in a JList
	 */
	@Override
	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int arg2, boolean isSelected, boolean cellHasFocus) {
		if (value instanceof Incident) {
			setText(incidentToEntry((Incident)value)); 
		}
		
		if (cellHasFocus) {
			this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		} else {
			this.setBorder(null);
		}
		
		return this; 
	}
	

	/** 
	 * Auxiliary method to convert a given incident to a standardized String format 
	 * @param incident Incident to be standardized to String
	 */
	public String incidentToEntry(Incident incident) {
		String durationString = incident.getDuration(); 
		Matcher matcher = DURATION_PATTERN.matcher(durationString); 
		
		String formattedString = ""; 
		formattedString += ("Time: " + incident.getDateAndTime()); 
		formattedString += (" City: " + incident.getCity()); 
		formattedString += (" Shape: " + incident.getShape()); 
		
		int durationInt = DurationComparator.toMinutes(matcher, durationString); 
		if (durationInt == Integer.MAX_VALUE) {
			formattedString += (" Duration: " + (-1)); 
		} else {
			formattedString += (" Duration: " + durationInt); 
		}
		
		formattedString += (" Posted: " + incident.getPosted()); 
		
		return formattedString; 
	}
	
	
	
}
