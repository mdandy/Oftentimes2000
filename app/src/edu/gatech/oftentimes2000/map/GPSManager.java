package edu.gatech.oftentimes2000.map;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.maps.GeoPoint;

public class GPSManager 
{
	public static GeoPoint getCurrentLocation(Context context)
	{
		Context appContext = context.getApplicationContext();
		LocationManager locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, false);

		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null)
		{	
			int latitude = (int) location.getLatitude() * 1000000;
			int longitude = (int) location.getLongitude() * 1000000;
			return new GeoPoint(latitude, longitude);
		}
		return new GeoPoint(0, 0);
	}
}
