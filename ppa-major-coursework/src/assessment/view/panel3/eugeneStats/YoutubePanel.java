package assessment.view.panel3.eugeneStats;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

public class YoutubePanel extends JPanel implements Observer{
	//Declaring fields for the widgets
	private JList<Object> videoJList;
	private DefaultListModel<Object> videoListModel;
	
	private JScrollPane scrollList;
	private JPanel videoPanels;
	private JLabel videoSlot;
	
	public YoutubePanel() {
		// TODO Pass controller through the constructor
		setLayout(new BorderLayout());
		initWidget();
	}

	public void initWidget(){ 
		//TODO add the key listeners and pass the controller in the args
		videoListModel = new DefaultListModel<Object>();
		videoJList = new JList<Object>(videoListModel);			
		scrollList = new JScrollPane(videoJList);
						
		videoPanels = new JPanel();
		videoPanels.setLayout(new GridLayout(0, 1));
		add(videoPanels, BorderLayout.CENTER);		
	}
	//Method to create an ImageIcon and that stores the URL and description	 
	protected ImageIcon createImageIcon(URL imgURL, String description) {
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file path. " );
			return null;
		}
	}
	public JList<Object> getVideoJList(){
		return videoJList;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Loading Videos...");
		/**
		 * 1.Stores the retrieved results and stores them in an array.
		 * 2.Iterates through the list of videos and stores the info needed.
		 */
		ArrayList<SearchResult> searchList = (ArrayList<SearchResult>) arg;	
		for (SearchResult i : searchList){
			Thumbnail thumbnail = i.getSnippet().getThumbnails().getDefault();
			URL url = null;
			try {
				url = new URL(thumbnail.getUrl());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}	
			/**
			 * Temporary variables to store video details need for the video list.
			 * 1. The youtubeID for the link for the video.
			 * 2. The title for the description of the video.
			 * 3. The icon for the thumb-nail of the video.
			 */
			String link = i.getId().getVideoId();
			String title = i.getSnippet().getTitle();	
			ImageIcon icon = createImageIcon(url,title);
			
			videoSlot = new JLabel(title, icon, JLabel.LEFT);		
			videoPanels.add(videoSlot);		
			
			System.out.println(i+" : " +title);//remove 

			videoSlot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			/**
			 *  Adding listeners to the JLabel
			 *  Action: Double click will open a URL in a separate browser
			 */
			videoSlot.addMouseListener(new MouseAdapter() {				
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			          if (Desktop.isDesktopSupported()) {
			                Desktop desktop = Desktop.getDesktop();
			                try {
			                    URI uri = new URI("https://www.youtube.com/watch?v="+link);
			                    desktop.browse(uri);
			                } catch (IOException ex) {
			                    ex.printStackTrace();
			                } catch (URISyntaxException ex) {
			                    ex.printStackTrace();
			                }
			        }
			      }
			   }
			});
		}
		videoJList.revalidate();
		videoJList.repaint();
		revalidate();
		repaint();
    }
}		


