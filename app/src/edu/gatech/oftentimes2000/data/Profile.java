package edu.gatech.oftentimes2000.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable
{
	/**
	 * The user name
	 */
	public String username;
	
	/**
	 * The name
	 */
	public String name;
	
	/**
	 * The address
	 */
	public Address address;
	
	/**
	 * The website URL
	 */
	public String url;
	
	/**
	 * The email
	 */
	public String email;
	
	/**
	 * About user
	 */
	public String about;

	/**
	 * Constructor
	 */
	public Profile()
	{
		this.username = "";
		this.name = "";
		this.address = new Address();
		this.url = "";
		this.email = "";
		this.about = "";
	}
	
	/**
	 * Parcelable constructor
	 * @param in the parcel
	 */
	public Profile(Parcel in)
	{
		this.username = in.readString();
		this.name = in.readString();
		this.address = in.readParcelable(Address.class.getClassLoader());
		this.url = in.readString();
		this.email = in.readString();
		this.about = in.readString();
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(this.username);
		dest.writeString(this.name);
		dest.writeParcelable(this.address, 0);
		dest.writeString(this.url);
		dest.writeString(this.email);
		dest.writeString(this.about);
	}
	
	public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>()
	{
		public Profile createFromParcel(Parcel in) 
		{
			return new Profile(in);
		}

		public Profile[] newArray(int size) 
		{
			return new Profile[size];
		}
	};
}
