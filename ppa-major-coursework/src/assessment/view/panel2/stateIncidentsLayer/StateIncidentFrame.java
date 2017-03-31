package assessment.view.panel2.stateIncidentsLayer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import api.ripley.Incident;
import assessment.model.panel2.comparators.CityComparator;
import assessment.model.panel2.comparators.DurationComparator;
import assessment.model.panel2.comparators.PostedComparator;
import assessment.model.panel2.comparators.ShapeComparator;
import assessment.model.panel2.comparators.TimeComparator;
import assessment.model.panel2.StateUS; 
import assessment.view.panel2.mapLayer.StateLabel;

/** 
 * Pop-up window when a state marker is clicked that displays the list of 
 * incidents in a rendered format for the specific state the marker was for
 * @author Munkhtulga Battogtokh
 *
 */
public class StateIncidentFrame extends JFrame {
	
	/**
	 * Removes warning: Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private JList<Incident> incidentsList; 	// List of incidents display
	private StateLabel sourceLabel; 	// the label from which the frame is called
	private StateUS state; 
	
	private DefaultListModel<Incident> listModel; 	// list model
	private ArrayList<Incident> timeSortedList; 	// list version to store incidents sorted by time 
	private ArrayList<Incident> citySortedList; 	// list version to store incidents sorted by city
	private ArrayList<Incident> shapeSortedList; 	// list version to store incidents sorted by shape
	private ArrayList<Incident> durationSortedList; // list version to store incidents sorted by duration
	private ArrayList<Incident> postedSortedList; 	// list version to store incidents sorted by posted date
	private ArrayList<Incident> unsortedList; 		// list version that is unsorted
	
	private TimeComparator<Incident> timeComparator; 
	private CityComparator<Incident> cityComparator; 
	private ShapeComparator<Incident> shapeComparator; 
	private DurationComparator<Incident> durationComparator; 
	private PostedComparator<Incident> postedComparator;  // comparators in this paragraph of fields
	
	/** 
	 * Constructs a frame object of this class based on a given state, and 
	 * label for that state. 
	 * @param state StateUS the model state
	 * @param sourceLabel StateUS the view representation
	 */
	public StateIncidentFrame(StateUS state, StateLabel sourceLabel) {
		super(state.getName() + " (" + state.getAbbreviation() + ")"); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		// Initialise comparators only once here
		this.timeComparator = new TimeComparator<Incident>(); 
		this.cityComparator = new CityComparator<Incident>(); 
		this.shapeComparator = new ShapeComparator<Incident>(); 
		this.durationComparator = new DurationComparator<Incident>(); 
		this.postedComparator = new PostedComparator<Incident>(); 
		
		this.sourceLabel = sourceLabel; 
		this.state = state; 
		
		initWidgets(); 
	
	}
	
