package edu.gatech.oftentimes2000.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import edu.gatech.oftentimes2000.R;
import edu.gatech.oftentimes2000.server.Authenticator;

public class AuthenticationDialog
{
	private static final String TAG = "AuthenticationDialog";
	private static Dialog dialog;
	
	/**
	 * Show authentication dialog.
	 * @param context the application context
	 */
	public static void show(final Context context)
	{		
		// Set the layout for the dialog
		dialog = new Dialog(context);
		dialog.setContentView(R.layout.authenticate);
		dialog.setTitle(R.string.login);

		// Set button
		Button bCancel = (Button) dialog.findViewById(R.id.bCancel);
		bCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		Button bOK = (Button) dialog.findViewById(R.id.bOK);
		bOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edUsername = (EditText) dialog.findViewById(R.id.edUsername);
				EditText edPassword = (EditText) dialog.findViewById(R.id.edPassword);
				
				String username = edUsername.getText().toString();
				String password = edPassword.getText().toString();
				
				boolean success = Authenticator.authenticate(context, username, password);
				Log.d(TAG, "Authentication: " + (success ? "success" : "fail"));
				dialog.dismiss();
			}
		});
		
		// Show dialog
		dialog.show();
	}
}
