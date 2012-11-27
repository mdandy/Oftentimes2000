package edu.gatech.oftentimes2000;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import edu.gatech.oftentimes2000.gcm.GCMManager;
import edu.gatech.oftentimes2000.gcm.NotificationCenter;

public class GCMIntentService extends GCMBaseIntentService
{
	private final String TAG = "GCMIntentService";
	
	/**
	 * Constructor
	 */
	public GCMIntentService()
	{
		super(GCMManager.SENDER_ID);
	}
	
	@Override
	public void onError(Context context, String errorId) 
	{
		Log.e(TAG, "GCM Error: " + errorId);
	}

	@Override
	public void onMessage(Context context, Intent intent) 
	{
		String dataRaw = intent.getStringExtra("data");
		Log.d(TAG, "Received GCM");
		
		// Sent notification
		Context appContext = context.getApplicationContext();
		Intent notiIntent = new Intent(appContext, Oftentimes2000.class);
		notiIntent.putExtra("data", dataRaw);
	    PendingIntent pIntent = PendingIntent.getActivity(appContext, 0, intent, 0);
	    NotificationCenter.createNotification(appContext, pIntent, "You get new announcements!");
	}

	@Override
	public void onRegistered(Context context, String registrationId) 
	{
		GCMManager.registerDevice(this, registrationId);
	}

	@Override
	public void onUnregistered(Context context, String registrationId) 
	{
		GCMManager.unregisterDevice(this, registrationId);
	}

}