	@SuppressWarnings("unchecked")
	private void initWidgets() {
		// Restore the size of the calling label upon close 
		this.addWindowListener(new WindowAdapter() {
			
			/** 
			 * Resets size of the StateLabel from which this StateIncidentFrame was called
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				sourceLabel.changeSize(sourceLabel.getScaledSize());
			}
					
		});
			
		
		// Include all incidents in list model of the incidentsList unordered when constructing
		listModel = new DefaultListModel<>();  	
		unsortedList = state.getIncidents(); 
		for (int i = 0; i < unsortedList.size(); i++) {
			listModel.addElement(unsortedList.get(i));
		}		
		
		
		incidentsList = new JList<Incident>(listModel); 
		incidentsList.setCellRenderer(new IncidentListCellRenderer());
		// Single click results in a dialog displaying the details of the incident 
		incidentsList.addMouseListener(new MouseAdapter() {
			
			/** 
			 * Opens the summary of the incident selected from JList the anonymous 
			 * MouseAdapter is registered with, namely the incidentsList of outer class
			 */
			public void mouseClicked(MouseEvent e) {
				
				JList<Incident> list = (JList<Incident>) e.getSource(); 
				if (e.getClickCount() == 1) {
					try {
						JEditorPane jepMessage = new JEditorPane();
						
						String summary = list.getSelectedValue().getSummary(); 
						jepMessage.setContentType("text/html");
						jepMessage.setEditable(false);
						jepMessage.setText("<html><body style='width:180px; height:100px'>" + summary); 
						
						jepMessage.setBackground(getBackground());
						
						JScrollPane jspMessage = new JScrollPane(jepMessage); 
						jspMessage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						jspMessage.setBorder(null);
						jspMessage.setPreferredSize(new Dimension(250, 120));
						JOptionPane.showMessageDialog(null, jspMessage, "Incident Details",
								JOptionPane.PLAIN_MESSAGE , new ImageIcon(
									(ImageIO.read(new File("images/ufo.png"))).getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
					} catch (HeadlessException exception) {
						System.out.println("Dialog headless");
					} catch (IOException ioException) {
						System.out.println("IO exception"); 
					}
				}
				
			}
			
		});
		
		setLayout(new BorderLayout()); 
		setSize(500, 400); 
	
		
		JPanel jpCenter = new JPanel(new BorderLayout()); 
		jpCenter.add(new JScrollPane(incidentsList), BorderLayout.CENTER); 
		jpCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel jpNorth = new JPanel(new BorderLayout()); 
		jpNorth.add(initComboBox()); 
		jpNorth.setBorder(new EmptyBorder(8, 8, 8, 8));
		
		add(jpCenter, BorderLayout.CENTER); 
		add(jpNorth, BorderLayout.NORTH); 
	}
	
	/**
	 * Auxiliary method to initialise the drop down menu
	 * @return JComboBox the initialised object of the combo box
	 */
	public JComboBox<String> initComboBox() {
		// Set up the drop-down menu for sorting 
		JComboBox<String> jcbSorter = new JComboBox<>(); 
		jcbSorter.addItem("-");
		jcbSorter.addItem("Date");
		jcbSorter.addItem("City");
		jcbSorter.addItem("Shape");
		jcbSorter.addItem("Duration");
		jcbSorter.addItem("Posted");
		jcbSorter.addActionListener(new sortListener());
		
		return jcbSorter; 
	}
	
	/** 
	 * Inner action listener class that sorts the list entries in chosen order
	 * @author admin
	 *
	 */
	public class sortListener implements ActionListener {
		
		/** 
		 * Overrides parent method actionpPerformed to perform sorting ordering as 
		 * selected by user when the sortListener is registered with the JCombobox 
		 * of the outer class. 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<String> jcb = (JComboBox<String>) e.getSource(); 
			
			String selectedString = (String)jcb.getSelectedItem(); 
			if ("Date".equals(selectedString)) 
				sortBy(timeComparator, timeSortedList); 
			if ("City".equals(selectedString)) 
				sortBy(cityComparator, citySortedList); 
			if ("Shape".equals(selectedString)) 
				sortBy(shapeComparator, shapeSortedList); 
			if ("Duration".equals(selectedString))
				sortBy(durationComparator, durationSortedList); 
			if ("Posted".equals(selectedString)) 
				sortBy(postedComparator, postedSortedList); 
			
		}
		
		/** 
		 * Sorts the listModel of the outerclass by given comparator. Updates the 
		 * corresponding sorted list version of the outer class, eg: TimeSortedList if 
		 * user selects to sort by date and time. Gives initial population to the sorted list 
		 * versions if used the first time. 
		 * @param comparator Comparator<Incident> compares incidents 
		 * @param list ArrayList<Incident> uses this list to update the listmodel 
		 */
		public void sortBy(Comparator<Incident> comparator, ArrayList<Incident> list) {
			listModel.clear();
			
			// Initialise the sorted list if first time
			if (list == null) {
				unsortedList.sort(comparator); 
				if (comparator instanceof TimeComparator<?>) { 
					timeSortedList = new ArrayList<Incident>(); 
					for (int i = 0; i < unsortedList.size(); i++) {
						timeSortedList.add(unsortedList.get(i)); 
					}
				}
				if (comparator instanceof CityComparator<?>) { 
					citySortedList = new ArrayList<Incident>(); 
					for (int i = 0; i < unsortedList.size(); i++) {
						citySortedList.add(unsortedList.get(i)); 
					}
				}; 
				if (comparator instanceof ShapeComparator<?>) {
					shapeSortedList = new ArrayList<Incident>(); 
					for (int i = 0; i < unsortedList.size(); i++) {
						shapeSortedList.add(unsortedList.get(i)); 
					}
				};  	
				if (comparator instanceof DurationComparator<?>) {
					durationSortedList = new ArrayList<Incident>(); 
					for (int i = 0; i < unsortedList.size(); i++) {
						durationSortedList.add(unsortedList.get(i)); 
					}
				}
				if (comparator instanceof PostedComparator<?>) {
					postedSortedList = new ArrayList<Incident>(); 
					for (int i = 0; i < unsortedList.size(); i++) {
						postedSortedList.add(unsortedList.get(i)); 
					}
				}
			} 
		
			if (comparator instanceof TimeComparator<?>) list = timeSortedList;
			if (comparator instanceof CityComparator<?>) list = citySortedList;  
			if (comparator instanceof ShapeComparator<?>) list = shapeSortedList; 	
			if (comparator instanceof DurationComparator<?>) list = durationSortedList; 
			if (comparator instanceof PostedComparator<?>) list = postedSortedList; 

			for (int i = 0; i < list.size(); i++) {
				listModel.addElement(list.get(i));
			}		
		}
	}
}
