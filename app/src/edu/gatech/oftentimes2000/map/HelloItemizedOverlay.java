package edu.gatech.oftentimes2000.map;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import edu.gatech.oftentimes2000.AnnouncementDetail;
import edu.gatech.oftentimes2000.data.Announcement;

public class HelloItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	private Context mContext;
	
	private Announcement currAnnouncement;
	
	private Hashtable<OverlayItem, Announcement> itemMap;

	public HelloItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public HelloItemizedOverlay(Drawable defaultMarker, Context context, 
			Hashtable<OverlayItem, Announcement> itemMap) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		this.itemMap = itemMap;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		currAnnouncement = itemMap.get(item);
		Builder builder = new AlertDialog.Builder(mContext);
		builder.setPositiveButton("Okay", new OkOnClickListener());
		builder.setNegativeButton("Cancel", new CancelOnClickListener());
		AlertDialog dialog = builder.create();
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		dialog.setCancelable(true);
		return true;
	}

	private final class CancelOnClickListener implements
	DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
		}
	}

	private final class OkOnClickListener implements
	DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			Announcement announcement;
			Intent intent = new Intent (mContext, AnnouncementDetail.class);
			intent.putExtra("announcement", currAnnouncement);
			mContext.startActivity(intent);
		}
	}

	public void clear() {
		mOverlays.clear();
		populate();
	}

}
