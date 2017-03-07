package panel2.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import panel2.controller.LabelMouseAdapter;
import panel2.controller.StateLabel;
import panel2.model.MapUS;
import panel2.model.StateUS;

public class MapPanel extends JPanel {
	
	private BufferedImage imageMap; 		// image for map
	private MapUS map; 						// Model for this map panel 
	private LabelMouseAdapter mouseAdapter; // Mouse adapter for all statelabels
	private BufferedImage imageMarker; 		// marker image for all state labels
	
	public MapPanel(MapUS map) {
		setLayout(null); // Enables free positioning labels
		this.map = map; 	
		
		try {
			imageMap = ImageIO.read(new File("images\\map.png"));
			imageMarker = ImageIO.read(new File("images\\alien.png")); 
		} catch (IOException e) {}
		
		mouseAdapter = new LabelMouseAdapter(); 
		
		initStateMarker(); 
		
	}
	
	/** 
	 * Experimental method that creates a button for single state
	 */
	public void initStateMarker() {
		// icon, used for all states initialised here only once
		ImageIcon icon = new ImageIcon(imageMarker); 
		
		map.getState(0).setIncidentsCount(10); 
		
		StateLabel arizonaLabel = new StateLabel(map.getState(0), 
									imageMarker, 150, 370);
		
		arizonaLabel.addMouseListener(mouseAdapter);
		
		add(arizonaLabel); 
		
		

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageMap, 0, 0, getWidth(), getHeight(), null); 
	
	}
}
