package edu.gatech.oftentimes2000.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class Crypto 
{
	private static final String TAG = "Crypto";
	/**
	 * Convert bytes to Hex String
	 * @param bytes the bytes
	 * @return the hex string
	 */
	private static String bytesToHexString(byte[] bytes) 
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) 
		{
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1)
				sb.append('0');
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * Perform MD5 hash.
	 * @param message the message digest
	 * @return the MD5 hash of the message digest
	 */
	public static String sha256(String message)
	{
		MessageDigest digest=null;
		String hash = null;
		
		try 
		{
			digest = MessageDigest.getInstance("SHA-256");
			digest.update(message.getBytes());
			hash = bytesToHexString(digest.digest());
		} 
		catch (NoSuchAlgorithmException e) 
		{
			Log.e(TAG, e.getMessage());
		}

		return hash;
	}
}
