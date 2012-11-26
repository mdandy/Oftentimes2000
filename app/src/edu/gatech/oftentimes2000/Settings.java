package edu.gatech.oftentimes2000;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import edu.gatech.oftentimes2000.gcm.GCMManager;

public class Settings extends Activity implements OnClickListener, OnItemSelectedListener
{
	public static final String SETTING_PREFERENCE = "Oftentimes2000Pref";
	private final String TAG = "Settings";
	
	private CheckBox cbPushNotificationEnable;
	private CheckBox cbPingIntervalEnable;
	private TextView tvPingInterval;
	private Spinner spPingInterval;
	private ServerPinger pinger;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		// Init views
		this.cbPushNotificationEnable = (CheckBox) findViewById(R.id.cbPushNotification);
		this.cbPushNotificationEnable.setOnClickListener(this);
		
		this.cbPingIntervalEnable = (CheckBox) findViewById(R.id.cbPingInterval);
		this.cbPingIntervalEnable.setOnClickListener(this);
		
		this.tvPingInterval = (TextView) findViewById(R.id.tvPingInterval);
		this.tvPingInterval.setVisibility(View.GONE);
		
		this.spPingInterval = (Spinner) findViewById(R.id.spPingInterval);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ping_interval, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spPingInterval.setAdapter(adapter);
		this.spPingInterval.setOnItemSelectedListener(this);
		this.spPingInterval.setVisibility(View.GONE);
		
		Button bPingServer = (Button) findViewById(R.id.bPingServer);
		bPingServer.setOnClickListener(this);
		
		// Init SharedPreferences
		SharedPreferences settings = getSharedPreferences(SETTING_PREFERENCE, Context.MODE_PRIVATE);
		boolean pushNotification = settings.getBoolean("push_notification_enabled", false);
		boolean pingInterval = settings.getBoolean("ping_interval_enabled", false);
		this.cbPushNotificationEnable.setChecked(pushNotification);
		this.cbPingIntervalEnable.setChecked(pingInterval);
	}
	
	@Override
	public void onClick(View view) 
	{
		// Open SharedPreferences
		SharedPreferences settings = getSharedPreferences(SETTING_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		
		switch (view.getId())
		{
			case R.id.cbPushNotification:
				editor.putBoolean("push_notification_enabled", this.cbPushNotificationEnable.isChecked());
				if (this.cbPushNotificationEnable.isChecked())
					GCMManager.registerGCM(this);
				else
					GCMManager.unregisterGCM(this);
				break;
			case R.id.cbPingInterval:
				editor.putBoolean("ping_interval_enabled", this.cbPingIntervalEnable.isChecked());
				if (this.cbPingIntervalEnable.isChecked())
				{
					this.tvPingInterval.setVisibility(View.VISIBLE);
					this.spPingInterval.setVisibility(View.VISIBLE);
				}
				else
				{
					this.tvPingInterval.setVisibility(View.GONE);
					this.spPingInterval.setVisibility(View.GONE);
				}
				break;
			case R.id.bPingServer:
				try
				{
					if (this.pinger == null || this.pinger.getStatus() == AsyncTask.Status.FINISHED)
					{
						this.pinger = this.new ServerPinger();
						this.pinger.execute();
					}
					else
					{
						Log.d(TAG, "An existing server pinger is running!");
					}
				}
				catch (Exception e)
				{
					Log.e(TAG, e.getMessage());
				}
				break;
		}
		
		// Commit SharedPreferences
		editor.commit();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
	{
		SharedPreferences settings = getSharedPreferences(SETTING_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		String interval = (String) parent.getItemAtPosition(position);
		editor.putInt("ping_interval", Integer.parseInt(interval));
		editor.commit();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		// NOP
	}
	
	public class ServerPinger extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params) 
		{
			GCMManager.pingServer(Settings.this);
			return null;
		}
	}
}
