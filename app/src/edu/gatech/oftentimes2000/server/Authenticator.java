package edu.gatech.oftentimes2000.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;

public class Authenticator 
{
	private static final String PREFS_NAME = "Authenticator";

	/**
	 * Return true if the device is authenticated.
	 * @param context the application context
	 * @return true if the device is authenticated or false otherwise
	 */
	public static boolean isAuthenticated(Context context)
	{
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		return settings.contains("username");
	}

	/**
	 * Authenticate the device against the server. 
	 * @param context the application context
	 * @param username the username
	 * @param password the password
	 * @return true on success or false otherwise
	 */
	public static boolean authenticate(Context context, String username, String password)
	{
		// Hash password
		password = Crypto.sha256(password);

		// Do authentication against server
		String postURL = "http//www.domain.com";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		HttpResponse response = HTTPUtil.doPost(postURL, params);

		if (HTTPUtil.getResponseCode(response) == 200)
		{
			// Read the response body
			String body = HTTPUtil.getResponseAsText(response);
			if (body.equalsIgnoreCase("SUCESS"))
			{
				// Persist the value
				register(context, username);
				return true;
			}
		}
		return false;
	}

	/**
	 * Logout.
	 * @param context the application context
	 */
	public static void logout(Context context)
	{
		unregister(context);
	}

	/**
	 * Get the active username.
	 * @param context the application context
	 * @return the active username
	 */
	public static String getUser(Context context)
	{
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		return settings.getString("username", null);
	}

	/**
	 * Register the device username.
	 * @param context the application context
	 * @param username the username
	 */
	private static void register(Context context, String username)
	{
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("username", username);
		editor.commit();
	}

	/**
	 * Unregister the device username
	 * @param context the application context
	 */
	private static void unregister(Context context)
	{
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.remove("username");
		editor.commit();
	}
}
