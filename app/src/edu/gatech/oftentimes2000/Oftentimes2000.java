package edu.gatech.oftentimes2000;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.darvds.ribbonmenu.RibbonMenuView;
import com.darvds.ribbonmenu.iRibbonMenuCallback;

import edu.gatech.oftentimes2000.dialog.AuthenticationDialog;
import edu.gatech.oftentimes2000.server.Authenticator;

public class Oftentimes2000 extends Activity implements iRibbonMenuCallback 
{
	/** Called when the activity is first created. */
	private RibbonMenuView rmvMenu;
	
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
		case R.id.ribbon_menu_featured:
			break;
		case R.id.ribbon_menu_favorites:
			if (!Authenticator.isAuthenticated(this))
				AuthenticationDialog.show(this);
			break;
		case R.id.ribbon_menu_categories:
			break;
		case R.id.ribbon_menu_settings:
			break;
		}
	}
}
