package edu.gatech.oftentimes2000.server;

import java.util.GregorianCalendar;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.maps.GeoPoint;

import edu.gatech.oftentimes2000.data.Address;
import edu.gatech.oftentimes2000.data.Announcement;
import edu.gatech.oftentimes2000.data.AnnouncementType;
import edu.gatech.oftentimes2000.data.Price;
import edu.gatech.oftentimes2000.data.Profile;

public class ContentManager 
{
	private static ContentManager instance;
	
	private final String TAG = "ContentManager";
	public static final String URL = "http://m2.cip.gatech.edu/d/maven/api/Oftentimes2000/";
	
	private ContentManager()
	{
		
	}
	
	public static ContentManager getInstance()
	{
		if (instance == null)
			instance = new ContentManager();
		return instance;
	}
	
	public Announcement[] getAnnouncemetsByType(String type)
	{
		type = normalizeType(type);
		HttpResponse response = HTTPUtil.doGet(URL + "announcement/all?type=" + type);
		JSONObject results = HTTPUtil.getResponseAsJSON(response);
		
		try 
		{
			String status = results.getString("res");
			if (status.equals("TRUE"))
			{
				JSONArray data = results.getJSONArray("data");
				
				Announcement[] announcements = new Announcement[data.length()];
				for (int i = 0; i < data.length(); i++)
				{
					// Parse data
					JSONObject datum = data.getJSONObject(i);
					Announcement announcement = new Announcement();
					
					announcement.id = datum.getInt("id");
					announcement.creator = getProfile(datum.getString("username"));
					announcement.title = datum.getString("title");
					announcement.type = getAnnouncementType(datum);
					announcement.highlights = datum.getString("highlights");
					announcement.finePrint = datum.getString("fine_print");
					announcement.address = getAddress(datum);
					announcement.price = getPrice(datum);
					announcement.fromDate = getDate(datum.getString("from_date"));
					announcement.toDate = getDate(datum.getString("to_date"));
					announcement.url = datum.getString("url");
					announcement.category = datum.getString("category");
					
					// Store it in the announcement array
					announcements[i] = announcement;
				}
				
				return announcements;
			}
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
		
		return new Announcement[0];
	}
	
	public Announcement[] getAnncouncementsByUser(String username)
	{
		
		return null;
	}
	
	public Announcement[] getAnncouncementsByUser(String username, String type)
	{
		return null;
	}
	
	public Profile getProfile(String username)
	{
		HttpResponse response = HTTPUtil.doGet(URL + "user/" + username);
		JSONObject results = HTTPUtil.getResponseAsJSON(response);
		
		Profile profile = new Profile();
		try 
		{
			String status = results.getString("res");
			if (status.equals("TRUE"))
			{
				JSONObject datum = results.getJSONObject("data");
				profile.username = datum.getString("username");
				profile.name = datum.getString("name");
				profile.address = getAddress(datum);
				profile.url = datum.getString("website");
				profile.email = datum.getString("email");
				profile.about = datum.getString("about");
			}
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
		
		return profile;
	}
	
	private String normalizeType(String type)
	{
		if (type.equalsIgnoreCase("Advertisement"))
			return "advertisement";
		
		if (type.equalsIgnoreCase("Public Service Announcement"))
			return "psa";
		
		if (type.equalsIgnoreCase("Event"))
			return "event";
		
		return type;
		
	}
	
	private Address getAddress(JSONObject data)
	{
		Address address = new Address();
		
		try 
		{
			address.street = data.getString("street_address");
			address.city = data.getString("city");
			address.state = data.getString("state");
			address.zipcode = data.getString("zipcode");
			
			if (data.has("radius"))
				address.radius = data.getInt("radius");
			
			if (data.has("latitude") && data.has("longitude"))
			{
				int latitude = data.getInt("latitude");
				int longitude = data.getInt("longitude"); 
				GeoPoint geopoint = new GeoPoint(latitude, longitude);
				address.geopoint = geopoint;
			}
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
		
		return address;
	}
	
	private AnnouncementType getAnnouncementType(JSONObject data)
	{
		try 
		{
			int type = data.getInt("type");
			switch (type)
			{
				case 1: return AnnouncementType.Advertisement;
				case 2: return AnnouncementType.PSA;
				case 3: return AnnouncementType.Event;
				default: return AnnouncementType.Unknown;
			}
		}
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
		
		return AnnouncementType.Unknown;
	}
	
	private Price getPrice(JSONObject data)
	{
		Price price = new Price();
		
		try 
		{
			price.originalPrice = data.getDouble("regular_price");
			price.promotionalPrice = data.getDouble("promotional_price");
			price.calcDiscount();
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
		
		return price;
	}
	
	private GregorianCalendar getDate(String raw)
	{
		// 2012-11-23 00:00:00
		String[] temp = raw.split("\\s+");
		String[] dateRaw = temp[0].split("-");
		String[] timeRaw = temp[1].split(":");
		
		try
		{
			int year = Integer.parseInt(dateRaw[0]);
			int month = Integer.parseInt(dateRaw[1]);
			int day = Integer.parseInt(dateRaw[2]);
			int hour = Integer.parseInt(timeRaw[0]);
			int minute = Integer.parseInt(timeRaw[1]);
			int second = Integer.parseInt(timeRaw[2]);
			return new GregorianCalendar(year, month, day, hour, minute, second);
		}
		catch (NumberFormatException e)
		{
			Log.e(TAG, e.getMessage());
		}
		
		return new GregorianCalendar();
	}
}
