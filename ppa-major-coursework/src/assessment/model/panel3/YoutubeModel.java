package assessment.model.panel3;

import java.util.List;
import java.util.Observable;

import com.google.api.services.youtube.model.SearchResult;

public class YoutubeModel extends Observable {
	//Creating an abject of the youtube search.
	YoutubeSearch youtubeSearch = new YoutubeSearch();

	public YoutubeModel() {
	}
	/**
	 *  1. Runs the search and retrieves the search results.
	 *  2. Sets the changes and notifies the view.
	 *  3. Print to console once the videos have been retrieved.
	 */
	public void handleVideos(){
		youtubeSearch.runSearch();
		setChanged();
		notifyObservers(youtubeSearch.getYoutubeList());
		
		System.out.println("youtube list retrieved");//remove
    }
}

