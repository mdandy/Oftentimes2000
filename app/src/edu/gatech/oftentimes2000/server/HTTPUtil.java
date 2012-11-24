package edu.gatech.oftentimes2000.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class HTTPUtil 
{
	private static final String TAG = "HTTPRequest";

	public static HttpResponse doGet(String url)
	{
		HttpResponse response = null;
		
		try 
		{
			HttpClient client = new DefaultHttpClient();  
			HttpGet get = new HttpGet(url);
			response = client.execute(get);  
		} 
		catch (Exception e) 
		{
			Log.e(TAG, e.getMessage());
		}
		
		return response;
	}

	/**
	 * Do POST request
	 * @param url the URL
	 * @param params the arguments
	 * @return HTTP response
	 */
	public static HttpResponse doPost(String url, List<NameValuePair> params) 
	{
		HttpResponse response = null;
		try 
		{
			HttpClient client = new DefaultHttpClient();  
			HttpPost post = new HttpPost(url);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			post.setEntity(ent);
			response = client.execute(post);  
		} 
		catch (Exception e) 
		{
			Log.e(TAG, e.getMessage());
		}

		return response;
	}

	/**
	 * Get the HTTP response code.
	 * @param response the HTTP response
	 * @return the HTTP response code
	 */
	public static int getResponseCode(HttpResponse response)
	{
		return response.getStatusLine().getStatusCode();
	}

	/**
	 * Get HTTP response as a text.
	 * @param response the HTTP response
	 * @return the text
	 */
	public static String getResponseAsText(HttpResponse response)
	{
		try 
		{
			HttpEntity entity = response.getEntity();  
			if (entity != null)
				return EntityUtils.toString(entity);
		} 
		catch (ParseException e) 
		{
			Log.e(TAG, e.getMessage());
		} 
		catch (IOException e) 
		{
			Log.e(TAG, e.getMessage());
		}

		return null;
	}

	/**
	 * Get HTTP response as a JSON.
	 * @param response the HTTP response
	 * @return the JSON
	 */
	public static JSONObject getResponseAsJSON(HttpResponse response)
	{
		JSONObject result = null;

		try 
		{
			InputStreamReader iReader = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
			BufferedReader reader = new BufferedReader(iReader);
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;)
				builder.append(line).append("\n");

			JSONTokener tokener = new JSONTokener(builder.toString());
			result = new JSONObject(tokener);
		} 
		catch (UnsupportedEncodingException e) 
		{
			Log.e(TAG, e.getMessage());
		} 
		catch (IllegalStateException e) 
		{
			Log.e(TAG, e.getMessage());
		} 
		catch (IOException e) 
		{
			Log.e(TAG, e.getMessage());
		} 
		catch (JSONException e) 
		{
			Log.e(TAG, e.getMessage());
		}

		return result;
	}
}
