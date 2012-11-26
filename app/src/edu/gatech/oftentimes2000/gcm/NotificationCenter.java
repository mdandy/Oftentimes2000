package edu.gatech.oftentimes2000.gcm;

import edu.gatech.oftentimes2000.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NotificationCenter
{
	public static void createNotification(Context context, PendingIntent pIntent, String message)
	{
		Context appContext = context.getApplicationContext();
		String title = appContext.getResources().getString(R.string.app_name);
		Bitmap icon = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.ic_launcher);
		
		Notification noti = new Notification.Builder(appContext)
			.setContentTitle(title)
	        .setContentText(message)
	        .setSmallIcon(R.drawable.ic_menu_favorites)
	        .setLargeIcon(icon)
	        .setContentIntent(pIntent)
	        .getNotification();
		NotificationManager nm = (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
		noti.flags |= Notification.FLAG_AUTO_CANCEL;
		nm.notify(0, noti);
	}
}
