package edu.gatech.oftentimes2000.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Price implements Parcelable
{
	/**
	 * The original price
	 */
	public double originalPrice;
	
	/**
	 * The promotional price
	 */
	public double promotionalPrice;
	
	/**
	 * The discount in percentage
	 */
	public int discount;
	
	/**
	 * Constructor
	 */
	public Price()
	{
		this.originalPrice = -1.0;
		this.promotionalPrice = -1.0;
		this.discount = 0;
	}
	
	/**
	 * Parcelable constructor
	 * @param in the parcel
	 */
	public Price(Parcel in)
	{
		this.originalPrice = in.readDouble();
		this.promotionalPrice = in.readDouble();
		this.discount = in.readInt();
	}
	
	/**
	 * Calculate the discount
	 */
	public void calcDiscount()
	{
		this.discount = (int) ((this.originalPrice - this.promotionalPrice) / this.originalPrice * 100);
	}
	
	/**
	 * Get the original price.
	 * @return the original price
	 */
	public String getOriginalPrice()
	{
		return String.format("$%.2f", this.originalPrice);
	}
	
	/**
	 * Get the promotional price.
	 * @return the promotional price
	 */
	public String getPromotionalPrice()
	{
		return String.format("$%.2f", this.promotionalPrice);
	}
	
	/**
	 * Get the discount.
	 * @return the discount
	 */
	public String getDiscount()
	{
		return this.discount + "%";
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeDouble(this.originalPrice);
		dest.writeDouble(this.promotionalPrice);
		dest.writeInt(this.discount);
	}
	
	public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>()
	{
		public Price createFromParcel(Parcel in) 
		{
			return new Price(in);
		}

		public Price[] newArray(int size) 
		{
			return new Price[size];
		}
	};
}
