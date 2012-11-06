package edu.gatech.oftentimes2000.map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.OverlayItem;

import edu.gatech.oftentimes2000.R;

public class MainActivity extends MapActivity implements LocationListener {


	private MyMapView mapView;
	private LocationManager locManager;

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	//	@Override
	//	public void onCreate(Bundle savedInstanceState) {
	//	    super.onCreate(savedInstanceState);
	//	    setContentView(R.layout.main);
	//	    MapView mapView = (MapView) findViewById(R.id.mapview);
	//	    mapView.setBuiltInZoomControls(true);
	//	    List<Overlay> mapOverlays = mapView.getOverlays();
	//	    Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
	//	    HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable, this);
	//	    GeoPoint point = new GeoPoint(19240000,-99120000);
	//	    OverlayItem overlayitem = new OverlayItem(point, "Example Output", "Example current location");
	//	    itemizedoverlay.addOverlay(overlayitem);
	//	    mapOverlays.add(itemizedoverlay);
	//	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		mapView = (MyMapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		//latitude and longitude of Rome
		double lat = 41.889882;
		double lon = 12.479267;
		//create geo point
		GeoPoint point = new GeoPoint((int)(lat * 1E6), (int)(lon *1E6));
		MapController controller = mapView.getController();
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

			point = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude() *1E6));

			controller.animateTo(point);

			controller.setZoom(15);

			Toast.makeText(
					this,
					"Current location:\nLatitude: " + location.getLatitude()
					+ "\n" + "Longitude: " + location.getLongitude(),
					Toast.LENGTH_LONG).show();
		} else {

			Toast.makeText(this, "Cannot fetch current location!",
					Toast.LENGTH_LONG).show();
		}

		// fetch the drawable - the pin that will be displayed on the map
		Drawable drawable = this.getResources().getDrawable(R.drawable.ic_map_pin);

		// create and add an OverlayItem to the HelloItemizedOverlay list
		OverlayItem overlayItem = new OverlayItem(point, "Test Output", "");

		HelloItemizedOverlay itemizedOverlay = new HelloItemizedOverlay(drawable,this);
		itemizedOverlay = new HelloItemizedOverlay(drawable,this);

		itemizedOverlay.addOverlay(overlayItem);

		// add the overlays to the map
		mapView.getOverlays().add(itemizedOverlay);
		mapView.invalidate();

		//when the current location is found – stop listening for updates (preserves battery)
		locManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

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
