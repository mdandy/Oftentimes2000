package edu.gatech.oftentimes2000;

import edu.gatech.oftentimes2000.adapter.AnnouncementAdapter;
import edu.gatech.oftentimes2000.data.Announcement;
import edu.gatech.oftentimes2000.map.MapAct;
import edu.gatech.oftentimes2000.server.ContentManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class AnnouncementSelection extends Activity implements OnItemClickListener, OnClickListener
{
	private final String TAG = "AnnouncementSelection";
	
	private Announcement[] announcements;
	private Button bMapIt;
	private ProgressBar pbLoading;
	private TextView tvNothing;
	private ListView lvAnnouncements;
	private ContentManager contentManager;
	private AnnouncementFetcher fetcher;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.announcement);
		
		// Init content manager
		this.contentManager = ContentManager.getInstance();

		// Init views
		this.bMapIt = (Button) findViewById(R.id.bMapIt);
		this.bMapIt.setOnClickListener(this);
		this.pbLoading = (ProgressBar) findViewById(R.id.pbAnnouncementLoading);
		this.lvAnnouncements = (ListView) findViewById(R.id.lvAnnouncement);
		this.tvNothing = (TextView) findViewById(R.id.tvAnnouncementNothing);

		// Get intent extra
		Intent intent = getIntent();
		String category = intent.getStringExtra("category");
		
		// Loading
		this.tvNothing.setVisibility(View.GONE);
		this.lvAnnouncements.setVisibility(View.GONE);
		this.fetch(category);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		Announcement announcement = (Announcement) this.lvAnnouncements.getItemAtPosition(position);
		
		Intent intent = new Intent (this, AnnouncementDetail.class);
		intent.putExtra("announcement", announcement);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
		//TODO: Call Maps Kevin
			case R.id.bMapIt: 
				Intent intent = new Intent (this, MapAct.class);
				intent.putExtra("arr", true);
				intent.putExtra("announcement", announcements);
				startActivity(intent);
				break;
		}
	}
	
	private void fetch(String category)
	{
		try
		{
			if (this.fetcher == null || this.fetcher.getStatus() == AsyncTask.Status.FINISHED)
			{
				this.fetcher = this.new AnnouncementFetcher();
				this.fetcher.execute(category);
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
			this.pbLoading.setVisibility(View.GONE);
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
			this.pbLoading.setVisibility(View.GONE);
			this.lvAnnouncements.setVisibility(View.VISIBLE);
		}
	}
	
	public class AnnouncementFetcher extends AsyncTask<String, Void, Announcement[]>
	{
		@Override
		protected Announcement[] doInBackground(String... params) 
		{
			// TODO Auto-generated method stub
			String category = params[0];
			Announcement[] announcements = contentManager.getAnnouncemetsByType(category);
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
