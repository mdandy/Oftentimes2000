package edu.gatech.oftentimes2000.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;

public class Feed 
{
	/**
	 * Get list of categories from the Server.
	 * @return the list of categories
	 */
	public static List<String> getCategories()
	{
		List<String> categories = new ArrayList<String>();

		// Request categories from the server
		String url = "http://www.someurl.com/categories";
		HttpResponse response = HTTPUtil.doGet(url);

		// Parse the response
		if (response != null)
		{
			JSONArray jsonFeeds = HTTPUtil.getResponseAsJSON(response);
		}
		return categories;
	}

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
		if (response != null)
		{
			JSONArray jsonFeeds = HTTPUtil.getResponseAsJSON(response);
		}
		return feeds;
	}
}
