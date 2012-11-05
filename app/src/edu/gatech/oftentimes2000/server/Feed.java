package edu.gatech.oftentimes2000.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;

public class Feed 
{
	/**
	 * Get feeds from the Server.
	 * @param slug the slug
	 * @return the FeedEntry
	 */
	public static List<FeedEntry> getFeeds(String slug)
	{
		List<FeedEntry> feeds = new ArrayList<FeedEntry>();
		
		// Request feeds from the server
		String url = "http://www.someurl.com/" + slug;
		HttpResponse response = HTTPUtil.doGet(url);
		
		// Parse the response
		JSONArray jsonFeeds = HTTPUtil.getResponseAsJSON(response);
		
		return feeds;
	}
}
