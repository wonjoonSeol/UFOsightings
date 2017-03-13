package assessment.view.panel2.stateIncidentsLayer;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import api.ripley.Incident;
import assessment.model.panel2.StateUS; 
import assessment.controller.panel2.StateLabel;

public class StateIncidentFrame extends JFrame {
	
	/**
	 * Removes warning: Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private JList<Incident> incidentsList; 	// List of incidents display
	private StateLabel sourceLabel; 	// the label from which the frame is called
	private StateUS state; 
	
	public StateIncidentFrame(StateUS state, StateLabel sourceLabel) {
		super(state.getName() + " (" + state.getAbbreviation() + ")"); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.sourceLabel = sourceLabel; 
		this.state = state; 
		
		initWidgets(); 
		
		setLayout(new BorderLayout()); 
		setSize(500, 400); 
	
		
		JPanel jpCenter = new JPanel(new BorderLayout()); 
		jpCenter.add(new JScrollPane(incidentsList), BorderLayout.CENTER); 
		jpCenter.setBorder(new EmptyBorder(20, 20, 20, 20));
	
		add(jpCenter, BorderLayout.CENTER); 
	
	}
	
	public void initWidgets() {
		// Restore the size of the calling label upon close 
		this.addWindowListener(new WindowAdapter() {
					
			@Override
			public void windowClosing(WindowEvent e) {
				sourceLabel.changeSize(sourceLabel.getScaledSize());
			}
					
		});
			
		
		// Include all incidents in a new list model of the incidentsList
		DefaultListModel<Incident> listModel = new DefaultListModel<Incident>();  
		for (Incident incident: state.getIncidents()) {
			listModel.addElement(incident);}
		incidentsList = new JList<Incident>(listModel); 
		
		
		// Double click results in a dialog displaying the details of the incident 
		incidentsList.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				@SuppressWarnings("unchecked")
				JList<Incident> list = (JList<Incident>) e.getSource(); 
				if (e.getClickCount() == 2) {
					try {
					JOptionPane.showMessageDialog(null, list.getSelectedValue().getSummary(), "Message",
							JOptionPane.PLAIN_MESSAGE , new ImageIcon(
									(ImageIO.read(new File("images\\ufo.png"))).getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
					} catch (HeadlessException exception) {
						System.out.println("Dialog headless");
					} catch (IOException ioException) {
						System.out.println("IO exception"); 
					}
				}
				
			}
			
		});
		
	}
}
