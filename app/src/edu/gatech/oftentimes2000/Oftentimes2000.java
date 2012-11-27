package edu.gatech.oftentimes2000;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.darvds.ribbonmenu.RibbonMenuView;
import com.darvds.ribbonmenu.iRibbonMenuCallback;
import com.google.android.maps.GeoPoint;

import edu.gatech.oftentimes2000.adapter.AnnouncementAdapter;
import edu.gatech.oftentimes2000.data.Announcement;
import edu.gatech.oftentimes2000.map.GPSManager;
import edu.gatech.oftentimes2000.map.MapAct;
import edu.gatech.oftentimes2000.server.ContentManager;

public class Oftentimes2000 extends Activity implements iRibbonMenuCallback, OnItemClickListener
{
	private final String TAG = "Oftentimes2000";
	private RibbonMenuView rmvMenu;
	private ProgressBar pbMainLoading;
	private TextView tvNothing;
	private ListView lvAnnouncements;
	
	private MapFetcher fetcher;
	private Announcement[] announcements;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Init ribbon menu
		rmvMenu = (RibbonMenuView) findViewById(R.id.ribbonMenuView);
		rmvMenu.setMenuClickCallback(this);
		rmvMenu.setMenuItems(R.menu.ribbon_menu);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Init views
		this.pbMainLoading = (ProgressBar) findViewById(R.id.pbMainAnnouncementLoading);
		this.tvNothing = (TextView) findViewById(R.id.tvMainAnnouncementNothing);
		this.lvAnnouncements = (ListView) findViewById(R.id.lvMainAnnouncement);
		
		// Loading
		this.tvNothing.setVisibility(View.GONE);
		this.lvAnnouncements.setVisibility(View.GONE);
		this.fetch();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		int id = item.getItemId();
		if (id == android.R.id.home) 
		{
			rmvMenu.toggleMenu();
			return true;
		} 
		else 
		{
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void RibbonMenuItemClick(int itemId) 
	{
		switch (itemId)
		{
//			case R.id.ribbon_menu_featured:
//				break;
//			case R.id.ribbon_menu_favorites:
//				if (!Authenticator.isAuthenticated(this))
//					AuthenticationDialog.show(this);
//				break;
			case R.id.ribbon_menu_categories:
				Intent category_intent = new Intent (this, CategorySelection.class);
				startActivity(category_intent);
				break;
			case R.id.ribbon_menu_map:
				//TODO: Kevin call maps with announcements
//				announcements[0].address.geopoint;
				Intent intent = new Intent (this, MapAct.class);
				intent.putExtra("announcement", announcements);
				startActivity(intent);
				
				break;
			case R.id.ribbon_menu_settings:
				Intent setting_intent = new Intent (this, Settings.class);
				startActivity(setting_intent);
				break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		Announcement announcement = (Announcement) this.lvAnnouncements.getItemAtPosition(position);
		
		Intent intent = new Intent (this, AnnouncementDetail.class);
		intent.putExtra("announcement", announcement);
		startActivity(intent);
	}
	
	private void fetch()
	{
		try
		{
			if (this.fetcher == null || this.fetcher.getStatus() == AsyncTask.Status.FINISHED)
			{
				this.fetcher = this.new MapFetcher();
				this.fetcher.execute();
			}
			else
			{
				Log.d(TAG, "An existing announcement fetcher is running!");
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage());
		}
	}
	
	private void publish(Announcement[] announcements)
	{
		if (announcements.length == 0)
		{
			// Display it
			this.pbMainLoading.setVisibility(View.GONE);
			this.tvNothing.setVisibility(View.VISIBLE);
		}
		else
		{
			this.announcements = announcements;
			
			// Init array adapter
			ArrayAdapter<Announcement> adapter = new AnnouncementAdapter(this, announcements);
			
			// Init list view
			this.lvAnnouncements.setAdapter(adapter);
			this.lvAnnouncements.setOnItemClickListener(this);
			
			// Display it
			this.pbMainLoading.setVisibility(View.GONE);
			this.lvAnnouncements.setVisibility(View.VISIBLE);
		}
	}
	
	public class MapFetcher extends AsyncTask<Void, Void, Announcement[]>
	{
		@Override
		protected Announcement[] doInBackground(Void... params) 
		{
			GeoPoint gp = GPSManager.getCurrentLocation(Oftentimes2000.this);
			int latitude = gp.getLatitudeE6();
			int longitude = gp.getLongitudeE6();
			int radius = 0;
			
			ContentManager cm = ContentManager.getInstance();
			Announcement[] announcements = cm.getAnncouncementsByLocation(latitude, longitude, radius);
			return announcements;
		}
		
		@Override
		protected void onPostExecute(Announcement[] result) 
		{
			super.onPostExecute(result);
			publish(result);
		}
	}
}
