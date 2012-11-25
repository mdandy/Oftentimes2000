package edu.gatech.oftentimes2000;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;

import edu.gatech.oftentimes2000.server.GCMManager;

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
		// TODO Put it on notification
		Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_LONG).show();
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
