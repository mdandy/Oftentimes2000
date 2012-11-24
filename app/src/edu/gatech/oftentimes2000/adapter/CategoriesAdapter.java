package edu.gatech.oftentimes2000.adapter;

import edu.gatech.oftentimes2000.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoriesAdapter extends ArrayAdapter<String>
{
	private final Context context;
	private final String[] values;

	public CategoriesAdapter(Context context, String[] values) 
	{
		super(context, R.layout.category_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.category_item, parent, false);
		
		TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
		tvTitle.setText(values[position]);
		// tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_menu_categories, 0, 0, 0);
		return rowView;
	}
}
