package panel2.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel; 

public class MapPanel extends JPanel {
	
	private BufferedImage imageMap; 
	private JLabel imageLabel; 
	
	public MapPanel() {
		super(); 
		
		try {
			imageMap = ImageIO.read(new File("image/"));
		} catch (IOException e) {}; 
		
		//imageLabel = new JLabel(new ImageIcon(imageMap));
		//add(imageLabel); 
	}
	
	@Override 
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageMap, 0, 0, null); 
		g.dispose();
	}
}
