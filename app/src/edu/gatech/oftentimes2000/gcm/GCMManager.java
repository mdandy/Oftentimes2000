package edu.gatech.oftentimes2000.gcm;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.maps.GeoPoint;

import edu.gatech.oftentimes2000.Settings;
import edu.gatech.oftentimes2000.map.GPSManager;
import edu.gatech.oftentimes2000.server.ContentManager;
import edu.gatech.oftentimes2000.server.HTTPUtil;

public class GCMManager 
{
	public static final String SENDER_ID = "736319988122";
	private static final String TAG = "GCMManager";
	
	public static void registerDevice(Context context, String registrationId)
	{
		Context appContext = context.getApplicationContext();
		SharedPreferences settings = appContext.getSharedPreferences(Settings.SETTING_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("gcm_supported", true);
		editor.putString("gcm_id", registrationId);
		editor.commit();
		
		// Update server status
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("gcm_id", registrationId));
		HttpResponse response = HTTPUtil.doPost(ContentManager.URL + "device/register", params);
		JSONObject results = HTTPUtil.getResponseAsJSON(response);
		try 
		{
			String status = results.getString("res");
			Log.d(TAG, "GCM Server Registration : " + status);
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
	}
	
	public static void unregisterDevice(Context context, String registrationId)
	{
		Context appContext = context.getApplicationContext();
		SharedPreferences settings = appContext.getSharedPreferences(Settings.SETTING_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("gcm_supported", false);
		editor.putString("gcm_id", "");
		editor.commit();
		
		// Update server status
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("gcm_id", registrationId));
		HttpResponse response = HTTPUtil.doPost(ContentManager.URL + "device/unregister", params);
		JSONObject results = HTTPUtil.getResponseAsJSON(response);
		try 
		{
			String status = results.getString("res");
			Log.d(TAG, "GCM Server Registration : " + status);
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
	}
	
	public static void registerGCM(Context context)
	{
		Context appContext = context.getApplicationContext();
		try
		{
			GCMRegistrar.checkDevice(appContext);
			GCMRegistrar.checkManifest(appContext);
			final String regId = GCMRegistrar.getRegistrationId(appContext);
			if (regId.equals("")) 
				GCMRegistrar.register(appContext, GCMManager.SENDER_ID);
		}
		catch (UnsupportedOperationException e)
		{
			SharedPreferences settings = appContext.getSharedPreferences(Settings.SETTING_PREFERENCE, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("gcm_supported", false);
			editor.commit();
			Log.i(TAG, e.getMessage());
		}
		catch (IllegalStateException e)
		{
			SharedPreferences settings = appContext.getSharedPreferences(Settings.SETTING_PREFERENCE, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("gcm_supported", false);
			editor.commit();
			Log.i(TAG, e.getMessage());
		}
	}
	
	public static void unregisterGCM(Context context)
	{
		Context appContext = context.getApplicationContext();
		GCMRegistrar.unregister(appContext);
	}
	
	public static void pingServer(Context context)
	{
		Context appContext = context.getApplicationContext();
		
		// Gather required params
		SharedPreferences settings = appContext.getSharedPreferences(Settings.SETTING_PREFERENCE, Context.MODE_PRIVATE);
		String gcmId = settings.getString("gcm_id", ""); 
		GeoPoint gp = GPSManager.getCurrentLocation(appContext);
		int latitude = gp.getLatitudeE6();
		int longitude = gp.getLongitudeE6();

		// Prepare HTTP Request
		List<NameValuePair> params = new ArrayList<NameValuePair>(3);
		params.add(new BasicNameValuePair("gcm_id", gcmId));
		params.add(new BasicNameValuePair("latitude", "" + latitude));
		params.add(new BasicNameValuePair("longitude", "" + longitude));
		
		// Do HTTP Request
		HttpResponse response = HTTPUtil.doPost(ContentManager.URL + "location", params);
		JSONObject results = HTTPUtil.getResponseAsJSON(response);
		try 
		{
			String status = results.getString("res");
			if (status.equalsIgnoreCase("TRUE"))
				Log.d(TAG, "Pinging Server : latitude = " + latitude + " & longitude = " + longitude);
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
	}
	
	public static void deleteSubscription(Context context, String subscription)
	{
		Context appContext = context.getApplicationContext();
		SharedPreferences settings = appContext.getSharedPreferences(Settings.SETTING_PREFERENCE, Context.MODE_PRIVATE);
		String gcmId = settings.getString("gcm_id", ""); 
		
		// Prepare HTTP Request
		List<NameValuePair> params = new ArrayList<NameValuePair>(3);
		params.add(new BasicNameValuePair("gcm_id", gcmId));
		params.add(new BasicNameValuePair("subscription", "" + subscription));

		// Do HTTP Request
		HttpResponse response = HTTPUtil.doPost(ContentManager.URL + "subscription/delete", params);
		JSONObject results = HTTPUtil.getResponseAsJSON(response);
		try 
		{
			String status = results.getString("res");
			if (status.equalsIgnoreCase("TRUE"))
				Log.d(TAG, "Pinging Server : delete subscription = " + subscription);
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
	}
	
	public static void updateSubscription(Context context, String subscription)
	{
		Context appContext = context.getApplicationContext();
		SharedPreferences settings = appContext.getSharedPreferences(Settings.SETTING_PREFERENCE, Context.MODE_PRIVATE);
		String gcmId = settings.getString("gcm_id", ""); 
		
		// Prepare HTTP Request
		List<NameValuePair> params = new ArrayList<NameValuePair>(3);
		params.add(new BasicNameValuePair("gcm_id", gcmId));
		params.add(new BasicNameValuePair("subscription", "" + subscription));

		// Do HTTP Request
		HttpResponse response = HTTPUtil.doPost(ContentManager.URL + "subscription/update", params);
		JSONObject results = HTTPUtil.getResponseAsJSON(response);
		try 
		{
			String status = results.getString("res");
			if (status.equalsIgnoreCase("TRUE"))
				Log.d(TAG, "Pinging Server : update subscription = " + subscription);
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}
	}
}
