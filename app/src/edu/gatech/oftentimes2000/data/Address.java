package edu.gatech.oftentimes2000.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.maps.GeoPoint;

public class Address implements Parcelable
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
	public String zipcode;
	
	/**
	 * The phone number
	 */
	public String phoneNumber;
	
	/**
	 * The radius
	 */
	public int radius;
	
	/**
	 * The geolocation
	 */
	public GeoPoint geopoint;
	
	/**
	 * Constructor
	 */
	public Address()
	{
		this.street = "";
		this.city = "";
		this.state = "";
		this.zipcode = "";
		this.phoneNumber = "";
		this.radius = 0;
		this.geopoint = new GeoPoint(0, 0);
	}
	
	/**
	 * Parcelable constructor
	 * @param in the parcel
	 */
	public Address (Parcel in)
	{
		this.street = in.readString();
		this.city = in.readString();
		this.state = in.readString();
		this.zipcode = in.readString();
		this.phoneNumber = in.readString();
		this.radius = in.readInt();
		
		int latitude = in.readInt();
		int logitude = in.readInt();
		this.geopoint = new GeoPoint(latitude, logitude);
	}
	
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
		output += this.radius + "\n";
		output += this.geopoint.toString();
		return output;
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(this.street);
		dest.writeString(this.city);
		dest.writeString(this.state);
		dest.writeString(this.zipcode);
		dest.writeString(this.phoneNumber);
		dest.writeInt(this.radius);
		dest.writeInt(this.geopoint.getLatitudeE6());
		dest.writeInt(this.geopoint.getLongitudeE6());
	}
	
	public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>()
	{
		public Address createFromParcel(Parcel in) 
		{
            return new Address(in);
        }

        public Address[] newArray(int size) 
        {
            return new Address[size];
        }
	};
}
