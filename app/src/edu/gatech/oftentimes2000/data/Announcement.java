package edu.gatech.oftentimes2000.data;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Parcel;
import android.os.Parcelable;

public class Announcement implements Parcelable
{
	/**
	 * The ID of this announcement
	 */
	public int id;
	
	/**
	 * The creator of this announcement
	 */
	public Profile creator;
	
	/**
	 * The title of this announcement
	 */
	public String title;
	
	/**
	 * Type of this announcement
	 */
	public AnnouncementType type;
	
	/**
	 * This entry's highlights
	 */
	public String highlights;
	
	/**
	 * This entry's fine print
	 */
	public String finePrint;

	/**
	 * This entry's image URL
	 */
	public String imageUrl;
	
	/**
	 * The address information associated with this entry.
	 */
	public Address address;
	
	/**
	 * This entry's price information
	 */
	public Price price;
	
	/**
	 * The from date
	 */
	public GregorianCalendar fromDate;
	
	/**
	 * The to date
	 */
	public GregorianCalendar toDate;
	
	/**
	 * The promotional URL
	 */
	public String url = "";
	
	/**
	 * The category
	 */
	public String category = "";
	
	/**
	 * Constructor
	 */
	public Announcement()
	{
		this.id = -1;
		this.creator = new Profile();
		this.title = "";
		this.type = AnnouncementType.Unknown;
		this.highlights = "";
		this.finePrint = "";
		this.imageUrl = "";
		this.address = new Address();
		this.price = new Price();
		this.fromDate = new GregorianCalendar();
		this.toDate = new GregorianCalendar();
		this.url = "";
		this.category = "";
	}
	
	/**
	 * Parcelable constructor
	 * @param in the parcel
	 */
	public Announcement(Parcel in)
	{
		this.id = in.readInt();
		this.creator = in.readParcelable(Profile.class.getClassLoader());
		this.title = in.readString();
		this.type = AnnouncementType.values()[in.readInt()];
		this.highlights = in.readString();
		this.finePrint = in.readString();
		this.imageUrl = in.readString();
		this.address = in.readParcelable(Address.class.getClassLoader());
		this.price = in.readParcelable(Price.class.getClassLoader());
		
		int fromYear = in.readInt();
		int fromMonth = in.readInt();
		int fromDay = in.readInt();
		int fromHour = in.readInt();
		int fromMinute = in.readInt();
		int fromSecond = in.readInt();
		this.fromDate = new GregorianCalendar(fromYear, fromMonth, fromDay, fromHour, fromMinute, fromSecond);
		
		int toYear = in.readInt();
		int toMonth = in.readInt();
		int toDay = in.readInt();
		int toHour = in.readInt();
		int toMinute = in.readInt();
		int toSecond = in.readInt();
		this.toDate = new GregorianCalendar(toYear, toMonth, toDay, toHour, toMinute, toSecond);
		
		this.url = in.readString();
		this.category = in.readString();
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeInt(this.id);
		dest.writeParcelable(this.creator, 0);
		dest.writeString(this.title);
		dest.writeInt(this.type.ordinal());
		dest.writeString(this.highlights);
		dest.writeString(this.finePrint);
		dest.writeString(this.imageUrl);
		dest.writeParcelable(this.address, 0);
		dest.writeParcelable(this.price, 0);
		
		int fromYear = this.fromDate.get(Calendar.YEAR);
		int fromMonth = this.fromDate.get(Calendar.MONTH);
		int fromDay = this.fromDate.get(Calendar.DAY_OF_MONTH);
		int fromHour = this.fromDate.get(Calendar.HOUR_OF_DAY);
		int fromMinute = this.fromDate.get(Calendar.MINUTE);
		int fromSecond = this.fromDate.get(Calendar.SECOND);
		dest.writeInt(fromYear);
		dest.writeInt(fromMonth);
		dest.writeInt(fromDay);
		dest.writeInt(fromHour);
		dest.writeInt(fromMinute);
		dest.writeInt(fromSecond);
		
		int toYear = this.toDate.get(Calendar.YEAR);
		int toMonth = this.toDate.get(Calendar.MONTH);
		int toDay = this.toDate.get(Calendar.DAY_OF_MONTH);
		int toHour = this.toDate.get(Calendar.HOUR_OF_DAY);
		int toMinute = this.toDate.get(Calendar.MINUTE);
		int toSecond = this.toDate.get(Calendar.SECOND);
		dest.writeInt(toYear);
		dest.writeInt(toMonth);
		dest.writeInt(toDay);
		dest.writeInt(toHour);
		dest.writeInt(toMinute);
		dest.writeInt(toSecond);
		
		dest.writeString(this.url);
		dest.writeString(this.category);
	}
	
	public static final Parcelable.Creator<Announcement> CREATOR = new Parcelable.Creator<Announcement>()
	{
		public Announcement createFromParcel(Parcel in) 
		{
			return new Announcement(in);
		}

		public Announcement[] newArray(int size) 
		{
			return new Announcement[size];
		}
	};
}
