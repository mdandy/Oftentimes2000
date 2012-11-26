package edu.gatech.oftentimes2000;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.darvds.ribbonmenu.RibbonMenuView;
import com.darvds.ribbonmenu.iRibbonMenuCallback;

import edu.gatech.oftentimes2000.data.Announcement;
import edu.gatech.oftentimes2000.server.ContentManager;

public class Oftentimes2000 extends Activity implements iRibbonMenuCallback 
{
	private final String TAG = "Oftentimes2000";
	private RibbonMenuView rmvMenu;
	private MapFetcher fetcher;
	
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
				break;
			case R.id.ribbon_menu_settings:
				Intent setting_intent = new Intent (this, Settings.class);
				startActivity(setting_intent);
				break;
		}
	}
	
	public class MapFetcher extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params) 
		{
			int latitude = 0;
			int longitude = 0;
			int radius = 0;
			
			ContentManager cm = ContentManager.getInstance();
			Announcement[] announcements = cm.getAnncouncementsByLocation(latitude, longitude, radius);
			
			// TODO: MapIt
			return null;
		}
	}
}
