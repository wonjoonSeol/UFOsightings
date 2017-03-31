package assessment.model.panel3;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Observable;
import java.util.Properties;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import authentication.Auth;

/**
 * <h1>PPA Group Project </h1> <br>
 * Computer Science <br>
 * Year 1
 * <p>
 * This model class manages and maintains Youtube data.
 *
 * @author Britton Forsyth(k1630500), Eugene Fong(k1630435), Mooeo Munkhtulga(k1631010), Wonjoon Seol(k1631098)
 */
public class YoutubeModel extends Observable {
    private static final String PROPERTIES_FILENAME = "authentication/youtube.properties";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;
    private static YouTube youtube;
	private List<SearchResult> searchResultList;
	private Properties properties;

    /**
     * Initialise a YouTube object to search for videos on YouTube. 
     * @return List of search results.
     */
    public void runSearch() {
        readAPI();
        // Request a search for recent UFO sighting 2017.
        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("UFO search").build();
            String searchQuery = "ufo sighting 2017";     
            // Define the API request for retrieving search results.
            // Set your developer key from the for: non-authenticated requests
            // Restrict the search results to only include videos. 
            YouTube.Search.List search = youtube.search().list("id,snippet");
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(searchQuery);
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            SearchListResponse searchResponse = search.execute();
            searchResultList = searchResponse.getItems();                   
            
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
            
        } catch (Throwable t) {
            t.printStackTrace();
        	}
            sendInformation();
    	}

    /**
     * Method to notify observers for MVC structure of finalized youtube details.
     */
    	private void sendInformation() {
            setChanged();
            notifyObservers("clear");
            for (SearchResult result : searchResultList) {
                String url = result.getSnippet().getThumbnails().getDefault().getUrl();
                String title = result.getSnippet().getTitle();
                String link = result.getId().getVideoId();
                setChanged();
                notifyObservers(url + ";" + title + ";" + link);
            }
        }

    	/**
    	 * Method to read developer key from properties file.
    	 */
    	private void readAPI() {
            properties = new Properties();
            try {
                InputStream in = YoutubeModel.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
                properties.load(in);
            } catch (IOException e) {
                System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                        + " : " + e.getMessage());
                System.exit(1);
            }
        }
    }