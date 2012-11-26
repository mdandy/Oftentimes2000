package edu.gatech.oftentimes2000;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.gatech.oftentimes2000.adapter.CategoriesAdapter;

public class CategorySelection extends Activity implements OnItemClickListener
{
	private ListView lvCategories;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);

		// Init array adapter
		String[] defaultCategories = getResources().getStringArray(R.array.default_category);
		ArrayAdapter<String> adapter = new CategoriesAdapter(this, defaultCategories);
		
		// Init list view
		this.lvCategories = (ListView) findViewById(R.id.lvCategory);
		this.lvCategories.setAdapter(adapter);
		this.lvCategories.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		String category = (String) this.lvCategories.getItemAtPosition(position);
		
		Intent intent = new Intent (this, AnnouncementSelection.class);
		intent.putExtra("category", category);
		startActivity(intent);
	}
}
