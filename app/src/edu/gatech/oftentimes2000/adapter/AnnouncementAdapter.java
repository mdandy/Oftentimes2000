package edu.gatech.oftentimes2000.adapter;

import edu.gatech.oftentimes2000.R;
import edu.gatech.oftentimes2000.data.Announcement;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AnnouncementAdapter extends ArrayAdapter<Announcement>
{
	private final Context context;
	private final Announcement[] values;

	public AnnouncementAdapter(Context context, Announcement[] values) 
	{
		super(context, R.layout.announcement_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.announcement_item, parent, false);
		
		TextView tvAnnouncementTitle = (TextView) rowView.findViewById(R.id.tvAnnouncementTitle);
		TextView tvAnnouncementSnippet = (TextView) rowView.findViewById(R.id.tvAnnouncementSnippet);
		
		tvAnnouncementTitle.setText(values[position].title);
		tvAnnouncementSnippet.setText(snippetfy(values[position].highlights, 100));

		return rowView;
	}
	
	private String snippetfy (String highlights, int length)
	{
		StringBuffer snippet = new StringBuffer(length);
		
		String[] tokens = highlights.split("\\s+");
		for (String token : tokens)
		{
			int tokenLength = token.length();
			if (snippet.length() + tokenLength >= length)
				break;
			else
				snippet.append(token + " ");
		}
		
		return snippet.toString();
	}
}
