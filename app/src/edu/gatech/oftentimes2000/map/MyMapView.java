package edu.gatech.oftentimes2000.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.google.android.maps.MapView;

public class MyMapView extends MapView {
	private long lastTouchTime = -1;

	public MyMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			long thisTime = System.currentTimeMillis();
			if (thisTime - lastTouchTime < 250) {
				// Double tap
				this.getController().zoomInFixing((int) ev.getX(),
						(int) ev.getY());
				lastTouchTime = -1;
			} else {
				// Too slow :)
				lastTouchTime = thisTime;
			}
		}

		return super.onInterceptTouchEvent(ev);
	}
}