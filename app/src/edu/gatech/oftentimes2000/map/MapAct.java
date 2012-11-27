package edu.gatech.oftentimes2000.map;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.OverlayItem;

import edu.gatech.oftentimes2000.R;
import edu.gatech.oftentimes2000.data.Announcement;

public class MapAct extends MapActivity implements LocationListener {


	private MyMapView mapView;
	private LocationManager locManager;
	private Parcelable[] temp;
	private ArrayList<Announcement> announcements = new ArrayList<Announcement>();
	private Announcement announcement;
	
	private ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>(); 
	private GeoPoint announcePoint;
	

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		boolean arrFlag = intent.getBooleanExtra("arr", false);
		if (arrFlag) { // sending array of announcements
			this.temp = intent.getParcelableArrayExtra("announcement");
			
			for (Parcelable addr: temp) {
				announcements.add((Announcement) addr);
			}
			
			for (Announcement a: announcements) {	
				 GeoPoint point = a.address.geopoint;
				 overlayItems.add(new OverlayItem(point,  a.title, a.highlights));
			}
		}
		else {
			this.announcement = intent.getParcelableExtra("announcement");
			announcePoint = announcement.address.geopoint;
			overlayItems.add(new OverlayItem(announcePoint, announcement.title, announcement.highlights));
		}

		
		setContentView(R.layout.map);
		mapView = (MyMapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
	
		//MapController controller = mapView.getController();
		// invalidate the map in order to show changes
		mapView.invalidate();
		// Use the location manager through GPS
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);

		//get the current location (last known location) from the location manager
		Location location = locManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		//if location found display as a toast the current latitude and longitude
		if (location != null) {

			//controller.animateTo(point);

			//controller.setZoom(15);

//			Toast.makeText(
//					this,
//					"Current location:\nLatitude: " + location.getLatitude()
//					+ "\n" + "Longitude: " + location.getLongitude(),
//					Toast.LENGTH_LONG).show();
		} else {

//			Toast.makeText(this, "Cannot fetch current location!",
//					Toast.LENGTH_LONG).show();
		}

		// fetch the drawable - the pin that will be displayed on the map
		Drawable drawable = this.getResources().getDrawable(R.drawable.ic_map_pin);
		

		HelloItemizedOverlay itemizedOverlay = new HelloItemizedOverlay(drawable,this);
		itemizedOverlay = new HelloItemizedOverlay(drawable,this);

		for (OverlayItem item: overlayItems) {
			itemizedOverlay.addOverlay(item);
		}

		// add the overlays to the map
		mapView.getOverlays().add(itemizedOverlay);

		mapView.invalidate();

		//when the current location is found – stop listening for updates (preserves battery)
		locManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location arg0) {
	}

	@Override
	public void onProviderDisabled(String arg0) {
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	}

	@Override
	protected void onResume() {
		super.onResume();
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locManager.removeUpdates(this); //activity pauses => stop listening for updates
	}
}
