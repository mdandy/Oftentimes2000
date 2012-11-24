package edu.gatech.oftentimes2000.data;

import java.util.GregorianCalendar;

public class Announcement 
{
	/**
	 * The ID of this announcement
	 */
	public int id = -1;
	
	/**
	 * The creator of this announcement
	 */
	public Profile creator;
	
	/**
	 * The title of this announcement
	 */
	public String title = "";
	
	/**
	 * Type of this announcement
	 */
	public AnnouncementType type = AnnouncementType.Unknown;
	
	/**
	 * This entry's highlights
	 */
	public String highlights = "";
	
	/**
	 * This entry's fine print
	 */
	public String finePrint = "";

	/**
	 * This entry's image URL
	 */
	public String imageUrl = "";
	
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
}
