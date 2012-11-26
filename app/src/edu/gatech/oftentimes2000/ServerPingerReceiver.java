package edu.gatech.oftentimes2000;

import edu.gatech.oftentimes2000.gcm.NotificationCenter;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServerPingerReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		// Get the intent extra
		String query = intent.getStringExtra("query");
		
		if (query.equalsIgnoreCase("ping"))
		{
			// TODO: ping server
			
			String status = "AlarmService : ServerPinge";
			Intent aIntent = new Intent(context, Oftentimes2000.class);
		    PendingIntent pIntent = PendingIntent.getActivity(context, 0, aIntent, 0);
		    NotificationCenter.createNotification(context, pIntent, status);
		}
	}

}
