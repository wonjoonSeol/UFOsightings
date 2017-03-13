package panel2.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This is for testing and seeing the contents of the panel 2
 * @author admin
 *
 */
public class MainFrame extends JFrame {
	
	private JPanel panel2; 
	
	public MainFrame() {
		super(); 
		setPreferredSize(new Dimension(500, 500)); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setLayout(new BorderLayout()); 

		panel2 = new MapPanel(); 
		add(panel2, BorderLayout.CENTER); 	
		
		pack(); 
	}
	
}
