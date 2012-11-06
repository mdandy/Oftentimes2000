package edu.gatech.oftentimes2000.data;

import com.google.android.maps.GeoPoint;

public class Address 
{
	/**
	 * The street
	 */
	public String street;
	
	/**
	 * The city
	 */
	public String city;
	
	/**
	 * The state
	 */
	public String state;
	
	/**
	 * The zip code
	 */
	public int zipcode;
	
	/**
	 * The phone number
	 */
	public String phoneNumber;
	
	/**
	 * The URL
	 */
	public String url;
	
	/**
	 * The geolocation
	 */
	public GeoPoint geopoint;
	
	/**
	 * Return the street address.
	 * @return the street address
	 */
	public String getAddress()
	{
		String output = "";
		output += this.street + "\n";
		output += this.city + ", " + this.state + " " + this.zipcode;
		return output;
	}
	
	@Override
	public String toString() 
	{
		String output = "";
		output += this.street + "\n";
		output += this.city + ", " + this.state + " " + this.zipcode + "\n";
		output += this.phoneNumber + "\n";
		output += this.url;
		return output;
	}
}
