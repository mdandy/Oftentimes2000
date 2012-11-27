package edu.gatech.oftentimes2000;

import java.text.SimpleDateFormat;

import edu.gatech.oftentimes2000.data.Announcement;
import edu.gatech.oftentimes2000.map.MapAct;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AnnouncementDetail extends Activity implements OnClickListener
{
	private Announcement announcement;
	
	private ImageView icon;
	private TextView title;
	private TextView oldPrice;
	private TextView newPrice;
	private TextView discount;
	
	private TextView highlights;
	private TextView finePrint;
	
	private TextView creator;
	private TextView location;
	private TextView phoneNumber;
	private TextView url;
	private Button mapIt;
	
	private TextView fromDate;
	private TextView toDate;
	private TextView about;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.announcement_detail);
		
		// Init views
		this.icon = (ImageView) findViewById(R.id.detailIcon);
		this.title = (TextView) findViewById(R.id.detailTitle);
		this.oldPrice = (TextView) findViewById(R.id.detailOldPrice);
		this.newPrice = (TextView) findViewById(R.id.detailNewPrice);
		this.discount = (TextView) findViewById(R.id.detailDiscount);
		this.highlights = (TextView) findViewById(R.id.detailHighlights);
		this.finePrint = (TextView) findViewById(R.id.detailFinePrint);
		this.creator = (TextView) findViewById(R.id.detailCreator);
		this.location = (TextView) findViewById(R.id.detailLocation);
		this.phoneNumber = (TextView) findViewById(R.id.detailPhoneNumber);
		this.url = (TextView) findViewById(R.id.detailURL);
		this.mapIt = (Button) findViewById(R.id.detailMapIt);
		this.mapIt.setOnClickListener(this);
		this.fromDate = (TextView) findViewById(R.id.detailFromDate);
		this.toDate = (TextView) findViewById(R.id.detailToDate);
		this.about = (TextView) findViewById(R.id.detailAbout);
		
		// Get intent extra
		Intent intent = getIntent();
		this.announcement = intent.getParcelableExtra("announcement");
		initViews();
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
			case R.id.detailMapIt: 
				Intent intent = new Intent (this, MapAct.class);
				intent.putExtra("arr", false);
				intent.putExtra("announcement", announcement);
				startActivity(intent);
				break;
		}
	}
	
	private void initViews()
	{
		if (announcement != null)
		{
			// Title
			this.title.setText(this.announcement.title);
			
			// Price
			if (this.announcement.price.originalPrice < 0 && this.announcement.price.promotionalPrice < 0)
			{
				this.oldPrice.setVisibility(View.GONE);
				this.newPrice.setVisibility(View.GONE);
				this.discount.setVisibility(View.GONE);
			}
			else
			{
				this.oldPrice.setText(this.announcement.price.getOriginalPrice());
				this.newPrice.setText(this.announcement.price.getPromotionalPrice());
				this.discount.setText(this.announcement.price.getDiscount());
				
				this.oldPrice.setPaintFlags(this.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			}
			
			// Highlights
			this.highlights.setText(this.announcement.highlights);
			
			// Fine Print
			if (this.announcement.finePrint.isEmpty())
				this.finePrint.setText(this.announcement.finePrint);
			else
			{
				TextView finePrintLabel = (TextView) findViewById(R.id.detailFinePrintLable);
				finePrintLabel.setVisibility(View.GONE);
				this.finePrint.setVisibility(View.GONE);
			}
			
			// Location
			this.creator.setText(this.announcement.creator.name);
			this.location.setText(this.announcement.address.getAddress());
			if (this.announcement.address.phoneNumber.isEmpty())
				this.phoneNumber.setVisibility(View.GONE);
			else
				this.phoneNumber.setText(this.announcement.address.phoneNumber);
			
			if (!this.announcement.url.isEmpty())
				this.url.setText(this.announcement.url);
			else if (!this.announcement.creator.url.isEmpty())
				this.url.setText(this.announcement.creator.url);
			else
				this.url.setVisibility(View.GONE);
			
			// Date
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern("EEE, MMM d, yyyy hh:mm a");
			this.fromDate.append(" " + dateFormat.format(this.announcement.fromDate.getTime()));
			this.toDate.append(" " + dateFormat.format(this.announcement.toDate.getTime()));
			
			// About
			this.about.setText(this.announcement.creator.about);
		}
	}
}
